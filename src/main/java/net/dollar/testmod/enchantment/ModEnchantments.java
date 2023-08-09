package net.dollar.testmod.enchantment;

import net.dollar.testmod.TestMod;
import net.dollar.testmod.item.ModMaceItem;
import net.dollar.testmod.util.IInfusedDiamondItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.MendingEnchantment;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, TestMod.MOD_ID);
//    public static final DeferredRegister<Enchantment> VANILLA_ENCHANTMENTS =
//            DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, TestMod.MOD_ID);

    //this new category allows enchantments using it to only apply to new Mace item
    public static final EnchantmentCategory MACE_ONLY = EnchantmentCategory.create(
            "MACE_ONLY", (item) -> item instanceof ModMaceItem);
    public static final EnchantmentCategory INFUSED_DIAMOND_ONLY = EnchantmentCategory.create(
            "INFUSED_DIAMOND_ONLY", (item) -> item instanceof IInfusedDiamondItem);


    public static RegistryObject<Enchantment> HARDNESS = ENCHANTMENTS.register("hardness",
                    () -> new HardnessEnchantment(Enchantment.Rarity.COMMON, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> POISON_EDGE = ENCHANTMENTS.register("poison_edge",
            () -> new PoisonEdgeEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> MENDING_VERY_RARE = ENCHANTMENTS.register("mending_very_rare",
            () -> new MendingVeryRareEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.values()));


    public static void register (IEventBus eventBus) {
        ENCHANTMENTS.register(eventBus);
//        VANILLA_ENCHANTMENTS.register(eventBus);
    }
}
