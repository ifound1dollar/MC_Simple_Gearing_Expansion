package net.dollar.testmod.item;

import net.dollar.testmod.TestMod;
import net.dollar.testmod.util.ModTiers;
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
    public static final RegistryObject<Item> RAW_TIN = ITEMS.register("raw_tin",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TIN_INGOT = ITEMS.register("tin_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BRONZE_INGOT = ITEMS.register("bronze_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STEEL_INGOT = ITEMS.register("steel_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_TUNGSTEN = ITEMS.register("raw_tungsten",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TUNGSTEN_INGOT = ITEMS.register("tungsten_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TUNGSTEN_CARBIDE_INGOT = ITEMS.register("tungsten_carbide_ingot",
            () -> new Item(new Item.Properties()));


    //IMPORTANT: numeric parameters are Attack Damage and Attack Speed, respectively
    //all PickaxeItems have 1 and -2.8f (baseline Attack Speed is 4, so -2.8f results in 1.2 Attack Speed)
    public static final RegistryObject<Item> BRONZE_PICKAXE = ITEMS.register("bronze_pickaxe",
            () -> new PickaxeItem(ModTiers.Tools.BRONZE, 1, -2.8f, new Item.Properties()));
    public static final RegistryObject<Item> GILDED_BRONZE_PICKAXE = ITEMS.register("gilded_bronze_pickaxe",
            () -> new PickaxeItem(ModTiers.Tools.GILDED_BRONZE, 1, -2.8f, new Item.Properties()));

    //all SwordItems have 3 and -2.4f by default

    //IMPORTANT: TUNGSTEN AND TUNGSTEN-CARBIDE TOOLS MUST CHANGE DEFAULT SPEED PARAMETER (not damage parameter as
    //  the damage modifier is already applied in the Tier)
    //ALSO INFUSED DIAMOND FOR INCREASED SPEED


    //bronze armor, is nothing special so can use default ArmorItem
    public static final RegistryObject<Item> BRONZE_HELMET = ITEMS.register("bronze_helmet",
            () -> new ArmorItem(ModTiers.Armor.BRONZE, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> BRONZE_CHESTPLATE = ITEMS.register("bronze_chestplate",
            () -> new ArmorItem(ModTiers.Armor.BRONZE, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> BRONZE_LEGGINGS = ITEMS.register("bronze_leggings",
            () -> new ArmorItem(ModTiers.Armor.BRONZE, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> BRONZE_BOOTS = ITEMS.register("bronze_boots",
            () -> new ArmorItem(ModTiers.Armor.BRONZE, ArmorItem.Type.BOOTS, new Item.Properties()));



    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
