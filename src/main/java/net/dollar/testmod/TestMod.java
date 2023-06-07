package net.dollar.testmod;

import com.mojang.logging.LogUtils;
import net.dollar.testmod.block.ModBlocks;
import net.dollar.testmod.item.ModCreativeModeTabs;
import net.dollar.testmod.item.ModItems;
import net.dollar.testmod.util.ModTags;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TestMod.MOD_ID)
public class TestMod
{
    public static final String MOD_ID = "testmod";
    private static final Logger LOGGER = LogUtils.getLogger();


    public TestMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        //register new items in ModItems class and new blocks in ModBlocks class
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }

    private void addCreative(CreativeModeTabEvent.BuildContents event)
    {
        //FOR NOW, KEEP ALL NEW ITEMS IN NEW TAB BELOW
        /*if (event.getTab() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.RUBY_SHARD);
            event.accept(ModItems.RUBY_GEM);
            event.accept(ModItems.AMETHYST_GEM);
        }*/

        //add to TEST_TAB
        if (event.getTab() == ModCreativeModeTabs.TEST_TAB) {
            //BLOCKS
            event.accept(ModBlocks.RUBY_BLOCK);
            event.accept(ModBlocks.RUBY_ORE);
            event.accept(ModBlocks.SAPPHIRE_BLOCK);
            event.accept(ModBlocks.SAPPHIRE_ORE);
            event.accept(ModBlocks.BRONZE_BLOCK);
            event.accept(ModBlocks.BRONZE_ORE);


            //ITEMS
            event.accept(ModItems.RUBY_SHARD);
            event.accept(ModItems.SAPPHIRE_SHARD);
            event.accept(ModItems.RAW_BRONZE);
            event.accept(ModItems.BRONZE_INGOT);

            //TOOLS, ARMOR
            event.accept(ModItems.BRONZE_PICKAXE);
            event.accept(ModItems.BRONZE_HELMET);
            event.accept(ModItems.BRONZE_CHESTPLATE);
            event.accept(ModItems.BRONZE_LEGGINGS);
            event.accept(ModItems.BRONZE_BOOTS);
        }
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {

        }
    }
}
