package net.dollar.simplegear.item;

import net.dollar.simplegear.SimpleGearingExpansion;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

/**
 * Handles registering new creative mode tab(s), run automatically as creative mode tabs are registered.
 */
@Mod.EventBusSubscriber(modid = SimpleGearingExpansion.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SimpleGearingExpansion.MOD_ID);

    public static RegistryObject<CreativeModeTab> SIMPLEGEAR_TAB = CREATIVE_MODE_TABS.register("simplegear_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.RUBY.get()))
                    .title(Component.literal("Simple Gearing Expansion")).build());



    /**
     * Register new creative mode tab(s).
     * @param eventBus Main event bus
     */
    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
