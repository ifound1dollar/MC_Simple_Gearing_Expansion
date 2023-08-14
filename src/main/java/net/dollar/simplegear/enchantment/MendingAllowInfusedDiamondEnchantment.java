package net.dollar.simplegear.enchantment;

import net.dollar.simplegear.util.IInfusedDiamondItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.MendingEnchantment;
import org.apache.commons.lang3.RandomUtils;

/**
 * Overrides vanilla Mending enchantment to be able to be applied at an enchanting table ONLY to
 *  Infused Diamond equipment.
 */
public class MendingAllowInfusedDiamondEnchantment extends MendingEnchantment {
    public MendingAllowInfusedDiamondEnchantment(Rarity rarity, EquipmentSlot... equipmentSlots) {
        super(rarity, equipmentSlots);
    }



    /**
     * Gets whether this Enchantment is treasure-only. Here, overridden to false so it can conditionally
     *  be applied at an Enchanting Table.
     * @return Whether this Enchantment is treasure-only
     */
    public boolean isTreasureOnly() {
        return false;
    }

    /**
     * Checks whether this Enchantment can be applied to a specific Item at an Enchanting Table. Here,
     *  can only be applied to Infused Diamond items.
     * @param stack ItemStack being queried
     * @return Whether the Item can be Enchanted at an Enchanting Table
     */
    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        //IInfusedDiamondItem AND random boolean will effectively reduce Mending chance by 1/2 (this
        //  effectively turns it from RARE to VERY_RARE enchantment at an Enchanting Table).

        //NOTE: Can't re-register as VERY_RARE because it will decrease frequency of Mending found on
        //  Enchanted Books as chest loot.

        return stack.getItem() instanceof IInfusedDiamondItem && RandomUtils.nextBoolean();
    }
}
