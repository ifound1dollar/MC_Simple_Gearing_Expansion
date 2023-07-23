package net.dollar.testmod.datagen;

import com.mojang.logging.LogUtils;
import net.dollar.testmod.TestMod;
import net.dollar.testmod.block.ModBlocks;
import net.dollar.testmod.item.ModItems;
import net.dollar.testmod.util.ModLegacySmithingRecipeBuilder;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output) {
        super(output);
    }

    private enum ToolType { AXE, HOE, MACE, PICKAXE, SHOVEL, SWORD }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        //region Ores, smelting AND blasting
        oreSmelting(consumer, List.of(ModBlocks.RUBY_ORE.get()), RecipeCategory.MISC,
                ModItems.RUBY.get(), 1.0f, 200, "ruby" );
        oreBlasting(consumer, List.of(ModBlocks.RUBY_ORE.get()), RecipeCategory.MISC,
                ModItems.RUBY.get(), 1.0f, 100, "ruby" );
        oreSmelting(consumer, List.of(ModBlocks.DEEPSLATE_RUBY_ORE.get()), RecipeCategory.MISC,
                ModItems.RUBY.get(), 1.0f, 200, "ruby" );
        oreBlasting(consumer, List.of(ModBlocks.DEEPSLATE_RUBY_ORE.get()), RecipeCategory.MISC,
                ModItems.RUBY.get(), 1.0f, 100, "ruby" );
        oreSmelting(consumer, List.of(ModBlocks.SAPPHIRE_ORE.get()), RecipeCategory.MISC,
                ModItems.SAPPHIRE.get(), 1.0f, 200, "sapphire" );
        oreBlasting(consumer, List.of(ModBlocks.SAPPHIRE_ORE.get()), RecipeCategory.MISC,
                ModItems.SAPPHIRE.get(), 1.0f, 100, "sapphire" );
        oreSmelting(consumer, List.of(ModBlocks.DEEPSLATE_SAPPHIRE_ORE.get()), RecipeCategory.MISC,
                ModItems.SAPPHIRE.get(), 1.0f, 200, "sapphire" );
        oreBlasting(consumer, List.of(ModBlocks.DEEPSLATE_SAPPHIRE_ORE.get()), RecipeCategory.MISC,
                ModItems.SAPPHIRE.get(), 1.0f, 100, "sapphire" );

        oreSmelting(consumer, List.of(ModBlocks.CARBONITE_ORE.get()), RecipeCategory.MISC,
                ModItems.CARBONITE_DUST.get(), 0.7f, 200, "carbonite_dust" );
        oreBlasting(consumer, List.of(ModBlocks.CARBONITE_ORE.get()), RecipeCategory.MISC,
                ModItems.CARBONITE_DUST.get(), 0.7f, 100, "carbonite_dust" );
        oreSmelting(consumer, List.of(ModBlocks.DEEPSLATE_CARBONITE_ORE.get()), RecipeCategory.MISC,
                ModItems.CARBONITE_DUST.get(), 0.7f, 200, "carbonite_dust" );
        oreBlasting(consumer, List.of(ModBlocks.DEEPSLATE_CARBONITE_ORE.get()), RecipeCategory.MISC,
                ModItems.CARBONITE_DUST.get(), 0.7f, 100, "carbonite_dust" );

        oreSmelting(consumer, List.of(ModBlocks.TIN_ORE.get()), RecipeCategory.MISC,
                ModItems.TIN_INGOT.get(), 0.7f, 200, "tin_ingot" );
        oreBlasting(consumer, List.of(ModBlocks.TIN_ORE.get()), RecipeCategory.MISC,
                ModItems.TIN_INGOT.get(), 0.7f, 100, "tin_ingot" );
        oreSmelting(consumer, List.of(ModBlocks.DEEPSLATE_TIN_ORE.get()), RecipeCategory.MISC,
                ModItems.TIN_INGOT.get(), 0.7f, 200, "tin_ingot" );
        oreBlasting(consumer, List.of(ModBlocks.DEEPSLATE_TIN_ORE.get()), RecipeCategory.MISC,
                ModItems.TIN_INGOT.get(), 0.7f, 100, "tin_ingot" );

        oreSmelting(consumer, List.of(ModItems.RAW_TIN.get()), RecipeCategory.MISC,
                ModItems.TIN_INGOT.get(), 0.7f, 200, "tin_ingot" );
        oreBlasting(consumer, List.of(ModItems.RAW_TIN.get()), RecipeCategory.MISC,
                ModItems.TIN_INGOT.get(), 0.7f, 100, "tin_ingot" );
        oreSmelting(consumer, List.of(ModItems.BRONZE_COMPOUND.get()), RecipeCategory.MISC,
                ModItems.BRONZE_INGOT.get(), 0.7f, 200, "bronze_ingot" );
        oreBlasting(consumer, List.of(ModItems.BRONZE_COMPOUND.get()), RecipeCategory.MISC,
                ModItems.BRONZE_INGOT.get(), 0.7f, 100, "bronze_ingot" );
        //endregion
        //region Ores, blasting ONLY
        oreBlasting(consumer, List.of(ModItems.STEEL_COMPOUND.get()), RecipeCategory.MISC,
                ModItems.STEEL_INGOT.get(), 0.7f, 100, "steel_ingot" );
        oreBlasting(consumer, List.of(ModBlocks.TUNGSTEN_ORE.get()), RecipeCategory.MISC,
                ModItems.TUNGSTEN_INGOT.get(), 1.0f, 100, "tungsten_ingot" );
        oreBlasting(consumer, List.of(ModBlocks.DEEPSLATE_TUNGSTEN_ORE.get()), RecipeCategory.MISC,
                ModItems.TUNGSTEN_INGOT.get(), 1.0f, 100, "tungsten_ingot" );
        oreBlasting(consumer, List.of(ModItems.RAW_TUNGSTEN.get()), RecipeCategory.MISC,
                ModItems.TUNGSTEN_INGOT.get(), 1.0f, 100, "tungsten_ingot" );
        //endregion
        //region Basic nine-block storage recipes
        //NOTE: FIRST IS FOR BLOCK->ITEM, SECOND IS FOR ITEM->BLOCK
        nineBlockStorageRecipes(consumer, RecipeCategory.BUILDING_BLOCKS, ModItems.RUBY.get(),
                RecipeCategory.MISC, ModBlocks.RUBY_BLOCK.get());
        nineBlockStorageRecipes(consumer, RecipeCategory.BUILDING_BLOCKS, ModItems.SAPPHIRE.get(),
                RecipeCategory.MISC, ModBlocks.SAPPHIRE_BLOCK.get());
        nineBlockStorageRecipes(consumer, RecipeCategory.BUILDING_BLOCKS, Items.AMETHYST_SHARD,
                RecipeCategory.MISC, ModBlocks.DECORATIVE_AMETHYST_BLOCK.get());
