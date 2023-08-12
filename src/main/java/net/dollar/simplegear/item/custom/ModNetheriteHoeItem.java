package net.dollar.simplegear.item.custom;

import net.dollar.simplegear.config.ModCommonConfigs;
import net.dollar.simplegear.util.ModUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ModNetheriteHoeItem extends HoeItem {
    public ModNetheriteHoeItem(Tier p_42961_, int p_42962_, float p_42963_, Properties p_42964_) {
        super(p_42961_, p_42962_, p_42963_, p_42964_);
    }

    /**
     * Performs normal hurtEnemy operations but with chance to apply additional effect(s)
     * @param stack ItemStack of this item
     * @param attackedEntity Attacked living entity
     * @param attacker Attacker (holder) living entity
     * @return Whether attack was successful???
     */
    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity attackedEntity, LivingEntity attacker) {
        ModUtils.rollNetheriteOnHitAndApply(attackedEntity, attacker,
                ModCommonConfigs.NETHERITE_EFFECT_CHANCE.get()); //blunt, default 20
        return super.hurtEnemy(stack, attackedEntity, attacker);
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> p_41423_, TooltipFlag p_41424_) {
        p_41423_.add(Component.literal("§4Chance on-hit: Wither target for 3s"));
    }
}
