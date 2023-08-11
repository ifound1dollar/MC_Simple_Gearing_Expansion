package net.dollar.testmod.util;

import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;

import java.util.Random;

public class ModUtils {
    public enum DamageCategory { NONE, BLUNT, SHARP, MAGIC, FIRE, EXPLOSION }

    public static DamageCategory getDamageCategory(DamageSource source)
    {
        //FIRST, check if source is from a mob or player (will check for held item in this case)
        if (source.is(DamageTypes.MOB_ATTACK) || source.is(DamageTypes.MOB_ATTACK_NO_AGGRO)
                || source.is(DamageTypes.PLAYER_ATTACK)) {
            //access attacker LivingEntity if valid, checking its held item
            if (source.getEntity() instanceof LivingEntity attacker) {
                //only Swords and Axes should be considered sharp weapons
                Item heldItem = attacker.getItemBySlot(EquipmentSlot.MAINHAND).getItem();
                if (heldItem instanceof SwordItem || heldItem instanceof AxeItem) {
                    return DamageCategory.SHARP;
                }
            }
            //if attacked directly from mob or player BUT IS NOT SHARP, assume BLUNT
            return DamageCategory.BLUNT;
        }

        //THEN, if damage is not directly coming from a mob or player attack, can check other categories

        //THIS COULD BE PUT IN A MAP, DamageType:DamageCategory, FOR MUCH MORE EFFICIENT ACCESSING
        //GOOD GOD PUT THIS IN A MAP PLEASE, if it doesn't contain key, return NONE (ternary operator)
        if (source.is(DamageTypes.ARROW) || source.is(DamageTypes.CACTUS)
                || source.is(DamageTypes.FALLING_STALACTITE) || source.is(DamageTypes.STALAGMITE)
                || source.is(DamageTypes.STING) || source.is(DamageTypes.SWEET_BERRY_BUSH)
                || source.is(DamageTypes.THORNS) || source.is(DamageTypes.TRIDENT)) {
            return DamageCategory.SHARP;
        } else if (source.is(DamageTypes.FALLING_ANVIL) || source.is(DamageTypes.FALLING_BLOCK)
                || source.is(DamageTypes.FLY_INTO_WALL) || source.is(DamageTypes.MOB_PROJECTILE)
                || source.is(DamageTypes.THROWN)) {
            return DamageCategory.BLUNT;
        } else if (source.is(DamageTypes.DRAGON_BREATH) || source.is(DamageTypes.INDIRECT_MAGIC)
                || source.is(DamageTypes.MAGIC) || source.is(DamageTypes.SONIC_BOOM)
                || source.is(DamageTypes.WITHER)) {
            return DamageCategory.MAGIC;
        } else if (source.is(DamageTypes.FIREBALL) || source.is(DamageTypes.HOT_FLOOR)
                || source.is(DamageTypes.IN_FIRE) || source.is(DamageTypes.LAVA)
                || source.is(DamageTypes.ON_FIRE) || source.is(DamageTypes.UNATTRIBUTED_FIREBALL)) {
            return DamageCategory.FIRE;
        } else if (source.is(DamageTypes.EXPLOSION) || source.is(DamageTypes.FIREWORKS)
                || source.is(DamageTypes.PLAYER_EXPLOSION) || source.is(DamageTypes.WITHER_SKULL)) {
            return DamageCategory.EXPLOSION;
        }

        //FINALLY, if not specific above, return NONE
        return DamageCategory.NONE;
    }



    /**
     * Rolls chance to apply special effect on attack using Infused Diamond tools/armor, and applies when applicable
     * @param attackedEntity Attacked entity
     * @param attacker Attacking (holder) entity
     * @param targetEffectChance Chance out of 100 to apply this effect
     */
    public static void rollInfusedDiamondOnHitAndApply(LivingEntity attackedEntity, LivingEntity attacker, int targetEffectChance) {
        //roll chance to remove Slowness from and apply Speed to user (attacker) for 4 seconds
        if (new Random().nextInt(100) < targetEffectChance) {
            attacker.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
            attacker.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 80));
        }
    }

    /**
     * Rolls chance to apply special effect on attack using Netherite tools/armor, and applies when applicable
     * @param attackedEntity Attacked entity
     * @param attacker Attacking (holder) entity
     * @param targetEffectChance Chance out of 100 to apply this effect
     */
    public static void rollNetheriteOnHitAndApply(LivingEntity attackedEntity, LivingEntity attacker, int targetEffectChance) {
        //roll chance to apply Wither effect to target (attackedEntity) for 4 seconds
        if (new Random().nextInt(100) < targetEffectChance) {
            //apply level 2 wither for once-per-second damage tick
            attackedEntity.addEffect(new MobEffectInstance(MobEffects.WITHER, 80, 1));
        }
    }

    /**
     * Rolls chance to apply special effect on attack using Tungsten-Carbide tools/armor, and applies when applicable
     * @param attackedEntity Attacked entity
     * @param attacker Attacking (holder) entity
     * @param targetEffectChance Chance out of 100 to apply this effect
     */
    public static void rollTungstenCarbideOnHitAndApply(LivingEntity attackedEntity, LivingEntity attacker, int targetEffectChance) {
        //roll chance to apply Slowness effect to target (attackedEntity) for 4 seconds
        if (new Random().nextInt(100) < targetEffectChance) {
            //apply level 2 slow (third argument) for 30% reduction, 15%/level
            attackedEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 80, 1));
        }
    }
}
