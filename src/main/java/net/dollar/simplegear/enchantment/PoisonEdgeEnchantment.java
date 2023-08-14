package net.dollar.simplegear.enchantment;

import net.dollar.simplegear.item.ModMaceItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.FireAspectEnchantment;

/**
 * Defines new Enchantment that can be applied only to Maces. Behaves similarly to Fire Aspect,
 *  but Poison instead.
 */
public class PoisonEdgeEnchantment extends Enchantment {
    //THE CONSTRUCTOR, MIN/MAX COST METHODS, AND MAX LEVEL METHOD ARE COPIED DIRECTLY FROM FireAspectEnchantment
    protected PoisonEdgeEnchantment(Enchantment.Rarity rarity, EquipmentSlot... slots) {
        super(rarity, ModEnchantments.MACE_ONLY, slots);
    }


    /**
     * Gets minimum cost required to apply this Enchantment at an Enchanting Table.
     * @param p_45000_ Unknown
     * @return Minimum cost required to apply
     */
    public int getMinCost(int p_45000_) {
        return 10 + 20 * (p_45000_ - 1);
    }

    /**
     * Gets maximum cost required to apply this Enchantment at an Enchanting Table.
     * @param p_45002_ Unknown
     * @return Maximum cost required to apply
     */
    public int getMaxCost(int p_45002_) {
        return super.getMinCost(p_45002_) + 50;
    }

    /**
     * Gets maximum level of this enchantment that can be applied. Here, the maximum level is 2.
     * @return Maximum level
     */
    public int getMaxLevel() {
        return 2;
    }



    /**
     * Checks compatibility of this Enchantment against another Enchantment. Here, is mutually exclusive
     *  with Fire Aspect (and itself of course).
     * @param enchantment Other enchantment being queried
     * @return Whether this and the param Enchantment are compatible
     */
    @Override
    public boolean checkCompatibility(Enchantment enchantment) {
        //mutually exclusive with Fire Aspect
        return !(enchantment instanceof PoisonEdgeEnchantment) && !(enchantment instanceof FireAspectEnchantment);
    }

    /**
     * Checks whether a specific Item can be Enchanted with this Enchantment. Here, can only be
     *  applied to Maces.
     * @param stack The item being queried
     * @return Whether the Item can be Enchanted with this
     */
    @Override
    public boolean canEnchant(ItemStack stack) {
        //can only apply to Mace
        return stack.getItem() instanceof ModMaceItem;
    }

    /**
     * Performs post-attack operation of an Item with this Enchantment. Here, Poisons target for
     *  either 5 or 10s based on level.
     * @param user LivingEntity (user) performing the attack
     * @param target Entity (target) being attacked
     * @param enchantLevel Level of this Enchantment applied to the item
     */
    @Override
    public void doPostAttack(LivingEntity user, Entity target, int enchantLevel) {
        //LivingEntity is user, Entity is target, int is enchantment level
        if (target instanceof LivingEntity livingEntity) {
            //Level 1 Poison (1 damage / 25 ticks), 5s for Enchantment Level 1, 10s for Level 2
            //  (Fire Aspect deals 1 damage / 20 ticks, 4s/8s for Enchantment Levels 1/2)
            if (enchantLevel == 0) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.POISON, 100, 0));
            } else {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.POISON, 200, 0));
            }
        }
    }
}