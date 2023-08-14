package net.dollar.simplegear.util;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

/**
 * Implemented by end-game armor tiers to modify damage taken from specific sources
 */
public interface IDamageHandlingArmor {
    default float onDamaged(LivingEntity entity, EquipmentSlot slot, DamageSource source, float amount) {
        return amount;
    }
}
