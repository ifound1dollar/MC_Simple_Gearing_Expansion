package net.dollar.simplegear.entity.custom;

import net.dollar.simplegear.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableWitchTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestHealableRaiderTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

/**
 * Custom boss Monster entity, spawned manually via Compound Gemstone interaction with a Spectral Lantern.
 */
public class OldLadyMuffEntity extends Monster implements RangedAttackMob {
    private static final UUID SPEED_MODIFIER_DRINKING_UUID = UUID.fromString("5CD17E52-A79A-43D3-A529-90FDE04B181E");
    private static final AttributeModifier SPEED_MODIFIER_DRINKING = new AttributeModifier(SPEED_MODIFIER_DRINKING_UUID, "Drinking speed penalty", -0.25D, AttributeModifier.Operation.ADDITION);
    private static final EntityDataAccessor<Boolean> DATA_USING_ITEM = SynchedEntityData.defineId(OldLadyMuffEntity.class, EntityDataSerializers.BOOLEAN);
    private int usingTime;

    private int spawnDelayTicks = 100;
    private boolean isAwaitingSpawnDelay = true;
    private int autoDespawnDelayTicks = 300;    //15 sec


    public OldLadyMuffEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }



    /**
     * Register mob goals (AI).
     */
    @Override
    protected void registerGoals() {
        //NOTE: smaller numbers (first argument) imply higher priority

        this.goalSelector.addGoal(1, new FloatGoal(this));
        //speedModifier
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0d));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
    }

    /**
     * Set mob attributes, like MAX_HEALTH, FOLLOW_RANGE, etc.
     * @return Newly created AttributeSupplier
     */
    public static AttributeSupplier setAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 120)
                .add(Attributes.MOVEMENT_SPEED, 0.25f)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.9f)
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

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(DATA_USING_ITEM, false);
    }

    /**
     * Gets ambient sound produced by this Monster.
     * @return Ambient SoundEvent
     */
    protected SoundEvent getAmbientSound() {
        return SoundEvents.WITCH_AMBIENT;
    }

    /**
     * Gets hurt sound produced by this Monster.
     * @param source DamageSource of damage being dealt
     * @return Hurt SoundEvent
     */
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.WITCH_HURT;
    }

    /**
     * Gets death sound produced by this Monster.
     * @return Death SoundEvent
     */
    protected SoundEvent getDeathSound() {
        return SoundEvents.WITCH_DEATH;
    }



    /**
     * Set whether this Monster is currently using an item.
     * @param isUsing Value to set
     */
    public void setUsingItem(boolean isUsing) {
        this.getEntityData().set(DATA_USING_ITEM, isUsing);
    }

    /**
     * Get whether this Monster is currently drinking a potion.
     * @return Whether this Monster is drinking a potion
     */
    public boolean isDrinkingPotion() {
        return this.getEntityData().get(DATA_USING_ITEM);
    }

    /**
     * Performs per-tick AI operations. Checks whether this Monster should begin drinking a potion.
     */
    public void aiStep() {
        if (!this.level().isClientSide && this.isAlive()) {
            if (this.isDrinkingPotion()) {
                if (this.usingTime-- <= 0) {
                    this.setUsingItem(false);
                    ItemStack itemstack = this.getMainHandItem();
                    this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
                    if (itemstack.is(Items.POTION)) {
                        List<MobEffectInstance> list = PotionUtils.getMobEffects(itemstack);
                        if (list != null) {
                            for(MobEffectInstance mobeffectinstance : list) {
                                this.addEffect(new MobEffectInstance(mobeffectinstance));
                            }
                        }
                    }

                    this.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(SPEED_MODIFIER_DRINKING);
                }
            } else {
                Potion potion = null;
                if (this.random.nextFloat() < 0.15F && (this.isOnFire() || this.getLastDamageSource() != null && this.getLastDamageSource().is(DamageTypeTags.IS_FIRE)) && !this.hasEffect(MobEffects.FIRE_RESISTANCE)) {
                    potion = Potions.FIRE_RESISTANCE;
                } else if (this.random.nextFloat() < 0.05F && this.getHealth() < this.getMaxHealth()) {
                    potion = Potions.HEALING;
                } else if (this.random.nextFloat() < 0.5F && this.getTarget() != null && !this.hasEffect(MobEffects.MOVEMENT_SPEED) && this.getTarget().distanceToSqr(this) > 121.0D) {
                    potion = Potions.SWIFTNESS;
                }

                if (potion != null) {
                    this.setItemSlot(EquipmentSlot.MAINHAND, PotionUtils.setPotion(new ItemStack(Items.POTION), potion));
                    this.usingTime = this.getMainHandItem().getUseDuration();
                    this.setUsingItem(true);
                    if (!this.isSilent()) {
                        this.level().playSound((Player)null, this.getX(), this.getY(), this.getZ(), SoundEvents.WITCH_DRINK, this.getSoundSource(), 1.0F, 0.8F + this.random.nextFloat() * 0.4F);
                    }

                    AttributeInstance attributeinstance = this.getAttribute(Attributes.MOVEMENT_SPEED);
                    attributeinstance.removeModifier(SPEED_MODIFIER_DRINKING);
                    attributeinstance.addTransientModifier(SPEED_MODIFIER_DRINKING);
                }
            }

            if (this.random.nextFloat() < 7.5E-4F) {
                this.level().broadcastEntityEvent(this, (byte)15);
            }
        }

        super.aiStep();
    }

    /**
     * Spawns witch particles around this Monster's head.
     * @param p_34138_ Unknown
     */
    public void handleEntityEvent(byte p_34138_) {
        if (p_34138_ == 15) {
            for(int i = 0; i < this.random.nextInt(35) + 10; ++i) {
                this.level().addParticle(ParticleTypes.WITCH, this.getX() + this.random.nextGaussian() * (double)0.13F, this.getBoundingBox().maxY + 0.5D + this.random.nextGaussian() * (double)0.13F, this.getZ() + this.random.nextGaussian() * (double)0.13F, 0.0D, 0.0D, 0.0D);
            }
        } else {
            super.handleEntityEvent(p_34138_);
        }
    }

    /**
     * Intercepts damage taken by this Monster to reduce damage taken from WITCH_RESISTANT_TO damage type tag.
     * @param source DamageSource of damage being audited
     * @param damageValue Original damage value
     * @return New damage value
     */
    protected float getDamageAfterMagicAbsorb(DamageSource source, float damageValue) {
        damageValue = super.getDamageAfterMagicAbsorb(source, damageValue);
        if (source.getEntity() == this) {
            damageValue = 0.0F;
        }

        if (source.is(DamageTypeTags.WITCH_RESISTANT_TO)) {
            damageValue *= 0.15F;
        }

        return damageValue;
    }

    /**
     * Performed ranged attack on the current target. Here, throws various harmful potions.
     * @param livingEntity Target LivingEntity
     * @param p_34144_ Unknown
     */
    public void performRangedAttack(LivingEntity livingEntity, float p_34144_) {
        //by default, can only attack when not drinking; Old Lady Muff can do both at the same time
        Vec3 vec3 = livingEntity.getDeltaMovement();
        double d0 = livingEntity.getX() + vec3.x - this.getX();
        double d1 = livingEntity.getEyeY() - (double)1.1F - this.getY();
        double d2 = livingEntity.getZ() + vec3.z - this.getZ();
        double d3 = Math.sqrt(d0 * d0 + d2 * d2);
        Potion potion = Potions.STRONG_HARMING;
        if (d3 >= 8.0D && !livingEntity.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) {
            potion = Potions.STRONG_SLOWNESS;
        } else if (livingEntity.getHealth() >= 8.0F && !livingEntity.hasEffect(MobEffects.POISON)) {
            potion = Potions.STRONG_POISON;
        } else if (d3 <= 3.0D && !livingEntity.hasEffect(MobEffects.WEAKNESS) && this.random.nextFloat() < 0.25F) {
            potion = Potions.WEAKNESS;
        }

        ThrownPotion thrownpotion = new ThrownPotion(this.level(), this);
        thrownpotion.setItem(PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), potion));
        thrownpotion.setXRot(thrownpotion.getXRot() - -20.0F);
        thrownpotion.shoot(d0, d1 + d3 * 0.2D, d2, 0.75F, 16.0f);
        if (!this.isSilent()) {
            this.level().playSound((Player)null, this.getX(), this.getY(), this.getZ(), SoundEvents.WITCH_THROW, this.getSoundSource(), 1.0F, 0.8F + this.random.nextFloat() * 0.4F);
        }

        this.level().addFreshEntity(thrownpotion);

    }

    /**
     * Get y height of this Monster's eyes while standing.
     * @param pose Current pose
     * @param dimensions This Monster Entity's dimensions
     * @return Y height of eyes
     */
    protected float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
        return 1.62F;
    }

    /**
     * Performs default hurt operations like taking damage, playing hurt sound, etc.
     * @param source DamageSource of damage being dealt
     * @param value Original amount of damage
     * @return Whether hurt operation was completed successfully
     */
    @Override
    public boolean hurt(DamageSource source, float value) {
//        if (ModUtils.getDamageCategory(source) == ModUtils.DamageCategory.SHARP) {
//            value *= 0.67f;  //reduce Sharp damage by 33%
//        }

        return super.hurt(source, value);
    }



    /**
     * Performs any per-tick operations of this Entity. Here, checks that this has a valid target, despawning
     *  if not. Ticks down initial 5s spawn delay and, when over, calls endSpawnDelay().
     */
    @Override
    public void tick() {
        //handle despawn conditions
        if (!level().isClientSide) {
            //if target is null, tick down auto-despawn delay
            if (getTarget() == null) {
                autoDespawnDelayTicks--;

                //if delay now <= 0, despawn
                if (autoDespawnDelayTicks <= 0) {
                    remove(RemovalReason.DISCARDED);
                }
            }
        }

        //handle initial spawn delay (applies only when spawning from Spectral Lantern)
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
    }

    /**
     * Ends initial 5s spawn delay: removes invisibility and silence, sets AttackGoal, and spawns particle.
     */
    private void endSpawnDelay() {
        this.setInvisible(false);
        this.setSilent(false);
        //speedModifier, attackInterval, attackRadius
        this.goalSelector.addGoal(2, new RangedAttackGoal(this, 1.0D, 40, 10.0F));

        this.level().addParticle(ParticleTypes.POOF,
                this.getX() + this.random.nextGaussian() * (double)0.13F,
                this.getBoundingBox().maxY + this.random.nextGaussian() * (double)0.13F,
                this.getZ() + this.random.nextGaussian() * (double)0.13F,
                0.0D, 0.0D, 0.0D);
    }

    /**
     * Spawns particle effect when despawned.
     */
    @Override
    public void onRemovedFromWorld() {
        this.level().addParticle(ParticleTypes.POOF,
                this.getX() + this.random.nextGaussian() * (double)0.13F,
                this.getBoundingBox().maxY + this.random.nextGaussian() * (double)0.13F,
                this.getZ() + this.random.nextGaussian() * (double)0.13F,
                0.0D, 0.0D, 0.0D);
    }



    /**
     * Gets whether this Monster should show its name over its head (true).
     * @return Whether it should show its name
     */
    @Override
    public boolean shouldShowName() {
        return true;
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
        ItemEntity itementity = this.spawnAtLocation(ModItems.INFUSED_GEMSTONE.get());
        if (itementity != null) {
            itementity.setExtendedLifetime();
        }

        //THIS IS THE METHOD TO DROP COLLECTOR ITEM WHEN THESE THREE EFFECTS ACTIVE
        if (this.hasEffect(MobEffects.REGENERATION) && this.hasEffect(MobEffects.MOVEMENT_SPEED) &&
                this.hasEffect(MobEffects.DAMAGE_BOOST)) {
            this.spawnAtLocation(ModItems.COLLECTOR_POTION_OF_EVERLASTING_YOUTH.get());
        }

//        //THIS IS THE METHOD TO DROP COLLECTOR ITEM WHEN NAMED 'Kevin'
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
