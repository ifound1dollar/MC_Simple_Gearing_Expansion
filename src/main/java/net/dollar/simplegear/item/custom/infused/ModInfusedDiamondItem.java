package net.dollar.simplegear.item.custom.infused;

import net.dollar.simplegear.util.IInfusedDiamondItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Used specifically for the Infused Diamond item, which has enchantment glint and custom hover text.
 */
public class ModInfusedDiamondItem extends Item implements IInfusedDiamondItem {
    public ModInfusedDiamondItem(Properties p_41383_) {
        super(p_41383_);
    }



    /**
     * Gets whether this Item should render with enchantment glint (true).
     * @param stack ItemStack of this Item
     * @return Whether this item has enchantment glint
     */
    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    /**
     * Appends text to the Item's hover tooltip (lore).
     * @param stack ItemStack corresponding to this Item
     * @param level Active level
     * @param components List of Components that make up the tooltip
     * @param flag TooltipFlag determining whether NORMAL or ADVANCED
     */
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.literal("§5Empowered with an ancient magic"));
    }
}
