package net.dollar.testmod.enchantment;

import net.dollar.testmod.util.ModMaceItem;
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
            //Level 2 Poison (1.66DPS), 4s for Enchantment Level 1, 8s for Level 2 (just like Fire Aspect)
            if (enchantLevel == 0) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.POISON, 80, 1));
            } else {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.POISON, 160, 1));
            }
        }
    }
}