package net.dollar.simplegear.enchantment;

import net.dollar.simplegear.item.ModMaceItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.DamageEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;

/**
 * Defines new Enchantment that can be applied only to Maces. Behaves identically to Sharpness.
 */
public class HardnessEnchantment extends Enchantment {
    private static final int MIN_COST = 1;
    private static final int LEVEL_COST = 11;
    private static final int LEVEL_COST_SPAN = 20;

    protected HardnessEnchantment(Rarity rarity, EquipmentSlot... slots) {
        super(rarity, ModEnchantments.MACE_ONLY, slots);
    }



    /**
     * Gets minimum cost required to apply this Enchantment at an Enchanting Table.
     * @param p_44633_ Unknown
     * @return Minimum cost required to apply
     */
    public int getMinCost(int p_44633_) {
        return MIN_COST + (p_44633_ - 1) * LEVEL_COST;
    }

    /**
     * Gets maximum cost required to apply this Enchantment at an Enchanting Table.
     * @param p_44646_ Unknown
     * @return Maximum cost required to apply
     */
    public int getMaxCost(int p_44646_) {
        return this.getMinCost(p_44646_) + LEVEL_COST_SPAN;
    }

    /**
     * Gets maximum level of this enchantment that can be applied. Here, the maximum level is 5.
     * @return Maximum level
     */
    public int getMaxLevel() {
        return 5;
    }



    /**
     * Checks compatibility of this Enchantment against another Enchantment. Here, is mutually exclusive
     *  with all other DamageEnchantments (Sharpness, Bane of Arthropods, Smite, and itself).
     * @param enchantment Other enchantment being queried
     * @return Whether this and the param Enchantment are compatible
     */
    public boolean checkCompatibility(Enchantment enchantment) {
        return !(enchantment instanceof HardnessEnchantment) && !(enchantment instanceof DamageEnchantment);
    }

    /**
     * Checks whether a specific Item can be Enchanted with this Enchantment. Here, can only be
     *  applied to Maces.
     * @param stack The item being queried
     * @return Whether the Item can be Enchanted with this
     */
    @Override
    public boolean canEnchant(ItemStack stack) {
        return stack.getItem() instanceof ModMaceItem;
    }

    /**
     * Gets damage bonus granted by this Enchantment.
     * @param level Level of this Enchantment
     * @param mobType MobType of mob being attacked
     * @return Damage bonus as float
     */
    public float getDamageBonus(int level, MobType mobType) {
        return 1.0F + (float)Math.max(0, level - 1) * 0.5F;
    }
}