//        nineBlockStorageRecipes(consumer, RecipeCategory.BUILDING_BLOCKS, ModItems.CARBONITE_DUST.get(),
//                RecipeCategory.MISC, ModBlocks.CARBONITE_BLOCK.get());
        nineBlockStorageRecipes(consumer, RecipeCategory.BUILDING_BLOCKS, ModItems.TIN_INGOT.get(),
                RecipeCategory.MISC, ModBlocks.TIN_BLOCK.get());
        nineBlockStorageRecipes(consumer, RecipeCategory.BUILDING_BLOCKS, ModItems.RAW_TIN.get(),
                RecipeCategory.MISC, ModBlocks.RAW_TIN_BLOCK.get());
        nineBlockStorageRecipes(consumer, RecipeCategory.BUILDING_BLOCKS, ModItems.BRONZE_INGOT.get(),
                RecipeCategory.MISC, ModBlocks.BRONZE_BLOCK.get());
        nineBlockStorageRecipes(consumer, RecipeCategory.BUILDING_BLOCKS, ModItems.STEEL_INGOT.get(),
                RecipeCategory.MISC, ModBlocks.STEEL_BLOCK.get());
        nineBlockStorageRecipes(consumer, RecipeCategory.BUILDING_BLOCKS, ModItems.TUNGSTEN_INGOT.get(),
                RecipeCategory.MISC, ModBlocks.TUNGSTEN_BLOCK.get());
        nineBlockStorageRecipes(consumer, RecipeCategory.BUILDING_BLOCKS, ModItems.RAW_TUNGSTEN.get(),
                RecipeCategory.MISC, ModBlocks.RAW_TUNGSTEN_BLOCK.get());
        //endregion
        //region Compounds and Gemstone (shapeless)
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BRONZE_COMPOUND.get(), 3)
                .requires(Items.COPPER_INGOT, 3)
                .requires(ModItems.TIN_INGOT.get())
                .unlockedBy("has_raw_tin", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.RAW_TIN.get()).build()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.STEEL_COMPOUND.get(), 1)
                .requires(Items.IRON_INGOT, 1)
                .requires(ModItems.CARBONITE_DUST.get())
                .unlockedBy("has_carbonite_dust", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.CARBONITE_DUST.get()).build()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.COMPOUND_GEMSTONE.get(), 1)
                .requires(Items.AMETHYST_SHARD)
                .requires(Items.EMERALD)
                .requires(ModItems.RUBY.get())
                .requires(ModItems.SAPPHIRE.get())
                .requires(Items.DIAMOND)
                .unlockedBy("has_diamond", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.DIAMOND).build()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.TUNGSTEN_CARBIDE_INGOT.get(), 1)
                .requires(ModItems.TUNGSTEN_INGOT.get(), 4)
                .requires(ModItems.CARBONITE_DUST.get(), 4)
                .requires(ModItems.SUPERHEATED_CORE.get(), 1)
                .unlockedBy("has_superheated_core", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.SUPERHEATED_CORE.get()).build()))
                .save(consumer);
        //endregion
        //region Upgrade Templates
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.GILDED_UPGRADE_SMITHING_TEMPLATE.get(), 1)
                .define('d', ModItems.BRONZE_INGOT.get())
                .define('i', Items.COBBLESTONE)
                .define('n', Items.GOLD_NUGGET)
                .pattern("ndn")
                .pattern("nin")
                .pattern("nnn")
                .unlockedBy("has_bronze_ingot", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.BRONZE_INGOT.get()).build()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.INFUSION_UPGRADE_SMITHING_TEMPLATE.get(), 2)
                .define('d', ModItems.INFUSION_UPGRADE_SMITHING_TEMPLATE.get())
                .define('i', Items.COBBLED_DEEPSLATE)
                .define('n', Items.DIAMOND)
                .pattern("ndn")
                .pattern("nin")
                .pattern("nnn")
                .unlockedBy("has_infusion_upgrade_smithing_template", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.INFUSION_UPGRADE_SMITHING_TEMPLATE.get()).build()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.CARBIDE_UPGRADE_SMITHING_TEMPLATE.get(), 2)
                .define('d', ModItems.CARBIDE_UPGRADE_SMITHING_TEMPLATE.get())
                .define('i', Items.OBSIDIAN)
                .define('n', ModItems.TUNGSTEN_INGOT.get())
                .pattern("ndn")
                .pattern("nin")
                .pattern("nnn")
                .unlockedBy("has_carbide_upgrade_smithing_template", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.CARBIDE_UPGRADE_SMITHING_TEMPLATE.get()).build()))
                .save(consumer);
        //endregion


        //region VANILLA TIER MACES
        toolRecipeBuilder(consumer, ToolType.MACE, Items.COBBLESTONE, ModItems.STONE_MACE.get(),
                "has_cobblestone");
        toolRecipeBuilder(consumer, ToolType.MACE, Items.IRON_INGOT, ModItems.IRON_MACE.get(),
                "has_iron_ingot");
        toolRecipeBuilder(consumer, ToolType.MACE, Items.GOLD_INGOT, ModItems.GOLD_MACE.get(),
                "has_gold_ingot");
        toolRecipeBuilder(consumer, ToolType.MACE, Items.DIAMOND, ModItems.DIAMOND_MACE.get(),
                "has_diamond");
        smithingRecipeBuilder(consumer, Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
                ModItems.DIAMOND_MACE.get(), Items.NETHERITE_INGOT, RecipeCategory.COMBAT,
                ModItems.NETHERITE_MACE.get(), "has_netherite_ingot");
        //endregion
        //region BRONZE ARMOR, TOOLS
        armorRecipeBuilder(consumer, EquipmentSlot.HEAD, ModItems.BRONZE_INGOT, ModItems.BRONZE_HELMET,
                "has_bronze_ingot");
        armorRecipeBuilder(consumer, EquipmentSlot.CHEST, ModItems.BRONZE_INGOT, ModItems.BRONZE_CHESTPLATE,
                "has_bronze_ingot");
        armorRecipeBuilder(consumer, EquipmentSlot.LEGS, ModItems.BRONZE_INGOT, ModItems.BRONZE_LEGGINGS,
                "has_bronze_ingot");
        armorRecipeBuilder(consumer, EquipmentSlot.FEET, ModItems.BRONZE_INGOT, ModItems.BRONZE_BOOTS,
                "has_bronze_ingot");

        toolRecipeBuilder(consumer, ToolType.AXE, ModItems.BRONZE_INGOT.get(), ModItems.BRONZE_AXE.get(),
                "has_bronze_ingot");
        toolRecipeBuilder(consumer, ToolType.HOE, ModItems.BRONZE_INGOT.get(), ModItems.BRONZE_HOE.get(),
                "has_bronze_ingot");
        toolRecipeBuilder(consumer, ToolType.MACE, ModItems.BRONZE_INGOT.get(), ModItems.BRONZE_MACE.get(),
                "has_bronze_ingot");
        toolRecipeBuilder(consumer, ToolType.PICKAXE, ModItems.BRONZE_INGOT.get(), ModItems.BRONZE_PICKAXE.get(),
                "has_bronze_ingot");
        toolRecipeBuilder(consumer, ToolType.SHOVEL, ModItems.BRONZE_INGOT.get(), ModItems.BRONZE_SHOVEL.get(),
                "has_bronze_ingot");
        toolRecipeBuilder(consumer, ToolType.SWORD, ModItems.BRONZE_INGOT.get(), ModItems.BRONZE_SWORD.get(),
                "has_bronze_ingot");
        //endregion
        //region GILDED BRONZE ARMOR, TOOLS (smithing)
