package net.dollar.simplegear.item.custom.arrow;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

/**
 * Used specifically for the Tin-Tipped arrow projectile, which is the actual projectile and not the
 *  inventory item. Tin-Tipped arrows have no custom behavior but deal bonus damage.
 */
public class ModTinArrowProjectile extends AbstractArrow {
    protected ModTinArrowProjectile(Level level, LivingEntity livingEntity) {
        super(EntityType.ARROW, livingEntity, level);
        setBaseDamage(3.0d);    //BASE ARROW DAMAGE IS 2
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
        //return new ItemStack(ModItems.TIN_TIPPED_ARROW.get());

        return null;
    }
}