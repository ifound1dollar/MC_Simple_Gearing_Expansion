package net.dollar.testmod.events;

import net.dollar.testmod.TestMod;
import net.dollar.testmod.entity.ModEntities;
import net.dollar.testmod.entity.client.renderer.ModKathleenTheWickedRenderer;
import net.dollar.testmod.entity.client.renderer.ModObsidianGolemEntityRenderer;
import net.dollar.testmod.entity.client.renderer.ModOldLadyMuffRenderer;
import net.dollar.testmod.entity.custom.KathleenTheWickedEntity;
import net.dollar.testmod.entity.custom.ObsidianGolemEntity;
import net.dollar.testmod.entity.custom.OldLadyMuffEntity;
import net.minecraft.client.renderer.entity.CowRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TestMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModClientEvents {
    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
        event.put(ModEntities.OBSIDIAN_GOLEM.get(), ObsidianGolemEntity.setAttributes());
        event.put(ModEntities.KATHLEEN_THE_WICKED.get(), KathleenTheWickedEntity.setAttributes());
        event.put(ModEntities.OLD_LADY_MUFF.get(), OldLadyMuffEntity.setAttributes());
    }

    @SubscribeEvent
    public static void entitySpawnRestriction(SpawnPlacementRegisterEvent event) {
        event.register(ModEntities.OBSIDIAN_GOLEM.get(), SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ObsidianGolemEntity::checkObsidianGolemSpawnRules,
                SpawnPlacementRegisterEvent.Operation.REPLACE);
    }

    @SubscribeEvent
    public static void entityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.OBSIDIAN_GOLEM.get(), ModObsidianGolemEntityRenderer::new);
        event.registerEntityRenderer(ModEntities.KATHLEEN_THE_WICKED.get(), ModKathleenTheWickedRenderer::new);
        event.registerEntityRenderer(ModEntities.OLD_LADY_MUFF.get(), ModOldLadyMuffRenderer::new);
    }
}
