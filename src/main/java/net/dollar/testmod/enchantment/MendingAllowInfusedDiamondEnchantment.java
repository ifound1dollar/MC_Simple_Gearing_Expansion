package net.dollar.testmod.enchantment;

import net.dollar.testmod.util.IInfusedDiamondItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.MendingEnchantment;

public class MendingAllowInfusedDiamondEnchantment extends MendingEnchantment {
    public MendingAllowInfusedDiamondEnchantment(Rarity rarity, EquipmentSlot... equipmentSlots) {
        super(rarity, equipmentSlots);
    }

    public boolean isTreasureOnly() {
        //needs to not be treasure only to allow enchanting table EVER
        return false;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        //can only ever be applied at enchanting table if is Infused Diamond item
        return stack.getItem() instanceof IInfusedDiamondItem;
    }
}
