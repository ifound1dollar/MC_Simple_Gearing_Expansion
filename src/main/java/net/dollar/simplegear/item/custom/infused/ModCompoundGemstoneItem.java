package net.dollar.simplegear.item.custom.infused;

import net.dollar.simplegear.tile.ModSpectralLanternBlockEntity;
import net.dollar.simplegear.util.IInfusedDiamondItem;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;

/**
 * Used specifically for the Compound Gemstone, which has custom interaction when used on a Spectral Lantern.
 */
public class ModCompoundGemstoneItem extends Item implements IInfusedDiamondItem {
    public ModCompoundGemstoneItem(Properties p_41383_) {
        super(p_41383_);
    }

    /**
     * Handles interaction when a player right clicks something with this Item. Specifically,
     *  when a player interacts with a Spectral Lantern while holding this Item.
     * NOTE: This is the first method called regarding this specific interaction.
     * @param context Generated UseOnContext
     * @return The corresponding InteractionResult; if CONSUME, prevents all further operations
     *  on this interaction
     */
    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getLevel().getBlockEntity(context.getClickedPos()) instanceof ModSpectralLanternBlockEntity tile) {
            //CONSUME only if method within ShrineBlockTile returned true, otherwise PASS below
            if (tile.attemptSpawnBoss(context)) {
                return InteractionResult.CONSUME;
            }
        }

        //if not ShrineBlockTile OR mob could not be spawned, PASS
        return InteractionResult.PASS;
    }
}
