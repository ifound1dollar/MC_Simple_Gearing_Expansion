package net.dollar.simplegear.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.packs.VanillaChestLoot;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;

/**
 * Handles loot table generation for Blocks and Entities (can support any loot table type).
 */
public class ModLootTableProvider {
    //auto-generates loot tables of the type(s) provided in the method
    public static LootTableProvider create(PackOutput output) {
        return new LootTableProvider(output, Set.of(),
                List.of(new LootTableProvider.SubProviderEntry(ModBlockLootTables::new, LootContextParamSets.BLOCK),
                        new LootTableProvider.SubProviderEntry(ModEntityLootTables::new, LootContextParamSets.ENTITY)));

        //NOTE: this is the way to make new loot tables for vanilla chests, can be extended to include any
        //  new mod chest loot tables
//        return new LootTableProvider(output, Set.of(),
//                List.of(new LootTableProvider.SubProviderEntry(ModBlockLootTables::new, LootContextParamSets.BLOCK),
//                        new LootTableProvider.SubProviderEntry(VanillaChestLoot::new, LootContextParamSets.CHEST)));
    }
}
