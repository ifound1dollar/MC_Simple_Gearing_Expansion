package net.dollar.testmod.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class ObsidianGolemEntity extends IronGolem {
    private int ticksSinceLastAttack = 0;
    private int teleportDelayTicks = 100;

    public ObsidianGolemEntity(EntityType<? extends IronGolem> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier setAttributes() {
        return IronGolem.createAttributes()
                .add(Attributes.MAX_HEALTH, 200)
                .add(Attributes.ATTACK_DAMAGE, 0.1f)    //NOT default
                .add(Attributes.MOVEMENT_SPEED, 0.25f)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0f)
                .add(Attributes.FOLLOW_RANGE, 20.0f)
                .build();
    }

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

    @Override
    public boolean doHurtTarget(Entity targetEntity) {
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

            //CHANCE TO APPLY EFFECT TO TARGET HERE, 33% chance on-hit
            if (this.random.nextInt(100) < 33) {
                if (targetEntity instanceof LivingEntity livingEntity) {
                    //APPLY ONE OF THESE TWO EFFECTS
                    if (this.random.nextBoolean()) {
                        //level 2 slow (third argument) is 30%, is 15%/level
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 1));
                    } else {
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 60));
                    }
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
            teleportDelayTicks = 100;
            return;
        }

        ticksSinceLastAttack++;
        teleportDelayTicks--;
        //if this hasn't attacked in >5 seconds, roll 2% chance per tick to afflict nearby players and teleport to target
        if (ticksSinceLastAttack > 100 && teleportDelayTicks <= 0 && this.random.nextInt(100) < 2) {
            blindAndSlowNearbyPlayers();

            if (ticksSinceLastAttack > 240) {
                //if this hasn't been able to attack in 12 seconds, teleport directly on top of the target
                teleportTo((this.random.nextDouble() - 0.5D) + this.getTarget().getX(),
                        this.getTarget().getY() + 2,
                        (this.random.nextDouble() - 0.5D) + this.getTarget().getZ());
            } else {
                //else teleport to near the target but not quite as close
                //FIX THIS TO GUARANTEE TELEPORT TO RANGE OF 3-4 BLOCKS AWAY
                randomTeleport((this.random.nextDouble() - 0.5D) + this.getTarget().getX() + (this.random.nextInt(8) - 4),
                        this.getTarget().getY() + 2,
                        (this.random.nextDouble() - 0.5D) + this.getTarget().getZ() + (this.random.nextInt(8) - 4),
                        false);

                //if now in attack range, should delay 1 second before allowing attack
                //IMPORTANT: Must happen only here BECAUSE: when teleporting directly onto target, must attack
                //  immediately to prevent cheesing (ex. knock off of pillar).
                if (this.isWithinMeleeAttackRange(this.getTarget())) {
                    ticksSinceLastAttack = 20;
                }
            }
            teleportDelayTicks = 100;
        }
    }

    private void blindAndSlowNearbyPlayers() {
        double x = this.position().x;
        double y = this.position().y;
        double z = this.position().z;

        List<Entity> entities = this.level().getEntities(this,
                new AABB(x - 20, y - 20, z - 20,
                        x + 20, y + 20, z + 20));

        this.playSound(SoundEvents.RAVAGER_ROAR, 1.0F, 1.0F);   //volume, pitch???
        for (Entity entity : entities) {
            if (entity instanceof Player player) {
                player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 60));
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 1));
            }
        }
    }



    @Override
    public int getMaxFallDistance() {
        return 10;  //can always fall 10 blocks with no concern
    }

    @Override
    protected InteractionResult mobInteract(Player p_28861_, InteractionHand p_28862_) {
        return InteractionResult.PASS;
    }

    @Override
    public Crackiness getCrackiness() {
        return Crackiness.NONE;
    }
}
