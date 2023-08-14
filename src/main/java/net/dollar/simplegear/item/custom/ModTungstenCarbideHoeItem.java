package net.dollar.simplegear.item.custom;

import net.dollar.simplegear.config.ModCommonConfigs;
import net.dollar.simplegear.util.ModUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Used specifically for the Tungsten-Carbide Hoe, which has custom on-hit effect chance, is fire
 *  and explosion resistant, and has custom hover text.
 */
public class ModTungstenCarbideHoeItem extends HoeItem {
    public ModTungstenCarbideHoeItem(Tier p_42961_, int p_42962_, float p_42963_, Properties p_42964_) {
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
        ModUtils.rollTungstenCarbideOnHitAndApply(attackedEntity, attacker,
                ModCommonConfigs.TUNGSTEN_CARBIDE_EFFECT_CHANCE.get()); //blunt, default 25
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
     * Gets whether Entities of this Item can be hurt by a specific DamageSource (false for Fire and Explosion).
     * @param source DamageSource being checked
     * @return Whether this Item can be hurt by the DamageSource
     */
    @Override
    public boolean canBeHurtBy(DamageSource source) {
        //entity cannot be destroyed by explosions or fire
        return !(source.is(DamageTypeTags.IS_FIRE) || source.is(DamageTypeTags.IS_EXPLOSION));
    }

    /**
     * Appends text (as Component) to the Item's hover tooltip (on-hit effect info).
     * @param stack ItemStack corresponding to this Item
     * @param level Active level
     * @param components List of Components that make up the tooltip
     * @param flag TooltipFlag determining whether NORMAL or ADVANCED
     */
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(ModUtils.getTungstenCarbideEquipmentTooltip(false));
    }
}
