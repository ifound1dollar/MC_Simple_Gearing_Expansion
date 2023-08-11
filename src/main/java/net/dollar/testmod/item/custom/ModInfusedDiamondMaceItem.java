package net.dollar.testmod.item.custom;

import net.dollar.testmod.config.ModCommonConfigs;
import net.dollar.testmod.enchantment.ModEnchantments;
import net.dollar.testmod.util.IInfusedDiamondItem;
import net.dollar.testmod.item.ModMaceItem;
import net.dollar.testmod.util.ModUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ModInfusedDiamondMaceItem extends ModMaceItem implements IInfusedDiamondItem {
    public ModInfusedDiamondMaceItem(Tier p_42961_, float p_42962_, float p_42963_, Properties p_42964_) {
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
        ModUtils.rollInfusedDiamondOnHitAndApply(attackedEntity, attacker,
                ModCommonConfigs.INFUSED_DIAMOND_EFFECT_CHANCE.get()); //blunt, default 15
        return super.hurtEnemy(stack, attackedEntity, attacker);
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> p_41423_, TooltipFlag p_41424_) {
        p_41423_.add(Component.literal("§5Chance on-hit: User gains Speed for 3s"));
    }
}
