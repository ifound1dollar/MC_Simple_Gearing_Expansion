package net.dollar.testmod.entity;

import net.dollar.testmod.TestMod;
import net.dollar.testmod.entity.custom.KathleenTheWickedEntity;
import net.dollar.testmod.entity.custom.ObsidianGolemEntity;
import net.dollar.testmod.entity.custom.OldLadyMuffEntity;
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
                            .sized(1.67f, 3.33f)   //roughly 1.25x Iron Golem hitbox size, but narrower
                            .build(new ResourceLocation(TestMod.MOD_ID + "obsidian_golem").toString()));
    public static final RegistryObject<EntityType<KathleenTheWickedEntity>> KATHLEEN_THE_WICKED =
            ENTITY_TYPES.register("kathleen_the_wicked",
                    () -> EntityType.Builder.of(KathleenTheWickedEntity::new, MobCategory.MONSTER)
                            .build(new ResourceLocation(TestMod.MOD_ID + "kathleen_the_wicked").toString()));
    public static final RegistryObject<EntityType<OldLadyMuffEntity>> OLD_LADY_MUFF =
            ENTITY_TYPES.register("old_lady_muff",
                    () -> EntityType.Builder.of(OldLadyMuffEntity::new, MobCategory.MONSTER)
                            .build(new ResourceLocation(TestMod.MOD_ID + "old_lady_muff").toString()));


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
