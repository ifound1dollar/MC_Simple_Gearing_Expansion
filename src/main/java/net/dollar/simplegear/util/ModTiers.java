package net.dollar.simplegear.util;

import net.dollar.simplegear.SimpleGearingExpansion;
import net.dollar.simplegear.item.ModItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.Tags;

/**
 * Definitions for both Tools and Armor of the six new equipment tiers
 */
public class ModTiers {
    /**
     * Tool Tiers: defines mining level, uses, mining speed, attack damage bonus, enchantment value, and repair ingredient
     */
    public static class Tools {
        //Identical to Iron
        public static final Tier BRONZE = new ForgeTier(
                2,
                250,
                6.0f,
                2.0f,
                14,
                BlockTags.NEEDS_IRON_TOOL,
                () -> Ingredient.of(ModItems.BRONZE_INGOT.get()));

        //Nearly identical to Bronze | speed and enchantmentValue match Gold
        public static final Tier GILDED_BRONZE = new ForgeTier(
                2,
                250,
                12.0f,
                2.0f,
                22,
                BlockTags.NEEDS_IRON_TOOL,
                () -> Ingredient.of(Items.GOLD_INGOT)); //MAYBE BRONZE?

        //Moderately better than Iron | +350 uses, +1 speed, +0.5 attackDamageBonus
        public static final Tier STEEL = new ForgeTier(
                2,
                600,
                7.0f,
                2.5f,
                14,
                BlockTags.NEEDS_IRON_TOOL,
                () -> Ingredient.of(ModItems.STEEL_INGOT.get()));

        //Slightly worse than Diamond | -1 speed, +1 attackDamageBonus, -1 enchantmentValue
        public static final Tier TUNGSTEN = new ForgeTier(
                3,
                1561,
                7.0f,
                4f,
                9,
                BlockTags.NEEDS_DIAMOND_TOOL,
                () -> Ingredient.of(ModItems.TUNGSTEN_INGOT.get()));

        //Power level equal to Netherite | +3 speed, -1 attackDamageBonus, +7 enchantmentValue
        public static final Tier INFUSED_DIAMOND = new ForgeTier(
                4,
                2031,
                12.0f,
                3f,
                22,
                Tags.Blocks.NEEDS_NETHERITE_TOOL,
                () -> Ingredient.of(ModItems.COMPOUND_GEMSTONE.get()));

        //Power level equal to Netherite | +470 uses, -2 speed, +2 attackDamageBonus, -3 enchantmentValue
        public static final Tier TUNGSTEN_CARBIDE = new ForgeTier(
                4,
                2501,
                7.0f,
                6f,
                12,
                Tags.Blocks.NEEDS_NETHERITE_TOOL,
                () -> Ingredient.of(ModItems.TUNGSTEN_INGOT.get()));
    }

    /**
     * Armor Tiers: defines durability multiplier, armor values (array B, L, C, H), enchantability, equip sound,
     *  toughness, knockback resistance, and repair ingredient
     */
    public static class Armor {
        //Identical to Iron
        public static final ArmorMaterial BRONZE = new ModArmorMaterial(
                SimpleGearingExpansion.MOD_ID + ":bronze",
                15,
                new int[] { 2, 5, 6, 2 },
                9,
                SoundEvents.ARMOR_EQUIP_IRON,
                0.0f,
                0.0f,
                () -> Ingredient.of(ModItems.BRONZE_INGOT.get())
        );
        //Nearly identical to Bronze | enchantability matches Gold
        public static final ArmorMaterial GILDED_BRONZE = new ModArmorMaterial(
                SimpleGearingExpansion.MOD_ID + ":gilded_bronze",
                15,
                new int[] { 2, 5, 6, 2 },
                25,
                SoundEvents.ARMOR_EQUIP_GOLD,
                0.0f,
                0.0f,
                () -> Ingredient.of(Items.GOLD_INGOT)   //MAYBE BRONZE?
        );
        //Moderately better than Iron | +7 durabilityMultiplier, +1 toughness
        public static final ArmorMaterial STEEL = new ModArmorMaterial(
                SimpleGearingExpansion.MOD_ID + ":steel",
                22,
                new int[] { 2, 5, 6, 2 },
                9,
                SoundEvents.ARMOR_EQUIP_IRON,
                1.0f,
                0.0f,
                () -> Ingredient.of(ModItems.STEEL_INGOT.get())
        );

        //Slightly worse than Diamond | -1 enchantability, -0.5 toughness
        public static final ArmorMaterial TUNGSTEN = new ModArmorMaterial(
                SimpleGearingExpansion.MOD_ID + ":tungsten",
                33,
                new int[] { 3, 6, 8, 3 },
                9,
                SoundEvents.ARMOR_EQUIP_IRON,
                1.5f,
                0.0f,
                () -> Ingredient.of(ModItems.TUNGSTEN_INGOT.get())
        );

        //Power level equal to Netherite | +10 enchantability, -0.5 toughness, -0.5 knockbackResistance
        public static final ArmorMaterial INFUSED_DIAMOND = new ModArmorMaterial(
                SimpleGearingExpansion.MOD_ID + ":infused_diamond",
                37,
                new int[] { 3, 6, 8, 3 },
                25,
                SoundEvents.ARMOR_EQUIP_DIAMOND,
                2.5f,
                0.5f,
                () -> Ingredient.of(ModItems.COMPOUND_GEMSTONE.get())
        );

        //Power level equal to Netherite | +4 durabilityMultiplier, -3 enchantability,
        public static final ArmorMaterial TUNGSTEN_CARBIDE = new ModArmorMaterial(
                SimpleGearingExpansion.MOD_ID + ":tungsten_carbide",
                41,
                new int[] { 3, 6, 8, 3 },
                12,
                SoundEvents.ARMOR_EQUIP_NETHERITE,
                3.0f,
                1.0f,
                () -> Ingredient.of(ModItems.TUNGSTEN_INGOT.get())
        );
    }
}



