package net.dollar.testmod.events;

import net.dollar.testmod.TestMod;
import net.dollar.testmod.entity.ModEntities;
import net.dollar.testmod.entity.client.renderer.ModObsidianGolemEntityRenderer;
import net.dollar.testmod.entity.custom.ObsidianGolemEntity;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TestMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModClientEvents {
    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
        event.put(ModEntities.OBSIDIAN_GOLEM.get(), ObsidianGolemEntity.setAttributes());
    }

    @SubscribeEvent
    public static void entityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.OBSIDIAN_GOLEM.get(), ModObsidianGolemEntityRenderer::new);
    }
}
