package net.dollar.testmod.events;

import net.dollar.testmod.item.ModItems;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class InfusedDiamondDeathHandler {
    @SubscribeEvent
    public static void dropsHandler(LivingDropsEvent event) {
        if (event.getEntity() instanceof Player player && event.getSource().is(DamageTypes.OUT_OF_WORLD)) {
            //iterate through all drops and if it is an Infused Diamond item, add back to inventory
            for (ItemEntity itemEntity : event.getDrops()) {
                ItemStack item = itemEntity.getItem();
                if (item.is(ModItems.INFUSED_DIAMOND_SWORD.get())) {
                    //set to 1 durability and add back to player inventory
                    item.setDamageValue(1);
                    player.getInventory().placeItemBackInInventory(item);
                }
            }
        }
    }

    @SubscribeEvent
    public static void playerRespawnHandler(PlayerEvent.PlayerRespawnEvent event) {
        //event.getEntity().getInventory().placeItemBackInInventory();
    }
}
