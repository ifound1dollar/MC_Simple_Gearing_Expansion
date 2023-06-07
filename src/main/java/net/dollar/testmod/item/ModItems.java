package net.dollar.testmod.item;

import net.dollar.testmod.TestMod;
import net.dollar.testmod.util.ModArmorMaterial;
import net.dollar.testmod.util.ModTiers;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, TestMod.MOD_ID);


    //create RegistryObjects for new items
    public static final RegistryObject<Item> RUBY_SHARD = ITEMS.register("ruby_shard",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SAPPHIRE_SHARD = ITEMS.register("sapphire_shard",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_BRONZE = ITEMS.register("raw_bronze",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BRONZE_INGOT = ITEMS.register("bronze_ingot",
            () -> new Item(new Item.Properties()));


    //all PickaxeItems (existing in Minecraft) have 1 and -2.8f as the 2nd and 3rd parameter, respectively
    public static final RegistryObject<Item> BRONZE_PICKAXE = ITEMS.register("bronze_pickaxe",
            () -> new PickaxeItem(ModTiers.Tools.BRONZE, 1, -2.8f, new Item.Properties()));


    //bronze armor
    public static final RegistryObject<Item> BRONZE_HELMET = ITEMS.register("bronze_helmet",
            () -> new BronzeArmorItem(ModTiers.Armor.BRONZE, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> BRONZE_CHESTPLATE = ITEMS.register("bronze_chestplate",
            () -> new BronzeArmorItem(ModTiers.Armor.BRONZE, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> BRONZE_LEGGINGS = ITEMS.register("bronze_leggings",
            () -> new BronzeArmorItem(ModTiers.Armor.BRONZE, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> BRONZE_BOOTS = ITEMS.register("bronze_boots",
            () -> new BronzeArmorItem(ModTiers.Armor.BRONZE, ArmorItem.Type.BOOTS, new Item.Properties()));



    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
