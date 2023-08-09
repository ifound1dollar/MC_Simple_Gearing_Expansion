package net.dollar.testmod.item.custom;

import net.dollar.testmod.config.ModCommonConfigs;
import net.dollar.testmod.enchantment.ModEnchantments;
import net.dollar.testmod.item.ModItems;
import net.dollar.testmod.util.IDamageHandlingArmor;
import net.dollar.testmod.util.IInfusedDiamondItem;
import net.dollar.testmod.util.ModUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ModInfusedDiamondArmorItem extends ArmorItem implements IDamageHandlingArmor, IInfusedDiamondItem {
    boolean isFullSet;


    public ModInfusedDiamondArmorItem(ArmorMaterial p_40386_, Type p_266831_, Properties p_40388_) {
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
        boolean hasHelmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ModItems.INFUSED_DIAMOND_HELMET.get();
        boolean hasChestplate = player.getItemBySlot(EquipmentSlot.CHEST).getItem() == ModItems.INFUSED_DIAMOND_CHESTPLATE.get();
        boolean hasLeggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem() == ModItems.INFUSED_DIAMOND_LEGGINGS.get();
        boolean hasBoots = player.getItemBySlot(EquipmentSlot.FEET).getItem() == ModItems.INFUSED_DIAMOND_BOOTS.get();
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

        //if taking damage from Magic source, reduce damage taken
        if (ModUtils.getDamageCategory(source) == ModUtils.DamageCategory.MAGIC) {
            return amount * (1 - (float)(ModCommonConfigs.INFUSED_DIAMOND_MAGIC_DAMAGE_REDUCTION.get() / 100));
        }
        return amount;  //if reaches here, return original amount
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return super.canApplyAtEnchantingTable(stack, enchantment)
                || enchantment == ModEnchantments.MENDING_VERY_RARE.get();
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> p_41423_, TooltipFlag p_41424_) {
        p_41423_.add(Component.literal(String.format("§5Full Set: %s%% Magic damage reduction",
                ModCommonConfigs.INFUSED_DIAMOND_MAGIC_DAMAGE_REDUCTION.get())));
    }
}
