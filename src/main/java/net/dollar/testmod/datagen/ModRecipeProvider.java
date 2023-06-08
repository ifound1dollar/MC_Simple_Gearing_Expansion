package net.dollar.testmod.datagen;

import com.mojang.logging.LogUtils;
import net.dollar.testmod.TestMod;
import net.dollar.testmod.block.ModBlocks;
import net.dollar.testmod.item.ModItems;
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

    private enum ToolType { AXE, HOE, PICKAXE, SHOVEL, SWORD }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        //BLASTING ONLY RECIPES ARE POSSIBLE BY NOT DEFINING oreSmelting
        oreSmelting(consumer, List.of(ModBlocks.TIN_ORE.get()), RecipeCategory.MISC,
                ModItems.TIN_INGOT.get(), 0.7f, 200, "tin_ingot" );
        oreBlasting(consumer, List.of(ModBlocks.TIN_ORE.get()), RecipeCategory.MISC,
                ModItems.TIN_INGOT.get(), 0.7f, 100, "tin_ingot" );
        oreSmelting(consumer, List.of(ModItems.RAW_TIN.get()), RecipeCategory.MISC,
                ModItems.TIN_INGOT.get(), 0.7f, 200, "tin_ingot" );
        oreBlasting(consumer, List.of(ModItems.RAW_TIN.get()), RecipeCategory.MISC,
                ModItems.TIN_INGOT.get(), 0.7f, 100, "tin_ingot" );

        oreSmelting(consumer, List.of(ModBlocks.TUNGSTEN_ORE.get()), RecipeCategory.MISC,
                ModItems.TUNGSTEN_INGOT.get(), 1.0f, 200, "tungsten_ingot" );
        oreBlasting(consumer, List.of(ModBlocks.TUNGSTEN_ORE.get()), RecipeCategory.MISC,
                ModItems.TUNGSTEN_INGOT.get(), 1.0f, 100, "tungsten_ingot" );
        oreSmelting(consumer, List.of(ModItems.RAW_TUNGSTEN.get()), RecipeCategory.MISC,
                ModItems.TUNGSTEN_INGOT.get(), 1.0f, 200, "tungsten_ingot" );
        oreBlasting(consumer, List.of(ModItems.RAW_TUNGSTEN.get()), RecipeCategory.MISC,
                ModItems.TUNGSTEN_INGOT.get(), 1.0f, 100, "tungsten_ingot" );


        //NOTE: FIRST IS FOR BLOCK->ITEM, SECOND IS FOR ITEM->BLOCK
        nineBlockStorageRecipes(consumer, RecipeCategory.BUILDING_BLOCKS, ModItems.RUBY_SHARD.get(),
                RecipeCategory.MISC, ModBlocks.RUBY_BLOCK.get());
        nineBlockStorageRecipes(consumer, RecipeCategory.BUILDING_BLOCKS, ModItems.SAPPHIRE_SHARD.get(),
                RecipeCategory.MISC, ModBlocks.SAPPHIRE_BLOCK.get());
        //replace above with custom-made fourBlockStorageRecipes method

        nineBlockStorageRecipes(consumer, RecipeCategory.BUILDING_BLOCKS, ModItems.TIN_INGOT.get(),
                RecipeCategory.MISC, ModBlocks.TIN_BLOCK.get());
        nineBlockStorageRecipes(consumer, RecipeCategory.BUILDING_BLOCKS, ModItems.BRONZE_INGOT.get(),
                RecipeCategory.MISC, ModBlocks.BRONZE_BLOCK.get());
        nineBlockStorageRecipes(consumer, RecipeCategory.BUILDING_BLOCKS, ModItems.TUNGSTEN_INGOT.get(),
                RecipeCategory.MISC, ModBlocks.TUNGSTEN_BLOCK.get());


        //region BRONZE ARMOR, TOOLS
        armorRecipeBuilder(consumer, EquipmentSlot.HEAD, ModItems.BRONZE_INGOT, ModItems.BRONZE_HELMET,
                "has_bronze_ingot");
        armorRecipeBuilder(consumer, EquipmentSlot.CHEST, ModItems.BRONZE_INGOT, ModItems.BRONZE_CHESTPLATE,
                "has_bronze_ingot");
        armorRecipeBuilder(consumer, EquipmentSlot.LEGS, ModItems.BRONZE_INGOT, ModItems.BRONZE_LEGGINGS,
                "has_bronze_ingot");
        armorRecipeBuilder(consumer, EquipmentSlot.FEET, ModItems.BRONZE_INGOT, ModItems.BRONZE_BOOTS,
                "has_bronze_ingot");
        toolRecipeBuilder(consumer, ToolType.PICKAXE, ModItems.BRONZE_INGOT, ModItems.BRONZE_PICKAXE,
                "has_bronze_ingot");
        //endregion



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
    }


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
                                   RegistryObject<Item> ingredient, RegistryObject<Item> result,
                                   String unlockedByString) {
        if (toolType == ToolType.AXE) {
            ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result.get(), 1)  //third param is quantity
                    .define('d', ingredient.get())
                    .define('i', Items.STICK)
                    .pattern("dd")
                    .pattern("di")
                    .pattern(" i")
                    .unlockedBy(unlockedByString, inventoryTrigger(ItemPredicate.Builder.item()
                            .of(ingredient.get()).build()))
                    .save(consumer);
        } else if (toolType == ToolType.HOE) {
            ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result.get(), 1)
                    .define('d', ingredient.get())
                    .define('i', Items.STICK)
                    .pattern("dd")
                    .pattern(" i")
                    .pattern(" i")
                    .unlockedBy(unlockedByString, inventoryTrigger(ItemPredicate.Builder.item()
                            .of(ingredient.get()).build()))
                    .save(consumer);
        } else if (toolType == ToolType.PICKAXE) {
            ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result.get(), 1)
                    .define('d', ingredient.get())
                    .define('i', Items.STICK)
                    .pattern("ddd")
                    .pattern(" i ")
                    .pattern(" i ")
                    .unlockedBy(unlockedByString, inventoryTrigger(ItemPredicate.Builder.item()
                            .of(ingredient.get()).build()))
                    .save(consumer);
        } else if (toolType == ToolType.SHOVEL) {
            ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result.get(), 1)
                    .define('d', ingredient.get())
                    .define('i', Items.STICK)
                    .pattern("d")
                    .pattern("i")
                    .pattern("i")
                    .unlockedBy(unlockedByString, inventoryTrigger(ItemPredicate.Builder.item()
                            .of(ingredient.get()).build()))
                    .save(consumer);
        } else if (toolType == ToolType.SWORD) {
            ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result.get(), 1)
                    .define('d', ingredient.get())
                    .define('i', Items.STICK)
                    .pattern("d")
                    .pattern("d")
                    .pattern("i")
                    .unlockedBy(unlockedByString, inventoryTrigger(ItemPredicate.Builder.item()
                            .of(ingredient.get()).build()))
                    .save(consumer);
        }
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
