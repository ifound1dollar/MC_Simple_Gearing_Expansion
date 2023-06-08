package net.dollar.testmod.datagen;

import net.dollar.testmod.TestMod;
import net.dollar.testmod.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, TestMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        basicBlockWithItem(ModBlocks.RUBY_BLOCK);
        basicBlockWithItem(ModBlocks.RUBY_ORE);
        basicBlockWithItem(ModBlocks.SAPPHIRE_BLOCK);
        basicBlockWithItem(ModBlocks.SAPPHIRE_ORE);
        basicBlockWithItem(ModBlocks.TIN_BLOCK);
        basicBlockWithItem(ModBlocks.TIN_ORE);
        basicBlockWithItem(ModBlocks.BRONZE_BLOCK);
        basicBlockWithItem(ModBlocks.TUNGSTEN_BLOCK);
        basicBlockWithItem(ModBlocks.TUNGSTEN_ORE);
    }

    //this method will produce a basic item corresponding to the block
    private void basicBlockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}
