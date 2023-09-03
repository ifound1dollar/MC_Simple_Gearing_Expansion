package net.dollar.simplegear.events;

import net.dollar.simplegear.SimpleGearingExpansion;
import net.dollar.simplegear.entity.ModEntities;
import net.dollar.simplegear.entity.client.renderer.*;
import net.dollar.simplegear.entity.custom.KathleenTheWickedEntity;
import net.dollar.simplegear.entity.custom.ObsidianGolemEntity;
import net.dollar.simplegear.entity.custom.OldLadyMuffEntity;
import net.dollar.simplegear.entity.custom.TheHeliroosterEntity;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Handles three different entity events regarding the newly added mobs: EntityAttributeCreationEvent,
 *  SpawnPlacementRegisterEvent, and EntityRenderersEvent.RegisterRenderers.
 */
@Mod.EventBusSubscriber(modid = SimpleGearingExpansion.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntityEvents {
    /**
     * Sets attributes for all newly added mobs and adds to the event.
     * @param event The fired EntityAttributeCreationEvent
     */
    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
        event.put(ModEntities.OBSIDIAN_GOLEM.get(), ObsidianGolemEntity.setAttributes());

        event.put(ModEntities.KATHLEEN_THE_WICKED.get(), KathleenTheWickedEntity.setAttributes());
        event.put(ModEntities.OLD_LADY_MUFF.get(), OldLadyMuffEntity.setAttributes());
        event.put(ModEntities.THE_HELIROOSTER.get(), TheHeliroosterEntity.setAttributes());
    }

    /**
     * Registers spawn restrictions for new naturally spawning entities (Obsidian Golem).
     * @param event The fired SpawnPlacementRegisterEvent
     */
    @SubscribeEvent
    public static void entitySpawnRestriction(SpawnPlacementRegisterEvent event) {
        event.register(ModEntities.OBSIDIAN_GOLEM.get(), SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ObsidianGolemEntity::checkObsidianGolemSpawnRules,
                SpawnPlacementRegisterEvent.Operation.REPLACE);
    }

    /**
     * Registers renderers for all newly added mobs.
     * @param event The fired EntityRenderersEvent.RegisterRenderers
     */
    @SubscribeEvent
    public static void entityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.TIN_ARROW.get(), ModTinArrowRenderer::new);

        event.registerEntityRenderer(ModEntities.OBSIDIAN_GOLEM.get(), ModObsidianGolemEntityRenderer::new);

        event.registerEntityRenderer(ModEntities.KATHLEEN_THE_WICKED.get(), ModKathleenTheWickedRenderer::new);
        event.registerEntityRenderer(ModEntities.OLD_LADY_MUFF.get(), ModOldLadyMuffRenderer::new);
        event.registerEntityRenderer(ModEntities.THE_HELIROOSTER.get(), ModTheHeliroosterRenderer::new);
    }
}