//        legacySmithingRecipeBuilder(consumer, ModItems.BRONZE_HELMET.get(), Items.GOLD_INGOT,
//                RecipeCategory.COMBAT, ModItems.GILDED_BRONZE_HELMET.get(), "has_gold_ingot");
//        legacySmithingRecipeBuilder(consumer, ModItems.BRONZE_CHESTPLATE.get(), Items.GOLD_INGOT,
//                RecipeCategory.COMBAT, ModItems.GILDED_BRONZE_CHESTPLATE.get(), "has_gold_ingot");
//        legacySmithingRecipeBuilder(consumer, ModItems.BRONZE_LEGGINGS.get(), Items.GOLD_INGOT,
//                RecipeCategory.COMBAT, ModItems.GILDED_BRONZE_LEGGINGS.get(), "has_gold_ingot");
//        legacySmithingRecipeBuilder(consumer, ModItems.BRONZE_BOOTS.get(), Items.GOLD_INGOT,
//                RecipeCategory.COMBAT, ModItems.GILDED_BRONZE_BOOTS.get(), "has_gold_ingot");
//
//        legacySmithingRecipeBuilder(consumer, ModItems.BRONZE_PICKAXE.get(), Items.GOLD_INGOT,
//                RecipeCategory.TOOLS, ModItems.GILDED_BRONZE_PICKAXE.get(), "has_gold_ingot");

        smithingRecipeBuilder(consumer, ModItems.GILDED_UPGRADE_SMITHING_TEMPLATE.get(),
                ModItems.BRONZE_HELMET.get(), Items.GOLD_INGOT, RecipeCategory.COMBAT,
                ModItems.GILDED_BRONZE_HELMET.get(), "has_gold_ingot");
        smithingRecipeBuilder(consumer, ModItems.GILDED_UPGRADE_SMITHING_TEMPLATE.get(),
                ModItems.BRONZE_CHESTPLATE.get(), Items.GOLD_INGOT, RecipeCategory.COMBAT,
                ModItems.GILDED_BRONZE_CHESTPLATE.get(), "has_gold_ingot");
        smithingRecipeBuilder(consumer, ModItems.GILDED_UPGRADE_SMITHING_TEMPLATE.get(),
                ModItems.BRONZE_LEGGINGS.get(), Items.GOLD_INGOT, RecipeCategory.COMBAT,
                ModItems.GILDED_BRONZE_LEGGINGS.get(), "has_gold_ingot");
        smithingRecipeBuilder(consumer, ModItems.GILDED_UPGRADE_SMITHING_TEMPLATE.get(),
                ModItems.BRONZE_BOOTS.get(), Items.GOLD_INGOT, RecipeCategory.COMBAT,
                ModItems.GILDED_BRONZE_BOOTS.get(), "has_gold_ingot");

        smithingRecipeBuilder(consumer, ModItems.GILDED_UPGRADE_SMITHING_TEMPLATE.get(),
                ModItems.BRONZE_AXE.get(), Items.GOLD_INGOT, RecipeCategory.TOOLS,
                ModItems.GILDED_BRONZE_AXE.get(), "has_gold_ingot");
        smithingRecipeBuilder(consumer, ModItems.GILDED_UPGRADE_SMITHING_TEMPLATE.get(),
                ModItems.BRONZE_HOE.get(), Items.GOLD_INGOT, RecipeCategory.TOOLS,
                ModItems.GILDED_BRONZE_HOE.get(), "has_gold_ingot");
        smithingRecipeBuilder(consumer, ModItems.GILDED_UPGRADE_SMITHING_TEMPLATE.get(),
                ModItems.BRONZE_MACE.get(), Items.GOLD_INGOT, RecipeCategory.COMBAT,
                ModItems.GILDED_BRONZE_MACE.get(), "has_gold_ingot");
        smithingRecipeBuilder(consumer, ModItems.GILDED_UPGRADE_SMITHING_TEMPLATE.get(),
                ModItems.BRONZE_PICKAXE.get(), Items.GOLD_INGOT, RecipeCategory.TOOLS,
                ModItems.GILDED_BRONZE_PICKAXE.get(), "has_gold_ingot");
        smithingRecipeBuilder(consumer, ModItems.GILDED_UPGRADE_SMITHING_TEMPLATE.get(),
                ModItems.BRONZE_SHOVEL.get(), Items.GOLD_INGOT, RecipeCategory.TOOLS,
                ModItems.GILDED_BRONZE_SHOVEL.get(), "has_gold_ingot");
        smithingRecipeBuilder(consumer, ModItems.GILDED_UPGRADE_SMITHING_TEMPLATE.get(),
                ModItems.BRONZE_SWORD.get(), Items.GOLD_INGOT, RecipeCategory.COMBAT,
                ModItems.GILDED_BRONZE_SWORD.get(), "has_gold_ingot");
        //endregion
        //region STEEL ARMOR, TOOLS
        armorRecipeBuilder(consumer, EquipmentSlot.HEAD, ModItems.STEEL_INGOT, ModItems.STEEL_HELMET,
                "has_steel_ingot");
        armorRecipeBuilder(consumer, EquipmentSlot.CHEST, ModItems.STEEL_INGOT, ModItems.STEEL_CHESTPLATE,
                "has_steel_ingot");
        armorRecipeBuilder(consumer, EquipmentSlot.LEGS, ModItems.STEEL_INGOT, ModItems.STEEL_LEGGINGS,
                "has_steel_ingot");
        armorRecipeBuilder(consumer, EquipmentSlot.FEET, ModItems.STEEL_INGOT, ModItems.STEEL_BOOTS,
                "has_steel_ingot");

        toolRecipeBuilder(consumer, ToolType.AXE, ModItems.STEEL_INGOT.get(), ModItems.STEEL_AXE.get(),
                "has_steel_ingot");
        toolRecipeBuilder(consumer, ToolType.HOE, ModItems.STEEL_INGOT.get(), ModItems.STEEL_HOE.get(),
                "has_steel_ingot");
        toolRecipeBuilder(consumer, ToolType.MACE, ModItems.STEEL_INGOT.get(), ModItems.STEEL_MACE.get(),
                "has_steel_ingot");
        toolRecipeBuilder(consumer, ToolType.PICKAXE, ModItems.STEEL_INGOT.get(), ModItems.STEEL_PICKAXE.get(),
                "has_steel_ingot");
        toolRecipeBuilder(consumer, ToolType.SHOVEL, ModItems.STEEL_INGOT.get(), ModItems.STEEL_SHOVEL.get(),
                "has_steel_ingot");
        toolRecipeBuilder(consumer, ToolType.SWORD, ModItems.STEEL_INGOT.get(), ModItems.STEEL_SWORD.get(),
                "has_steel_ingot");
        //endregion
        //region TUNGSTEN ARMOR, TOOLS
        armorRecipeBuilder(consumer, EquipmentSlot.HEAD, ModItems.TUNGSTEN_INGOT, ModItems.TUNGSTEN_HELMET,
                "has_tungsten_ingot");
        armorRecipeBuilder(consumer, EquipmentSlot.CHEST, ModItems.TUNGSTEN_INGOT, ModItems.TUNGSTEN_CHESTPLATE,
                "has_tungsten_ingot");
        armorRecipeBuilder(consumer, EquipmentSlot.LEGS, ModItems.TUNGSTEN_INGOT, ModItems.TUNGSTEN_LEGGINGS,
                "has_tungsten_ingot");
        armorRecipeBuilder(consumer, EquipmentSlot.FEET, ModItems.TUNGSTEN_INGOT, ModItems.TUNGSTEN_BOOTS,
                "has_tungsten_ingot");

        toolRecipeBuilder(consumer, ToolType.AXE, ModItems.TUNGSTEN_INGOT.get(), ModItems.TUNGSTEN_AXE.get(),
                "has_tungsten_ingot");
        toolRecipeBuilder(consumer, ToolType.HOE, ModItems.TUNGSTEN_INGOT.get(), ModItems.TUNGSTEN_HOE.get(),
                "has_tungsten_ingot");
        toolRecipeBuilder(consumer, ToolType.MACE, ModItems.TUNGSTEN_INGOT.get(), ModItems.TUNGSTEN_MACE.get(),
                "has_tungsten_ingot");
        toolRecipeBuilder(consumer, ToolType.PICKAXE, ModItems.TUNGSTEN_INGOT.get(), ModItems.TUNGSTEN_PICKAXE.get(),
                "has_tungsten_ingot");
        toolRecipeBuilder(consumer, ToolType.SHOVEL, ModItems.TUNGSTEN_INGOT.get(), ModItems.TUNGSTEN_SHOVEL.get(),
                "has_tungsten_ingot");
        toolRecipeBuilder(consumer, ToolType.SWORD, ModItems.TUNGSTEN_INGOT.get(), ModItems.TUNGSTEN_SWORD.get(),
                "has_tungsten_ingot");
        //endregion
        //region TUNGSTEN-CARBIDE ARMOR, TOOLS (smithing)
