package net.dollar.testmod.events;

import net.dollar.testmod.util.IDamageHandlingArmor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ArmorHandlers {
    @SubscribeEvent
    public static void armorAttackHandler(LivingDamageEvent event) {
        //iterate through all four armor slots of the damaged actor
        for (ItemStack armor : event.getEntity().getArmorSlots()) {
            //if this armor item implements interface, should do special operation on attacked
            if (armor.getItem() instanceof IDamageHandlingArmor) {
                //call interface method to alter damage taken (conditionally, may do nothing this iteration)
                float newDamage = ((IDamageHandlingArmor) armor.getItem()).onDamaged(
                        event.getEntity(), LivingEntity.getEquipmentSlotForItem(armor), event.getSource(), event.getAmount());

                //finally, set damage amount to newDamage
                event.setAmount(newDamage);
            }
        }
    }
}
