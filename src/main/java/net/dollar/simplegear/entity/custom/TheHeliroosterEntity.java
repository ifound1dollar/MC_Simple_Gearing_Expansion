package net.dollar.simplegear.entity.custom;

import net.dollar.simplegear.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Custom boss Monster entity, spawned manually via Compound Gemstone interaction with a Spectral Lantern.
 */
public class TheHeliroosterEntity extends Monster {
    private int ticksSinceLastAttack = 0;
    private int spawnDelayTicks = 100;
    private boolean isAwaitingSpawnDelay = true;

    public TheHeliroosterEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }


    /**
     * Register mob goals (AI).
     */
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
    }

    /**
     * Set mob attributes, like MAX_HEALTH, FOLLOW_RANGE, etc.
     * @return Newly created AttributeSupplier
     */
    public static AttributeSupplier setAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 120)
                .add(Attributes.ATTACK_DAMAGE, 14f)     //Normal, Easy/Hard values are auto-scaled
                .add(Attributes.MOVEMENT_SPEED, 0.4f)   //fast chicken (default 0.25)
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
     * Gets ambient sound produced by this Monster.
     * @return Ambient SoundEvent
     */
    protected SoundEvent getAmbientSound() {
        return SoundEvents.CHICKEN_AMBIENT;
    }

    /**
     * Gets hurt sound produced by this Monster.
     * @param source DamageSource of damage being dealt
     * @return Hurt SoundEvent
     */
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.CHICKEN_HURT;
    }

    /**
     * Gets death sound produced by this Monster.
     * @return Death SoundEvent
     */
    protected SoundEvent getDeathSound() {
        return SoundEvents.CHICKEN_DEATH;
    }

    /**
     * Plays step sound of this Monster.
     * @param blockPos Position being stepped on
     * @param blockState Blockstate of block at position being stepped on
     */
    protected void playStepSound(BlockPos blockPos, BlockState blockState) {
        this.playSound(SoundEvents.CHICKEN_STEP, 0.15F, 1.0F);
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
        //can only attack once per second, then resets counter
        if (ticksSinceLastAttack < 20) {
            return false;
        }
        ticksSinceLastAttack = 0;

        //actual attack here
        float f = this.getAttackDamage();
        float f1 = (int)f > 0 ? f / 2.0F + (float)this.random.nextInt((int)f) : f;
        boolean flag = targetEntity.hurt(this.damageSources().mobAttack(this), f1);
        if (flag)
        {
            this.doEnchantDamageEffects(this, targetEntity);
        }

        this.playSound(SoundEvents.CHICKEN_HURT, 1.0F, 1.0F);
        return flag;
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

    /**
     * Ends initial 5s spawn delay: removes invisibility and silence, sets AttackGoal, and spawns particle.
     */
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

        //THIS IS THE METHOD TO DROP COLLECTOR ITEM WHEN ON FIRE WHEN KILLED
        if (this.wasOnFire) {
            this.spawnAtLocation(ModItems.COLLECTOR_SLIGHTLY_OVERCOOKED_CHICKEN.get());
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

    /**
     * Gets experience drop from this Monster on death.
     * @return Amount of experience reward
     */
    public int getExperienceReward() {
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