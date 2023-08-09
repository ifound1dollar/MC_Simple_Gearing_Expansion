package net.dollar.testmod.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.MendingEnchantment;

public class MendingVeryRareEnchantment extends Enchantment {
    public MendingVeryRareEnchantment(Rarity rarity, EquipmentSlot... equipmentSlots) {
        super(rarity, ModEnchantments.INFUSED_DIAMOND_ONLY, equipmentSlots);
    }

    public int getMinCost(int p_45102_) {
        return p_45102_ * 25;
    }

    public int getMaxCost(int p_45105_) {
        return this.getMinCost(p_45105_) + 50;
    }

    public boolean checkCompatibility(Enchantment enchantment) {
        return !(enchantment instanceof MendingVeryRareEnchantment) && !(enchantment instanceof MendingEnchantment);
    }
    
    public boolean isTreasureOnly() {
        return false;
    }   //unnecessary override
}
