package net.dollar.simplegear.entity;

import net.dollar.simplegear.SimpleGearingExpansion;
import net.dollar.simplegear.entity.custom.KathleenTheWickedEntity;
import net.dollar.simplegear.entity.custom.ObsidianGolemEntity;
import net.dollar.simplegear.entity.custom.OldLadyMuffEntity;
import net.dollar.simplegear.entity.custom.TheHeliroosterEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Handles registering new mob entities.
 */
public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, SimpleGearingExpansion.MOD_ID);



    public static final RegistryObject<EntityType<ObsidianGolemEntity>> OBSIDIAN_GOLEM =
            ENTITY_TYPES.register("obsidian_golem",
                    () -> EntityType.Builder.of(ObsidianGolemEntity::new, MobCategory.MONSTER)
                            .fireImmune()
                            .sized(1.67f, 3.33f)   //roughly 1.25x Iron Golem hitbox size, but narrower
                            .build(new ResourceLocation(SimpleGearingExpansion.MOD_ID + "obsidian_golem").toString()));

    public static final RegistryObject<EntityType<KathleenTheWickedEntity>> KATHLEEN_THE_WICKED =
            ENTITY_TYPES.register("kathleen_the_wicked",
                    () -> EntityType.Builder.of(KathleenTheWickedEntity::new, MobCategory.MONSTER)
                            .build(new ResourceLocation(SimpleGearingExpansion.MOD_ID + "kathleen_the_wicked").toString()));
    public static final RegistryObject<EntityType<OldLadyMuffEntity>> OLD_LADY_MUFF =
            ENTITY_TYPES.register("old_lady_muff",
                    () -> EntityType.Builder.of(OldLadyMuffEntity::new, MobCategory.MONSTER)
                            .build(new ResourceLocation(SimpleGearingExpansion.MOD_ID + "old_lady_muff").toString()));
    public static final RegistryObject<EntityType<TheHeliroosterEntity>> THE_HELIROOSTER =
            ENTITY_TYPES.register("the_helirooster",
                    () -> EntityType.Builder.of(TheHeliroosterEntity::new, MobCategory.MONSTER)
                            .build(new ResourceLocation(SimpleGearingExpansion.MOD_ID + "the_helirooster").toString()));



    /**
     * Register new mob entities.
     * @param eventBus Main event bus
     */
    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
