package net.dollar.simplegear.item.custom;

import net.dollar.simplegear.util.IInfusedDiamondItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ModInfusedDiamondItem extends Item implements IInfusedDiamondItem {
    public ModInfusedDiamondItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;    //enchantment glint
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> p_41423_, TooltipFlag p_41424_) {
        p_41423_.add(Component.literal("§5Empowered with an ancient magic"));
        super.appendHoverText(p_41421_, p_41422_, p_41423_, p_41424_);
    }
}
