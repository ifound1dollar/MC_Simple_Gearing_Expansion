package net.dollar.testmod.util;

import net.dollar.testmod.TestMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;

public class ModMaceItem extends DiggerItem {
    public ModMaceItem(Tier tier, float attackDamage, float attackSpeed, Properties properties) {
        super(attackDamage, attackSpeed, tier,
                BlockTags.create(new ResourceLocation(TestMod.MOD_ID, "mineable_with_mace")),
                properties);    //new blocktag not needed for swords
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity targetEntity, LivingEntity userEntity) {
        stack.hurtAndBreak(1, userEntity, (p_43296_) -> {
            p_43296_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        }); //override to only deal 1 durability damage to tool when attacking enemy
        return true;
    }

    @Override
    public boolean canPerformAction(ItemStack stack, net.minecraftforge.common.ToolAction toolAction) {
        return false;   //cannot perform any non-attack actions
    }
}
