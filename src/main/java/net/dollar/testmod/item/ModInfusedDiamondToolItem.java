package net.dollar.testmod.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;

import java.util.Random;

public class ModInfusedDiamondToolItem {
    private final int targetEffectChance = 20;

    /**
     * Rolls chance to apply special effect to user or target, then applies when necessary
     * @param attackedEntity Attacked entity
     * @param attacker Attacking (holder) entity
     */
    protected void rollEffectChanceAndApply(LivingEntity attackedEntity, LivingEntity attacker) {
        //roll chance to remove Slowness from and apply Speed effect to user (attacker) for 3 seconds
        if (new Random().nextInt(100) < targetEffectChance) {
            attacker.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
            attacker.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 3));
        }
    }



    public class Axe extends AxeItem {
        public Axe(Tier p_43269_, int p_43270_, float p_43271_, Properties p_43272_) {
            super(p_43269_, p_43270_, p_43271_, p_43272_);
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
            rollEffectChanceAndApply(attackedEntity, attacker);
            return super.hurtEnemy(stack, attackedEntity, attacker);
        }
    }
    public class Hoe extends HoeItem {
        public Hoe(Tier p_43269_, int p_43270_, float p_43271_, Properties p_43272_) {
            super(p_43269_, p_43270_, p_43271_, p_43272_);
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
            rollEffectChanceAndApply(attackedEntity, attacker);
            return super.hurtEnemy(stack, attackedEntity, attacker);
        }
    }
    public class Pickaxe extends PickaxeItem {
        public Pickaxe(Tier p_43269_, int p_43270_, float p_43271_, Properties p_43272_) {
            super(p_43269_, p_43270_, p_43271_, p_43272_);
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
            rollEffectChanceAndApply(attackedEntity, attacker);
            return super.hurtEnemy(stack, attackedEntity, attacker);
        }
    }
    public class Shovel extends ShovelItem {
        public Shovel(Tier p_43269_, int p_43270_, float p_43271_, Properties p_43272_) {
            super(p_43269_, p_43270_, p_43271_, p_43272_);
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
            rollEffectChanceAndApply(attackedEntity, attacker);
            return super.hurtEnemy(stack, attackedEntity, attacker);
        }
    }
    public class Sword extends SwordItem {
        public Sword(Tier p_43269_, int p_43270_, float p_43271_, Properties p_43272_) {
            super(p_43269_, p_43270_, p_43271_, p_43272_);
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
            rollEffectChanceAndApply(attackedEntity, attacker);
            return super.hurtEnemy(stack, attackedEntity, attacker);
        }
    }
}
