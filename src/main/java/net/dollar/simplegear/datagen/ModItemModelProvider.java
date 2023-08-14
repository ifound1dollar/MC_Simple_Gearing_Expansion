package net.dollar.simplegear.datagen;

import net.dollar.simplegear.SimpleGearingExpansion;
import net.dollar.simplegear.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

/**
 * Used to auto-generate item model JSON files in 'src/generated' subdirectory. In-code definitions of recipes
 *  to be generated AND their corresponding helper methods are contained within this class.
 */
public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, SimpleGearingExpansion.MOD_ID, existingFileHelper);
    }



    /**
     * Build item models, auto-generating JSON files in 'src/generated' subdirectory. Developer defines
     *  models to generate within this method.
     */
    @Override
    protected void registerModels() {
        withExistingParent(ModItems.OBSIDIAN_GOLEM_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));


        //region COLLECTOR ITEMS
        simpleItem(ModItems.COLLECTOR_KATHLEENS_LOST_DIADEM);
        simpleItem(ModItems.COLLECTOR_POTION_OF_EVERLASTING_YOUTH);
        simpleItem(ModItems.COLLECTOR_SLIGHTLY_OVERCOOKED_CHICKEN);
        simpleItem(ModItems.COLLECTOR_OBSIDIAN_DUST);
        //endregion

        //region BASIC ITEMS
        simpleItem(ModItems.RUBY);
        simpleItem(ModItems.SAPPHIRE);
        simpleItem(ModItems.COMPOUND_GEMSTONE);
        simpleItem(ModItems.INFUSED_GEMSTONE);
        simpleItem(ModItems.MOLTEN_CORE);
        simpleItem(ModItems.TUNGSTEN_CARBIDE_INGOT);

        simpleItem(ModItems.CARBONITE_DUST);
        simpleItem(ModItems.RAW_TIN);
        simpleItem(ModItems.TIN_INGOT);
        simpleItem(ModItems.BRONZE_COMPOUND);
        simpleItem(ModItems.BRONZE_INGOT);
        simpleItem(ModItems.STEEL_COMPOUND);
        simpleItem(ModItems.STEEL_INGOT);
        simpleItem(ModItems.RAW_TUNGSTEN);
        simpleItem(ModItems.TUNGSTEN_INGOT);

        simpleItem(ModItems.GILDED_UPGRADE_SMITHING_TEMPLATE);
        simpleItem(ModItems.BASIC_UPGRADE_TEMPLATE);
        simpleItem(ModItems.INFUSION_UPGRADE_SMITHING_TEMPLATE);
        simpleItem(ModItems.CARBIDE_UPGRADE_SMITHING_TEMPLATE);
        //endregion

        //region ARMORS
        trimmableArmorItem(ModItems.BRONZE_HELMET);
        trimmableArmorItem(ModItems.BRONZE_CHESTPLATE);
        trimmableArmorItem(ModItems.BRONZE_LEGGINGS);
        trimmableArmorItem(ModItems.BRONZE_BOOTS);

        trimmableArmorItem(ModItems.GILDED_BRONZE_HELMET);
        trimmableArmorItem(ModItems.GILDED_BRONZE_CHESTPLATE);
        trimmableArmorItem(ModItems.GILDED_BRONZE_LEGGINGS);
        trimmableArmorItem(ModItems.GILDED_BRONZE_BOOTS);

        trimmableArmorItem(ModItems.STEEL_HELMET);
        trimmableArmorItem(ModItems.STEEL_CHESTPLATE);
        trimmableArmorItem(ModItems.STEEL_LEGGINGS);
        trimmableArmorItem(ModItems.STEEL_BOOTS);

        trimmableArmorItem(ModItems.TUNGSTEN_HELMET);
        trimmableArmorItem(ModItems.TUNGSTEN_CHESTPLATE);
        trimmableArmorItem(ModItems.TUNGSTEN_LEGGINGS);
        trimmableArmorItem(ModItems.TUNGSTEN_BOOTS);

        trimmableArmorItem(ModItems.TUNGSTEN_CARBIDE_HELMET);
        trimmableArmorItem(ModItems.TUNGSTEN_CARBIDE_CHESTPLATE);
        trimmableArmorItem(ModItems.TUNGSTEN_CARBIDE_LEGGINGS);
        trimmableArmorItem(ModItems.TUNGSTEN_CARBIDE_BOOTS);

        trimmableArmorItem(ModItems.INFUSED_DIAMOND_HELMET);
        trimmableArmorItem(ModItems.INFUSED_DIAMOND_CHESTPLATE);
        trimmableArmorItem(ModItems.INFUSED_DIAMOND_LEGGINGS);
        trimmableArmorItem(ModItems.INFUSED_DIAMOND_BOOTS);
        //endregion

        //region HANDHELD ITEMS
        handheldItem(ModItems.TIN_SHEARS);

        handheldItem(ModItems.BRONZE_AXE);
        handheldItem(ModItems.GILDED_BRONZE_AXE);
        handheldItem(ModItems.STEEL_AXE);
        handheldItem(ModItems.TUNGSTEN_AXE);
        handheldItem(ModItems.TUNGSTEN_CARBIDE_AXE);
        handheldItem(ModItems.INFUSED_DIAMOND_AXE);

        handheldItem(ModItems.BRONZE_HOE);
        handheldItem(ModItems.GILDED_BRONZE_HOE);
        handheldItem(ModItems.STEEL_HOE);
        handheldItem(ModItems.TUNGSTEN_HOE);
        handheldItem(ModItems.TUNGSTEN_CARBIDE_HOE);
        handheldItem(ModItems.INFUSED_DIAMOND_HOE);

        handheldItem(ModItems.STONE_MACE);
        handheldItem(ModItems.IRON_MACE);
        handheldItem(ModItems.GOLD_MACE);
        handheldItem(ModItems.DIAMOND_MACE);
        handheldItem(ModItems.NETHERITE_MACE);
        handheldItem(ModItems.BRONZE_MACE);
        handheldItem(ModItems.GILDED_BRONZE_MACE);
        handheldItem(ModItems.STEEL_MACE);
        handheldItem(ModItems.TUNGSTEN_MACE);
        handheldItem(ModItems.TUNGSTEN_CARBIDE_MACE);
        handheldItem(ModItems.INFUSED_DIAMOND_MACE);

        handheldItem(ModItems.BRONZE_PICKAXE);
        handheldItem(ModItems.GILDED_BRONZE_PICKAXE);
        handheldItem(ModItems.STEEL_PICKAXE);
        handheldItem(ModItems.TUNGSTEN_PICKAXE);
        handheldItem(ModItems.TUNGSTEN_CARBIDE_PICKAXE);
        handheldItem(ModItems.INFUSED_DIAMOND_PICKAXE);

        handheldItem(ModItems.BRONZE_SHOVEL);
        handheldItem(ModItems.GILDED_BRONZE_SHOVEL);
        handheldItem(ModItems.STEEL_SHOVEL);
        handheldItem(ModItems.TUNGSTEN_SHOVEL);
        handheldItem(ModItems.TUNGSTEN_CARBIDE_SHOVEL);
        handheldItem(ModItems.INFUSED_DIAMOND_SHOVEL);

        handheldItem(ModItems.BRONZE_SWORD);
        handheldItem(ModItems.GILDED_BRONZE_SWORD);
        handheldItem(ModItems.STEEL_SWORD);
        handheldItem(ModItems.TUNGSTEN_SWORD);
        handheldItem(ModItems.TUNGSTEN_CARBIDE_SWORD);
        handheldItem(ModItems.INFUSED_DIAMOND_SWORD);
        //endregion

        //region INCOMPLETE ARMOR TRIM ITEM SUBMODEL GENERATION
