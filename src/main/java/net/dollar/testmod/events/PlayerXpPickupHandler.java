package net.dollar.testmod.events;

import net.dollar.testmod.enchantment.ModEnchantments;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerXpPickupHandler {
    @SubscribeEvent
    public static void playerXpPickupHandler(PlayerXpEvent.PickupXp event) {
        //this method will attempt to repair Player item IF it has custom Mending enchantment
        //NOTE: Sets orb value because repairing with Mending consumes the XP.
        event.getOrb().value = repairPlayerItems(event.getEntity(), event.getOrb().value);
    }

    static int repairPlayerItems(Player player, int value) {
        //IF it finds item with MENDING_VERY_RARE, consume XP to repair (when applicable), else no consume
        Map.Entry<EquipmentSlot, ItemStack> entry = EnchantmentHelper.getRandomItemWith(
                ModEnchantments.MENDING_VERY_RARE.get(), player, ItemStack::isDamaged);
        if (entry != null) {
            ItemStack itemstack = entry.getValue();
            int i = Math.min((int) (value * itemstack.getXpRepairRatio()), itemstack.getDamageValue());
            itemstack.setDamageValue(itemstack.getDamageValue() - i);   //actually repair item here
            int j = value - (i / 2); //1:2 ratio durability:xp

            //if orb still has value, recursively call this method to attempt to repair further
            return j > 0 ? repairPlayerItems(player, j) : 0;
        } else {
            return value;
        }
    }
}
