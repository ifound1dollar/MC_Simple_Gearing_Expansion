package net.dollar.testmod.datagen;

import net.dollar.testmod.TestMod;
import net.dollar.testmod.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, TestMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        withExistingParent(ModItems.OBSIDIAN_GOLEM_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));


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
        simpleItem(ModItems.GENERIC_UPGRADE_TEMPLATE);
        simpleItem(ModItems.INFUSION_UPGRADE_SMITHING_TEMPLATE);
        simpleItem(ModItems.CARBIDE_UPGRADE_SMITHING_TEMPLATE);
        //endregion

        //region ARMORS
        simpleItem(ModItems.BRONZE_HELMET);
        simpleItem(ModItems.BRONZE_CHESTPLATE);
        simpleItem(ModItems.BRONZE_LEGGINGS);
        simpleItem(ModItems.BRONZE_BOOTS);

        simpleItem(ModItems.GILDED_BRONZE_HELMET);
        simpleItem(ModItems.GILDED_BRONZE_CHESTPLATE);
        simpleItem(ModItems.GILDED_BRONZE_LEGGINGS);
        simpleItem(ModItems.GILDED_BRONZE_BOOTS);

        simpleItem(ModItems.STEEL_HELMET);
        simpleItem(ModItems.STEEL_CHESTPLATE);
        simpleItem(ModItems.STEEL_LEGGINGS);
        simpleItem(ModItems.STEEL_BOOTS);

        simpleItem(ModItems.TUNGSTEN_HELMET);
        simpleItem(ModItems.TUNGSTEN_CHESTPLATE);
        simpleItem(ModItems.TUNGSTEN_LEGGINGS);
        simpleItem(ModItems.TUNGSTEN_BOOTS);

        simpleItem(ModItems.TUNGSTEN_CARBIDE_HELMET);
        simpleItem(ModItems.TUNGSTEN_CARBIDE_CHESTPLATE);
        simpleItem(ModItems.TUNGSTEN_CARBIDE_LEGGINGS);
        simpleItem(ModItems.TUNGSTEN_CARBIDE_BOOTS);

        simpleItem(ModItems.INFUSED_DIAMOND_HELMET);
        simpleItem(ModItems.INFUSED_DIAMOND_CHESTPLATE);
        simpleItem(ModItems.INFUSED_DIAMOND_LEGGINGS);
        simpleItem(ModItems.INFUSED_DIAMOND_BOOTS);
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
    }

    //creates an auto-generated simple item
    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(), new ResourceLocation("item/generated"))
                .texture("layer0", new ResourceLocation(TestMod.MOD_ID, "item/" + item.getId().getPath()));
    }

    //creates an auto-generated handheld item
    private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(), new ResourceLocation("item/handheld"))
                .texture("layer0", new ResourceLocation(TestMod.MOD_ID, "item/" + item.getId().getPath()));
    }
}
