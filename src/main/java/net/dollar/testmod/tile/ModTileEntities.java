package net.dollar.testmod.tile;

import net.dollar.testmod.TestMod;
import net.dollar.testmod.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModTileEntities {
    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(
            ForgeRegistries.BLOCK_ENTITY_TYPES, TestMod.MOD_ID);


    public static final RegistryObject<BlockEntityType<ShrineBlockTile>> SHRINE_BLOCK = TILE_ENTITY_TYPES.register(
            "shrine_block",
            () -> BlockEntityType.Builder.of(ShrineBlockTile::new, ModBlocks.SHRINE_BLOCK.get()).build(null));



    public static void register(IEventBus eventBus) {
        TILE_ENTITY_TYPES.register(eventBus);
    }
}
