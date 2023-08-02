package net.dollar.testmod.enchantment;

import net.dollar.testmod.item.ModMaceItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.DamageEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;

public class HardnessEnchantment extends Enchantment {
    protected HardnessEnchantment(Rarity rarity, EquipmentSlot... slots) {
        super(rarity, ModEnchantments.MACE_ONLY, slots);
    }

    private static final int MIN_COST = 1;
    private static final int LEVEL_COST = 11;
    private static final int LEVEL_COST_SPAN = 20;

    public int getMinCost(int p_44633_) {
        return MIN_COST + (p_44633_ - 1) * LEVEL_COST;
    }

    public int getMaxCost(int p_44646_) {
        return this.getMinCost(p_44646_) + LEVEL_COST_SPAN;
    }

    public int getMaxLevel() {
        return 5;
    }

    public float getDamageBonus(int p_44635_, MobType mobType) {
        return 1.0F + (float)Math.max(0, p_44635_ - 1) * 0.5F;
    }

    public boolean checkCompatibility(Enchantment enchantment) {
        return !(enchantment instanceof HardnessEnchantment) && !(enchantment instanceof DamageEnchantment);
    }

    @Override
    public boolean canEnchant(ItemStack p_44642_) {
        return p_44642_.getItem() instanceof ModMaceItem;
    }
}
