package net.dollar.simplegear.item.custom.infused;

import net.dollar.simplegear.config.ModCommonConfigs;
import net.dollar.simplegear.enchantment.ModEnchantments;
import net.dollar.simplegear.util.IInfusedDiamondItem;
import net.dollar.simplegear.util.ModUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Used specifically for the Infused Diamond Hoe, which has custom on-hit effect chance and
 *  custom hover text.
 */
public class ModInfusedDiamondHoeItem extends HoeItem implements IInfusedDiamondItem {
    public ModInfusedDiamondHoeItem(Tier p_42961_, int p_42962_, float p_42963_, Properties p_42964_) {
        super(p_42961_, p_42962_, p_42963_, p_42964_);
    }



    /**
     * Performs normal hurtEnemy operations but with chance to apply additional effect(s).
     * @param stack ItemStack of this Item
     * @param attackedEntity Attacked (target) living entity
     * @param attacker Attacker (user) living entity
     * @return Whether attack was successfully performed
     */
    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity attackedEntity, LivingEntity attacker) {
        ModUtils.rollInfusedDiamondOnHitAndApply(attackedEntity, attacker,
                ModCommonConfigs.INFUSED_DIAMOND_EFFECT_CHANCE.get()); //blunt, default 17
        return super.hurtEnemy(stack, attackedEntity, attacker);
    }

    /**
     * Appends text (as Component) to the Item's hover tooltip (on-hit effect info).
     * @param stack ItemStack corresponding to this Item
     * @param level Active level
     * @param components List of Components that make up the tooltip
     * @param flag TooltipFlag determining whether NORMAL or ADVANCED
     */
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        ModUtils.appendInfusedDiamondEquipmentTooltip(components, false);
    }
}
