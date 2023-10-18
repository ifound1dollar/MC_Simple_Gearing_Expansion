package net.dollar.simplegear.entity.custom;

import net.dollar.simplegear.enchantment.ModEnchantments;
import net.dollar.simplegear.item.ModItems;
import net.dollar.simplegear.util.ModUtils;
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

/**
 * Custom boss Monster entity, naturally spawning very low in the world.
 */
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



    /**
     * Register mob goals (AI).
     */
    @Override
    protected void registerGoals() {
        //NOTE: smaller numbers (first argument) imply higher priority

        //speedModifier, followingTargetEvenIfNotSeen
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0d, true));
        //speedModifier
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0d));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    /**
     * Returns the Mob's bounding box as AABB object. Can be adjusted from default using this method.
     * @return The Mob's bounding box
     */
    @Override
    protected AABB getAttackBoundingBox() {
        AABB temp = super.getAttackBoundingBox();
        temp.deflate(temp.getXsize() / 3d, temp.getYsize() / 3d,
                temp.getZsize() / 3d);

        return temp;
    }

    /**
     * Checks whether a spawn attempt is valid, specifically whether it is below a specific y-value.
     * @param entityType EntityType of ObsidianGolemEntity (this)
     * @param accessor Active LevelAccessor
     * @param spawnType Type of mob spawn (NATURAL)
     * @param blockPos Position of spawn attempt being queried
     * @param randomSource RandomSource instance
     * @return Whether the spawn attempt is valid
     */
    public static boolean checkObsidianGolemSpawnRules(EntityType<ObsidianGolemEntity> entityType, LevelAccessor accessor,
                                                       MobSpawnType spawnType, BlockPos blockPos, RandomSource randomSource) {
        //only valid spawn very low in the world
        if (blockPos.getY() >= -16) {
            return false;
        }

        return checkMobSpawnRules(entityType, accessor, spawnType, blockPos, randomSource);
    }



    /**
     * Set mob attributes, like MAX_HEALTH, FOLLOW_RANGE, etc.
     * @return Newly created AttributeSupplier
     */
    public static AttributeSupplier setAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 240)
                .add(Attributes.ATTACK_DAMAGE, 20f)     //Normal, Easy/Hard values are auto-scaled
                .add(Attributes.MOVEMENT_SPEED, 0.25f)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0f)
                .add(Attributes.FOLLOW_RANGE, 30f)
                .build();
    }

    /**
     * Decreases air supply while the mob is underwater (infinite).
     * @param value Original air supply value
     * @return New air supply value
     */
    @Override
    protected int decreaseAirSupply(int value) {
        return value;
    }

    /**
     * Performs per-tick AI operations. Updates attackAnimationTick and persistentAnger (neutral mob).
     */
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


    /**
     * Begins counting persistent anger, called when a LivingEntity attacks this Monster.
     */
    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }

    /**
     * Sets current remaining persistent anger time.
     * @param value New persistent anger time
     */
    public void setRemainingPersistentAngerTime(int value) {
        this.remainingPersistentAngerTime = value;
    }

    /**
     * Gets current remaining persistent anger time.
     * @return Current remaining persistent anger time.
     */
    public int getRemainingPersistentAngerTime() {
        return this.remainingPersistentAngerTime;
    }

    /**
     * Sets current persistent anger target via UUID.
     * @param targetUUID UUID of new persistent anger target
     */
    public void setPersistentAngerTarget(@javax.annotation.Nullable UUID targetUUID) {
        this.persistentAngerTarget = targetUUID;
    }

    /**
     * Gets current persistent target UUID.
     * @return Current persistent target UUID
     */
    @javax.annotation.Nullable
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }



    /**
     * Gets hurt sound produced by this Monster.
     * @param source DamageSource of damage being dealt
     * @return Hurt SoundEvent
     */
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.IRON_GOLEM_HURT;
    }

    /**
     * Gets death sound produced by this Monster.
     * @return Death SoundEvent
     */
    protected SoundEvent getDeathSound() {
        return SoundEvents.IRON_GOLEM_DEATH;
    }

    /**
     * Plays step sound of this Monster.
     * @param blockPos Position being stepped on
     * @param blockState Blockstate of block at position being stepped on
     */
    protected void playStepSound(BlockPos blockPos, BlockState blockState) {
        this.playSound(SoundEvents.IRON_GOLEM_STEP, 1.0F, 1.0F);
    }



    /**
     * Gets ATTACK_DAMAGE attribute assigned to this Monster.
     * @return ATTACK_DAMAGE attribute as float
     */
    private float getAttackDamage() {
        return (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
    }

    /**
     * Performs attack operations like checking for attack timer, dealing damage to target, and playing sound.
     * @param targetEntity Target Entity
     * @return Whether attack was performed successfully
     */
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
                //CHANCE TO APPLY EFFECT TO TARGET HERE, 67% chance on-hit
                if (this.random.nextInt(100) < 67) {
                    //APPLY ONE OF THESE TWO EFFECTS
                    if (this.random.nextBoolean()) {
                        //apply only level 1 slow, 15%/level (30% was a bit too much)
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 80, 0));
                    } else {
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 80));
                    }
                }

                //ALSO CHANCE TO SET TARGET ON FIRE BASED ON % MISSING HP + 10% (LOOSELY CORRESPONDS TO CRACKINESS)
                if (this.random.nextFloat() > (this.getHealth() / this.getMaxHealth()) - 0.1f) {
                    livingEntity.setSecondsOnFire(4);
                }
            }
        }

        this.playSound(SoundEvents.IRON_GOLEM_ATTACK, 1.0F, 1.0F);
        return flag;
    }

    /**
     * Performs default hurt operations like taking damage, playing hurt sound, etc. Here, reduces
     *  Sharp damage taken and updates visual crackiness.
     * @param source DamageSource of damage being dealt
     * @param value Original amount of damage
     * @return Whether hurt operation was completed successfully
     */
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

    /**
     * Gets Crackiness enum value based on percent current Health.
     * @return IronGolem.Crackiness value
     */
    public IronGolem.Crackiness getCrackiness() {
        return IronGolem.Crackiness.byFraction(this.getHealth() / this.getMaxHealth());
    }

    /**
     * Gets current attack animation tick.
     * @return Current attack animation tick
     */
    public int getAttackAnimationTick() {
        return this.attackAnimationTick;
    }



    /**
     * Performs any per-tick operations of this Entity. Here, checks if this Monster has not been
     *  able to attack for at least 3s. If it hasn't, rolls a chance each tick to blind and slow all
     *  nearby LivingEntities then teleport toward its target.
     */
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

    /**
     * Applies Blindness and Slowness effects to all nearby LivingEntities, then plays aggressive sound.
     */
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

    /**
     * Teleports this Monster either toward or directly on top of its target, depending on param value.
     * @param target Target to teleport toward / on top of
     * @param onTop Whether to teleport directly on top
     */
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
            //  much faster to prevent cheesing (ex. knock off of pillar).
            if (this.isWithinMeleeAttackRange(target)) {
                ticksSinceLastAttack = 30;
            }
        }
        teleportDelayTicks = 80;   //minimum of 4s delay between teleports
    }


    /**
     * Determines whether this Monster can be affected by a specific MobEffect.
     * @param effectInstance MobEffectInstance to check validity of
     * @return Whether the MobEffect can be applied to this Monster
     */
    @Override
    public boolean canBeAffected(MobEffectInstance effectInstance) {
        MobEffect mobeffect = effectInstance.getEffect();
        return mobeffect != MobEffects.POISON && mobeffect != MobEffects.WITHER && mobeffect != MobEffects.HUNGER;
    }

    /**
     * Drops custom loot from this Monster when slain by a player. Also checks certain conditions to
     *  determine whether this should drop a custom collector item.
     * @param source DamageSource of killing blow
     * @param lootingLevel Looting level of slaying weapon
     * @param killedByPlayer Whether this was killed by a player
     */
    @Override
    protected void dropCustomDeathLoot(DamageSource source, int lootingLevel, boolean killedByPlayer) {
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

    /**
     * Gets experience drop from this Monster on death.
     * @return Amount of experience reward
     */
    @Override
    public int getExperienceReward() {
        //WitherBoss drops 50xp on death
        return 50;
    }

    /**
     * Gets maximum distance that this Monster will voluntarily drop during pathfinding.
     * @return Maximum fall distance
     */
    @Override
    public int getMaxFallDistance() {
        return 10;  //can always fall 10 blocks with no concern
    }
}
