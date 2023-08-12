package net.dollar.simplegear.datagen;

import net.dollar.simplegear.entity.ModEntities;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public class ModEntityLootTables extends EntityLootSubProvider {
    protected ModEntityLootTables() {
        super(FeatureFlags.REGISTRY.allFlags());
    }


    //generate loot tables for entities
    @Override
    public void generate() {
//        add(ModEntities.OBSIDIAN_GOLEM.get(), LootTable.lootTable().withPool(LootPool.lootPool()
//                        .setRolls(ConstantValue.exactly(1.0F))
//                        .add(LootItem.lootTableItem(ModItems.MOLTEN_CORE.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
//                        .when(LootItemKilledByPlayerCondition.killedByPlayer())));

        add(ModEntities.OBSIDIAN_GOLEM.get(), LootTable.lootTable());   //drops custom loot (method) instead of this^

        add(ModEntities.KATHLEEN_THE_WICKED.get(), LootTable.lootTable());
        add(ModEntities.OLD_LADY_MUFF.get(), LootTable.lootTable());
        add(ModEntities.THE_HELIROOSTER.get(), LootTable.lootTable());
    }



    //this gets all known entities from the mod as a stream
    @Override
    protected @NotNull Stream<EntityType<?>> getKnownEntityTypes() {
        return ModEntities.ENTITY_TYPES.getEntries().stream().map(RegistryObject::get);
    }
}
