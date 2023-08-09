package net.dollar.testmod;

import com.mojang.logging.LogUtils;
import net.dollar.testmod.block.ModBlocks;
import net.dollar.testmod.config.ModCommonConfigs;
import net.dollar.testmod.entity.ModEntities;
import net.dollar.testmod.tile.ModTileEntities;
import net.dollar.testmod.enchantment.ModEnchantments;
import net.dollar.testmod.item.ModCreativeModeTabs;
import net.dollar.testmod.item.ModItems;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
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
        ModCreativeModeTabs.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModEnchantments.register(modEventBus);
        ModTileEntities.register(modEventBus);
        ModEntities.register(modEventBus);

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        //config registration
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ModCommonConfigs.SPEC,
                "simple_gearing_expansion-common.toml");
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }

    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        //FOR NOW, KEEP ALL NEW ITEMS IN NEW TAB BELOW
        /*if (event.getTab() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.RUBY_SHARD);
            event.accept(ModItems.RUBY_GEM);
            event.accept(ModItems.AMETHYST_GEM);
        }*/

        //add to TEST_TAB
        if (event.getTab() == ModCreativeModeTabs.SGE_TAB.get()) {
            //region BLOCKS
            event.accept(ModBlocks.CARBONITE_ORE);
            event.accept(ModBlocks.DEEPSLATE_CARBONITE_ORE);
            event.accept(ModBlocks.RUBY_ORE);
            event.accept(ModBlocks.DEEPSLATE_RUBY_ORE);
            event.accept(ModBlocks.SAPPHIRE_ORE);
            event.accept(ModBlocks.DEEPSLATE_SAPPHIRE_ORE);
            event.accept(ModBlocks.TIN_ORE);
            event.accept(ModBlocks.DEEPSLATE_TIN_ORE);
            event.accept(ModBlocks.TUNGSTEN_ORE);
            event.accept(ModBlocks.DEEPSLATE_TUNGSTEN_ORE);

            event.accept(ModBlocks.DECORATIVE_AMETHYST_BLOCK);
            event.accept(ModBlocks.RUBY_BLOCK);
            event.accept(ModBlocks.SAPPHIRE_BLOCK);
            event.accept(ModBlocks.TIN_BLOCK);
            event.accept(ModBlocks.RAW_TIN_BLOCK);
            event.accept(ModBlocks.BRONZE_BLOCK);
            event.accept(ModBlocks.STEEL_BLOCK);
            event.accept(ModBlocks.TUNGSTEN_BLOCK);
            event.accept(ModBlocks.RAW_TUNGSTEN_BLOCK);

            event.accept(ModBlocks.SPECTRAL_LANTERN);
            //endregion




            //region BASIC ITEMS
            event.accept(ModItems.TIN_SHEARS);
            event.accept(ModItems.OBSIDIAN_GOLEM_SPAWN_EGG);

            event.accept(ModItems.RUBY);
            event.accept(ModItems.SAPPHIRE);
            event.accept(ModItems.CARBONITE_DUST);
            event.accept(ModItems.RAW_TIN);
            event.accept(ModItems.TIN_INGOT);
            event.accept(ModItems.BRONZE_COMPOUND);
            event.accept(ModItems.BRONZE_INGOT);
            event.accept(ModItems.STEEL_COMPOUND);
            event.accept(ModItems.STEEL_INGOT);
            event.accept(ModItems.RAW_TUNGSTEN);
            event.accept(ModItems.TUNGSTEN_INGOT);

            event.accept(ModItems.COMPOUND_GEMSTONE);
            event.accept(ModItems.INFUSED_GEMSTONE);
            event.accept(ModItems.MOLTEN_CORE);
            event.accept(ModItems.TUNGSTEN_CARBIDE_INGOT);

            event.accept(ModItems.BASIC_UPGRADE_TEMPLATE);
            event.accept(ModItems.GILDED_UPGRADE_SMITHING_TEMPLATE);
            event.accept(ModItems.CARBIDE_UPGRADE_SMITHING_TEMPLATE);
            event.accept(ModItems.INFUSION_UPGRADE_SMITHING_TEMPLATE);
            //endregion


            //region NEW VANILLA TIER MACES
            event.accept(ModItems.STONE_MACE);
            event.accept(ModItems.IRON_MACE);
            event.accept(ModItems.GOLD_MACE);
            event.accept(ModItems.DIAMOND_MACE);
            event.accept(ModItems.NETHERITE_MACE);
            //endregion
            //region BRONZE ARMOR/TOOLS
            event.accept(ModItems.BRONZE_HELMET);
            event.accept(ModItems.BRONZE_CHESTPLATE);
            event.accept(ModItems.BRONZE_LEGGINGS);
            event.accept(ModItems.BRONZE_BOOTS);
            event.accept(ModItems.BRONZE_AXE);
            event.accept(ModItems.BRONZE_HOE);
            event.accept(ModItems.BRONZE_MACE);
            event.accept(ModItems.BRONZE_PICKAXE);
            event.accept(ModItems.BRONZE_SHOVEL);
            event.accept(ModItems.BRONZE_SWORD);
            //endregion
            //region GILDED BRONZE ARMOR/TOOLS
            event.accept(ModItems.GILDED_BRONZE_HELMET);
            event.accept(ModItems.GILDED_BRONZE_CHESTPLATE);
            event.accept(ModItems.GILDED_BRONZE_LEGGINGS);
            event.accept(ModItems.GILDED_BRONZE_BOOTS);
            event.accept(ModItems.GILDED_BRONZE_AXE);
            event.accept(ModItems.GILDED_BRONZE_HOE);
            event.accept(ModItems.GILDED_BRONZE_MACE);
            event.accept(ModItems.GILDED_BRONZE_PICKAXE);
            event.accept(ModItems.GILDED_BRONZE_SHOVEL);
            event.accept(ModItems.GILDED_BRONZE_SWORD);
            //endregion
            //region STEEL ARMOR/TOOLS
            event.accept(ModItems.STEEL_HELMET);
            event.accept(ModItems.STEEL_CHESTPLATE);
            event.accept(ModItems.STEEL_LEGGINGS);
            event.accept(ModItems.STEEL_BOOTS);
            event.accept(ModItems.STEEL_AXE);
            event.accept(ModItems.STEEL_HOE);
            event.accept(ModItems.STEEL_MACE);
            event.accept(ModItems.STEEL_PICKAXE);
            event.accept(ModItems.STEEL_SHOVEL);
            event.accept(ModItems.STEEL_SWORD);
            //endregion
            //region TUNGSTEN ARMOR/TOOLS
            event.accept(ModItems.TUNGSTEN_HELMET);
            event.accept(ModItems.TUNGSTEN_CHESTPLATE);
            event.accept(ModItems.TUNGSTEN_LEGGINGS);
            event.accept(ModItems.TUNGSTEN_BOOTS);
            event.accept(ModItems.TUNGSTEN_AXE);
            event.accept(ModItems.TUNGSTEN_HOE);
            event.accept(ModItems.TUNGSTEN_MACE);
            event.accept(ModItems.TUNGSTEN_PICKAXE);
            event.accept(ModItems.TUNGSTEN_SHOVEL);
            event.accept(ModItems.TUNGSTEN_SWORD);
            //endregion
            //region INFUSED DIAMOND ARMOR/TOOLS
            event.accept(ModItems.INFUSED_DIAMOND_HELMET);
            event.accept(ModItems.INFUSED_DIAMOND_CHESTPLATE);
            event.accept(ModItems.INFUSED_DIAMOND_LEGGINGS);
            event.accept(ModItems.INFUSED_DIAMOND_BOOTS);
            event.accept(ModItems.INFUSED_DIAMOND_AXE);
            event.accept(ModItems.INFUSED_DIAMOND_HOE);
            event.accept(ModItems.INFUSED_DIAMOND_MACE);
            event.accept(ModItems.INFUSED_DIAMOND_PICKAXE);
            event.accept(ModItems.INFUSED_DIAMOND_SHOVEL);
            event.accept(ModItems.INFUSED_DIAMOND_SWORD);
            //endregion
            //region TUNGSTEN-CARBIDE ARMOR/TOOLS
            event.accept(ModItems.TUNGSTEN_CARBIDE_HELMET);
            event.accept(ModItems.TUNGSTEN_CARBIDE_CHESTPLATE);
            event.accept(ModItems.TUNGSTEN_CARBIDE_LEGGINGS);
            event.accept(ModItems.TUNGSTEN_CARBIDE_BOOTS);
            event.accept(ModItems.TUNGSTEN_CARBIDE_AXE);
            event.accept(ModItems.TUNGSTEN_CARBIDE_HOE);
            event.accept(ModItems.TUNGSTEN_CARBIDE_MACE);
            event.accept(ModItems.TUNGSTEN_CARBIDE_PICKAXE);
            event.accept(ModItems.TUNGSTEN_CARBIDE_SHOVEL);
            event.accept(ModItems.TUNGSTEN_CARBIDE_SWORD);
            //endregion

            //region COLLECTOR ITEMS
            event.accept(ModItems.COLLECTOR_KATHLEENS_LOST_DIADEM);
            event.accept(ModItems.COLLECTOR_POTION_OF_EVERLASTING_YOUTH);
            event.accept(ModItems.COLLECTOR_SLIGHTLY_OVERCOOKED_CHICKEN);
            //endregion
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
