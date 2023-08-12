package net.dollar.simplegear.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ModCommonConfigs {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;



    //region Ore Generation
    public static final ForgeConfigSpec.ConfigValue<Integer> CARBONITE_ORE_VEINS_PER_CHUNK;
    public static final ForgeConfigSpec.ConfigValue<Integer> CARBONITE_ORE_VEIN_SIZE;
    public static final ForgeConfigSpec.ConfigValue<Integer> RUBY_ORE_VEINS_PER_CHUNK;
    public static final ForgeConfigSpec.ConfigValue<Integer> RUBY_ORE_VEIN_SIZE;
    public static final ForgeConfigSpec.ConfigValue<Integer> SAPPHIRE_ORE_VEINS_PER_CHUNK;
    public static final ForgeConfigSpec.ConfigValue<Integer> SAPPHIRE_ORE_VEIN_SIZE;
    public static final ForgeConfigSpec.ConfigValue<Integer> TIN_ORE_VEINS_PER_CHUNK;
    public static final ForgeConfigSpec.ConfigValue<Integer> TIN_ORE_VEIN_SIZE;
    public static final ForgeConfigSpec.ConfigValue<Integer> TUNGSTEN_ORE_VEINS_PER_CHUNK;
    public static final ForgeConfigSpec.ConfigValue<Integer> TUNGSTEN_ORE_VEIN_SIZE;
    //endregion
    //region Endgame Tier Tool/Weapon Effect Chances AND Armor Set Damage Reduction
    public static final ForgeConfigSpec.ConfigValue<Integer> INFUSED_DIAMOND_EFFECT_CHANCE;
    public static final ForgeConfigSpec.ConfigValue<Integer> INFUSED_DIAMOND_MAGIC_DAMAGE_REDUCTION;
    public static final ForgeConfigSpec.ConfigValue<Integer> NETHERITE_EFFECT_CHANCE;
    public static final ForgeConfigSpec.ConfigValue<Integer> NETHERITE_FIRE_DAMAGE_REDUCTION;
    public static final ForgeConfigSpec.ConfigValue<Integer> TUNGSTEN_CARBIDE_EFFECT_CHANCE;
    public static final ForgeConfigSpec.ConfigValue<Integer> TUNGSTEN_CARBIDE_EXPLOSION_DAMAGE_REDUCTION;
    //endregion



    static {
        BUILDER.push("Common configs for Simple Gearing Expansion");
        BUILDER.pop();



        //DEFINE CONFIGS HERE
        //region Ore Generation
        BUILDER.push("Ore Generation");

        BUILDER.push("Carbonite Ore");
        CARBONITE_ORE_VEINS_PER_CHUNK = BUILDER.comment("Number of Carbonite Ore veins that will spawn per chunk")
                .define("Veins Per Chunk", 16);
        CARBONITE_ORE_VEIN_SIZE = BUILDER.comment("Size of Carbonite Ore veins")
                .defineInRange("Vein Size", 4, 2, 10);
        BUILDER.pop();

        BUILDER.push("Ruby Ore");
        RUBY_ORE_VEINS_PER_CHUNK = BUILDER.comment("Number of Ruby Ore veins that will spawn per chunk")
                .define("Veins Per Chunk", 7);
        RUBY_ORE_VEIN_SIZE = BUILDER.comment("Size of Ruby Ore veins")
                .defineInRange("Vein Size", 3, 2, 10);
        BUILDER.pop();

        BUILDER.push("Sapphire Ore");
        SAPPHIRE_ORE_VEINS_PER_CHUNK = BUILDER.comment("Number of Sapphire Ore veins that will spawn per chunk")
                .define("Veins Per Chunk", 7);
        SAPPHIRE_ORE_VEIN_SIZE = BUILDER.comment("Size of Sapphire Ore veins")
                .defineInRange("Vein Size", 3, 2, 10);
        BUILDER.pop();

        BUILDER.push("Tin Ore");
        TIN_ORE_VEINS_PER_CHUNK = BUILDER.comment("Number of Tin Ore veins that will spawn per chunk")
                        .define("Veins Per Chunk", 12);
        TIN_ORE_VEIN_SIZE = BUILDER.comment("Size of regular Tin Ore veins")
                        .defineInRange("Vein Size", 6, 3, 20);
        BUILDER.pop();

        BUILDER.push("Tungsten Ore");
        TUNGSTEN_ORE_VEINS_PER_CHUNK = BUILDER.comment("Number of Tungsten Ore veins that will spawn per chunk")
                .define("Veins Per Chunk", 14); //NOTE: includes rare ore generation (way less)
        TUNGSTEN_ORE_VEIN_SIZE = BUILDER.comment("Size of regular Tungsten Ore veins")
                .defineInRange("Vein Size", 12, 4, 20);
        BUILDER.pop();

        BUILDER.pop();
        //endregion
        //region Endgame Tier Tool/Weapon Effect Chances AND Armor Set Damage Reduction
        BUILDER.push("Endgame Tier Tool/Weapon Effect Chances AND Armor Set Damage Reduction");

        BUILDER.push("Infused Diamond tier");
        INFUSED_DIAMOND_EFFECT_CHANCE = BUILDER.comment("Chance (out of 100) to apply special effect on-hit with Infused Diamond tools and weapons")
                .defineInRange("Effect Chance", 15, 0, 100);
        INFUSED_DIAMOND_MAGIC_DAMAGE_REDUCTION = BUILDER.comment("Percentage (out of 100) Magic damage reduction granted by a full set of Infused Diamond armor")
                .defineInRange("Percent Damage Reduction", 33, 0, 100);
        BUILDER.pop();

        BUILDER.push("Netherite tier");
        NETHERITE_EFFECT_CHANCE = BUILDER.comment("Chance (out of 100) to apply special effect on-hit with Netherite tools and weapons")
                .defineInRange("Effect Chance", 20, 0, 100);
        NETHERITE_FIRE_DAMAGE_REDUCTION = BUILDER.comment("Percentage (out of 100) Fire damage reduction granted by a full set of Netherite armor")
                .defineInRange("Percent Damage Reduction", 33, 0, 100);
        BUILDER.pop();

        BUILDER.push("Tungsten-Carbide tier");
        TUNGSTEN_CARBIDE_EFFECT_CHANCE = BUILDER.comment("Chance (out of 100) to apply special effect on-hit with Tungsten-Carbide tools and weapons")
                .defineInRange("Effect Chance", 25, 0, 100);
        TUNGSTEN_CARBIDE_EXPLOSION_DAMAGE_REDUCTION = BUILDER.comment("Percentage (out of 100) Explosion damage reduction granted by a full set of Tungsten-Carbide armor")
                .defineInRange("Percent Damage Reduction", 33, 0, 100);
        BUILDER.pop();

        BUILDER.pop();
        //endregion



        SPEC = BUILDER.build();
    }
}
