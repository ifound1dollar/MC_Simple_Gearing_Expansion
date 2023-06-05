package net.dollar.testmod.util;

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
                () -> Ingredient.of(Items.IRON_INGOT));   //TEMPORARILY IRON INGOT, WILL BE BRONZE
    }
    public static class Weapons {

    }
    public static class Armor {

    }
}



