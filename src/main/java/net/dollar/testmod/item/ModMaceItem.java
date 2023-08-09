package net.dollar.testmod.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.dollar.testmod.enchantment.ModEnchantments;
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

public class ModMaceItem extends TieredItem implements Vanishable {
    private final float attackDamage;
    private final Multimap<Attribute, AttributeModifier> defaultModifiers;

    public ModMaceItem(Tier tier, float attackDamage, float attackSpeed, Properties properties) {
        super(tier, properties);
        this.attackDamage = attackDamage + tier.getAttackDamageBonus();
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (double)this.attackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", (double)attackSpeed, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }

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

    public float getDamage() {
        return this.attackDamage;
    }

    public boolean canAttackBlock(BlockState state, Level level, BlockPos blockPos, Player player) {
        return !player.isCreative();
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity targetEntity, LivingEntity userEntity) {
        stack.hurtAndBreak(1, userEntity, (p_43296_) -> {
            p_43296_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        }); //override to only deal 1 durability damage to tool when attacking enemy
        return true;
    }

    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos blockPos, LivingEntity userEntity) {
        if (state.getDestroySpeed(level, blockPos) != 0.0F) {
            stack.hurtAndBreak(1, userEntity, (p_43276_) -> {
                p_43276_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            }); //change to only deal 1 durability damage instead of 2
        }

        return true;
    }



    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return 1.0F;    //do not destroy anything more efficiently
    }

    public boolean isCorrectToolForDrops(BlockState state) {
        return false;   //THIS CAN BE MADE TO WORK WITH COBBLESTONE, COBBLED DEEPSLATE, AND GRAVEL
    }

    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(slot);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, net.minecraftforge.common.ToolAction toolAction) {
        return false;   //cannot perform any non-attack actions
    }
}
