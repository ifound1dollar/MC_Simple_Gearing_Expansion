package net.dollar.testmod.util;

import net.dollar.testmod.TestMod;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public record ModArmorMaterial(String name, int durabilityMultiplier, int[] protection, int enchantability,
                               SoundEvent equipsound, float toughness, float knockbackResistance,
                               Supplier<Ingredient> repairIngredient) implements ArmorMaterial {
    private static final int[] DURABILITY_PER_SLOT = new int[] { 13, 15, 16, 11 };  //boots, leggings, chestplate, helmet


    @Override
    public int getDurabilityForType(ArmorItem.Type slot) {
        return DURABILITY_PER_SLOT[slot.getSlot().getIndex()] * this.durabilityMultiplier;
    }

    @Override
    public int getDefenseForType(ArmorItem.Type slot) {
        return this.protection[slot.getSlot().getIndex()];
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantability;
    }

    @Override
    public @NotNull SoundEvent getEquipSound() {
        return this.equipsound;
    }

    @Override
    public @NotNull Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Override
    public @NotNull String getName() {
        return TestMod.MOD_ID + this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
