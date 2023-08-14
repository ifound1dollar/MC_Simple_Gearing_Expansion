package net.dollar.simplegear.worldgen;

import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

/**
 * Contains helper methods for common and rare ore generation (used by ModConfiguredFeatures and ModPlacedFeatures).
 */
public class ModOrePlacement {
    /**
     * Returns list of PlacementModifiers used to generate ore block placements in the world.
     * @param generated PlacementModifier generated in commonOrePlacement or rareOrePlacement
     * @param defined HeightRangePlacement defined by the developer
     * @return Generated List of PlacementModifiers
     */
    public static List<PlacementModifier> orePlacement(PlacementModifier generated, PlacementModifier defined) {
        return List.of(generated, InSquarePlacement.spread(), defined, BiomeFilter.biome());
    }

    /**
     * Returns List of PlacementModifiers, where each item is guaranteed to generate a placement.
     * @param count Number of placements to generate
     * @param placementModifier HeightRangePlacement that determines range to generate
     * @return Generated List of PlacementModifiers
     */
    public static List<PlacementModifier> commonOrePlacement(int count, PlacementModifier placementModifier) {
        return orePlacement(CountPlacement.of(count), placementModifier);
    }

    /**
     * Returns list of PlacementModifiers, where each item has only a chance to generate a placement.
     * @param chance Chance to generate placement (1 / chance)
     * @param placementModifier HeightRangePlacement that determines range to generate
     * @return Generated List of PlacementModifiers
     */
    public static List<PlacementModifier> rareOrePlacement(int chance, PlacementModifier placementModifier) {
        return orePlacement(RarityFilter.onAverageOnceEvery(chance), placementModifier);
    }

}
