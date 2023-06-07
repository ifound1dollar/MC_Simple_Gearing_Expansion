package net.dollar.testmod.datagen;

import net.dollar.testmod.block.ModBlocks;
import net.dollar.testmod.item.ModItems;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    //IMPORTANT: Must register ALL blocks added in the mod here that are not specifically noted as 'no loot table'
    //  else it will throw an exception.
    //ALTERNATIVELY, in ModBlocks, can add 'noLootTable()' property to a block for a block with no loot.



    //generate loot tables for all blocks
    @Override
    protected void generate() {
        dropSelf(ModBlocks.RUBY_BLOCK.get());
        dropSelf(ModBlocks.SAPPHIRE_BLOCK.get());
        dropSelf(ModBlocks.BRONZE_BLOCK.get());

        //ORES (NOTE: OVERRIDE createLapisOreDrops FOR MORE THAN ONE DROP)
        add(ModBlocks.RUBY_ORE.get(),
                (block) -> createOreDrop(ModBlocks.RUBY_ORE.get(), ModItems.RUBY_SHARD.get()));
        add(ModBlocks.SAPPHIRE_ORE.get(),
                (block) -> createOreDrop(ModBlocks.SAPPHIRE_ORE.get(), ModItems.SAPPHIRE_SHARD.get()));
        add(ModBlocks.BRONZE_ORE.get(),
                (block) -> createOreDrop(ModBlocks.BRONZE_ORE.get(), ModItems.RAW_BRONZE.get()));
    }

    //this gets all known blocks from the mod as an iterator
    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
