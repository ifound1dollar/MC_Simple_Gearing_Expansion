package net.dollar.testmod.item.custom;

import net.dollar.testmod.tile.ModSpectralLanternBlockEntity;
import net.dollar.testmod.util.IInfusedDiamondItem;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;

public class ModCompoundGemstoneItem extends Item implements IInfusedDiamondItem {
    public ModCompoundGemstoneItem(Properties p_41383_) {
        super(p_41383_);
    }

//    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> p_41423_, TooltipFlag p_41424_) {
//        p_41423_.add(Component.literal("§5Empowered with an ancient magic"));
//        super.appendHoverText(p_41421_, p_41422_, p_41423_, p_41424_);
//    }

    //this method is the first that will be called; should consume the interaction here
    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getLevel().getBlockEntity(context.getClickedPos()) instanceof ModSpectralLanternBlockEntity tile) {
            //CONSUME only if method within ShrineBlockTile returned true, otherwise PASS below
            if (tile.attemptSpawnBoss(context)) {
//                context.getLevel().playLocalSound(context.getClickedPos(), SoundEvents.ENCHANTMENT_TABLE_USE,
//                        SoundSource.BLOCKS, 5.0f, 1.0f, true);
                //ABOVE ALWAYS FAILS BECAUSE METHOD WILL NEVER RETURN SUCCESSFUL CLIENT-SIDE

                return InteractionResult.CONSUME;
            }
        }

        //if not ShrineBlockTile OR mob could not be spawned, PASS
        return InteractionResult.PASS;
    }
}
