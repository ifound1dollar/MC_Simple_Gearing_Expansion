package net.dollar.testmod.entity.custom;

import net.dollar.testmod.item.ModItems;
import net.dollar.testmod.util.ModUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class ObsidianGolemEntity extends IronGolem {
    private int ticksSinceLastAttack = 0;
    private int teleportDelayTicks = 0;

    public ObsidianGolemEntity(EntityType<? extends IronGolem> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier setAttributes() {
        return IronGolem.createAttributes()
                .add(Attributes.MAX_HEALTH, 240)
                .add(Attributes.ATTACK_DAMAGE, 16f)     //Normal, Easy/Hard values are auto-scaled
                .add(Attributes.MOVEMENT_SPEED, 0.25f)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0f)
                .add(Attributes.FOLLOW_RANGE, 30f)
                .build();
    }

    public static boolean checkObsidianGolemSpawnRules(EntityType<ObsidianGolemEntity> entityType, LevelAccessor accessor,
                                                       MobSpawnType spawnType, BlockPos blockPos, RandomSource randomSource) {
        //only valid spawn very low in the world
        if (blockPos.getY() >= -16) {
            return false;
        }

        return checkMobSpawnRules(entityType, accessor, spawnType, blockPos, randomSource);
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

    @Override
    public boolean hurt(DamageSource source, float value) {
        //BECAUSE THIS DERIVES FROM IRON GOLEM, ZOMBIES AND SKELETONS WILL ATTACK IT
        if (source.getEntity() instanceof Skeleton || source.getEntity() instanceof Zombie) {
            return super.hurt(source, value * 0.25f);   //take 1/4 damage from attacking Skeletons and Zombies
        }

        if (ModUtils.getDamageCategory(source) == ModUtils.DamageCategory.SHARP) {
            value *= 0.67f;  //reduce Sharp damage by 33%
        }

        return super.hurt(source, value);
    }

    @Override
    public boolean doHurtTarget(Entity targetEntity) {
        //can only attack once every 2 seconds, then resets counter
        if (ticksSinceLastAttack < 40) {
            return false;
        }
        ticksSinceLastAttack = 0;

        //actual attack here
        float f = (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
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

    @Override
    protected InteractionResult mobInteract(Player p_28861_, InteractionHand p_28862_) {
        return InteractionResult.PASS;
    }
}
