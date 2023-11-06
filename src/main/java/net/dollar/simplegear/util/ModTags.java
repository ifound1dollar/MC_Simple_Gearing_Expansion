package net.dollar.simplegear.util;

import net.dollar.simplegear.SimpleGearingExpansion;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

/**
 * Handles creating new tags specific to the mod, like Blocks, Items, and EntityTypes.
 */
public class ModTags {
    public static class Blocks {
        //public static final TagKey<Block> SOMETHING = null;



        public static final TagKey<Block> FORGE_DEEPSLATE = forgeBlockTag("deepslate");

        public static final TagKey<Block> FORGE_ORES_RUBY = forgeBlockTag("ores/ruby");
        public static final TagKey<Block> FORGE_ORES_SAPPHIRE = forgeBlockTag("ores/sapphire");
        public static final TagKey<Block> FORGE_ORES_TIN = forgeBlockTag("ores/tin");
        public static final TagKey<Block> FORGE_ORES_TUNGSTEN = forgeBlockTag("ores/tungsten");
        public static final TagKey<Block> FORGE_STORAGE_BLOCKS_BRONZE = forgeBlockTag("storage_blocks/bronze");
        public static final TagKey<Block> FORGE_STORAGE_BLOCKS_RAW_TIN = forgeBlockTag("storage_blocks/raw_tin");
        public static final TagKey<Block> FORGE_STORAGE_BLOCKS_RAW_TUNGSTEN = forgeBlockTag("storage_blocks/raw_tungsten");
        public static final TagKey<Block> FORGE_STORAGE_BLOCKS_RUBY = forgeBlockTag("storage_blocks/ruby");
        public static final TagKey<Block> FORGE_STORAGE_BLOCKS_SAPPHIRE = forgeBlockTag("storage_blocks/sapphire");
        public static final TagKey<Block> FORGE_STORAGE_BLOCKS_STEEL = forgeBlockTag("storage_blocks/steel");
        public static final TagKey<Block> FORGE_STORAGE_BLOCKS_TIN = forgeBlockTag("storage_blocks/tin");
        public static final TagKey<Block> FORGE_STORAGE_BLOCKS_TUNGSTEN = forgeBlockTag("storage_blocks/tungsten");



        /**
         * Generate a forge block tag. NOTE: BlockTags.create() method auto-inserts the 'block/' path
         *  before the JSON file name.
         * @param name Name of tag's JSON file
         * @return Generated Block TagKey
         */
        private static TagKey<Block> forgeBlockTag(String name)
        {
            return BlockTags.create(new ResourceLocation("forge", name));
        }
        /**
         * Generate a simplegear block tag. NOTE: BlockTags.create() method auto-inserts the 'block/' path
         *  before the JSON file name.
         * @param name Name of tag's JSON file
         * @return Generated Block TagKey
         */
        private static TagKey<Block> modBlockTag(String name)
        {
            return BlockTags.create(new ResourceLocation(SimpleGearingExpansion.MOD_ID, name));
        }

    }

    public static class Items {
        public static final TagKey<Item> ARROWS_HEAVY = modItemTag("arrows_heavy");

        public static final TagKey<Item> TOOLS_INFUSED_DIAMOND = modItemTag("tools_infused_diamond");
        public static final TagKey<Item> TOOLS_NETHERITE = modItemTag("tools_netherite");
        public static final TagKey<Item> TOOLS_TUNGSTEN_CARBIDE = modItemTag("tools_tungsten_carbide");



        public static final TagKey<Item> FORGE_DEEPSLATE = forgeItemTag("deepslate");

        public static final TagKey<Item> FORGE_COKES = forgeItemTag("cokes");

        public static final TagKey<Item> FORGE_GEMS_RUBY = forgeItemTag("gems/ruby");
        public static final TagKey<Item> FORGE_GEMS_SAPPHIRE = forgeItemTag("gems/sapphire");

        public static final TagKey<Item> FORGE_INGOTS_BRONZE = forgeItemTag("ingots/bronze");
        public static final TagKey<Item> FORGE_INGOTS_STEEL = forgeItemTag("ingots/steel");
        public static final TagKey<Item> FORGE_INGOTS_TIN = forgeItemTag("ingots/tin");
        public static final TagKey<Item> FORGE_INGOTS_TUNGSTEN = forgeItemTag("ingots/tungsten");

