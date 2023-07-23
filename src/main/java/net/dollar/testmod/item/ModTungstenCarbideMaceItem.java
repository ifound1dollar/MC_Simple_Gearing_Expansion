package net.dollar.testmod.item;

import net.dollar.testmod.util.ModMaceItem;
import net.dollar.testmod.util.ModUtils;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;

public class ModTungstenCarbideMaceItem extends ModMaceItem {
    public ModTungstenCarbideMaceItem(Tier p_42961_, float p_42962_, float p_42963_, Properties p_42964_) {
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
        ModUtils.rollTungstenCarbideOnHitAndApply(attackedEntity, attacker, 25);  //blunt
        return super.hurtEnemy(stack, attackedEntity, attacker);
    }

    @Override
    public boolean isFireResistant() {
        return true;
    }

    @Override
    public boolean canBeHurtBy(DamageSource source) {
        //entity cannot be destroyed by explosions or fire
        return !(source.is(DamageTypeTags.IS_FIRE) || source.is(DamageTypeTags.IS_EXPLOSION));
    }

}
