package net.dollar.simplegear.item.custom;

import net.dollar.simplegear.config.ModCommonConfigs;
import net.dollar.simplegear.item.ModMaceItem;
import net.dollar.simplegear.util.ModUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Used specifically for the Netherite Mace, which has custom on-hit effect chance, is fire-resistant,
 *  and has custom hover text.
 */
public class ModNetheriteMaceItem extends ModMaceItem {
    public ModNetheriteMaceItem(Tier p_42961_, float p_42962_, float p_42963_, Properties p_42964_) {
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
        ModUtils.rollNetheriteOnHitAndApply(attackedEntity, attacker,
                ModCommonConfigs.NETHERITE_EFFECT_CHANCE.get()); //blunt, default 20
        return super.hurtEnemy(stack, attackedEntity, attacker);
    }

    /**
     * Gets whether Entities of this Item are resistant to fire and lava (true).
     * @return Whether this Item is fire-resistant.
     */
    @Override
    public boolean isFireResistant() {
        return true;
    }

    /**
     * Appends text to the Item's hover tooltip (on-hit effect info).
     * @param stack ItemStack corresponding to this Item
     * @param level Active level
     * @param components List of Components that make up the tooltip
     * @param flag TooltipFlag determining whether NORMAL or ADVANCED
     */
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        ModUtils.appendNetheriteEquipmentTooltip(components, false);
    }
}