//        legacySmithingRecipeBuilder(consumer, ModItems.TUNGSTEN_HELMET.get(), ModItems.TUNGSTEN_CARBIDE_INGOT.get(),
//                RecipeCategory.COMBAT, ModItems.TUNGSTEN_CARBIDE_HELMET.get(), "has_tungsten_carbide_ingot");
//        legacySmithingRecipeBuilder(consumer, ModItems.TUNGSTEN_CHESTPLATE.get(), ModItems.TUNGSTEN_CARBIDE_INGOT.get(),
//                RecipeCategory.COMBAT, ModItems.TUNGSTEN_CARBIDE_CHESTPLATE.get(), "has_tungsten_carbide_ingot");
//        legacySmithingRecipeBuilder(consumer, ModItems.TUNGSTEN_LEGGINGS.get(), ModItems.TUNGSTEN_CARBIDE_INGOT.get(),
//                RecipeCategory.COMBAT, ModItems.TUNGSTEN_CARBIDE_LEGGINGS.get(), "has_tungsten_carbide_ingot");
//        legacySmithingRecipeBuilder(consumer, ModItems.TUNGSTEN_BOOTS.get(), ModItems.TUNGSTEN_CARBIDE_INGOT.get(),
//                RecipeCategory.COMBAT, ModItems.TUNGSTEN_CARBIDE_BOOTS.get(), "has_tungsten_carbide_ingot");

        smithingRecipeBuilder(consumer, ModItems.CARBIDE_UPGRADE_SMITHING_TEMPLATE.get(),
                ModItems.TUNGSTEN_HELMET.get(), ModItems.TUNGSTEN_CARBIDE_INGOT.get(), RecipeCategory.COMBAT,
                ModItems.TUNGSTEN_CARBIDE_HELMET.get(), "has_carbide_upgrade");
        smithingRecipeBuilder(consumer, ModItems.CARBIDE_UPGRADE_SMITHING_TEMPLATE.get(),
                ModItems.TUNGSTEN_CHESTPLATE.get(), ModItems.TUNGSTEN_CARBIDE_INGOT.get(), RecipeCategory.COMBAT,
                ModItems.TUNGSTEN_CARBIDE_CHESTPLATE.get(), "has_carbide_upgrade");
        smithingRecipeBuilder(consumer, ModItems.CARBIDE_UPGRADE_SMITHING_TEMPLATE.get(),
                ModItems.TUNGSTEN_LEGGINGS.get(), ModItems.TUNGSTEN_CARBIDE_INGOT.get(), RecipeCategory.COMBAT,
                ModItems.TUNGSTEN_CARBIDE_LEGGINGS.get(), "has_carbide_upgrade");
        smithingRecipeBuilder(consumer, ModItems.CARBIDE_UPGRADE_SMITHING_TEMPLATE.get(),
                ModItems.TUNGSTEN_BOOTS.get(), ModItems.TUNGSTEN_CARBIDE_INGOT.get(), RecipeCategory.COMBAT,
                ModItems.TUNGSTEN_CARBIDE_BOOTS.get(), "has_carbide_upgrade");

        smithingRecipeBuilder(consumer, ModItems.CARBIDE_UPGRADE_SMITHING_TEMPLATE.get(),
                ModItems.TUNGSTEN_AXE.get(), ModItems.TUNGSTEN_CARBIDE_INGOT.get(), RecipeCategory.TOOLS,
                ModItems.TUNGSTEN_CARBIDE_AXE.get(), "has_carbide_upgrade");
        smithingRecipeBuilder(consumer, ModItems.CARBIDE_UPGRADE_SMITHING_TEMPLATE.get(),
                ModItems.TUNGSTEN_HOE.get(), ModItems.TUNGSTEN_CARBIDE_INGOT.get(), RecipeCategory.TOOLS,
                ModItems.TUNGSTEN_CARBIDE_HOE.get(), "has_carbide_upgrade");
        smithingRecipeBuilder(consumer, ModItems.CARBIDE_UPGRADE_SMITHING_TEMPLATE.get(),
                ModItems.TUNGSTEN_MACE.get(), ModItems.TUNGSTEN_CARBIDE_INGOT.get(), RecipeCategory.COMBAT,
                ModItems.TUNGSTEN_CARBIDE_MACE.get(), "has_carbide_upgrade");
        smithingRecipeBuilder(consumer, ModItems.CARBIDE_UPGRADE_SMITHING_TEMPLATE.get(),
                ModItems.TUNGSTEN_PICKAXE.get(), ModItems.TUNGSTEN_CARBIDE_INGOT.get(), RecipeCategory.TOOLS,
                ModItems.TUNGSTEN_CARBIDE_PICKAXE.get(), "has_carbide_upgrade");
        smithingRecipeBuilder(consumer, ModItems.CARBIDE_UPGRADE_SMITHING_TEMPLATE.get(),
                ModItems.TUNGSTEN_SHOVEL.get(), ModItems.TUNGSTEN_CARBIDE_INGOT.get(), RecipeCategory.TOOLS,
                ModItems.TUNGSTEN_CARBIDE_SHOVEL.get(), "has_carbide_upgrade");
        smithingRecipeBuilder(consumer, ModItems.CARBIDE_UPGRADE_SMITHING_TEMPLATE.get(),
                ModItems.TUNGSTEN_SWORD.get(), ModItems.TUNGSTEN_CARBIDE_INGOT.get(), RecipeCategory.COMBAT,
                ModItems.TUNGSTEN_CARBIDE_SWORD.get(), "has_carbide_upgrade");
        //endregion
        //region INFUSED DIAMOND ARMOR, TOOLS (smithing)
