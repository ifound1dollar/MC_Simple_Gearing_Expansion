package net.dollar.testmod.item;

import net.dollar.testmod.TestMod;
import net.dollar.testmod.entity.ModEntities;
import net.dollar.testmod.item.custom.*;
import net.dollar.testmod.util.ModSmithingUpgradeItemHelper;
import net.dollar.testmod.util.ModTiers;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, TestMod.MOD_ID);
    public static final DeferredRegister<Item> VANILLA_ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, "minecraft");




    public static final RegistryObject<Item> OBSIDIAN_GOLEM_SPAWN_EGG = ITEMS.register("obsidian_golem_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.OBSIDIAN_GOLEM, 0x12031E, 0xED4D0E,
                    new Item.Properties()));
    public static final RegistryObject<Item> TIN_SHEARS = ITEMS.register("tin_shears",
            () -> new ShearsItem(new Item.Properties().durability(150)));   //Iron is 238 (for some reason)

    //region COLLECTOR ITEMS
    public static final RegistryObject<Item> COLLECTOR_KATHLEENS_LOST_DIADEM = ITEMS.register("collector_kathleens_lost_diadem",
            () -> new ModCollectorItem(new Item.Properties(), "1"));
    public static final RegistryObject<Item> COLLECTOR_POTION_OF_EVERLASTING_YOUTH = ITEMS.register("collector_potion_of_everlasting_youth",
            () -> new ModCollectorItem(new Item.Properties(), "2"));
    //endregion

    //create RegistryObjects for new items
    //region BASIC ITEMS
    public static final RegistryObject<Item> RUBY = ITEMS.register("ruby",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SAPPHIRE = ITEMS.register("sapphire",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> COMPOUND_GEMSTONE = ITEMS.register("compound_gemstone",
            () -> new ModCompoundGemstoneItem(new Item.Properties()));
    public static final RegistryObject<Item> INFUSED_GEMSTONE = ITEMS.register("infused_gemstone",
            () -> new ModInfusedDiamondItem(new Item.Properties()));
    public static final RegistryObject<Item> MOLTEN_CORE = ITEMS.register("molten_core",
            () -> new Item(new Item.Properties().fireResistant()));
    public static final RegistryObject<Item> TUNGSTEN_CARBIDE_INGOT = ITEMS.register("tungsten_carbide_ingot",
            () -> new ModTungstenCarbideIngotItem(new Item.Properties()));

    public static final RegistryObject<Item> BASIC_UPGRADE_TEMPLATE =
            ITEMS.register("basic_upgrade_template", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GILDED_UPGRADE_SMITHING_TEMPLATE =
            ITEMS.register("gilded_upgrade_smithing_template", ModSmithingUpgradeItemHelper::createGildedUpgradeTemplate);
    public static final RegistryObject<Item> INFUSION_UPGRADE_SMITHING_TEMPLATE =
            ITEMS.register("infusion_upgrade_smithing_template", ModSmithingUpgradeItemHelper::createInfusionUpgradeTemplate);
    public static final RegistryObject<Item> CARBIDE_UPGRADE_SMITHING_TEMPLATE =
            ITEMS.register("carbide_upgrade_smithing_template", ModSmithingUpgradeItemHelper::createCarbideUpgradeTemplate);

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
    //re-register Netherite Ingot with a custom tooltip
    public static final RegistryObject<Item> NETHERITE_INGOT = VANILLA_ITEMS.register("netherite_ingot",
            () -> new ModNetheriteIngotItem(new Item.Properties()));
    //endregion


    //IMPORTANT: numeric parameters are Attack Damage (base) and Attack Speed (base 4, so -2.8 = 1.2), respectively
    //see VANILLA TOOL DATA in OneNote for vanilla values
    //region Axes
    public static final RegistryObject<Item> BRONZE_AXE = ITEMS.register("bronze_axe",
            () -> new AxeItem(ModTiers.Tools.BRONZE, 6, -3.1f, new Item.Properties()));
    public static final RegistryObject<Item> GILDED_BRONZE_AXE = ITEMS.register("gilded_bronze_axe",
            () -> new AxeItem(ModTiers.Tools.GILDED_BRONZE, 6, -3.0f, new Item.Properties()));  //gold speed
    public static final RegistryObject<Item> STEEL_AXE = ITEMS.register("steel_axe",
            () -> new AxeItem(ModTiers.Tools.STEEL, 6, -3.1f, new Item.Properties()));
    public static final RegistryObject<Item> TUNGSTEN_AXE = ITEMS.register("tungsten_axe",
            () -> new AxeItem(ModTiers.Tools.TUNGSTEN, 6, -3.2f, new Item.Properties()));   //slow (Stone)
    public static final RegistryObject<Item> TUNGSTEN_CARBIDE_AXE = ITEMS.register("tungsten_carbide_axe",
            () -> new ModTungstenCarbideAxeItem(ModTiers.Tools.TUNGSTEN_CARBIDE, 6, -3.2f,
                    new Item.Properties()));    //slow (Stone)
    public static final RegistryObject<Item> INFUSED_DIAMOND_AXE = ITEMS.register("infused_diamond_axe",
            () -> new ModInfusedDiamondAxeItem(ModTiers.Tools.INFUSED_DIAMOND, 5, -2.8f,
                    new Item.Properties()));    //fast
    public static final RegistryObject<Item> NETHERITE_AXE = VANILLA_ITEMS.register("netherite_axe",
            () -> new ModNetheriteAxeItem(Tiers.NETHERITE, 6, -3.0f,
                    new Item.Properties()));
    //endregion
    //region Hoes
    public static final RegistryObject<Item> BRONZE_HOE = ITEMS.register("bronze_hoe",
            () -> new HoeItem(ModTiers.Tools.BRONZE, -2, -1.0f, new Item.Properties()));
    public static final RegistryObject<Item> GILDED_BRONZE_HOE = ITEMS.register("gilded_bronze_hoe",
            () -> new HoeItem(ModTiers.Tools.GILDED_BRONZE, -2, 0.0f, new Item.Properties()));  //fast
    public static final RegistryObject<Item> STEEL_HOE = ITEMS.register("steel_hoe",
            () -> new HoeItem(ModTiers.Tools.STEEL, -2, -1.0f, new Item.Properties()));
    public static final RegistryObject<Item> TUNGSTEN_HOE = ITEMS.register("tungsten_hoe",
            () -> new HoeItem(ModTiers.Tools.TUNGSTEN, -3, -2.0f, new Item.Properties()));   //slow (Stone)
    public static final RegistryObject<Item> TUNGSTEN_CARBIDE_HOE = ITEMS.register("tungsten_carbide_hoe",
            () -> new ModTungstenCarbideHoeItem(ModTiers.Tools.TUNGSTEN_CARBIDE, -3, -2.0f,
                    new Item.Properties()));    //slow (Stone)
    public static final RegistryObject<Item> INFUSED_DIAMOND_HOE = ITEMS.register("infused_diamond_hoe",
            () -> new ModInfusedDiamondHoeItem(ModTiers.Tools.INFUSED_DIAMOND, -2, 0.0f,
                    new Item.Properties()));    //fast
    public static final RegistryObject<Item> NETHERITE_HOE = VANILLA_ITEMS.register("netherite_hoe",
            () -> new ModNetheriteHoeItem(Tiers.NETHERITE, -2, -1.0f,
                    new Item.Properties()));
    //endregion
    //region Maces
    public static final RegistryObject<Item> STONE_MACE = ITEMS.register("stone_mace",
            () -> new ModMaceItem(Tiers.STONE, 7.0f, -3.3f, new Item.Properties()));
    public static final RegistryObject<Item> IRON_MACE = ITEMS.register("iron_mace",
            () -> new ModMaceItem(Tiers.IRON, 7.0f, -3.3f, new Item.Properties()));
    public static final RegistryObject<Item> GOLD_MACE = ITEMS.register("gold_mace",
            () -> new ModMaceItem(Tiers.GOLD, 7.0f, -3.2f, new Item.Properties()));
    public static final RegistryObject<Item> DIAMOND_MACE = ITEMS.register("diamond_mace",
            () -> new ModMaceItem(Tiers.DIAMOND, 7.0f, -3.2f, new Item.Properties()));
    public static final RegistryObject<Item> BRONZE_MACE = ITEMS.register("bronze_mace",
            () -> new ModMaceItem(ModTiers.Tools.BRONZE, 7.0f, -3.3f, new Item.Properties()));
    public static final RegistryObject<Item> GILDED_BRONZE_MACE = ITEMS.register("gilded_bronze_mace",
            () -> new ModMaceItem(ModTiers.Tools.GILDED_BRONZE, 7.0f, -3.2f, new Item.Properties()));
    public static final RegistryObject<Item> STEEL_MACE = ITEMS.register("steel_mace",
            () -> new ModMaceItem(ModTiers.Tools.STEEL, 7.0f, -3.3f, new Item.Properties()));
    public static final RegistryObject<Item> TUNGSTEN_MACE = ITEMS.register("tungsten_mace",
            () -> new ModMaceItem(ModTiers.Tools.TUNGSTEN, 8f, -3.3f, new Item.Properties()));
    public static final RegistryObject<Item> TUNGSTEN_CARBIDE_MACE = ITEMS.register("tungsten_carbide_mace",
            () -> new ModTungstenCarbideMaceItem(ModTiers.Tools.TUNGSTEN_CARBIDE, 8f, -3.3f, new Item.Properties()));
    public static final RegistryObject<Item> INFUSED_DIAMOND_MACE = ITEMS.register("infused_diamond_mace",
            () -> new ModInfusedDiamondMaceItem(ModTiers.Tools.INFUSED_DIAMOND, 7.0f, -3.0f, new Item.Properties()));
    public static final RegistryObject<Item> NETHERITE_MACE = ITEMS.register("netherite_mace",
            () -> new ModNetheriteMaceItem(Tiers.NETHERITE, 8f, -3.2f, new Item.Properties()));

    //endregion
    //region Pickaxes
    public static final RegistryObject<Item> BRONZE_PICKAXE = ITEMS.register("bronze_pickaxe",
            () -> new PickaxeItem(ModTiers.Tools.BRONZE, 1, -2.8f, new Item.Properties()));
    public static final RegistryObject<Item> GILDED_BRONZE_PICKAXE = ITEMS.register("gilded_bronze_pickaxe",
            () -> new PickaxeItem(ModTiers.Tools.GILDED_BRONZE, 1, -2.7f, new Item.Properties()));
    public static final RegistryObject<Item> STEEL_PICKAXE = ITEMS.register("steel_pickaxe",
            () -> new PickaxeItem(ModTiers.Tools.STEEL, 1, -2.8f, new Item.Properties()));
    public static final RegistryObject<Item> TUNGSTEN_PICKAXE = ITEMS.register("tungsten_pickaxe",
            () -> new PickaxeItem(ModTiers.Tools.TUNGSTEN, 1, -3.1f, new Item.Properties()));   //slow
    public static final RegistryObject<Item> TUNGSTEN_CARBIDE_PICKAXE = ITEMS.register("tungsten_carbide_pickaxe",
            () -> new ModTungstenCarbidePickaxeItem(ModTiers.Tools.TUNGSTEN_CARBIDE, 1, -3.1f,
                    new Item.Properties()));    //slow
    public static final RegistryObject<Item> INFUSED_DIAMOND_PICKAXE = ITEMS.register("infused_diamond_pickaxe",
            () -> new ModInfusedDiamondPickaxeItem(ModTiers.Tools.INFUSED_DIAMOND, 1, -2.6f,
                    new Item.Properties()));    //fast
    public static final RegistryObject<Item> NETHERITE_PICKAXE = VANILLA_ITEMS.register("netherite_pickaxe",
            () -> new ModNetheritePickaxeItem(Tiers.NETHERITE, 1, -2.8f,
                    new Item.Properties()));
    //endregion
    //region Shovels
    public static final RegistryObject<Item> BRONZE_SHOVEL = ITEMS.register("bronze_shovel",
            () -> new ShovelItem(ModTiers.Tools.BRONZE, 1.5f, -3.0f, new Item.Properties()));
    public static final RegistryObject<Item> GILDED_BRONZE_SHOVEL = ITEMS.register("gilded_bronze_shovel",
            () -> new ShovelItem(ModTiers.Tools.GILDED_BRONZE, 1.5f, -2.9f, new Item.Properties()));
    public static final RegistryObject<Item> STEEL_SHOVEL = ITEMS.register("steel_shovel",
            () -> new ShovelItem(ModTiers.Tools.STEEL, 1.5f, -3.0f, new Item.Properties()));
    public static final RegistryObject<Item> TUNGSTEN_SHOVEL = ITEMS.register("tungsten_shovel",
            () -> new ShovelItem(ModTiers.Tools.TUNGSTEN, 2.0f, -3.2f, new Item.Properties()));   //slow
    public static final RegistryObject<Item> TUNGSTEN_CARBIDE_SHOVEL = ITEMS.register("tungsten_carbide_shovel",
            () -> new ModTungstenCarbideShovelItem(ModTiers.Tools.TUNGSTEN_CARBIDE, 2.0f, -3.2f,
                    new Item.Properties()));    //slow
    public static final RegistryObject<Item> INFUSED_DIAMOND_SHOVEL = ITEMS.register("infused_diamond_shovel",
            () -> new ModInfusedDiamondShovelItem(ModTiers.Tools.INFUSED_DIAMOND, 1.5f, -2.7f,
                    new Item.Properties()));    //fast
    public static final RegistryObject<Item> NETHERITE_SHOVEL = VANILLA_ITEMS.register("netherite_shovel",
            () -> new ModNetheriteShovelItem(Tiers.NETHERITE, 2.0f, -3.0f,
                    new Item.Properties()));
    //endregion
    //region Swords
    public static final RegistryObject<Item> BRONZE_SWORD = ITEMS.register("bronze_sword",
            () -> new SwordItem(ModTiers.Tools.BRONZE, 3, -2.4f, new Item.Properties()));
    public static final RegistryObject<Item> GILDED_BRONZE_SWORD = ITEMS.register("gilded_bronze_sword",
            () -> new SwordItem(ModTiers.Tools.GILDED_BRONZE, 3, -2.3f, new Item.Properties()));
    public static final RegistryObject<Item> STEEL_SWORD = ITEMS.register("steel_sword",
            () -> new SwordItem(ModTiers.Tools.STEEL, 3, -2.4f, new Item.Properties()));
    public static final RegistryObject<Item> TUNGSTEN_SWORD = ITEMS.register("tungsten_sword",
            () -> new SwordItem(ModTiers.Tools.TUNGSTEN, 3, -2.6f, new Item.Properties()));   //slow
    public static final RegistryObject<Item> TUNGSTEN_CARBIDE_SWORD = ITEMS.register("tungsten_carbide_sword",
            () -> new ModTungstenCarbideSwordItem(ModTiers.Tools.TUNGSTEN_CARBIDE, 3, -2.6f,
                    new Item.Properties()));    //slow
    public static final RegistryObject<Item> INFUSED_DIAMOND_SWORD = ITEMS.register("infused_diamond_sword",
            () -> new ModInfusedDiamondSwordItem(ModTiers.Tools.INFUSED_DIAMOND, 3, -2.0f,
                    new Item.Properties()));    //fast
    public static final RegistryObject<Item> NETHERITE_SWORD = VANILLA_ITEMS.register("netherite_sword",
            () -> new ModNetheriteSwordItem(Tiers.NETHERITE, 4, -2.4f,
                    new Item.Properties()));
    //endregion


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
    //region Infused Diamond armor, has custom full set bonus
    public static final RegistryObject<Item> INFUSED_DIAMOND_HELMET = ITEMS.register("infused_diamond_helmet",
            () -> new ModInfusedDiamondArmorItem(ModTiers.Armor.INFUSED_DIAMOND, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> INFUSED_DIAMOND_CHESTPLATE = ITEMS.register("infused_diamond_chestplate",
            () -> new ModInfusedDiamondArmorItem(ModTiers.Armor.INFUSED_DIAMOND, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> INFUSED_DIAMOND_LEGGINGS = ITEMS.register("infused_diamond_leggings",
            () -> new ModInfusedDiamondArmorItem(ModTiers.Armor.INFUSED_DIAMOND, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> INFUSED_DIAMOND_BOOTS = ITEMS.register("infused_diamond_boots",
            () -> new ModInfusedDiamondArmorItem(ModTiers.Armor.INFUSED_DIAMOND, ArmorItem.Type.BOOTS, new Item.Properties()));
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
