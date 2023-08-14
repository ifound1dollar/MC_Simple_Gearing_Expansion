package net.dollar.simplegear.events;

import net.dollar.simplegear.util.IDamageHandlingArmor;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Handles adjusting damage taken if the player is wearing a full set of IDamageHandlingArmor. Intercepts
 *  LivingDamageEvent and performs operations there.
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModArmorDamageHandler {
    /**
     * Intercepts LivingDamageEvent to potentially reduce damage taken if all armor items are of
     *  IDamageHandlingArmor type. Only checks worn chestplate because the chestplate is the Item
     *  that handles the full-set bonus.
     * @param event Event fired when a LivingEntity takes damage
     */
    @SubscribeEvent
    public static void chestplateAttackHandler(LivingDamageEvent event) {
        //Item worn on chest is the Item that will handle adjusting damage taken with full-set
        Item chestplateItem = event.getEntity().getItemBySlot(EquipmentSlot.CHEST).getItem();

        //if armorItem implements interface, call method to adjust damage taken if full set
        if (chestplateItem instanceof IDamageHandlingArmor damageHandlingChestplate) {
            float newDamage = (damageHandlingChestplate.onDamaged(
                    event.getEntity(), EquipmentSlot.CHEST, event.getSource(), event.getAmount()));

            //set damage amount to newDamage (may not have changed)
            event.setAmount(newDamage);
        }
    }
}
