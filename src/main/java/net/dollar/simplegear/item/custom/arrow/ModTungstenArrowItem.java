package net.dollar.simplegear.item.custom.arrow;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Used specifically for the Tungsten Arrow Item, which is only the inventory item and should
 *  not be mistaken for the projectile. General use is to spawn the correct projectile on
 *  projectile weapon fire.
 */
public class ModTungstenArrowItem extends ArrowItem {
    public ModTungstenArrowItem(Properties properties) {
        super(properties);
    }



    /**
     * Creates an instance of the arrow projectile that corresponds to this Item.
     * @param level Level to spawn the projectile
     * @param stack ItemStack corresponding to this Item
     * @param livingEntity LivingEntity the projectile is being spawned from
     * @return Generated arrow projectile
     */
    @Override
    public AbstractArrow createArrow(Level level, ItemStack stack, LivingEntity livingEntity) {
        return new ModTungstenArrowProjectile(level, livingEntity);
    }

    /**
     * Gets whether this Arrow is infinite (false, is custom crafted).
     * @param stack ItemStack corresponding to this Item
     * @param bow ItemStack corresponding to the Bow/Crossbow firing this Arrow
     * @param player Player firing this Arrow
     * @return Whether this Arrow is infinite
     */
    @Override
    public boolean isInfinite(ItemStack stack, ItemStack bow, Player player) {
        return false;
    }

    /**
     * Appends text to the Item's hover tooltip.
     * @param stack ItemStack corresponding to this Item
     * @param level Active level
     * @param components List of Components that make up the tooltip
     * @param flag TooltipFlag determining whether NORMAL or ADVANCED
     */
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.literal("§7Heavy Arrow "));
        components.add(Component.literal("§7> +50% damage "));
        super.appendHoverText(stack, level, components, flag);
    }

}
