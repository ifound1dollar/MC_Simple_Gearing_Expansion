package net.dollar.testmod.util;

import net.dollar.testmod.TestMod;
import net.dollar.testmod.item.ModItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.Tags;

public class ModTiers {
    public static class Tools {
        //same as iron
        public static final Tier BRONZE = new ForgeTier(
                2,
                250,
                6.0f,
                2.0f,
                14,
                BlockTags.NEEDS_IRON_TOOL,
                () -> Ingredient.of(ModItems.BRONZE_INGOT.get()));

        //almost identical to Bronze, but speed and enchantability is higher (just below Gold [12/22])
        public static final Tier GILDED_BRONZE = new ForgeTier(
                2,
                250,
                10.0f,
                2.0f,
                20,
                BlockTags.NEEDS_IRON_TOOL,
                () -> Ingredient.of(Items.GOLD_INGOT)); //MAYBE BRONZE?

        //slightly better than Iron (halfway between Iron and Diamond)
        public static final Tier STEEL = new ForgeTier(
                2,
                600,
                7.0f,
                2.5f,
                14,
                BlockTags.NEEDS_IRON_TOOL,
                () -> Ingredient.of(ModItems.STEEL_INGOT.get()));

        //more damage than Diamond (3), but speed between Stone (4) and Iron (6) (very slow)
        public static final Tier TUNGSTEN = new ForgeTier(
                3,
                1561,
                5.0f,
                4f,
                14,
                BlockTags.NEEDS_DIAMOND_TOOL,
                () -> Ingredient.of(ModItems.TUNGSTEN_INGOT.get()));

        //more durable than Netherite, speed between Stone and Iron, damage +1 above Netherite, same enchantability as Diamond
        public static final Tier TUNGSTEN_CARBIDE = new ForgeTier(
                4,
                2501,
                5.0f,
                6f,
                10,
                Tags.Blocks.NEEDS_NETHERITE_TOOL,
                () -> Ingredient.of(ModItems.TUNGSTEN_CARBIDE_INGOT.get()));

        //same durability and as Netherite, same damage as Diamond (3), speed and enchantability equal to Gold
        public static final Tier INFUSED_DIAMOND = new ForgeTier(
                4,
                2031,
                12.0f,
                3f,
                22,
                Tags.Blocks.NEEDS_NETHERITE_TOOL,
                () -> Ingredient.of(Items.DIAMOND));
    }

    public static class Armor {
        //same as Iron
        public static final ArmorMaterial BRONZE = new ModArmorMaterial(
                TestMod.MOD_ID + ":bronze",
                15,
                new int[] { 2, 5, 6, 2 },
                9,
                SoundEvents.ARMOR_EQUIP_IRON,
                0.0f,
                0.0f,
                () -> Ingredient.of(ModItems.BRONZE_INGOT.get())
        );
        //same as Bronze/Iron EXCEPT enchantability matches Gold
        public static final ArmorMaterial GILDED_BRONZE = new ModArmorMaterial(
                TestMod.MOD_ID + ":gilded_bronze",
                15,
                new int[] { 2, 5, 6, 2 },
                25,
                SoundEvents.ARMOR_EQUIP_GOLD,
                0.0f,
                0.0f,
                () -> Ingredient.of(Items.GOLD_INGOT)   //MAYBE BRONZE?
        );
        //+7 durabilityMultiplier, +1 toughness from Iron
        public static final ArmorMaterial STEEL = new ModArmorMaterial(
                TestMod.MOD_ID + ":steel",
                22,
                new int[] { 2, 5, 6, 2 },
                9,
                SoundEvents.ARMOR_EQUIP_IRON,
                1.0f,
                0.0f,
                () -> Ingredient.of(ModItems.STEEL_INGOT.get())
        );

        //-1 enchantability, -0.5 armor toughness from Diamond (very slightly worse)
        public static final ArmorMaterial TUNGSTEN = new ModArmorMaterial(
                TestMod.MOD_ID + ":tungsten",
                33,
                new int[] { 3, 6, 8, 3 },
                9,
                SoundEvents.ARMOR_EQUIP_IRON,
                1.5f,
                0.0f,
                () -> Ingredient.of(ModItems.TUNGSTEN_INGOT.get())
        );

        //higher durability than Netherite, same echantability as Diamond, toughness matches Netherite
        public static final ArmorMaterial TUNGSTEN_CARBIDE = new ModArmorMaterial(
                TestMod.MOD_ID + ":tungsten_carbide",
                41,
                new int[] { 3, 6, 8, 3 },
                10,
                SoundEvents.ARMOR_EQUIP_NETHERITE,
                3.0f,
                0.0f,
                () -> Ingredient.of(ModItems.TUNGSTEN_CARBIDE_INGOT.get())
        );

        //same durability as Netherite, higher echantability than Netherite, same toughness as Diamond
        public static final ArmorMaterial INFUSED_DIAMOND = new ModArmorMaterial(
                TestMod.MOD_ID + ":infused_diamond",
                37,
                new int[] { 3, 6, 8, 3 },
                20,
                SoundEvents.ARMOR_EQUIP_DIAMOND,
                2.0f,
                0.0f,
                () -> Ingredient.of(Items.DIAMOND)
        );
    }
}



