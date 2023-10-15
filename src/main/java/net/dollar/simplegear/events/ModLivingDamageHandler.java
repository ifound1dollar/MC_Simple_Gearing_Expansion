package net.dollar.simplegear.events;

import net.dollar.simplegear.config.ModCommonConfigs;
import net.dollar.simplegear.util.ModTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Handles adjusting damage dealt to a LivingEntity if the attacker is wielding a weapon that should
 *  do bonus damage to the target. Intercepts LivingAttackEvent and performs operations there.
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModLivingDamageHandler {
//    /**
//     * Intercepts LivingAttackEvent to potentially increase damage to deal if the user is wielding a
//     *  weapon that should deal bonus damage to the target EntityType.
//     * @param event Event fired when LivingEntity is attacked
//     */
//    @SubscribeEvent
//    public static void damageEventHandler(LivingDamageEvent event) {
//        if (event.getSource().getEntity() instanceof LivingEntity attackerLiving) {
//            LivingEntity targetLiving = event.getEntity();
//            boolean flag1 = (targetLiving.getType().is(ModTags.Entities.END_MOBS) &&
//                    attackerLiving.getItemBySlot(EquipmentSlot.MAINHAND).is(ModTags.Items.TOOLS_INFUSED_DIAMOND));
//            boolean flag2 = (targetLiving.getType().is(ModTags.Entities.NETHER_MOBS) &&
//                    attackerLiving.getItemBySlot(EquipmentSlot.MAINHAND).is(ModTags.Items.TOOLS_NETHERITE));
//            boolean flag3 = (targetLiving.getType().is(ModTags.Entities.STURDY_MOBS) &&
//                    attackerLiving.getItemBySlot(EquipmentSlot.MAINHAND).is(ModTags.Items.TOOLS_TUNGSTEN_CARBIDE));
//
//            //if any of the three are true, increase damage by 10%
//            if (flag1 || flag2 || flag3) {
//                //convert bonus damage % config (int, ex. 10) to float modifier (ex. 1.1f)
//                float bonusDamageToFloatModifier = 1 + (
//                        (float)ModCommonConfigs.ENDGAME_WEAPON_BONUS_DAMAGE.get() / 100f);
//                event.setAmount(event.getAmount() * bonusDamageToFloatModifier);
//            }
//        }
//    }
}
