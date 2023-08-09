package net.dollar.testmod.entity.custom;

import javax.annotation.Nullable;

import net.dollar.testmod.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;

public class RoosterFromHellEntity extends Monster {
    private int ticksSinceLastAttack = 0;
    private int spawnDelayTicks = 100;
    private boolean isAwaitingSpawnDelay = true;

    public RoosterFromHellEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier setAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 160)
                .add(Attributes.ATTACK_DAMAGE, 17f)     //Normal, Easy/Hard values are auto-scaled
                .add(Attributes.FOLLOW_RANGE, 30f)
                .build();
    }

    @Override
    protected int decreaseAirSupply(int p_21303_) {
        return p_21303_;
    }



    protected SoundEvent getAmbientSound() {
        return SoundEvents.CHICKEN_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource p_28262_) {
        return SoundEvents.CHICKEN_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.CHICKEN_DEATH;
    }

    protected void playStepSound(BlockPos p_28254_, BlockState p_28255_) {
        this.playSound(SoundEvents.CHICKEN_STEP, 0.15F, 1.0F);
    }



    private float getAttackDamage() {
        return (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
    }

    @Override
    public boolean doHurtTarget(Entity targetEntity) {
        //can only attack once per second, then resets counter
        if (ticksSinceLastAttack < 20) {
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
                //SET TARGET ON FIRE FOR 5s
                livingEntity.setRemainingFireTicks(100);
            }
        }

        this.playSound(SoundEvents.BLAZE_HURT, 1.0F, 1.0F);
        return flag;
    }

    @Override
    public boolean hurt(DamageSource source, float value) {
//        if (ModUtils.getDamageCategory(source) == ModUtils.DamageCategory.SHARP) {
//            value *= 0.67f;  //reduce Sharp damage by 33%
//        }

        return super.hurt(source, value);
    }



    @Override
    public void tick() {
        //NOTE: Must check despawn before delay because otherwise reloading a single player world would
        //  cause the mob to wait the full 5s before despawning (constructor is called on reload).

        //if target is null OR current target is a player in creative mode, despawn
        if (!level().isClientSide) {
            if (getTarget() == null || (getTarget() instanceof Player player && player.isCreative())) {
                remove(RemovalReason.DISCARDED);
            }
        }

        if (isAwaitingSpawnDelay) {
            spawnDelayTicks--;
            if (spawnDelayTicks <= 0) {
                //if delay ticks now 0, reset boolean, remove invisibility, remove silence, and allow attacking
                isAwaitingSpawnDelay = false;
                endSpawnDelay();
            }
            return;
        }

        super.tick();
        ticksSinceLastAttack++;
    }

    private void endSpawnDelay() {
        this.setInvisible(false);
        this.setSilent(false);
        //speedModifier, followEvenIfNotSeen
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0d, true));

        this.level().addParticle(ParticleTypes.POOF,
                this.getX() + this.random.nextGaussian() * (double)0.13F,
                this.getBoundingBox().maxY + this.random.nextGaussian() * (double)0.13F,
                this.getZ() + this.random.nextGaussian() * (double)0.13F,
                0.0D, 0.0D, 0.0D);
    }

    @Override
    public void onRemovedFromWorld() {
        this.level().addParticle(ParticleTypes.POOF,
                this.getX() + this.random.nextGaussian() * (double)0.13F,
                this.getBoundingBox().maxY + this.random.nextGaussian() * (double)0.13F,
                this.getZ() + this.random.nextGaussian() * (double)0.13F,
                0.0D, 0.0D, 0.0D);
    }



    @Override
    public boolean shouldShowName() {
        return true;
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int p_21386_, boolean p_21387_) {
        if (!(this.getLastAttacker() instanceof Player)) {
            //only drop if last attacker was Player
            return;
        }

        //below is copied from how a Nether Star drops from WitherBoss
        ItemEntity itementity = this.spawnAtLocation(ModItems.INFUSED_GEMSTONE.get());
        if (itementity != null) {
            itementity.setExtendedLifetime();
        }

        //THIS IS THE METHOD TO DROP COLLECTOR ITEM WHEN ON FIRE WHEN KILLED
        if (this.wasOnFire) {
            this.spawnAtLocation(ModItems.COLLECTOR_KATHLEENS_LOST_DIADEM.get());
        }

//        //THIS IS THE METHOD TO DROP COLLECTOR ITEM WHEN NAMED 'James'
//        if (this.getCustomName() != null && this.getCustomName().getString().equals("Kevin")) {
//            this.spawnAtLocation(ModItems.COLLECTOR_KATHLEENS_LOST_DIADEM.get());
//        }

//        //THIS IS THE METHOD TO DROP COLLECTOR ITEM WHEN IN WATER
//        if (this.isInWaterOrBubble()) {
//            this.spawnAtLocation(ModItems.COLLECTOR_KATHLEENS_LOST_DIADEM.get());
//        }

//        //THIS IS THE METHOD TO DROP COLLECTOR ITEM WHEN IN SAME VEHICLE
//        if (this.getLastAttacker() != null &&
//                this.getLastAttacker().getVehicle() == this.getVehicle()) {
//            this.spawnAtLocation(ModItems.COLLECTOR_KATHLEENS_LOST_DIADEM.get());
//        }

//        //THIS IS THE METHOD TO DROP COLLECTOR ITEM WHEN SPIDER KILLED WITH BANE OF ARTHROPODS
//        if (getLastAttacker().getItemBySlot(EquipmentSlot.MAINHAND).getItem().getEnchantmentLevel(
//                getLastAttacker().getItemBySlot(EquipmentSlot.MAINHAND),
//                Enchantments.BANE_OF_ARTHROPODS) > 0) {
//            this.spawnAtLocation(ModItems.COLLECTOR_KATHLEENS_LOST_DIADEM.get());
//        }

//        //THIS IS THE METHOD TO DROP COLLECTOR ITEM WHEN SHOT EXACTLY 21 ARROWS
//        if (arrowCounter == 3) {
//            this.spawnAtLocation(ModItems.COLLECTOR_KATHLEENS_LOST_DIADEM.get());
//            //NOTE: must implement RangedAttackMob and copy/paste vanilla AbstractSkeleton's attack method
//        }
    }



    public int getExperienceReward() {
        return 50;
    }

    @Override
    public int getMaxFallDistance() {
        return 10;  //can always fall 10 blocks with no concern
    }
}