package net.dollar.simplegear.util;

import net.dollar.simplegear.config.ModCommonConfigs;
import net.minecraft.network.chat.Component;
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

/**
 * Contains a number of helper methods used in various parts of the mod
 */
public class ModUtils {
    public enum DamageCategory { NONE, BLUNT, SHARP, MAGIC, FIRE, EXPLOSION }


    /**
     * Takes a vanilla DamageSource and determines which custom DamageCategory it should fall under
     * @param source Vanilla DamageSource
     * @return The determined DamageCategory (NONE if inconclusive)
     */
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
            attacker.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,
                    ModCommonConfigs.ENDGAME_TIER_EFFECT_SECONDS.get() * 20));
        }
    }
    /**
     * Generates tooltip to be appended to all Infused Diamond equipment, different for armor and tools/weapons
     * @param isArmor Whether tooltip should be generated for armor or tools/weapons
     * @return Generated tooltip as Component
     */
    public static Component getInfusedDiamondEquipmentTooltip(boolean isArmor) {
        if (isArmor) {
            return Component.literal(String.format("§5Full Set: %s%% Magic damage reduction",
                    ModCommonConfigs.INFUSED_DIAMOND_MAGIC_DAMAGE_REDUCTION.get()));
        } else {
            return Component.literal(String.format("§5Chance on-hit: User gains Speed for %ss",
                    ModCommonConfigs.ENDGAME_TIER_EFFECT_SECONDS.get()));
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
            //apply level 2 wither for once-per-second damage tick (duration +1 so ticks 4 times)
            attackedEntity.addEffect(new MobEffectInstance(MobEffects.WITHER,
                    (ModCommonConfigs.ENDGAME_TIER_EFFECT_SECONDS.get() * 20) + 1, 1));
        }
    }
    /**
     * Generates tooltip to be appended to all Netherite equipment, different for armor and tools/weapons
     * @param isArmor Whether tooltip should be generated for armor or tools/weapons
     * @return Generated tooltip as Component
     */
    public static Component getNetheriteEquipmentTooltip(boolean isArmor) {
        if (isArmor) {
            return Component.literal(String.format("§4Full Set: %s%% Fire damage reduction",
                    ModCommonConfigs.NETHERITE_FIRE_DAMAGE_REDUCTION.get()));
        } else {
            return Component.literal(String.format("§4Chance on-hit: Wither target for %ss",
                    ModCommonConfigs.ENDGAME_TIER_EFFECT_SECONDS.get()));
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
            attackedEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,
                    ModCommonConfigs.ENDGAME_TIER_EFFECT_SECONDS.get() * 20, 1));
        }
    }
    /**
     * Generates tooltip to be appended to all Tungsten-Carbide equipment, different for armor and tools/weapons
     * @param isArmor Whether tooltip should be generated for armor or tools/weapons
     * @return Generated tooltip as Component
     */
    public static Component getTungstenCarbideEquipmentTooltip(boolean isArmor) {
        if (isArmor) {
            return Component.literal(String.format("§8Full Set: %s%% Explosion damage reduction",
                    ModCommonConfigs.TUNGSTEN_CARBIDE_EXPLOSION_DAMAGE_REDUCTION.get()));
        } else {
            return Component.literal(String.format("§8Chance on-hit: Slow target for %ss",
                    ModCommonConfigs.ENDGAME_TIER_EFFECT_SECONDS.get()));
        }
    }

}
