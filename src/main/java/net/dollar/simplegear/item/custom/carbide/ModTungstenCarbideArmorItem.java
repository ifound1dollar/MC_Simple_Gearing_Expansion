package net.dollar.simplegear.item.custom.carbide;

import net.dollar.simplegear.config.ModCommonConfigs;
import net.dollar.simplegear.item.ModItems;
import net.dollar.simplegear.util.IDamageHandlingArmor;
import net.dollar.simplegear.util.ModUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Used specifically for Tungsten-Carbide armor items, which check for full-set equipped, conditionally reduce
 *  Explosion damage taken, and have custom hover text.
 */
public class ModTungstenCarbideArmorItem extends ArmorItem implements IDamageHandlingArmor {
    boolean isFullSet;

    public ModTungstenCarbideArmorItem(ArmorMaterial p_40386_, Type p_266831_, Properties p_40388_) {
        super(p_40386_, p_266831_, p_40388_);
    }



    /**
     * Checks each tick if the player has a full set of Netherite armor.
     * @param stack The ItemStack associated with this Object
     * @param level Active level/world
     * @param player Player this armor item is equipped on
     * @param slotIndex The index of this ItemStack's active slot
     * @param selectedIndex The index of the selected slot
     */
    @Override
    public void onInventoryTick(ItemStack stack, Level level, Player player, int slotIndex, int selectedIndex) {
        //do nothing if on client side OR if not chestplate (isFullSet will never be true on clients)
        if (level.isClientSide || LivingEntity.getEquipmentSlotForItem(stack) != EquipmentSlot.CHEST) { return; }

        //check for correct equipment, then set isFullSet accordingly
        boolean hasHelmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ModItems.TUNGSTEN_CARBIDE_HELMET.get();
        boolean hasChestplate = player.getItemBySlot(EquipmentSlot.CHEST).getItem() == ModItems.TUNGSTEN_CARBIDE_CHESTPLATE.get();
        boolean hasLeggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem() == ModItems.TUNGSTEN_CARBIDE_LEGGINGS.get();
        boolean hasBoots = player.getItemBySlot(EquipmentSlot.FEET).getItem() == ModItems.TUNGSTEN_CARBIDE_BOOTS.get();
        isFullSet = hasHelmet && hasChestplate && hasLeggings && hasBoots;

        super.onInventoryTick(stack, level, player, slotIndex, selectedIndex);
    }

    /**
     * Intercepts the damage taken operation to reduce Explosion damage taken.
     * @param entity Attacked LivingEntity (wearer)
     * @param slot EquipmentSlot of this item
     * @param source Source of damage to be dealt
     * @param amount Initial damage amount
     * @return Updated damage amount
     */
    @Override
    public float onDamaged(LivingEntity entity, EquipmentSlot slot, DamageSource source, float amount) {
        //if not chestplate OR not full set, do not alter damage
        if (slot != EquipmentSlot.CHEST || !isFullSet) { return amount; }

        //entity.sendSystemMessage(Component.literal(String.valueOf(source.type())));
        //entity.sendSystemMessage(Component.literal(String.valueOf(ModUtils.getDamageCategory(source))));

        //if taking damage from Explosion source, reduce damage taken
        if (ModUtils.getDamageCategory(source) == ModUtils.DamageCategory.EXPLOSION) {
            return amount * (1 - (float)(ModCommonConfigs.TUNGSTEN_CARBIDE_EXPLOSION_DAMAGE_REDUCTION.get() / 100));
        }
        return amount;  //if reaches here, return original amount
    }

    /**
     * Gets whether Entities of this Item are resistant to fire and lava (true).
     * @return Whether this Item is fire-resistant.
     */
    @Override
    public boolean isFireResistant() {
        return true;
    }

    /**
     * Gets whether Entities of this Item can be hurt by a specific DamageSource (false for Fire and Explosion).
     * @param source DamageSource being checked
     * @return Whether this Item can be hurt by the DamageSource
     */
    @Override
    public boolean canBeHurtBy(DamageSource source) {
        //entity cannot be destroyed by explosions or fire
        return !(source.is(DamageTypeTags.IS_FIRE) || source.is(DamageTypeTags.IS_EXPLOSION));
    }

    /**
     * Appends text to the Item's hover tooltip (full-set armor bonus).
     * @param stack ItemStack corresponding to this Item
     * @param level Active level
     * @param components List of Components that make up the tooltip
     * @param flag TooltipFlag determining whether NORMAL or ADVANCED
     */
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        ModUtils.appendTungstenCarbideEquipmentTooltip(components, true);
    }
}
