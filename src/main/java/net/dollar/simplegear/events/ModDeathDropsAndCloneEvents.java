package net.dollar.simplegear.events;

import net.dollar.simplegear.config.ModCommonConfigs;
import net.dollar.simplegear.item.ModItems;
import net.dollar.simplegear.util.IInfusedDiamondItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.UUID;

/**
 * Handles events fired on LivingEntity death, LivingEntity drops, and Player clone operations. Primary
 *  purpose of this class is to not drop Infused Diamond items if the player dies to the void.
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModDeathDropsAndCloneEvents {
    private static final HashMap<UUID, ItemStack[]> storedInventories = new HashMap<>();



    /**
     * Stores any and all Infused Diamond items from a player's inventory upon death caused by falling
     *  into the void, so they can be re-added (Infused Diamond feature).
     *  NOTE: This is the first of three steps to keep Infused Diamond items in the player's inventory.
     * @param event Event fired whenever a LivingEntity dies.
     */
    @SubscribeEvent
    public static void playerDeathHandler(LivingDeathEvent event) {
        //if keepInventory true, do nothing special upon death
        if (event.getEntity().level().getGameRules().getRule(GameRules.RULE_KEEPINVENTORY).get()) {
            return;
        }

        //only if the dead entity is a ServerPlayer and cause of death was Void
        if (event.getEntity() instanceof ServerPlayer player && event.getSource().is(DamageTypes.FELL_OUT_OF_WORLD)) {
            //create array of ItemStacks and add all Infused Diamond items to it at corresponding indices
            boolean foundInfusedDiamondItem = false;
            ItemStack[] itemStacks = new ItemStack[41];
            for (int i = 0; i < 41; i++) {
                ItemStack currItemStack = player.getInventory().getItem(i);
                if (currItemStack.getItem() instanceof IInfusedDiamondItem) {
                    foundInfusedDiamondItem = true;
                    itemStacks[i] = currItemStack;
                }
            }

            //finally, add array of ItemStacks to 'inventories' HashMap IF an Infused Diamond item was found
            if (foundInfusedDiamondItem) {
                storedInventories.put(player.getUUID(), itemStacks);
            }
        }
    }

    /**
     * TWO OPERATIONS: 1) Rolls small chance to add a single Basic Upgrade Template to a Monster's
     *  drops on death. 2) Accesses stored HashMap of player inventories and adds player's Infused
     *  Diamond items back into their inventory on void death (when valid).
     *  NOTE: This is the second of three steps to keep Infused Diamond items in the player's inventory.
     * @param event Event fired when a LivingEntity drops all items on death
     */
    @SubscribeEvent
    public static void dropsHandler(LivingDropsEvent event) {
        if (event.getEntity() instanceof Monster monster) {
            //HERE, will roll a very small chance to drop a Generic Upgrade Template from a Monster
            //  that was slain by the player.

            //if killer was not a player, do not roll chance
            if (!(monster.getKillCredit() instanceof Player)) {
                return;
            }

            //roll chance (from config) to add ONE Generic Upgrade Template item entity as an additional drop
            if (event.getEntity().getRandom().nextInt(0, 100)
                    < ModCommonConfigs.BASIC_UPGRADE_TEMPLATE_DROP_CHANCE.get()) {
                event.getDrops().add(new ItemEntity(monster.level(), monster.getX(), monster.getY(), monster.getZ(),
                        new ItemStack(ModItems.BASIC_UPGRADE_TEMPLATE.get())));
            }
        } else if (event.getEntity() instanceof ServerPlayer player) {
            //HERE, will take any stored Infused Diamond items of the player's and add them back to
            //  the player's inventory. This is done here immediately because the hashmap must be
            //  populated then immediately emptied before there is a chance that the server goes
            //  down. The hashmap is not saved, so if the player does not instantly respawn and
            //  the server goes down during that time, the items would otherwise be lost.
            //This is the second of three steps to keep Infused Diamond items in the player's inventory.

            //if keepInventory true, do not do anything upon respawn (NOTE: no drops if keepInventory is true)
            if (event.getEntity().level().getGameRules().getRule(GameRules.RULE_KEEPINVENTORY).get()) {
                return;
            }
            //if this player does not have items stored in 'inventories' map, had no Infused Diamond items
            if (!storedInventories.containsKey(player.getUUID())) {
                return;
            }

            //iterate through all 41 valid slots and add items stored in 'inventories' back to player inventory
            ItemStack[] itemStacks = storedInventories.get(player.getUUID());
            for (int i = 0; i < 41; i++) {
                if (itemStacks[i] != null) {
                    player.getInventory().setItem(i, itemStacks[i]);
                }
            }

            //CRITICAL to remove old entry immediately
            storedInventories.remove(player.getUUID());
        }
    }

    /**
     * Clones old player's inventory to new player upon clone event IF the player was dead; by default,
     *  inventories are not cloned so must be done manually.
     *  NOTE: This is the third of three steps to keep Infused Diamond items in the player's inventory.
     * @param event Event fired when a player is cloned (death or changed dimensions)
     */
    @SubscribeEvent
    public static void playerCloneHandler(PlayerEvent.Clone event) {
        //if keepInventory true, do nothing special upon respawn
        if (event.getEntity().level().getGameRules().getRule(GameRules.RULE_KEEPINVENTORY).get()) {
            return;
        }

        //if was not death, do nothing
        if (!event.isWasDeath()) {
            return;
        }

        //do one-to-one translation of old player's items to new player
        Player oldPlayer = event.getOriginal();
        Player newPlayer = event.getEntity();
        for (int i = 0; i < 41; i++) {
            newPlayer.getInventory().setItem(i, oldPlayer.getInventory().getItem(i));
        }
    }
}
