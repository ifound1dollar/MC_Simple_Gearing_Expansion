package net.dollar.testmod.events;

import net.dollar.testmod.entity.custom.ObsidianGolemEntity;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FallDamageHandler {
    @SubscribeEvent
    public static void fallDamageHandler(LivingFallEvent event) {
        if (event.getEntity() instanceof ObsidianGolemEntity) {
            //make Obsidian Golem immune to fall damage
            event.setCanceled(true);
        }
    }
}
