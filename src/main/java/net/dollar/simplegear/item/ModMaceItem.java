package net.dollar.simplegear.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.dollar.simplegear.enchantment.ModEnchantments;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

/**
 * New weapon type, extending TieredItem and implementing Vanishable. Fields and methods are nearly
 *  identical to that of SwordItem. Has custom canApplyAtEnchantingTable() behavior to allow applying
 *  custom enchantments.
 */
public class ModMaceItem extends TieredItem implements Vanishable {
    private final float attackDamage;
    private final Multimap<Attribute, AttributeModifier> defaultModifiers;

    /**
     * Constructs a new ModMaceItem object.
     * @param tier Equipment tier
     * @param attackDamage Base attack damage (before Tier modifier)
     * @param attackSpeed Attack speed (NOT mining speed)
     * @param properties Item properties
     */
    public ModMaceItem(Tier tier, float attackDamage, float attackSpeed, Properties properties) {
        super(tier, properties);
        this.attackDamage = attackDamage + tier.getAttackDamageBonus();
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (double)this.attackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", (double)attackSpeed, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }

    /**
     * Allows typical weapon enchantments (excluding Sharpness) in addition to new Mace-only
     *  enchantments: Hardness (replaces Sharpness, same functionality) and Poison Edge (poisons target on-hit).
     * @param stack The ItemStack attempting to be enchanted (this)
     * @param enchantment The Enchantment attempting to be applied
     * @return Whether the enchantment is allowed
     */
    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return (enchantment == ModEnchantments.HARDNESS.get() ||
                enchantment == Enchantments.BANE_OF_ARTHROPODS ||
                enchantment == Enchantments.SMITE ||
                enchantment == Enchantments.UNBREAKING ||
                enchantment == Enchantments.FIRE_ASPECT ||
                enchantment == ModEnchantments.POISON_EDGE.get() ||
                enchantment == Enchantments.MOB_LOOTING ||
                enchantment == Enchantments.MENDING);   //NOTE: Mending is treasure only, this just allows books.
    }

    /**
     * Getter for attackDamage field.
     * @return Value of attackDamage field
     */
    public float getDamage() {
        return this.attackDamage;
    }

    /**
     * Checks whether a player can attack a specific block with this item.
     * @return Whether the player can attack a block with this Item
     */
    public boolean canAttackBlock(BlockState state, Level level, BlockPos blockPos, Player player) {
        return !player.isCreative();
    }

    /**
     * Performs Item-specific attack operations (ex. deal durability damage).
     * @param stack ItemStack corresponding to this Item
     * @param targetEntity Target LivingEntity
     * @param userEntity User LivingEntity
     * @return Whether the attack was successfully performed
     */
    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity targetEntity, LivingEntity userEntity) {
        stack.hurtAndBreak(1, userEntity, (p_43296_) -> {
            p_43296_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        }); //override to only deal 1 durability damage to tool when attacking enemy
        return true;
    }

    /**
     * Performs Item-specific mining operations (ex. deal durability damage).
     * @param stack ItemStack corresponding to this Item
     * @param level Active game Level
     * @param state Blockstate of block being mined
     * @param blockPos Position of block being mined
     * @param userEntity User LivingEntity
     * @return Whether the mining was successfully performed
     */
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos blockPos, LivingEntity userEntity) {
        if (state.getDestroySpeed(level, blockPos) != 0.0F) {
            stack.hurtAndBreak(1, userEntity, (p_43276_) -> {
                p_43276_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            }); //change to only deal 1 durability damage instead of 2
        }

        return true;
    }


    /**
     * Calculate destroy speed of a specific block using this Item.
     * @param stack ItemStack corresponding to this Item
     * @param state BlockState of target block
     * @return Calculated destroy speed
     */
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return 1.0F;    //do not destroy anything more efficiently
    }

    /**
     * Checks whether this Item is the correct tool to drop loot from the target block.
     * @param state BlockState of target block
     * @return Whether this is the correct tool for drops
     */
    public boolean isCorrectToolForDrops(BlockState state) {
        return false;   //THIS CAN BE MADE TO WORK WITH COBBLESTONE, COBBLED DEEPSLATE, AND GRAVEL
    }

    /**
     * Gets Map of default attribute modifiers for the corresponding EquipmentSlot.
     * @param slot EquipmentSlot corresponding to this Item
     * @return Multimap of Attributes, AttributeModifiers
     */
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(slot);
    }

    /**
     * Checks whether this Item can perform a specific ToolAction.
     * @param stack ItemStack corresponding to this Item
     * @param toolAction ToolAction being queried
     * @return Whether this Item can perform the queried ToolAction
     */
    @Override
    public boolean canPerformAction(ItemStack stack, net.minecraftforge.common.ToolAction toolAction) {
        return false;   //cannot perform any non-attack actions
    }
}