//        legacySmithingRecipeBuilder(consumer, Items.DIAMOND_HELMET, ModItems.INFUSED_GEMSTONE.get(),
//                RecipeCategory.COMBAT, ModItems.INFUSED_DIAMOND_HELMET.get(), "has_infused_gemstone");
//        legacySmithingRecipeBuilder(consumer, Items.DIAMOND_HELMET, ModItems.INFUSED_GEMSTONE.get(),
//                RecipeCategory.COMBAT, ModItems.INFUSED_DIAMOND_CHESTPLATE.get(), "has_infused_gemstone");
//        legacySmithingRecipeBuilder(consumer, Items.DIAMOND_HELMET, ModItems.INFUSED_GEMSTONE.get(),
//                RecipeCategory.COMBAT, ModItems.INFUSED_DIAMOND_LEGGINGS.get(), "has_infused_gemstone");
//        legacySmithingRecipeBuilder(consumer, Items.DIAMOND_HELMET, ModItems.INFUSED_GEMSTONE.get(),
//                RecipeCategory.COMBAT, ModItems.INFUSED_DIAMOND_BOOTS.get(), "has_infused_gemstone");
//
//        legacySmithingRecipeBuilder(consumer, Items.DIAMOND_PICKAXE, ModItems.INFUSED_GEMSTONE.get(),
//                RecipeCategory.TOOLS, ModItems.INFUSED_DIAMOND_PICKAXE.get(), "has_infused_gemstone");

        smithingRecipeBuilder(consumer, ModItems.INFUSION_UPGRADE_SMITHING_TEMPLATE.get(),
                Items.DIAMOND_HELMET, ModItems.INFUSED_GEMSTONE.get(), RecipeCategory.COMBAT,
                ModItems.INFUSED_DIAMOND_HELMET.get(), "has_infused_gemstone");
        smithingRecipeBuilder(consumer, ModItems.INFUSION_UPGRADE_SMITHING_TEMPLATE.get(),
                Items.DIAMOND_CHESTPLATE, ModItems.INFUSED_GEMSTONE.get(), RecipeCategory.COMBAT,
                ModItems.INFUSED_DIAMOND_CHESTPLATE.get(), "has_infused_gemstone");
        smithingRecipeBuilder(consumer, ModItems.INFUSION_UPGRADE_SMITHING_TEMPLATE.get(),
                Items.DIAMOND_LEGGINGS, ModItems.INFUSED_GEMSTONE.get(), RecipeCategory.COMBAT,
                ModItems.INFUSED_DIAMOND_LEGGINGS.get(), "has_infused_gemstone");
        smithingRecipeBuilder(consumer, ModItems.INFUSION_UPGRADE_SMITHING_TEMPLATE.get(),
                Items.DIAMOND_BOOTS, ModItems.INFUSED_GEMSTONE.get(), RecipeCategory.COMBAT,
                ModItems.INFUSED_DIAMOND_BOOTS.get(), "has_infused_gemstone");

        smithingRecipeBuilder(consumer, ModItems.INFUSION_UPGRADE_SMITHING_TEMPLATE.get(),
                Items.DIAMOND_AXE, ModItems.INFUSED_GEMSTONE.get(), RecipeCategory.TOOLS,
                ModItems.INFUSED_DIAMOND_AXE.get(), "has_infused_gemstone");
        smithingRecipeBuilder(consumer, ModItems.INFUSION_UPGRADE_SMITHING_TEMPLATE.get(),
                Items.DIAMOND_HOE, ModItems.INFUSED_GEMSTONE.get(), RecipeCategory.TOOLS,
                ModItems.INFUSED_DIAMOND_HOE.get(), "has_infused_gemstone");
        smithingRecipeBuilder(consumer, ModItems.INFUSION_UPGRADE_SMITHING_TEMPLATE.get(),
                ModItems.DIAMOND_MACE.get(), ModItems.INFUSED_GEMSTONE.get(), RecipeCategory.COMBAT,
                ModItems.INFUSED_DIAMOND_MACE.get(), "has_infused_gemstone");
        smithingRecipeBuilder(consumer, ModItems.INFUSION_UPGRADE_SMITHING_TEMPLATE.get(),
                Items.DIAMOND_PICKAXE, ModItems.INFUSED_GEMSTONE.get(), RecipeCategory.TOOLS,
                ModItems.INFUSED_DIAMOND_PICKAXE.get(), "has_infused_gemstone");
        smithingRecipeBuilder(consumer, ModItems.INFUSION_UPGRADE_SMITHING_TEMPLATE.get(),
                Items.DIAMOND_SHOVEL, ModItems.INFUSED_GEMSTONE.get(), RecipeCategory.TOOLS,
                ModItems.INFUSED_DIAMOND_SHOVEL.get(), "has_infused_gemstone");
        smithingRecipeBuilder(consumer, ModItems.INFUSION_UPGRADE_SMITHING_TEMPLATE.get(),
                Items.DIAMOND_SWORD, ModItems.INFUSED_GEMSTONE.get(), RecipeCategory.COMBAT,
                ModItems.INFUSED_DIAMOND_SWORD.get(), "has_infused_gemstone");
        //endregion
    }




    //BELOW IS HOW TO MANUALLY CREATE SHAPELESS RECIPES (third shapeless() param is quantity)