        public static final TagKey<Item> FORGE_ORES_RUBY = forgeItemTag("ores/ruby");
        public static final TagKey<Item> FORGE_ORES_SAPPHIRE = forgeItemTag("ores/sapphire");
        public static final TagKey<Item> FORGE_ORES_TIN = forgeItemTag("ores/tin");
        public static final TagKey<Item> FORGE_ORES_TUNGSTEN = forgeItemTag("ores/tungsten");

        public static final TagKey<Item> FORGE_RAW_MATERIALS_BRONZE = forgeItemTag("raw_materials/bronze");
        public static final TagKey<Item> FORGE_RAW_MATERIALS_STEEL = forgeItemTag("raw_materials/steel");
        public static final TagKey<Item> FORGE_RAW_MATERIALS_TIN = forgeItemTag("raw_materials/tin");
        public static final TagKey<Item> FORGE_RAW_MATERIALS_TUNGSTEN = forgeItemTag("raw_materials/tungsten");

        public static final TagKey<Item> FORGE_STORAGE_BLOCKS_BRONZE = forgeItemTag("storage_blocks/bronze");
        public static final TagKey<Item> FORGE_STORAGE_BLOCKS_RAW_TIN = forgeItemTag("storage_blocks/raw_tin");
        public static final TagKey<Item> FORGE_STORAGE_BLOCKS_RAW_TUNGSTEN = forgeItemTag("storage_blocks/raw_tungsten");
        public static final TagKey<Item> FORGE_STORAGE_BLOCKS_RUBY = forgeItemTag("storage_blocks/ruby");
        public static final TagKey<Item> FORGE_STORAGE_BLOCKS_SAPPHIRE = forgeItemTag("storage_blocks/sapphire");
        public static final TagKey<Item> FORGE_STORAGE_BLOCKS_STEEL = forgeItemTag("storage_blocks/steel");
        public static final TagKey<Item> FORGE_STORAGE_BLOCKS_TIN = forgeItemTag("storage_blocks/tin");
        public static final TagKey<Item> FORGE_STORAGE_BLOCKS_TUNGSTEN = forgeItemTag("storage_blocks/tungsten");

        public static final TagKey<Item> FORGE_TOOLS_MACES = forgeItemTag("tools/maces");



        /**
         * Generate a forge item tag. NOTE: ItemTags.create() method auto-inserts the 'item/' path
         *  before the JSON file name.
         * @param name Name of tag's JSON file
         * @return Generated Item TagKey
         */
        private static TagKey<Item> forgeItemTag(String name)
        {
            return ItemTags.create(new ResourceLocation("forge", name));
        }
        /**
         * Generate a simplegear item tag. NOTE: ItemTags.create() method auto-inserts the 'item/' path
         *  before the JSON file name.
         * @param name Name of tag's JSON file
         * @return Generated Item TagKey
         */
        private static TagKey<Item> modItemTag(String name)
        {
            return ItemTags.create(new ResourceLocation(SimpleGearingExpansion.MOD_ID, name));
        }
    }

    public static class Entities {
        public static final TagKey<EntityType<?>> ARROWS_HEAVY = modEntityTypeTag("arrows_heavy");

        public static final TagKey<EntityType<?>> STURDY_MOBS = modEntityTypeTag("sturdy_mobs");
        public static final TagKey<EntityType<?>> END_MOBS = modEntityTypeTag("end_mobs");
        public static final TagKey<EntityType<?>> NETHER_MOBS = modEntityTypeTag("nether_mobs");



        /**
         * Generate a forge entity_type tag. Using Registries.ENTITY_TYPE auto-inserts the
         *  'entity_type/' path before the JSON file name.
         * @param name Name of tag's JSON file
         * @return Generated EntityType TagKey
         */
        private static TagKey<EntityType<?>> forgeEntityTypeTag(String name)
        {
            return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("forge", name));
        }
        /**
         * Generate a simplegear entity_type tag. NOTE: Using Registries.ENTITY_TYPE auto-inserts the
         *  'entity_type/' path before the JSON file name.
         * @param name Name of tag's JSON file
         * @return Generated EntityType TagKey
         */
        private static TagKey<EntityType<?>> modEntityTypeTag(String name)
        {
            return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(SimpleGearingExpansion.MOD_ID, name));
        }

    }
}
