package net.dollar.simplegear.loot;

import com.mojang.serialization.Codec;
import net.dollar.simplegear.SimpleGearingExpansion;
import net.dollar.simplegear.loot.chests.*;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Handles registering new GlobalLootModifiers, which inject additional loot into generated vanilla loot scenarios
 */
public class ModLootModifiers {
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIER_SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, SimpleGearingExpansion.MOD_ID);



    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> BASIC_UPGRADE_TEMPLATE_COMMON =
            LOOT_MODIFIER_SERIALIZERS.register("basic_upgrade_template_common",
                    BasicUpgradeTemplateCommon.CODEC);
    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> BASIC_UPGRADE_TEMPLATE_UNCOMMON =
            LOOT_MODIFIER_SERIALIZERS.register("basic_upgrade_template_uncommon",
                    BasicUpgradeTemplateUncommon.CODEC);
    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> BASIC_UPGRADE_TEMPLATE_RARE =
            LOOT_MODIFIER_SERIALIZERS.register("basic_upgrade_template_rare",
                    BasicUpgradeTemplateRare.CODEC);
    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> FINAL_UPGRADE_TEMPLATES_UNCOMMON =
            LOOT_MODIFIER_SERIALIZERS.register("final_upgrade_templates_uncommon",
                    FinalUpgradeTemplatesUncommon.CODEC);
    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> FINAL_UPGRADE_TEMPLATES_RARE =
            LOOT_MODIFIER_SERIALIZERS.register("final_upgrade_templates_rare",
                    FinalUpgradeTemplatesRare.CODEC);
    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> TUNGSTEN_INGOT_COMMON =
            LOOT_MODIFIER_SERIALIZERS.register("tungsten_ingot_common",
                    TungstenIngotCommon.CODEC);



    /**
     * Register new loot modifiers
     * @param bus Main event bus
     */
    public static void register(IEventBus bus) {
        LOOT_MODIFIER_SERIALIZERS.register(bus);
    }
}
