package net.dollar.simplegear.tile;

import net.dollar.simplegear.SimpleGearingExpansion;
import net.dollar.simplegear.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModTileEntities {
    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(
            ForgeRegistries.BLOCK_ENTITY_TYPES, SimpleGearingExpansion.MOD_ID);


    public static final RegistryObject<BlockEntityType<ModSpectralLanternBlockEntity>> SPECTRAL_LANTERN_TILE = TILE_ENTITY_TYPES.register(
            "spectral_lantern",
            () -> BlockEntityType.Builder.of(ModSpectralLanternBlockEntity::new, ModBlocks.SPECTRAL_LANTERN.get()).build(null));



    public static void register(IEventBus eventBus) {
        TILE_ENTITY_TYPES.register(eventBus);
    }
}
