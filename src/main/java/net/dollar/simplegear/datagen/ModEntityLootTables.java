package net.dollar.simplegear.datagen;

import net.dollar.simplegear.entity.ModEntities;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

/**
 * Used to auto-generate entity loot JSON files in 'src/generated' subdirectory. In-code definitions
 *  of loot tables to be generated are contained within this class.
 */
public class ModEntityLootTables extends EntityLootSubProvider {
    protected ModEntityLootTables() {
        super(FeatureFlags.REGISTRY.allFlags());
    }



    /**
     * Generate loot tables for each new Entity.
     */
    @Override
    public void generate() {
        add(ModEntities.OBSIDIAN_GOLEM.get(), LootTable.lootTable());   //empty loot table, drops custom loot in class

        add(ModEntities.KATHLEEN_THE_WICKED.get(), LootTable.lootTable());
        add(ModEntities.OLD_LADY_MUFF.get(), LootTable.lootTable());
        add(ModEntities.THE_HELIROOSTER.get(), LootTable.lootTable());
    }



    /**
     * Gets all known EntityTypes added by the mod as a Stream.
     * @return Stream of mod's EntityTypes
     */
    @Override
    protected @NotNull Stream<EntityType<?>> getKnownEntityTypes() {
        return ModEntities.ENTITY_TYPES.getEntries().stream().map(RegistryObject::get);
    }
}
