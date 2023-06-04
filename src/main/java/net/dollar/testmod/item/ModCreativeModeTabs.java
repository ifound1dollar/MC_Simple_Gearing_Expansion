package net.dollar.testmod.item;

import net.dollar.testmod.TestMod;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TestMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCreativeModeTabs {
    public static CreativeModeTab TEST_TAB;

    //this SubscribeEvent will fire automatically, loading new creative mode tab(s) (builds icon and title)
    @SubscribeEvent
    public static void registerCreativeModeTabs(CreativeModeTabEvent.Register event) {
        TEST_TAB = event.registerCreativeModeTab(new ResourceLocation(TestMod.MOD_ID, "test_tab"),
                builder -> builder.icon(() -> new ItemStack(ModItems.RUBY_SHARD.get()))
                        .title(Component.literal("Test Tab")));
        //NOTE: Component.translatable is unable to find translation in lang file, must use Component.literal instead
    }
}
