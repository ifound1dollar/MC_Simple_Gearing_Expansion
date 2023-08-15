package net.dollar.simplegear.worldgen;

import net.dollar.simplegear.SimpleGearingExpansion;
import net.dollar.simplegear.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

/**
 * ConfiguredFeatures determine which blocks that generated ores can replace AND the size of the veins.
 *  Starting with Minecraft version 1.19, PlacedFeatures are data-driven using JSON files only, and
 *  cannot be adjusted via config files.
 */
public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> RUBY_ORE_KEY = registerKey("ruby_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SAPPHIRE_ORE_KEY = registerKey("sapphire_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CARBONITE_ORE_KEY = registerKey("carbonite_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TIN_ORE_KEY = registerKey("tin_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TIN_ORE_SMALL_KEY = registerKey("tin_ore_small");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TUNGSTEN_ORE_KEY = registerKey("tungsten_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TUNGSTEN_ORE_SMALL_KEY = registerKey("tungsten_ore_small");



    /**
     * Registers new ConfiguredFeatures using one of two helper methods.
     * @param context BootstrapContext of type ConfiguredFeature
     */
    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        //define valid ore replacements here
        RuleTest stoneReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        List<OreConfiguration.TargetBlockState> rubyOres = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.RUBY_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.DEEPSLATE_RUBY_ORE.get().defaultBlockState())
        );
        List<OreConfiguration.TargetBlockState> sapphireOres = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.SAPPHIRE_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.DEEPSLATE_SAPPHIRE_ORE.get().defaultBlockState())
        );
        List<OreConfiguration.TargetBlockState> carboniteOres = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.CARBONITE_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.DEEPSLATE_CARBONITE_ORE.get().defaultBlockState())
        );
        List<OreConfiguration.TargetBlockState> tinOres = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.TIN_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.DEEPSLATE_TIN_ORE.get().defaultBlockState())
        );
        List<OreConfiguration.TargetBlockState> tungstenOres = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.TUNGSTEN_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.DEEPSLATE_TUNGSTEN_ORE.get().defaultBlockState())
        );

        //INT PARAM IS VEIN SIZE
        //OPTIONAL FLOAT PARAM IS CHANCE PER BLOCK TO FAIL WHEN EXPOSED TO AIR

        //3 is equivalent to Emerald vein size
        register(context, CARBONITE_ORE_KEY, Feature.ORE, new OreConfiguration(carboniteOres,
                4));   //default 4
        register(context, RUBY_ORE_KEY, Feature.ORE, new OreConfiguration(rubyOres,
                3));        //default 3
        register(context, SAPPHIRE_ORE_KEY, Feature.ORE, new OreConfiguration(sapphireOres,
                3));    //default 3

        //9 is Iron equivalent (4 is small for Iron and Diamond)
        register(context, TIN_ORE_KEY, Feature.ORE, new OreConfiguration(tinOres,
                6));         //default 6
        register(context, TIN_ORE_SMALL_KEY, Feature.ORE, new OreConfiguration(tinOres,
                3)); //half of default

        //Diamond is 0.7 for 12 (regular), 0.5 for 4 (small)
        register(context, TUNGSTEN_ORE_KEY, Feature.ORE, new OreConfiguration(tungstenOres,
                12, 0.6f));  //default 12
        register(context, TUNGSTEN_ORE_SMALL_KEY, Feature.ORE, new OreConfiguration(tungstenOres,
                4, 0.4f));  //one third of default
    }



    /**
     * Generates a ConfiguredFeature ResourceKey.
     * @param name String corresponding to the ConfiguredFeature's ResourceLocation
     * @return The generated ResourceKey
     */
    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(SimpleGearingExpansion.MOD_ID, name));
    }

    /**
     * Registers a new ConfiguredFeature ResourceKey.
     * @param context The ConfiguredFeature's BoostrapContext
     * @param key ResourceKey of the ConfiguredFeature to register
     * @param feature Feature type (ex. ORE)
     * @param configuration Configuration corresponding to the specific feature to generate (ex. OreConfiguration)
     */
    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(
            BootstapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key,
            F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
