package net.dollar.testmod.item;

import net.dollar.testmod.util.IInfusedDiamondItem;
import net.dollar.testmod.util.ModUtils;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class ModInfusedDiamondSwordItem extends SwordItem implements IInfusedDiamondItem {
    public ModInfusedDiamondSwordItem(Tier p_42961_, int p_42962_, float p_42963_, Properties p_42964_) {
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
        ModUtils.rollInfusedDiamondOnHitAndApply(attackedEntity, attacker, 15); //sharp, all 15
        return super.hurtEnemy(stack, attackedEntity, attacker);
    }

    @Override
    public boolean isFireResistant() {
        return true;
    }

    @Override
    public boolean canBeHurtBy(DamageSource source) {
        if (this.isFireResistant() && source.is(DamageTypeTags.IS_FIRE)) {
            return false;
        }
        return !source.is(DamageTypeTags.IS_EXPLOSION);
        //entity cannot be destroyed by explosions or fire if fire-resistant

        //THIS METHOD ONLY NEEDS TO BE OVERRIDDEN IN TUNGSTEN-CARBIDE, NOTHING CHANGES IN INFUSED DIAMOND
    }
}
