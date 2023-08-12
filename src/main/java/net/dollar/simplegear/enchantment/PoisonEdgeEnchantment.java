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

public class PoisonEdgeEnchantment extends Enchantment {
    //THE CONSTRUCTOR, MIN/MAX COST METHODS, AND MAX LEVEL METHOD ARE COPIED DIRECTLY FROM FireAspectEnchantment
    protected PoisonEdgeEnchantment(Enchantment.Rarity rarity, EquipmentSlot... slots) {
        super(rarity, ModEnchantments.MACE_ONLY, slots);
    }

    public int getMinCost(int p_45000_) {
        return 10 + 20 * (p_45000_ - 1);
    }

    public int getMaxCost(int p_45002_) {
        return super.getMinCost(p_45002_) + 50;
    }

    public int getMaxLevel() {
        return 2;
    }

    @Override
    public boolean checkCompatibility(Enchantment enchantment) {
        //mutually exclusive with Fire Aspect
        return !(enchantment instanceof PoisonEdgeEnchantment) && !(enchantment instanceof FireAspectEnchantment);
    }

    @Override
    public boolean canEnchant(ItemStack p_44642_) {
        //can only apply to Mace
        return p_44642_.getItem() instanceof ModMaceItem;
    }

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