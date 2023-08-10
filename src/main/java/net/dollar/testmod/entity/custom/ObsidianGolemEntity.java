package net.dollar.testmod.entity.custom;

import net.dollar.testmod.enchantment.ModEnchantments;
import net.dollar.testmod.item.ModItems;
import net.dollar.testmod.util.ModUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class ObsidianGolemEntity extends Monster implements NeutralMob {
    private int attackAnimationTick;
    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
    private int remainingPersistentAngerTime;
    @Nullable
    private UUID persistentAngerTarget;

    private int ticksSinceLastAttack = 0;
    private int teleportDelayTicks = 0;

    public ObsidianGolemEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }


    @Override
    protected void registerGoals() {
        //NOTE: smaller numbers (first argument) imply higher priority

        //speedModifier, followingTargetEvenIfNotSeen
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0d, true) {
            @Override
            protected double getAttackReachSqr(LivingEntity p_25556_) {
                return super.getAttackReachSqr(p_25556_) / 1.5; //cut attack range down
            }
        });
        //speedModifier
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0d));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    public static boolean checkObsidianGolemSpawnRules(EntityType<ObsidianGolemEntity> entityType, LevelAccessor accessor,
                                                       MobSpawnType spawnType, BlockPos blockPos, RandomSource randomSource) {
        //only valid spawn very low in the world
        if (blockPos.getY() >= -16) {
            return false;
        }

        return checkMobSpawnRules(entityType, accessor, spawnType, blockPos, randomSource);
    }



    public static AttributeSupplier setAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 240)
                .add(Attributes.ATTACK_DAMAGE, 20f)     //Normal, Easy/Hard values are auto-scaled
                .add(Attributes.MOVEMENT_SPEED, 0.25f)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0f)
                .add(Attributes.FOLLOW_RANGE, 30f)
                .build();
    }

    @Override
    protected int decreaseAirSupply(int p_21303_) {
        return p_21303_;
    }

    public void aiStep() {
        super.aiStep();
        if (this.attackAnimationTick > 0) {
            --this.attackAnimationTick;
        }

        if (!this.level().isClientSide) {
            this.updatePersistentAnger((ServerLevel)this.level(), true);
        }
    }

    public void addAdditionalSaveData(CompoundTag p_28867_) {
        super.addAdditionalSaveData(p_28867_);
        this.addPersistentAngerSaveData(p_28867_);
    }

    public void readAdditionalSaveData(CompoundTag p_28857_) {
        super.readAdditionalSaveData(p_28857_);
        this.readPersistentAngerSaveData(this.level(), p_28857_);
    }



    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }

    public void setRemainingPersistentAngerTime(int p_28859_) {
        this.remainingPersistentAngerTime = p_28859_;
    }

    public int getRemainingPersistentAngerTime() {
        return this.remainingPersistentAngerTime;
    }

    public void setPersistentAngerTarget(@javax.annotation.Nullable UUID p_28855_) {
        this.persistentAngerTarget = p_28855_;
    }

    @javax.annotation.Nullable
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }



    private float getAttackDamage() {
        return (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
    }

    @Override
    public boolean doHurtTarget(Entity targetEntity) {
        //can only attack once every 2 seconds, then resets counter
        if (ticksSinceLastAttack < 40) {
            return false;
        }
        ticksSinceLastAttack = 0;

        //actual attack here
        float f = this.getAttackDamage();
        float f1 = (int)f > 0 ? f / 2.0F + (float)this.random.nextInt((int)f) : f;
        boolean flag = targetEntity.hurt(this.damageSources().mobAttack(this), f1);
        if (flag) {
            this.doEnchantDamageEffects(this, targetEntity);

            if (targetEntity instanceof LivingEntity livingEntity) {
                //CHANCE TO APPLY EFFECT TO TARGET HERE, 50% chance on-hit
                if (this.random.nextInt(100) < 50) {
                    //APPLY ONE OF THESE TWO EFFECTS
                    if (this.random.nextBoolean()) {
                        //apply only level 1 slow, 15%/level (30% was a bit too much)
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 0));
                    } else {
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 60));
                    }
                }

                //ALSO CHANCE TO SET TARGET ON FIRE BASED ON % MISSING HP + 10% (LOOSELY CORRESPONDS TO CRACKINESS)
                if (this.random.nextFloat() > (this.getHealth() / this.getMaxHealth()) - 0.1f) {
                    livingEntity.setSecondsOnFire(3);
                }
            }
        }

        this.playSound(SoundEvents.IRON_GOLEM_ATTACK, 1.0F, 1.0F);
        return flag;
    }

    @Override
    public boolean hurt(DamageSource source, float value) {
        if (ModUtils.getDamageCategory(source) == ModUtils.DamageCategory.SHARP) {
            value *= 0.67f;  //reduce Sharp damage by 33%
        }

        IronGolem.Crackiness irongolem$crackiness = this.getCrackiness();
        boolean flag = super.hurt(source, value);
        if (flag && this.getCrackiness() != irongolem$crackiness) {
            this.playSound(SoundEvents.IRON_GOLEM_DAMAGE, 1.0F, 1.0F);
        }

        return flag;
    }

    public IronGolem.Crackiness getCrackiness() {
        return IronGolem.Crackiness.byFraction(this.getHealth() / this.getMaxHealth());
    }

    public int getAttackAnimationTick() {
        return this.attackAnimationTick;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.IRON_GOLEM_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.IRON_GOLEM_DEATH;
    }

    protected void playStepSound(BlockPos p_28864_, BlockState p_28865_) {
        this.playSound(SoundEvents.IRON_GOLEM_STEP, 1.0F, 1.0F);
    }


    @Override
    public void tick() {
        super.tick();

        if (this.getTarget() == null) {
            ticksSinceLastAttack = 0;
            return;
        }

        ticksSinceLastAttack++;
        teleportDelayTicks--;
        //if this hasn't attacked in >3 seconds, roll 1% chance per tick to afflict nearby players and teleport to target
        if (ticksSinceLastAttack > 60 && teleportDelayTicks <= 0 && this.random.nextInt(100) < 1) {
            blindAndSlowNearbyLivingEntities();

            //IF this hasn't been able to attack in 12s (240 ticks), teleport directly on top of the
            //  target, ELSE teleport to near the target
            teleportTowardTarget(this.getTarget(), ticksSinceLastAttack > 240);
        }
    }

    private void blindAndSlowNearbyLivingEntities() {
        //blind and slow for 3s all players within 25 blocks
        double x = this.position().x;
        double y = this.position().y;
        double z = this.position().z;

        List<Entity> entities = this.level().getEntities(this,
                new AABB(x - 30, y - 30, z - 30,
                        x + 30, y + 30, z + 30));

        this.playSound(SoundEvents.RAVAGER_ROAR, 1.0F, 1.0F);   //volume, pitch???
        for (Entity entity : entities) {
            if (entity instanceof LivingEntity livingEntity) {
                //blind and slow ALL nearby LivingEntities, regardless of whether angry at
                livingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 60));
                livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 1));
            }
        }
    }

    private void teleportTowardTarget(LivingEntity target, boolean onTop) {
        if (onTop) {
            //teleport directly on top of target, +- 0.5 blocks
            teleportTo((this.random.nextDouble() - 0.5D) + target.getX(),
                    target.getY() + 0.5D,
                    (this.random.nextDouble() - 0.5D) + target.getZ());
        } else {
            //teleport to within 5 blocks of the target
            randomTeleport((this.random.nextDouble() - 0.5D) + target.getX() + (this.random.nextInt(10) - 5),
                    target.getY() + 2,
                    (this.random.nextDouble() - 0.5D) + target.getZ() + (this.random.nextInt(10) - 5),
                    false);

            //if now in attack range, should delay 0.5s before allowing attack
            //IMPORTANT: Must happen only here BECAUSE: when teleporting directly onto target, must attack
            //  immediately to prevent cheesing (ex. knock off of pillar).
            if (this.isWithinMeleeAttackRange(target)) {
                ticksSinceLastAttack = 30;
            }
        }
        teleportDelayTicks = 80;   //minimum of 4s delay between teleports
    }



    @Override
    public boolean canBeAffected(MobEffectInstance effectInstance) {
        MobEffect mobeffect = effectInstance.getEffect();
        return mobeffect != MobEffects.POISON && mobeffect != MobEffects.WITHER && mobeffect != MobEffects.HUNGER;
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource p_21385_, int p_21386_, boolean p_21387_) {
        if (!(this.getLastAttacker() instanceof Player)) {
            //only drop if last attacker was Player
            return;
        }

        //below is copied from how a Nether Star drops from WitherBoss
        ItemEntity itementity = this.spawnAtLocation(ModItems.MOLTEN_CORE.get());
        if (itementity != null) {
            itementity.setExtendedLifetime();
        }

        //if killer player is holding Tungsten-Carbide Mace with Hardness V, drop collector item
        ItemStack heldItem = getLastAttacker().getItemBySlot(EquipmentSlot.MAINHAND);
        if (heldItem.getItem() == ModItems.TUNGSTEN_CARBIDE_MACE.get() &&
                heldItem.getEnchantmentLevel(ModEnchantments.HARDNESS.get()) >= 5)
        {
            this.spawnAtLocation(ModItems.COLLECTOR_OBSIDIAN_DUST.get());
        }
    }

    @Override
    public int getExperienceReward() {
        //WitherBoss drops 50xp on death
        return 50;
    }

    @Override
    public int getMaxFallDistance() {
        return 10;  //can always fall 10 blocks with no concern
    }
}
