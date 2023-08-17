package net.dollar.simplegear.util;

import net.dollar.simplegear.SimpleGearingExpansion;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

/**
 * Handles creating new tags specific to the mod, like Blocks, Items, and EntityTypes.
 */
public class ModTags {
    public static class Blocks {
        //public static final TagKey<Block> SOMETHING = null;
    }

    public static class Items {
        public static final TagKey<Item> TOOLS_INFUSED_DIAMOND = TagKey.create(Registries.ITEM,
                new ResourceLocation(SimpleGearingExpansion.MOD_ID, "tools_infused_diamond"));
        public static final TagKey<Item> TOOLS_NETHERITE = TagKey.create(Registries.ITEM,
                new ResourceLocation(SimpleGearingExpansion.MOD_ID, "tools_netherite"));
        public static final TagKey<Item> TOOLS_TUNGSTEN_CARBIDE = TagKey.create(Registries.ITEM,
                new ResourceLocation(SimpleGearingExpansion.MOD_ID, "tools_tungsten_carbide"));
    }

    public static class Entities {
        public static final TagKey<EntityType<?>> ARROWS_HEAVY = TagKey.create(Registries.ENTITY_TYPE,
                new ResourceLocation(SimpleGearingExpansion.MOD_ID, "arrows_heavy"));

        public static final TagKey<EntityType<?>> STURDY_MOBS = TagKey.create(Registries.ENTITY_TYPE,
                new ResourceLocation(SimpleGearingExpansion.MOD_ID, "sturdy_mobs"));
        public static final TagKey<EntityType<?>> END_MOBS = TagKey.create(Registries.ENTITY_TYPE,
                new ResourceLocation(SimpleGearingExpansion.MOD_ID, "end_mobs"));
        public static final TagKey<EntityType<?>> NETHER_MOBS = TagKey.create(Registries.ENTITY_TYPE,
                new ResourceLocation(SimpleGearingExpansion.MOD_ID, "nether_mobs"));
    }
}
