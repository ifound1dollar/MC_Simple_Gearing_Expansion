package net.dollar.simplegear.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Used specifically for Gilded Bronze armor items, which pacify Piglins and have custom hover text.
 */
public class ModGildedBronzeArmorItem extends ArmorItem {
    public ModGildedBronzeArmorItem(ArmorMaterial p_40386_, Type p_266831_, Properties p_40388_) {
        super(p_40386_, p_266831_, p_40388_);
    }



    /**
     * Determines whether an ItemStack of this Item pacifies Piglins.
     * @param stack ItemStack corresponding to this Item
     * @param wearer The LivingEntity wearing this ItemStack
     * @return Whether this item makes Piglins neutral
     */
    @Override
    public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer) {
        return true;
    }

    /**
     * Appends text (as Component) to the Item's hover tooltip.
     * @param stack ItemStack corresponding to this Item
     * @param level Active level
     * @param components List of Components that make up the tooltip
     * @param flag TooltipFlag determining whether NORMAL or ADVANCED
     */
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.literal("§6Pacifies Piglins"));
    }
}
