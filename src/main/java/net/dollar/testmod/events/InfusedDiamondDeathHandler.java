package net.dollar.testmod.events;

import net.dollar.testmod.item.ModItems;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class InfusedDiamondDeathHandler {
    @SubscribeEvent
    public static void dropsHandler(LivingDropsEvent event) {
        //if keepInventory true, do not do anything upon respawn
        if (event.getEntity().level.getGameRules().getRule(GameRules.RULE_KEEPINVENTORY).get()) {
            return;
        }

        if (event.getEntity() instanceof ServerPlayer player && event.getSource().is(DamageTypes.OUT_OF_WORLD)) {
            //iterate through all drops and if it is an Infused Diamond item, add back to inventory
            int tempSlot = 0;
            for (ItemEntity itemEntity : event.getDrops()) {
                //break if tempSlot exceeds last inventory index (35)
                if (tempSlot > 35) {
                    break;
                }

                ItemStack itemStack = itemEntity.getItem();
                if (itemStack.is(ModItems.INFUSED_DIAMOND_AXE.get()) ||
                        itemStack.is(ModItems.INFUSED_DIAMOND_HOE.get()) ||
                        itemStack.is(ModItems.INFUSED_DIAMOND_MACE.get()) ||
                        itemStack.is(ModItems.INFUSED_DIAMOND_PICKAXE.get()) ||
                        itemStack.is(ModItems.INFUSED_DIAMOND_SHOVEL.get()) ||
                        itemStack.is(ModItems.INFUSED_DIAMOND_SWORD.get()) ||
                        itemStack.is(ModItems.INFUSED_DIAMOND_HELMET.get()) ||
                        itemStack.is(ModItems.INFUSED_DIAMOND_CHESTPLATE.get()) ||
                        itemStack.is(ModItems.INFUSED_DIAMOND_LEGGINGS.get()) ||
                        itemStack.is(ModItems.INFUSED_DIAMOND_BOOTS.get())) {
                    //set to 1 durability and add back to player inventory starting from top left slot (index 0)
                    itemStack.setDamageValue(itemStack.getMaxDamage() - 1);
                    player.getInventory().add(tempSlot, itemStack);
                    tempSlot++;


                    //CHANGE TO REMAIN AT ORIGINAL DURABILITY AND KEEP IN ORIGINAL SLOTS
                    for (ItemStack armorItem : player.getInventory().armor) {
                        //CAN ITERATE THROUGH FOUR ARMORS HERE, FROM BOOTS -> HELMET
                        //SAME LOGIC USED FOR REGULAR INVENTORY AND OFFHAND
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void playerCloneHandler(PlayerEvent.Clone event) {
        //if keepInventory true, do not do anything upon respawn
        if (event.getEntity().level.getGameRules().getRule(GameRules.RULE_KEEPINVENTORY).get()) {
            return;
        }

        int tempSlot = 0;
        for (ItemStack itemStack : event.getOriginal().getInventory().items) {
            //break if tempSlot exceeds last inventory index (35)
            if (tempSlot > 35) {
                break;
            }

            //add all items back to inventory starting from top left slot (guaranteed to be Infused Diamond items)
            event.getEntity().getInventory().add(tempSlot, itemStack);
            tempSlot++;
        }
    }
}
