package net.dollar.testmod.util;

import net.dollar.testmod.TestMod;
import net.dollar.testmod.item.ModItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;

public class ModTiers {
    public static class Tools {
        public static final Tier BRONZE = new ForgeTier(
                2,
                250,
                6.0f,
                2.0f,
                14,
                BlockTags.NEEDS_IRON_TOOL,
                () -> Ingredient.of(ModItems.BRONZE_INGOT.get()));
    }
    public static class Weapons {

    }
    public static class Armor {
        public static final ArmorMaterial BRONZE = new ModArmorMaterial(
                TestMod.MOD_ID + ":bronze",
                20,
                new int[] { 4, 6, 7, 4 },
                20,
                SoundEvents.ARMOR_EQUIP_IRON,
                0.0f,
                0.0f,
                () -> Ingredient.of(ModItems.BRONZE_INGOT.get())
        );
    }
}



