package net.dollar.simplegear.util;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 * Custom armor material record, implements ArmorMaterial (Forge does not have simple method to make new
 *  ArmorMaterials like it does with ForgeTiers)
 * @param name Name of new ArmorMaterial, is used for ResourceLocation
 * @param durabilityMultiplier Integer multiplied by DURABILITY_PER_SLOT [B, L, C, H] to determine actual durability
 * @param protection Array of protection values per slot [B, L, C, H]
 * @param enchantability Enchantment value (Table use)
 * @param equipsound Equip sound played on clients
 * @param toughness Toughness value
 * @param knockbackResistance Knockback resistance, range 0-1
 * @param repairIngredient Ingredient applied in an Anvil to repair the armor piece
 */
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
        return this.name;
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
