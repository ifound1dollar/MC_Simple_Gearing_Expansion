package net.dollar.testmod.entity.custom;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageTypes;
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
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class ObsidianGolemEntity extends IronGolem implements RangedAttackMob {
    private boolean cannotReachTarget = false;
    private int reachTargetCounter = -1;

    public ObsidianGolemEntity(EntityType<? extends IronGolem> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier setAttributes() {
        return IronGolem.createAttributes()
                .add(Attributes.MAX_HEALTH, 100)
                .add(Attributes.ATTACK_DAMAGE, 0.1f)    //NOT default
                .add(Attributes.ATTACK_SPEED, 0.5f)
                .add(Attributes.MOVEMENT_SPEED, 0.25f)
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


        //this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    @Override
    public void onPathfindingDone() {
        //HERE SHOULD SET VARIABLE
        if (this.getTarget() != null && !isWithinMeleeAttackRange(this.getTarget())) {
            //if valid target but not within range AND pathfinding is done, must not be able to reach target
            cannotReachTarget = true;
        } else {
            //else either there is no target OR is in range, so reset both
            cannotReachTarget = false;
            reachTargetCounter = 0;
        }
    }

    @Override
    public boolean doHurtTarget(Entity targetEntity) {
        //IF ATTACKED, RESET cannotReachTarget
        cannotReachTarget = false;
        reachTargetCounter = 0;
        return super.doHurtTarget(targetEntity);
    }

    @Override
    public void tick() {
        super.tick();

        //pause ticking up while pathfinding, but do not reset
        if (isPathFinding()) { return; }

        if (cannotReachTarget) {
            reachTargetCounter++;
            if (reachTargetCounter >= 100) {
                //APPLY EFFECT TO NEARBY PLAYERS HERE
                blindNearbyPlayers();
                reachTargetCounter = 0;
            }
        }
    }

    private void blindNearbyPlayers() {
        double x = this.position().x;
        double y = this.position().y;
        double z = this.position().z;

        List<Entity> entities = this.level().getEntities(this,
                new AABB(x - 10, y - 10, z - 10,
                        x + 10, y + 10, z + 10));

        this.playSound(SoundEvents.RAVAGER_ROAR, 1.0F, 1.0F);   //volume, pitch???
        for (Entity entity : entities) {
            if (entity instanceof Player player) {
                player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 60));
                player.hurt(damageSources().magic(), ((float)this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
                //probably drag the player close to the mob here???
            }
        }
    }

    @Override
    public void performRangedAttack(LivingEntity targetEntity, float p_33318_) {
        Snowball snowball = new Snowball(this.level(), this);
        double d0 = targetEntity.getEyeY() - (double)1.1F;
        double d1 = targetEntity.getX() - this.getX();
        double d2 = d0 - snowball.getY();
        double d3 = targetEntity.getZ() - this.getZ();
        double d4 = Math.sqrt(d1 * d1 + d3 * d3) * (double)0.2F;
        snowball.shoot(d1, d2 + d4, d3, 1.6F, 12.0F);
        this.playSound(SoundEvents.GHAST_SHOOT, 1.0F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level().addFreshEntity(snowball);
    }
}
