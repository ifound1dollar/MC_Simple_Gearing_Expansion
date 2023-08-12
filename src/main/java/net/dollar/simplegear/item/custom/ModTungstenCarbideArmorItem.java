package net.dollar.simplegear.item.custom;

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

public class ModTungstenCarbideArmorItem extends ArmorItem implements IDamageHandlingArmor {
    boolean isFullSet;


    public ModTungstenCarbideArmorItem(ArmorMaterial p_40386_, Type p_266831_, Properties p_40388_) {
        super(p_40386_, p_266831_, p_40388_);
    }

    /**
     * Checks each tick if the player has a full set of this armor
     * @param stack The ItemStack associated with this Object
     * @param level Active level/world
     * @param player Player this armor item is attached to
     */
    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        //do nothing if on client side OR if not chestplate (isFullSet will never be true on clients)
        if (level.isClientSide || LivingEntity.getEquipmentSlotForItem(stack) != EquipmentSlot.CHEST) { return; }

        //check for correct equipment, then set isFullSet accordingly
        boolean hasHelmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ModItems.TUNGSTEN_CARBIDE_HELMET.get();
        boolean hasChestplate = player.getItemBySlot(EquipmentSlot.CHEST).getItem() == ModItems.TUNGSTEN_CARBIDE_CHESTPLATE.get();
        boolean hasLeggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem() == ModItems.TUNGSTEN_CARBIDE_LEGGINGS.get();
        boolean hasBoots = player.getItemBySlot(EquipmentSlot.FEET).getItem() == ModItems.TUNGSTEN_CARBIDE_BOOTS.get();
        isFullSet = hasHelmet && hasChestplate && hasLeggings && hasBoots;
    }

    /**
     * Intercepts the damage operation and reduces damage from a certain source
     * @param entity The attacked LivingEntity (wearer)
     * @param slot Equipment slot of this item
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

    @Override
    public boolean isFireResistant() {
        return true;
    }

    @Override
    public boolean canBeHurtBy(DamageSource source) {
        //entity cannot be destroyed by explosions or fire
        return !(source.is(DamageTypeTags.IS_FIRE) || source.is(DamageTypeTags.IS_EXPLOSION));
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> p_41423_, TooltipFlag p_41424_) {
        p_41423_.add(Component.literal(String.format("§8Full Set: %s%% Explosion damage reduction",
                ModCommonConfigs.TUNGSTEN_CARBIDE_EXPLOSION_DAMAGE_REDUCTION.get())));
    }
}
