package net.dollar.testmod.worldgen;

import net.dollar.testmod.TestMod;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> RUBY_ORE_PLACED_KEY = createKey("ruby_ore_placed");
    public static final ResourceKey<PlacedFeature> SAPPHIRE_ORE_PLACED_KEY = createKey("sapphire_ore_placed");
    public static final ResourceKey<PlacedFeature> CARBONITE_ORE_LOWER_PLACED_KEY = createKey("carbonite_ore_lower_placed");
    public static final ResourceKey<PlacedFeature> CARBONITE_ORE_UPPER_PLACED_KEY = createKey("carbonite_ore_upper_placed");
    public static final ResourceKey<PlacedFeature> TIN_ORE_PLACED_KEY = createKey("tin_ore_placed");
    public static final ResourceKey<PlacedFeature> TIN_ORE_SMALL_PLACED_KEY = createKey("tin_ore_small_placed");
    public static final ResourceKey<PlacedFeature> TUNGSTEN_ORE_PLACED_KEY = createKey("tungsten_ore_placed");
    public static final ResourceKey<PlacedFeature> TUNGSTEN_ORE_SMALL_PLACED_KEY = createKey("tungsten_ore_small_placed");


    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        //FIRST PARAM IS VEIN SIZE, SECOND TWO ARE HEIGHT RANGE WHICH CAN BE TRIANGULAR OR UNIFORM
        //both are as rare as small Diamond BUT triangle base is 16 higher (but very small veins)
        register(context, RUBY_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.RUBY_ORE_KEY),
                ModOrePlacement.commonOrePlacement(7, HeightRangePlacement.triangle(
                                VerticalAnchor.aboveBottom(-64),
                                VerticalAnchor.aboveBottom(80))));
        register(context, SAPPHIRE_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.SAPPHIRE_ORE_KEY),
                ModOrePlacement.commonOrePlacement(7, HeightRangePlacement.triangle(
                                VerticalAnchor.aboveBottom(-64),
                                VerticalAnchor.aboveBottom(80))));

        //uncommon but not rare (only small veins)
        register(context, CARBONITE_ORE_UPPER_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.CARBONITE_ORE_KEY),
                ModOrePlacement.commonOrePlacement(8, HeightRangePlacement.triangle(
                                VerticalAnchor.absolute(-16),
                                VerticalAnchor.absolute(64))));
        register(context, CARBONITE_ORE_LOWER_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.CARBONITE_ORE_KEY),
                ModOrePlacement.commonOrePlacement(8, HeightRangePlacement.uniform(
                                VerticalAnchor.absolute(-64),
                                VerticalAnchor.absolute(16))));

        //less frequent than Iron, and slightly smaller veins
        register(context, TIN_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.TIN_ORE_KEY),
                ModOrePlacement.commonOrePlacement(6, HeightRangePlacement.triangle(
                                VerticalAnchor.absolute(16),
                                VerticalAnchor.absolute(96))));
        register(context, TIN_ORE_SMALL_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.TIN_ORE_SMALL_KEY),
                ModOrePlacement.commonOrePlacement(8, HeightRangePlacement.uniform(
                                VerticalAnchor.bottom(),
                                VerticalAnchor.absolute(32))));

        //triangle base higher than Diamond, and average slightly larger veins
        register(context, TUNGSTEN_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.TUNGSTEN_ORE_KEY),
                ModOrePlacement.commonOrePlacement(7, HeightRangePlacement.triangle(
                                VerticalAnchor.aboveBottom(-64),
                                VerticalAnchor.aboveBottom(80))));
        register(context, TUNGSTEN_ORE_SMALL_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.TUNGSTEN_ORE_SMALL_KEY),
                ModOrePlacement.rareOrePlacement(9, HeightRangePlacement.triangle(
                                VerticalAnchor.aboveBottom(-64),
                                VerticalAnchor.aboveBottom(80))));
    }



    private static ResourceKey<PlacedFeature> createKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(TestMod.MOD_ID, name));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key,
                                 Holder<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key,
                                 Holder<ConfiguredFeature<?, ?>> configuration, PlacementModifier... modifiers) {
        register(context, key, configuration, List.of(modifiers));
    }
}
