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

    //also make DeferredRegister for vanilla items to be overridden
    public static final DeferredRegister<Item> VANILLA_ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, "minecraft");


    //create RegistryObjects for new items
    public static final RegistryObject<Item> RUBY = ITEMS.register("ruby",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SAPPHIRE = ITEMS.register("sapphire",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> INFUSED_GEMSTONE = ITEMS.register("infused_gemstone",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> NAMELESS_INFUSION_ITEM = ITEMS.register("nameless_infusion_item",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> CARBONITE_DUST = ITEMS.register("carbonite_dust",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_TIN = ITEMS.register("raw_tin",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TIN_INGOT = ITEMS.register("tin_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BRONZE_COMPOUND = ITEMS.register("bronze_compound",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BRONZE_INGOT = ITEMS.register("bronze_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STEEL_COMPOUND = ITEMS.register("steel_compound",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STEEL_INGOT = ITEMS.register("steel_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_TUNGSTEN = ITEMS.register("raw_tungsten",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TUNGSTEN_INGOT = ITEMS.register("tungsten_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TUNGSTEN_CARBIDE_COMPOUND = ITEMS.register("tungsten_carbide_compound",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TUNGSTEN_CARBIDE_INGOT = ITEMS.register("tungsten_carbide_ingot",
            () -> new Item(new Item.Properties()));


    //IMPORTANT: numeric parameters are Attack Damage and Attack Speed, respectively
    //all PickaxeItems have 1 and -2.8f (baseline Attack Speed is 4, so -2.8f results in 1.2 Attack Speed)
    //see VANILLA TOOL DATA in OneNote for vanilla values
    public static final RegistryObject<Item> BRONZE_PICKAXE = ITEMS.register("bronze_pickaxe",
            () -> new PickaxeItem(ModTiers.Tools.BRONZE, 1, -2.8f, new Item.Properties()));
    public static final RegistryObject<Item> GILDED_BRONZE_PICKAXE = ITEMS.register("gilded_bronze_pickaxe",
            () -> new PickaxeItem(ModTiers.Tools.GILDED_BRONZE, 1, -2.8f, new Item.Properties()));
    public static final RegistryObject<Item> INFUSED_DIAMOND_PICKAXE = ITEMS.register("infused_diamond_pickaxe",
            () -> new PickaxeItem(ModTiers.Tools.INFUSED_DIAMOND, 1, -2.4f, new Item.Properties()));

    //IMPORTANT: TUNGSTEN AND TUNGSTEN-CARBIDE TOOLS MUST CHANGE DEFAULT SPEED PARAMETER (not damage parameter as
    //  the damage modifier is already applied in the Tier)
    //ALSO INFUSED DIAMOND FOR INCREASED SPEED


    //region Bronze armor, is nothing special so can use default ArmorItem
    public static final RegistryObject<Item> BRONZE_HELMET = ITEMS.register("bronze_helmet",
            () -> new ArmorItem(ModTiers.Armor.BRONZE, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> BRONZE_CHESTPLATE = ITEMS.register("bronze_chestplate",
            () -> new ArmorItem(ModTiers.Armor.BRONZE, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> BRONZE_LEGGINGS = ITEMS.register("bronze_leggings",
            () -> new ArmorItem(ModTiers.Armor.BRONZE, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> BRONZE_BOOTS = ITEMS.register("bronze_boots",
            () -> new ArmorItem(ModTiers.Armor.BRONZE, ArmorItem.Type.BOOTS, new Item.Properties()));
    //endregion

    //region Gilded Bronze armor, overrides makePiglinsNeutral() method to true
    public static final RegistryObject<Item> GILDED_BRONZE_HELMET = ITEMS.register("gilded_bronze_helmet",
            () -> new ModGildedBronzeArmorItem(ModTiers.Armor.GILDED_BRONZE, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> GILDED_BRONZE_CHESTPLATE = ITEMS.register("gilded_bronze_chestplate",
            () -> new ModGildedBronzeArmorItem(ModTiers.Armor.GILDED_BRONZE, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> GILDED_BRONZE_LEGGINGS = ITEMS.register("gilded_bronze_leggings",
            () -> new ModGildedBronzeArmorItem(ModTiers.Armor.GILDED_BRONZE, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> GILDED_BRONZE_BOOTS = ITEMS.register("gilded_bronze_boots",
            () -> new ModGildedBronzeArmorItem(ModTiers.Armor.GILDED_BRONZE, ArmorItem.Type.BOOTS, new Item.Properties()));
    //endregion

    //region Steel armor, is nothing special so can use default ArmorItem
    public static final RegistryObject<Item> STEEL_HELMET = ITEMS.register("steel_helmet",
            () -> new ArmorItem(ModTiers.Armor.STEEL, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> STEEL_CHESTPLATE = ITEMS.register("steel_chestplate",
            () -> new ArmorItem(ModTiers.Armor.STEEL, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> STEEL_LEGGINGS = ITEMS.register("steel_leggings",
            () -> new ArmorItem(ModTiers.Armor.STEEL, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> STEEL_BOOTS = ITEMS.register("steel_boots",
            () -> new ArmorItem(ModTiers.Armor.STEEL, ArmorItem.Type.BOOTS, new Item.Properties()));
    //endregion

    //region Tungsten armor, is nothing special so can use default ArmorItem
    public static final RegistryObject<Item> TUNGSTEN_HELMET = ITEMS.register("tungsten_helmet",
            () -> new ArmorItem(ModTiers.Armor.TUNGSTEN, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> TUNGSTEN_CHESTPLATE = ITEMS.register("tungsten_chestplate",
            () -> new ArmorItem(ModTiers.Armor.TUNGSTEN, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> TUNGSTEN_LEGGINGS = ITEMS.register("tungsten_leggings",
            () -> new ArmorItem(ModTiers.Armor.TUNGSTEN, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> TUNGSTEN_BOOTS = ITEMS.register("tungsten_boots",
            () -> new ArmorItem(ModTiers.Armor.TUNGSTEN, ArmorItem.Type.BOOTS, new Item.Properties()));
    //endregion

    //region Tungsten-Carbide armor, has custom full set bonus
    public static final RegistryObject<Item> TUNGSTEN_CARBIDE_HELMET = ITEMS.register("tungsten_carbide_helmet",
            () -> new ModTungstenCarbideArmorItem(ModTiers.Armor.TUNGSTEN_CARBIDE, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> TUNGSTEN_CARBIDE_CHESTPLATE = ITEMS.register("tungsten_carbide_chestplate",
            () -> new ModTungstenCarbideArmorItem(ModTiers.Armor.TUNGSTEN_CARBIDE, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> TUNGSTEN_CARBIDE_LEGGINGS = ITEMS.register("tungsten_carbide_leggings",
            () -> new ModTungstenCarbideArmorItem(ModTiers.Armor.TUNGSTEN_CARBIDE, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> TUNGSTEN_CARBIDE_BOOTS = ITEMS.register("tungsten_carbide_boots",
            () -> new ModTungstenCarbideArmorItem(ModTiers.Armor.TUNGSTEN_CARBIDE, ArmorItem.Type.BOOTS, new Item.Properties()));
    //endregion

    //region Tungsten-Carbide armor, has custom full set bonus
    public static final RegistryObject<Item> INFUSED_DIAMOND_HELMET = ITEMS.register("infused_diamond_helmet",
            () -> new ModInfusedDiamondArmorItem(ModTiers.Armor.TUNGSTEN_CARBIDE, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> INFUSED_DIAMOND_CHESTPLATE = ITEMS.register("infused_diamond_chestplate",
            () -> new ModInfusedDiamondArmorItem(ModTiers.Armor.TUNGSTEN_CARBIDE, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> INFUSED_DIAMOND_LEGGINGS = ITEMS.register("infused_diamond_leggings",
            () -> new ModInfusedDiamondArmorItem(ModTiers.Armor.TUNGSTEN_CARBIDE, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> INFUSED_DIAMOND_BOOTS = ITEMS.register("infused_diamond_boots",
            () -> new ModInfusedDiamondArmorItem(ModTiers.Armor.TUNGSTEN_CARBIDE, ArmorItem.Type.BOOTS, new Item.Properties()));
    //endregion

    //region Netherite armor, replaces vanilla registry because it has custom full set bonus
    public static final RegistryObject<Item> NETHERITE_HELMET = VANILLA_ITEMS.register("netherite_helmet",
            () -> new ModNetheriteArmorItem(ArmorMaterials.NETHERITE, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> NETHERITE_CHESTPLATE = VANILLA_ITEMS.register("netherite_chestplate",
            () -> new ModNetheriteArmorItem(ArmorMaterials.NETHERITE, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> NETHERITE_LEGGINGS = VANILLA_ITEMS.register("netherite_leggings",
            () -> new ModNetheriteArmorItem(ArmorMaterials.NETHERITE, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> NETHERITE_BOOTS = VANILLA_ITEMS.register("netherite_boots",
            () -> new ModNetheriteArmorItem(ArmorMaterials.NETHERITE, ArmorItem.Type.BOOTS, new Item.Properties()));
    //endregion



    //register new items and vanilla items overridden by the mod
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
        VANILLA_ITEMS.register(eventBus);
    }
}
