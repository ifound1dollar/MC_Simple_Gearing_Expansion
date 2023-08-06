package net.dollar.testmod.entity.custom;

import net.dollar.testmod.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class KathleenTheWickedEntity extends Monster {
    private int ticksSinceLastAttack = 0;
    private int spawnDelayTicks = 100;
    private boolean isAwaitingSpawnDelay = true;


    public KathleenTheWickedEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);

        //make invisible and set silent TEMPORARILY, is removed/reset in tick function after delay
        this.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 100));
        setSilent(true);
    }


    @Override
    protected void registerGoals() {
        //NOTE: smaller numbers (first argument) imply higher priority

        this.goalSelector.addGoal(1, new FloatGoal(this));
        //speedModifier, followingTargetEvenIfNotSeen
        //this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0d, true));
        //speedModifier
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0d));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
    }



    public static AttributeSupplier setAttributes() {
        return IronGolem.createAttributes()
                .add(Attributes.MAX_HEALTH, 160)
                .add(Attributes.ATTACK_DAMAGE, 15f)     //Normal, Easy/Hard values are auto-scaled
                .add(Attributes.FOLLOW_RANGE, 30f)
                .build();
    }

    @Override
    protected int decreaseAirSupply(int p_21303_) {
        return p_21303_;
    }

    public void aiStep() {
        super.aiStep();
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
                //WITHER TARGET FOR 3s
                livingEntity.addEffect(new MobEffectInstance(MobEffects.WITHER, 60, 1));
            }
        }

        this.playSound(SoundEvents.MOOSHROOM_MILK_SUSPICIOUSLY, 1.0F, 1.0F);
        return flag;
    }

    @Override
    public boolean hurt(DamageSource source, float value) {
//        if (ModUtils.getDamageCategory(source) == ModUtils.DamageCategory.SHARP) {
//            value *= 0.67f;  //reduce Sharp damage by 33%
//        }

        return super.hurt(source, value);
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.COW_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.COW_DEATH;
    }

    protected void playStepSound(BlockPos p_28864_, BlockState p_28865_) {
        this.playSound(SoundEvents.COW_STEP, 1.0F, 1.0F);
    }



    @Override
    public void tick() {
        super.tick();   //NOTE: WILL MOVE AND STARE AT THE PLAYER EVEN DURING DELAY, BUT IS INVISIBLE AND SILENT

        //NOTE: Must check despawn before delay because otherwise reloading a single player world would
        //  cause the mob to wait the full 5s before despawning (constructor is called on reload).
        if (!level().isClientSide && getTarget() == null) {
            remove(RemovalReason.DISCARDED);
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

        ticksSinceLastAttack++;
    }

    private void endSpawnDelay() {
        removeEffect(MobEffects.INVISIBILITY);
        setSilent(false);
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0d, true));
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

//        //THIS IS THE METHOD TO DROP COLLECTOR ITEM WHEN THESE THREE EFFECTS ACTIVE
//        if (this.hasEffect(MobEffects.REGENERATION) && this.hasEffect(MobEffects.MOVEMENT_SPEED) &&
//                this.hasEffect(MobEffects.DAMAGE_BOOST)) {
//            this.spawnAtLocation(ModItems.COLLECTOR_KATHLEENS_LOST_DIADEM.get());
//        }

//        //THIS IS THE METHOD TO DROP COLLECTOR ITEM WHEN ON FIRE WHEN KILLED
//        if (this.wasOnFire) {
//            this.spawnAtLocation(ModItems.COLLECTOR_KATHLEENS_LOST_DIADEM.get());
//        }

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

        //if killer player is holding Golden Hoe or Gilded Bronze Hoe, drop collector item
        if (getLastAttacker().getItemBySlot(EquipmentSlot.MAINHAND).getItem() == Items.GOLDEN_HOE ||
                getLastAttacker().getItemBySlot(EquipmentSlot.MAINHAND).getItem() == ModItems.GILDED_BRONZE_HOE.get()) {
            this.spawnAtLocation(ModItems.COLLECTOR_KATHLEENS_LOST_DIADEM.get());
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
