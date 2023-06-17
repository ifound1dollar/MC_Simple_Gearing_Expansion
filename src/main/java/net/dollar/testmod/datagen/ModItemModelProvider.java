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
        simpleItem(ModItems.RUBY);
        simpleItem(ModItems.SAPPHIRE);
        simpleItem(ModItems.INFUSED_GEMSTONE);
        simpleItem(ModItems.NAMELESS_INFUSION_ITEM);

        simpleItem(ModItems.CARBONITE_DUST);
        simpleItem(ModItems.RAW_TIN);
        simpleItem(ModItems.TIN_INGOT);
        simpleItem(ModItems.BRONZE_COMPOUND);
        simpleItem(ModItems.BRONZE_INGOT);
        simpleItem(ModItems.STEEL_COMPOUND);
        simpleItem(ModItems.STEEL_INGOT);
        simpleItem(ModItems.RAW_TUNGSTEN);
        simpleItem(ModItems.TUNGSTEN_INGOT);
        simpleItem(ModItems.TUNGSTEN_CARBIDE_COMPOUND);
        simpleItem(ModItems.TUNGSTEN_CARBIDE_INGOT);

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

        handheldItem(ModItems.BRONZE_PICKAXE);
        handheldItem(ModItems.GILDED_BRONZE_PICKAXE);
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