//        //tempTrimmedArmorItem(ModItems.BRONZE_HELMET, "helmet", "redstone");
//        tempTrimmedArmorItem(ModItems.BRONZE_CHESTPLATE, "chestplate", "redstone");
//        tempTrimmedArmorItem(ModItems.BRONZE_LEGGINGS, "leggings", "redstone");
//        tempTrimmedArmorItem(ModItems.BRONZE_BOOTS, "boots", "redstone");
//
//        //tempTrimmedArmorItem(ModItems.GILDED_BRONZE_HELMET, "helmet", "redstone");
//        tempTrimmedArmorItem(ModItems.GILDED_BRONZE_CHESTPLATE, "chestplate", "redstone");
//        tempTrimmedArmorItem(ModItems.GILDED_BRONZE_LEGGINGS, "leggings", "redstone");
//        tempTrimmedArmorItem(ModItems.GILDED_BRONZE_BOOTS, "boots", "redstone");
//
//        tempTrimmedArmorItem(ModItems.STEEL_HELMET, "helmet", "redstone");
//        tempTrimmedArmorItem(ModItems.STEEL_CHESTPLATE, "chestplate", "redstone");
//        tempTrimmedArmorItem(ModItems.STEEL_LEGGINGS, "leggings", "redstone");
//        tempTrimmedArmorItem(ModItems.STEEL_BOOTS, "boots", "redstone");
//
//        tempTrimmedArmorItem(ModItems.TUNGSTEN_HELMET, "helmet", "redstone");
//        tempTrimmedArmorItem(ModItems.TUNGSTEN_CHESTPLATE, "chestplate", "redstone");
//        tempTrimmedArmorItem(ModItems.TUNGSTEN_LEGGINGS, "leggings", "redstone");
//        tempTrimmedArmorItem(ModItems.TUNGSTEN_BOOTS, "boots", "redstone");
//
//        tempTrimmedArmorItem(ModItems.TUNGSTEN_CARBIDE_HELMET, "helmet", "redstone");
//        tempTrimmedArmorItem(ModItems.TUNGSTEN_CARBIDE_CHESTPLATE, "chestplate", "redstone");
//        tempTrimmedArmorItem(ModItems.TUNGSTEN_CARBIDE_LEGGINGS, "leggings", "redstone");
//        tempTrimmedArmorItem(ModItems.TUNGSTEN_CARBIDE_BOOTS, "boots", "redstone");
//
//        tempTrimmedArmorItem(ModItems.INFUSED_DIAMOND_HELMET, "helmet", "redstone");
//        tempTrimmedArmorItem(ModItems.INFUSED_DIAMOND_CHESTPLATE, "chestplate", "redstone");
//        tempTrimmedArmorItem(ModItems.INFUSED_DIAMOND_LEGGINGS, "leggings", "redstone");
//        tempTrimmedArmorItem(ModItems.INFUSED_DIAMOND_BOOTS, "boots", "redstone");
        //endregion
    }



    /**
     * Builds a simple item model for an Item.
     * @param item Item the model is for
     * @return Generated ItemModelBuilder (unused)
     */
    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(), new ResourceLocation("item/generated"))
                .texture("layer0", new ResourceLocation(SimpleGearingExpansion.MOD_ID, "item/" + item.getId().getPath()));
    }

    /**
     * Builds a handheld item model for an Item.
     * @param item Item the model is for
     * @return Generated ItemModelBuilder (unused)
     */
    private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(), new ResourceLocation("item/handheld"))
                .texture("layer0", new ResourceLocation(SimpleGearingExpansion.MOD_ID, "item/" + item.getId().getPath()));
    }



    /**
     * Builds armor item models for each of the ten color trims that can be applied to trimmable armor.
     *  Implements overrides in the JSON file for each trim palette.
     *  IMPORTANT: The JSON files created using this method are only the main item model files pointing to
     *  sub-files where the actual texture layers are defined. These sub-files had to be generated
     *  INCORRECTLY using tempTrimmedArmorItem(), then fixed and moved to the non-generated 'models' directory.
     * @param item Armor item the model is for
     * @return Generated ItemModelBuilder (unused)
     */
    private ItemModelBuilder trimmableArmorItem(RegistryObject<Item> item) {
        //NOTE: Each override's model file is placed in its own directory for readability. See
        // resources/Assets/simplegear/models/item/[DIRECTORY NAME MATCHING ITEM]/...).
        return withExistingParent(item.getId().getPath(), new ResourceLocation("item/generated"))
                //first override
                .override()
                .model(new ModelFile.UncheckedModelFile(new ResourceLocation(SimpleGearingExpansion.MOD_ID,
                        "item/trims_" + item.getId().getPath() + "/" + item.getId().getPath() + "_quartz_trim")))
                .predicate(new ResourceLocation("trim_type"), 0.1f)
                .end()
                //second override
                .override()
                .model(new ModelFile.UncheckedModelFile(new ResourceLocation(SimpleGearingExpansion.MOD_ID,
                        "item/trims_" + item.getId().getPath() + "/" + item.getId().getPath() + "_iron_trim")))
                .predicate(new ResourceLocation("trim_type"), 0.2f)
                .end()
                //third override
                .override()
                .model(new ModelFile.UncheckedModelFile(new ResourceLocation(SimpleGearingExpansion.MOD_ID,
                        "item/trims_" + item.getId().getPath() + "/" + item.getId().getPath() + "_netherite_trim")))
                .predicate(new ResourceLocation("trim_type"), 0.3f)
                .end()
                //fourth override
                .override()
                .model(new ModelFile.UncheckedModelFile(new ResourceLocation(SimpleGearingExpansion.MOD_ID,
                        "item/trims_" + item.getId().getPath() + "/" + item.getId().getPath() + "_redstone_trim")))
                .predicate(new ResourceLocation("trim_type"), 0.4f)
                .end()
                //fifth override
                .override()
                .model(new ModelFile.UncheckedModelFile(new ResourceLocation(SimpleGearingExpansion.MOD_ID,
                        "item/trims_" + item.getId().getPath() + "/" + item.getId().getPath() + "_copper_trim")))
                .predicate(new ResourceLocation("trim_type"), 0.5f)
                .end()
                //sixth override
                .override()
                .model(new ModelFile.UncheckedModelFile(new ResourceLocation(SimpleGearingExpansion.MOD_ID,
                        "item/trims_" + item.getId().getPath() + "/" + item.getId().getPath() + "_gold_trim")))
                .predicate(new ResourceLocation("trim_type"), 0.6f)
                .end()
                //seventh override
                .override()
                .model(new ModelFile.UncheckedModelFile(new ResourceLocation(SimpleGearingExpansion.MOD_ID,
                        "item/trims_" + item.getId().getPath() + "/" + item.getId().getPath() + "_emerald_trim")))
                .predicate(new ResourceLocation("trim_type"), 0.7f)
                .end()
                //eighth override
                .override()
                .model(new ModelFile.UncheckedModelFile(new ResourceLocation(SimpleGearingExpansion.MOD_ID,
                        "item/trims_" + item.getId().getPath() + "/" + item.getId().getPath() + "_diamond_darker_trim")))
                .predicate(new ResourceLocation("trim_type"), 0.8f)
                .end()
                //ninth override
                .override()
                .model(new ModelFile.UncheckedModelFile(new ResourceLocation(SimpleGearingExpansion.MOD_ID,
                        "item/trims_" + item.getId().getPath() + "/" + item.getId().getPath() + "_lapis_trim")))
                .predicate(new ResourceLocation("trim_type"), 0.9f)
                .end()
                //tenth override
                .override()
                .model(new ModelFile.UncheckedModelFile(new ResourceLocation(SimpleGearingExpansion.MOD_ID,
                        "item/trims_" + item.getId().getPath() + "/" + item.getId().getPath() + "_amethyst_trim")))
                .predicate(new ResourceLocation("trim_type"), 1.0f)
                .end()
                //default texture when ignoring overrides here
                .texture("layer0", new ResourceLocation(SimpleGearingExpansion.MOD_ID, "item/" + item.getId().getPath()));
    }

    /**
     * Builds NEARLY CORRECT item model files for each armor trim palette applied to trimmable armor items.
     *  Main JSON files that point to these files are correctly generated using the trimmableArmorItem()
     *  method, BUT these must be manually edited after generation. This method can be used to generate
     *  the files themselves for efficiency, but each file has an incorrect texture 'layer1' that needs
     *  the 'trimMaterial' suffix (ex. '_quartz'). This suffix cannot be used when generating with runData
     *  because the ResourceLocations with the suffix do not exist until a Minecraft instance
     *  is running. The suffix must be added to each file manually with find & replace, then moved to
     *  the non-generated 'assets/simplegear/models/items' directory.
     * @param item Armor item the model is for
     * @param armorPiece Specific armor piece the trim is applied to (ex. 'leggings')
     * @param trimMaterial Trim material string (ex. 'redstone')
     * @return Generated ItemModelBuilder (unused)
     */
    @Deprecated
    private ItemModelBuilder tempTrimmedArmorItem(RegistryObject<Item> item, String armorPiece, String trimMaterial) {
        return withExistingParent(item.getId().getPath() + "_" + trimMaterial + "_trim",
                new ResourceLocation("item/generated"))
                .texture("layer0", new ResourceLocation(SimpleGearingExpansion.MOD_ID, "item/" + item.getId().getPath()))
                .texture("layer1", new ResourceLocation("trims/items/" + armorPiece + "_trim"));  //apparently 'trimMaterial' part DNE yet??
    }
}
