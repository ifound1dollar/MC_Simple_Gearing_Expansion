package net.dollar.simplegear.enchantment;

import net.dollar.simplegear.util.IInfusedDiamondItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.MendingEnchantment;
import org.apache.commons.lang3.RandomUtils;

public class MendingAllowInfusedDiamondEnchantment extends MendingEnchantment {
    public MendingAllowInfusedDiamondEnchantment(Rarity rarity, EquipmentSlot... equipmentSlots) {
        super(rarity, equipmentSlots);
    }

    public boolean isTreasureOnly() {
        //needs to not be treasure only to allow enchanting table EVER (below method still limits)
        return false;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        //can only ever be applied at enchanting table if is Infused Diamond item
        //IInfusedDiamondItem AND random boolean will effectively reduce Mending chance by 1/2 (this
        //  effectively turns it from RARE to VERY_RARE enchantment at an Enchanting Table).
        //NOTE: Can't re-register as VERY_RARE because it will decrease frequency of Mending found on
        //  Enchanted Books as chest loot.
        return stack.getItem() instanceof IInfusedDiamondItem && RandomUtils.nextBoolean();
    }
}
