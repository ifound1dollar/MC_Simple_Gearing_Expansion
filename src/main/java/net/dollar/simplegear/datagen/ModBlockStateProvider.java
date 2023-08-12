package net.dollar.simplegear.datagen;

import net.dollar.simplegear.SimpleGearingExpansion;
import net.dollar.simplegear.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, SimpleGearingExpansion.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        basicBlockWithItem(ModBlocks.RUBY_BLOCK);
        basicBlockWithItem(ModBlocks.RUBY_ORE);
        basicBlockWithItem(ModBlocks.DEEPSLATE_RUBY_ORE);
        basicBlockWithItem(ModBlocks.SAPPHIRE_BLOCK);
        basicBlockWithItem(ModBlocks.SAPPHIRE_ORE);
        basicBlockWithItem(ModBlocks.DEEPSLATE_SAPPHIRE_ORE);
        basicBlockWithItem(ModBlocks.DECORATIVE_AMETHYST_BLOCK);

//        basicBlockWithItem(ModBlocks.CARBONITE_BLOCK);
        basicBlockWithItem(ModBlocks.CARBONITE_ORE);
        basicBlockWithItem(ModBlocks.DEEPSLATE_CARBONITE_ORE);
        basicBlockWithItem(ModBlocks.TIN_BLOCK);
        basicBlockWithItem(ModBlocks.RAW_TIN_BLOCK);
        basicBlockWithItem(ModBlocks.TIN_ORE);
        basicBlockWithItem(ModBlocks.DEEPSLATE_TIN_ORE);
        basicBlockWithItem(ModBlocks.BRONZE_BLOCK);
        basicBlockWithItem(ModBlocks.STEEL_BLOCK);
        basicBlockWithItem(ModBlocks.TUNGSTEN_BLOCK);
        basicBlockWithItem(ModBlocks.RAW_TUNGSTEN_BLOCK);
        basicBlockWithItem(ModBlocks.TUNGSTEN_ORE);
        basicBlockWithItem(ModBlocks.DEEPSLATE_TUNGSTEN_ORE);
    }

    //this method will produce a basic item corresponding to the block
    private void basicBlockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}