//        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.RUBY_SHARD.get(), 9)
//                .requires(ModBlocks.RUBY_BLOCK.get())
//                .unlockedBy("has_ruby_block", inventoryTrigger(ItemPredicate.Builder.item()
//                        .of(ModBlocks.RUBY_BLOCK.get()).build()))
//                .save(consumer);

    //BELOW IS HOW TO MANUALLY CREATE SHAPED RECIPES (third shaped() param is quantity)
//        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.BRONZE_PICKAXE.get(), 1)
//                .define('d', ModItems.BRONZE_INGOT.get())
//                .define('i', Items.STICK)
//                .pattern("ddd")
//                .pattern(" i ")
//                .pattern(" i ")
//                .unlockedBy("has_bronze_ingot", inventoryTrigger(ItemPredicate.Builder.item()
//                        .of(ModItems.BRONZE_INGOT.get()).build()))
//                .save(consumer);


    /**
     * Helper to automatically generate shaped recipes for the four armor slots
     * @param consumer Consumer of FinishedRecipe
     * @param slot This armor piece's equipment slot
     * @param ingredient Crafting ingredient
     * @param result Crafting result
     * @param unlockedByString Tag used for unlocking crafting recipe
     */
    private void armorRecipeBuilder(Consumer<FinishedRecipe> consumer, EquipmentSlot slot,
                                    RegistryObject<Item> ingredient, RegistryObject<Item> result,
                                    String unlockedByString) {
        if (slot == EquipmentSlot.HEAD) {
            ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result.get(), 1)  //third param is quantity
                    .define('d', ingredient.get())
                    .pattern("ddd")
                    .pattern("d d")
                    .unlockedBy(unlockedByString, inventoryTrigger(ItemPredicate.Builder.item()
                            .of(ingredient.get()).build()))
                    .save(consumer);
        } else if (slot == EquipmentSlot.CHEST) {
            ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result.get(), 1)
                    .define('d', ingredient.get())
                    .pattern("d d")
                    .pattern("ddd")
                    .pattern("ddd")
                    .unlockedBy(unlockedByString, inventoryTrigger(ItemPredicate.Builder.item()
                            .of(ingredient.get()).build()))
                    .save(consumer);
        } else if (slot == EquipmentSlot.LEGS) {
            ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result.get(), 1)
                    .define('d', ingredient.get())
                    .pattern("ddd")
                    .pattern("d d")
                    .pattern("d d")
                    .unlockedBy(unlockedByString, inventoryTrigger(ItemPredicate.Builder.item()
                            .of(ingredient.get()).build()))
                    .save(consumer);
        } else if (slot == EquipmentSlot.FEET) {
            ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result.get(), 1)
                    .define('d', ingredient.get())
                    .pattern("d d")
                    .pattern("d d")
                    .unlockedBy(unlockedByString, inventoryTrigger(ItemPredicate.Builder.item()
                            .of(ingredient.get()).build()))
                    .save(consumer);
        } else {
            LogUtils.getLogger().debug("Invalid EquipmentSlot provided to armorRecipeBuilder.");
        }
    }

    /**
     * Helper to automatically generate shaped recipes for the five vanilla tool types
     * @param consumer Consumer of FinishedRecipe
     * @param toolType Type of tool defined in ToolType enum
     * @param ingredient Crafting ingredient
     * @param result Crafting result
     * @param unlockedByString Tag used for unlocking crafting recipe
     */
    private void toolRecipeBuilder(Consumer<FinishedRecipe> consumer, ToolType toolType,
                                   Item ingredient, Item result,
                                   String unlockedByString) {
        if (toolType == ToolType.AXE) {
            ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result, 1)  //third param is quantity
                    .define('d', ingredient)
                    .define('i', Items.STICK)
                    .pattern("dd")
                    .pattern("di")
                    .pattern(" i")
                    .unlockedBy(unlockedByString, inventoryTrigger(ItemPredicate.Builder.item()
                            .of(ingredient).build()))
                    .save(consumer);
        } else if (toolType == ToolType.HOE) {
            ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result, 1)
                    .define('d', ingredient)
                    .define('i', Items.STICK)
                    .pattern("dd")
                    .pattern(" i")
                    .pattern(" i")
                    .unlockedBy(unlockedByString, inventoryTrigger(ItemPredicate.Builder.item()
                            .of(ingredient).build()))
                    .save(consumer);
        } else if (toolType == ToolType.MACE) {
            ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result, 1)
                    .define('d', ingredient)
                    .define('i', Items.STICK)
                    .pattern(" dd")
                    .pattern(" id")
                    .pattern("i  ")
                    .unlockedBy(unlockedByString, inventoryTrigger(ItemPredicate.Builder.item()
                            .of(ingredient).build()))
                    .save(consumer);
        } else if (toolType == ToolType.PICKAXE) {
            ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result, 1)
                    .define('d', ingredient)
                    .define('i', Items.STICK)
                    .pattern("ddd")
                    .pattern(" i ")
                    .pattern(" i ")
                    .unlockedBy(unlockedByString, inventoryTrigger(ItemPredicate.Builder.item()
                            .of(ingredient).build()))
                    .save(consumer);
        } else if (toolType == ToolType.SHOVEL) {
            ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result, 1)
                    .define('d', ingredient)
                    .define('i', Items.STICK)
                    .pattern("d")
                    .pattern("i")
                    .pattern("i")
                    .unlockedBy(unlockedByString, inventoryTrigger(ItemPredicate.Builder.item()
                            .of(ingredient).build()))
                    .save(consumer);
        } else if (toolType == ToolType.SWORD) {
            ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result, 1)
                    .define('d', ingredient)
                    .define('i', Items.STICK)
                    .pattern("d")
                    .pattern("d")
                    .pattern("i")
                    .unlockedBy(unlockedByString, inventoryTrigger(ItemPredicate.Builder.item()
                            .of(ingredient).build()))
                    .save(consumer);
        }
    }

    @Deprecated
    /**
     * Helper to automatically generate LEGACY smithing recipes for versions before 1.20
     * @param consumer Consumer of FinishedRecipe
     * @param upgradeItem Item being upgraded
     * @param ingredient Ingredient to upgrade item
     * @param category Recipe category
     * @param result Smithing result
     * @param unlockedByString Tag used for unlocking smithing recipe
     */
    private void legacySmithingRecipeBuilder(Consumer<FinishedRecipe> consumer, Item upgradeItem,
                                                      Item ingredient, RecipeCategory category, Item result,
                                                      String unlockedByString) {
        ModLegacySmithingRecipeBuilder.smithing(Ingredient.of(upgradeItem), Ingredient.of(ingredient),
                category, result).unlocks(unlockedByString, has(ingredient))
                .save(consumer, new ResourceLocation(TestMod.MOD_ID, getItemName(result)) + "_smithing");
    }

    /**
     * Helper to automatically generate smithing recipes (1.20+)
     * @param consumer Consumer of FinishedRecipe
     * @param template Required upgrade template
     * @param upgradeItem Item being upgraded
     * @param ingredient Ingredient to upgrade item
     * @param category Recipe category
     * @param result Smithing result
     * @param unlockedByString Tag used for unlocking smithing recipe
     */
    private void smithingRecipeBuilder(Consumer<FinishedRecipe> consumer, Item template, Item upgradeItem,
                                                Item ingredient, RecipeCategory category, Item result,
                                                String unlockedByString) {
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(template), Ingredient.of(upgradeItem),
                        Ingredient.of(ingredient), category, result)
                .unlocks(unlockedByString, has(ingredient))
                .save(consumer, new ResourceLocation(TestMod.MOD_ID, getItemName(result)) + "_smithing");
    }


    protected static void fourBlockStorageRecipes(Consumer<FinishedRecipe> p_249580_, RecipeCategory p_251203_, ItemLike p_251689_, RecipeCategory p_251376_, ItemLike p_248771_) {
        fourBlockStorageRecipes(p_249580_, p_251203_, p_251689_, p_251376_, p_248771_, getSimpleRecipeName(p_248771_), (String)null, getSimpleRecipeName(p_251689_), (String)null);
    }
    protected static void fourBlockStorageRecipes(Consumer<FinishedRecipe> p_250423_, RecipeCategory p_250083_, ItemLike p_250042_, RecipeCategory p_248977_, ItemLike p_251911_, String p_250475_, @Nullable String p_248641_, String p_252237_, @Nullable String p_250414_) {
        ShapelessRecipeBuilder.shapeless(p_250083_, p_250042_, 4).requires(p_251911_).group(p_250414_).unlockedBy(getHasName(p_251911_), has(p_251911_)).save(p_250423_, new ResourceLocation(TestMod.MOD_ID, p_252237_));
        ShapedRecipeBuilder.shaped(p_248977_, p_251911_).define('#', p_250042_).pattern("##").pattern("##").group(p_248641_).unlockedBy(getHasName(p_250042_), has(p_250042_)).save(p_250423_, new ResourceLocation(TestMod.MOD_ID, p_250475_));
    }





    //region COPIED METHODS TO FIX RECIPES GENERATING UNDER MINECRAFT INSTEAD OF MOD

    //To fix generation issue: Copy over all methods used in this class and then edit all instances
    // of 'new ResourceLocation()' to first take TestMod.MOD_ID, THEN the actual value.
    //  ex. 'new ResourceLocation(p_252237_)' -> 'new ResourceLocation(TestMod.MOD_ID, p_252237_)'
    protected static void oreSmelting(Consumer<FinishedRecipe> p_250654_, List<ItemLike> p_250172_, RecipeCategory p_250588_, ItemLike p_251868_, float p_250789_, int p_252144_, String p_251687_) {
        oreCooking(p_250654_, RecipeSerializer.SMELTING_RECIPE, p_250172_, p_250588_, p_251868_, p_250789_, p_252144_, p_251687_, "_from_smelting");
    }
    protected static void oreBlasting(Consumer<FinishedRecipe> p_248775_, List<ItemLike> p_251504_, RecipeCategory p_248846_, ItemLike p_249735_, float p_248783_, int p_250303_, String p_251984_) {
        oreCooking(p_248775_, RecipeSerializer.BLASTING_RECIPE, p_251504_, p_248846_, p_249735_, p_248783_, p_250303_, p_251984_, "_from_blasting");
    }
    protected static void oreCooking(Consumer<FinishedRecipe> p_250791_, RecipeSerializer<? extends AbstractCookingRecipe> p_251817_, List<ItemLike> p_249619_, RecipeCategory p_251154_, ItemLike p_250066_, float p_251871_, int p_251316_, String p_251450_, String p_249236_) {
        for(ItemLike itemlike : p_249619_) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), p_251154_, p_250066_, p_251871_, p_251316_, p_251817_).group(p_251450_).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(p_250791_, new ResourceLocation(TestMod.MOD_ID, getItemName(p_250066_)) + p_249236_ + "_" + getItemName(itemlike));
        }
        //must use new ResourceLocation in this method before getItemName(p_250066_) so it uses mod's package
    }

    protected static void nineBlockStorageRecipes(Consumer<FinishedRecipe> p_249580_, RecipeCategory p_251203_, ItemLike p_251689_, RecipeCategory p_251376_, ItemLike p_248771_) {
        nineBlockStorageRecipes(p_249580_, p_251203_, p_251689_, p_251376_, p_248771_, getSimpleRecipeName(p_248771_), (String)null, getSimpleRecipeName(p_251689_), (String)null);
    }
    protected static void nineBlockStorageRecipes(Consumer<FinishedRecipe> p_250423_, RecipeCategory p_250083_, ItemLike p_250042_, RecipeCategory p_248977_, ItemLike p_251911_, String p_250475_, @Nullable String p_248641_, String p_252237_, @Nullable String p_250414_) {
        ShapelessRecipeBuilder.shapeless(p_250083_, p_250042_, 9).requires(p_251911_).group(p_250414_).unlockedBy(getHasName(p_251911_), has(p_251911_)).save(p_250423_, new ResourceLocation(TestMod.MOD_ID, p_252237_));
        ShapedRecipeBuilder.shaped(p_248977_, p_251911_).define('#', p_250042_).pattern("###").pattern("###").pattern("###").group(p_248641_).unlockedBy(getHasName(p_250042_), has(p_250042_)).save(p_250423_, new ResourceLocation(TestMod.MOD_ID, p_250475_));
    }

    //endregion
}
