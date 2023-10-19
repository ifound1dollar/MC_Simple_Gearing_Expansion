package net.dollar.simplegear.config;

import com.electronwill.nightconfig.core.Config;
import net.minecraftforge.common.ForgeConfigSpec;

/**
 * Handles creating new common (both sides) configs file for the mod.
 */
public class ModCommonConfigs {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;



    public static final ForgeConfigSpec.ConfigValue<Integer> BASIC_UPGRADE_TEMPLATE_DROP_CHANCE;
    //region Endgame Equipment Tier configs
    public static final ForgeConfigSpec.ConfigValue<Integer> ENDGAME_TIER_EFFECT_SECONDS;
    public static final ForgeConfigSpec.ConfigValue<Integer> INFUSED_DIAMOND_EFFECT_CHANCE;
    public static final ForgeConfigSpec.ConfigValue<Integer> INFUSED_DIAMOND_MAGIC_DAMAGE_REDUCTION;
    public static final ForgeConfigSpec.ConfigValue<Integer> NETHERITE_EFFECT_CHANCE;
    public static final ForgeConfigSpec.ConfigValue<Integer> NETHERITE_FIRE_DAMAGE_REDUCTION;
    public static final ForgeConfigSpec.ConfigValue<Integer> TUNGSTEN_CARBIDE_EFFECT_CHANCE;
    public static final ForgeConfigSpec.ConfigValue<Integer> TUNGSTEN_CARBIDE_EXPLOSION_DAMAGE_REDUCTION;
    //endregion



    //ACTUALLY CREATE CONFIG FILE HERE
    static {
        Config.setInsertionOrderPreserved(true);


        //DEFINE CONFIGS HERE

        BUILDER.comment("Basic Upgrade Template").push("basic_upgrade_template");
        BASIC_UPGRADE_TEMPLATE_DROP_CHANCE = BUILDER.comment("Drop chance (%) of Basic Upgrade Template from Monsters")
                .defineInRange("basic_template_drop_chance", 1, 0, 100);
        BUILDER.pop();

        //region Endgame Equipment Tiers
        BUILDER.comment("Endgame Equipment Tiers").push("endgame_tiers");
        ENDGAME_TIER_EFFECT_SECONDS = BUILDER.comment("Duration (in seconds) of effect applied by endgame tools and weapons")
                        .defineInRange("endgame_tool_effect_duration", 4, 1, 10);
        BUILDER.pop();

        BUILDER.comment("Infused Diamond tier").push("infused_diamond");
        INFUSED_DIAMOND_EFFECT_CHANCE = BUILDER.comment("Chance (out of 100) to apply special effect on-hit with" +
                        " Infused Diamond tools and weapons")
                .defineInRange("infused_diamond_effect_chance", 17, 0, 100);
        INFUSED_DIAMOND_MAGIC_DAMAGE_REDUCTION = BUILDER.comment("Percentage (out of 100) Magic damage reduction" +
                        " granted by a full set of Infused Diamond armor")
                .defineInRange("infused_diamond_damage_reduction", 33, 0, 100);
        BUILDER.pop();

        BUILDER.comment("Netherite tier").push("netherite");
        NETHERITE_EFFECT_CHANCE = BUILDER.comment("Chance (out of 100) to apply special effect on-hit with Netherite" +
                        " tools and weapons")
                .defineInRange("netherite_effect_chance", 20, 0, 100);
        NETHERITE_FIRE_DAMAGE_REDUCTION = BUILDER.comment("Percentage (out of 100) Fire damage reduction granted by a" +
                        " full set of Netherite armor")
                .defineInRange("netherite_damage_reduction", 33, 0, 100);
        BUILDER.pop();

        BUILDER.comment("Tungsten-Carbide tier").push("tungsten_carbide");
        TUNGSTEN_CARBIDE_EFFECT_CHANCE = BUILDER.comment("Chance (out of 100) to apply special effect on-hit" +
                        " with Tungsten-Carbide tools and weapons")
                .defineInRange("tungsten_carbide_effect_chance", 25, 0, 100);
        TUNGSTEN_CARBIDE_EXPLOSION_DAMAGE_REDUCTION = BUILDER.comment("Percentage (out of 100) Explosion damage" +
                        " reduction granted by a full set of Tungsten-Carbide armor")
                .defineInRange("tungsten_carbide_damage_reduction", 33, 0, 100);
        BUILDER.pop();
        //endregion



        SPEC = BUILDER.build();
    }
}
