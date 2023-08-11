package net.dollar.testmod.loot;

import com.mojang.serialization.Codec;
import net.dollar.testmod.TestMod;
import net.dollar.testmod.loot.custom.BasicUpgradeTemplateCommon;
import net.dollar.testmod.loot.custom.BasicUpgradeTemplateRare;
import net.dollar.testmod.loot.custom.BasicUpgradeTemplateUncommon;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModLootModifiers {
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIER_SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, TestMod.MOD_ID);



    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> BASIC_UPGRADE_TEMPLATE_COMMON =
            LOOT_MODIFIER_SERIALIZERS.register("basic_upgrade_template_common",
                    BasicUpgradeTemplateCommon.CODEC);
    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> BASIC_UPGRADE_TEMPLATE_UNCOMMON =
            LOOT_MODIFIER_SERIALIZERS.register("basic_upgrade_template_uncommon",
                    BasicUpgradeTemplateUncommon.CODEC);
    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> BASIC_UPGRADE_TEMPLATE_RARE =
            LOOT_MODIFIER_SERIALIZERS.register("basic_upgrade_template_rare",
                    BasicUpgradeTemplateRare.CODEC);





    public static void register(IEventBus bus) {
        LOOT_MODIFIER_SERIALIZERS.register(bus);
    }
}
