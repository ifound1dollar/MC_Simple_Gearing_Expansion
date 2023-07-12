package net.dollar.testmod.enchantment;

import net.dollar.testmod.TestMod;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, TestMod.MOD_ID);

    public static RegistryObject<Enchantment> HARDNESS = ENCHANTMENTS.register("hardness",
                    () -> new HardnessEnchantment(Enchantment.Rarity.COMMON, EquipmentSlot.MAINHAND));


    public static void register (IEventBus eventBus) {
        ENCHANTMENTS.register(eventBus);
    }
}
