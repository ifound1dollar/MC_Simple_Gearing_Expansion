package net.dollar.testmod.util;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.dollar.testmod.TestMod;
import net.dollar.testmod.enchantment.ModEnchantments;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

import java.util.Map;

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
                enchantment == Enchantments.MOB_LOOTING);
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        //only allow these six enchantments on maces
        return EnchantmentHelper.getTagEnchantmentLevel(ModEnchantments.HARDNESS.get(), book) == 0 ||
                EnchantmentHelper.getTagEnchantmentLevel(Enchantments.BANE_OF_ARTHROPODS, book) == 0 ||
                EnchantmentHelper.getTagEnchantmentLevel(Enchantments.SMITE, book) == 0 ||
                EnchantmentHelper.getTagEnchantmentLevel(Enchantments.UNBREAKING, book) == 0 ||
                EnchantmentHelper.getTagEnchantmentLevel(Enchantments.MOB_LOOTING, book) == 0 ||
                EnchantmentHelper.getTagEnchantmentLevel(Enchantments.MENDING, book) == 0;

//        return book.getEnchantmentLevel(ModEnchantments.HARDNESS.get()) > 0 ||
//                book.getEnchantmentLevel(Enchantments.BANE_OF_ARTHROPODS) > 0 ||
//                book.getEnchantmentLevel(Enchantments.SMITE) > 0 ||
//                book.getEnchantmentLevel(Enchantments.UNBREAKING) > 0 ||
//                book.getEnchantmentLevel(Enchantments.MOB_LOOTING) > 0 ||
//                book.getEnchantmentLevel(Enchantments.MENDING) > 0;
    }

    public float getDamage() {
        return this.attackDamage;
    }

    public boolean canAttackBlock(BlockState state, Level level, BlockPos blockPos, Player player) {
        return !player.isCreative();
    }

    public float getDestroySpeed(ItemStack stack, BlockState state) {
        Material material = state.getMaterial();
        return material != Material.PLANT && material != Material.REPLACEABLE_PLANT && !state.is(BlockTags.LEAVES)
                && material != Material.VEGETABLE ? 1.0F : 1.5F;    //destroys nothing faster
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
