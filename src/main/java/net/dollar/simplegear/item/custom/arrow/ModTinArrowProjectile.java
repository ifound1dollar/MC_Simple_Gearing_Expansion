package net.dollar.simplegear.item.custom.arrow;

import net.dollar.simplegear.entity.ModEntities;
import net.dollar.simplegear.item.ModItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.SpectralArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

/**
 * Used specifically for the Tin Arrow projectile, which is the actual projectile and not the
 *  inventory item. Tin Arrows have no custom behavior but deal bonus damage.
 */
public class ModTinArrowProjectile extends AbstractArrow {
    public ModTinArrowProjectile(EntityType<? extends ModTinArrowProjectile> entityType, Level level) {
        super(entityType, level);
    }
    public ModTinArrowProjectile(Level level, LivingEntity livingEntity) {
        super(ModEntities.TIN_ARROW.get(), livingEntity, level);
        setBaseDamage(3.0d);    //BASE ARROW DAMAGE MULTIPLIER IS 2
    }



    /**
     * Called when this arrow projectile hits an Entity.
     * @param hitResult EntityHitResult
     */
    @Override
    protected void onHitEntity(EntityHitResult hitResult) {
        //do not change in this class (Tin is just bonus damage), but need to change in others
        // (ex. to change hurt() call DamageSource)
        super.onHitEntity(hitResult);
    }

    /**
     * Called when this arrow projectile hits a Block.
     * @param hitResult BlockHitResult
     */
    @Override
    protected void onHitBlock(BlockHitResult hitResult) {
        //do not change in this class, but need to change in explosive/lightning
        // (ex. do process then discard() arrow)
        super.onHitBlock(hitResult);
    }

    /**
     * Gets the Item that will be picked up when the player contacts the Entity.
     * @return Generated ItemStack of Item corresponding to Projectile
     */
    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ModItems.TIN_ARROW.get());
    }
}
