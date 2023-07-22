package net.dollar.testmod.events;

import net.dollar.testmod.item.ModItems;
import net.dollar.testmod.util.IInfusedDiamondItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
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

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class InfusedDiamondDeathHandler {
    private static HashMap<UUID, ItemStack[]> inventories = new HashMap<>();

    @SubscribeEvent
    public static void playerDeathHandler(LivingDeathEvent event) {
        //THIS METHOD WILL STORE ALL INFUSED DIAMOND ITEMS IN THE 'inventories' HASHMAP TO BE RE-ADDED
        //  TO THE DEAD PLAYER'S INVENTORY IF AND ONLY IF THE CAUSE OF DEATH WAS VOID.
        //This is the first of three steps to keep Infused Diamond items in the player's inventory.

        //if keepInventory true, do nothing special upon death
        if (event.getEntity().level.getGameRules().getRule(GameRules.RULE_KEEPINVENTORY).get()) {
            return;
        }

        //only if the dead entity is a ServerPlayer and cause of death was Void
        if (event.getEntity() instanceof ServerPlayer player && event.getSource().is(DamageTypes.OUT_OF_WORLD)) {
            //create array of ItemStacks and add all Infused Diamond items to it at corresponding indices
            ItemStack[] itemStacks = new ItemStack[41];
            for (int i = 0; i < 41; i++) {
                ItemStack currItemStack = player.getInventory().getItem(i);
                if (currItemStack.getItem() instanceof IInfusedDiamondItem) {
                    itemStacks[i] = currItemStack;
                }
            }

            //finally, add array of ItemStacks to 'inventories' HashMap
            inventories.put(player.getUUID(), itemStacks);
        }
    }

    @SubscribeEvent
    public static void dropsHandler(LivingDropsEvent event) {
        //THIS METHOD WILL TAKE ANY STORED INFUSED DIAMOND ITEMS OF THE PLAYER'S AND ADD THEM BACK TO
        //  THE PLAYER'S INVENTORY. THIS IS DONE IMMEDIATELY BECAUSE THE HASHMAP MUST BE USED AND EMPTIED
        //  IMMEDIATELY BEFORE THERE IS ANY CHANCE OF THE SERVER GOING DOWN AND THE DATA BEING LOST.
        //This is the second of three steps to keep Infused Diamond items in the player's inventory.

        //if keepInventory true, do not do anything upon respawn (NOTE: no drops if keepInventory is true)
        if (event.getEntity().level.getGameRules().getRule(GameRules.RULE_KEEPINVENTORY).get()) {
            return;
        }

        //if this entity is ServerPlayer AND this player has items stored in 'inventories' Map
        if (event.getEntity() instanceof ServerPlayer player && inventories.containsKey(player.getUUID())) {
            //iterate through all 41 valid slots and add items stored in 'inventories' back to player inventory
            ItemStack[] itemStacks = inventories.get(player.getUUID());
            for (int i = 0; i < 41; i++) {
                if (itemStacks[i] != null) {
                    player.getInventory().setItem(i, itemStacks[i]);
                }
            }

            //CRITICAL to remove old entry immediately
            inventories.remove(player.getUUID());
        }
    }

    @SubscribeEvent
    public static void playerCloneHandler(PlayerEvent.Clone event) {
        //THIS METHOD WILL ENSURE THAT THE OLD PLAYER'S INVENTORY (WHICH IS NOW ONLY INFUSED DIAMOND
        //  ITEMS) IS CLONED TO THE NEW PLAYER'S INVENTORY.
        //This is the third of three steps to keep Infused Diamond items in the player's inventory.

        //if keepInventory true, do nothing special upon respawn
        if (event.getEntity().level.getGameRules().getRule(GameRules.RULE_KEEPINVENTORY).get()) {
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
