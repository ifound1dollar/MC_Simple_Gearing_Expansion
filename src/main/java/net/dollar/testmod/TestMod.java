package net.dollar.testmod;

import com.mojang.logging.LogUtils;
import net.dollar.testmod.block.ModBlocks;
import net.dollar.testmod.item.ModCreativeModeTabs;
import net.dollar.testmod.item.ModItems;
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

            event.accept(ModBlocks.CARBONITE_ORE);
            event.accept(ModBlocks.TIN_BLOCK);
            event.accept(ModBlocks.TIN_ORE);
            event.accept(ModBlocks.BRONZE_BLOCK);
            event.accept(ModBlocks.STEEL_BLOCK);
            event.accept(ModBlocks.TUNGSTEN_BLOCK);
            event.accept(ModBlocks.TUNGSTEN_ORE);
            event.accept(ModBlocks.TUNGSTEN_CARBIDE_BLOCK);


            //ITEMS
            event.accept(ModItems.RUBY);
            event.accept(ModItems.SAPPHIRE);
            event.accept(ModItems.INFUSED_GEMSTONE);
            event.accept(ModItems.NAMELESS_INFUSION_ITEM);

            event.accept(ModItems.CARBONITE_DUST);
            event.accept(ModItems.RAW_TIN);
            event.accept(ModItems.TIN_INGOT);
            event.accept(ModItems.BRONZE_COMPOUND);
            event.accept(ModItems.BRONZE_INGOT);
            event.accept(ModItems.STEEL_COMPOUND);
            event.accept(ModItems.STEEL_INGOT);
            event.accept(ModItems.RAW_TUNGSTEN);
            event.accept(ModItems.TUNGSTEN_INGOT);
            event.accept(ModItems.TUNGSTEN_CARBIDE_COMPOUND);
            event.accept(ModItems.TUNGSTEN_CARBIDE_INGOT);

            //ARMOR, TOOLS
            event.accept(ModItems.BRONZE_HELMET);
            event.accept(ModItems.BRONZE_CHESTPLATE);
            event.accept(ModItems.BRONZE_LEGGINGS);
            event.accept(ModItems.BRONZE_BOOTS);
            event.accept(ModItems.BRONZE_PICKAXE);

            event.accept(ModItems.GILDED_BRONZE_HELMET);
            event.accept(ModItems.GILDED_BRONZE_CHESTPLATE);
            event.accept(ModItems.GILDED_BRONZE_LEGGINGS);
            event.accept(ModItems.GILDED_BRONZE_BOOTS);
            event.accept(ModItems.GILDED_BRONZE_PICKAXE);

            event.accept(ModItems.STEEL_HELMET);
            event.accept(ModItems.STEEL_CHESTPLATE);
            event.accept(ModItems.STEEL_LEGGINGS);
            event.accept(ModItems.STEEL_BOOTS);

            event.accept(ModItems.TUNGSTEN_HELMET);
            event.accept(ModItems.TUNGSTEN_CHESTPLATE);
            event.accept(ModItems.TUNGSTEN_LEGGINGS);
            event.accept(ModItems.TUNGSTEN_BOOTS);

            event.accept(ModItems.TUNGSTEN_CARBIDE_HELMET);
            event.accept(ModItems.TUNGSTEN_CARBIDE_CHESTPLATE);
            event.accept(ModItems.TUNGSTEN_CARBIDE_LEGGINGS);
            event.accept(ModItems.TUNGSTEN_CARBIDE_BOOTS);

            event.accept(ModItems.INFUSED_DIAMOND_HELMET);
            event.accept(ModItems.INFUSED_DIAMOND_CHESTPLATE);
            event.accept(ModItems.INFUSED_DIAMOND_LEGGINGS);
            event.accept(ModItems.INFUSED_DIAMOND_BOOTS);
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
