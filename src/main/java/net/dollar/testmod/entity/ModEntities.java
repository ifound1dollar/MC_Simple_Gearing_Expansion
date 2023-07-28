package net.dollar.testmod.entity;

import net.dollar.testmod.TestMod;
import net.dollar.testmod.entity.custom.ObsidianGolemEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, TestMod.MOD_ID);


    public static final RegistryObject<EntityType<ObsidianGolemEntity>> OBSIDIAN_GOLEM =
            ENTITY_TYPES.register("obsidian_golem",
                    () -> EntityType.Builder.of(ObsidianGolemEntity::new, MobCategory.MONSTER)
                            .fireImmune()
                            .sized(1.75f, 4f)   //roughly 1.5x Iron Golem hitbox size, but less wide
                            .build(new ResourceLocation(TestMod.MOD_ID + "obsidian_golem").toString()));


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
