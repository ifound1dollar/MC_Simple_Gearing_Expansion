package net.dollar.simplegear.block;

import net.dollar.simplegear.tile.ModTileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

/**
 * Used specifically by the new Spectral Lantern block. Overrides newBlockEntity() to spawn a custom
 *  BlockEntity that allows player interaction using a Compound Gemstone.
 */
public class ModSpectralLanternBlock extends LanternBlock implements EntityBlock {
    public ModSpectralLanternBlock(Properties p_49795_) {
        super(p_49795_);
    }



    /**
     * Generates a new BlockEntity of a specific type.
     * @param pos Position to generate
     * @param state Current blockstate
     * @return Generated BlockEntity
     */
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return ModTileEntities.SPECTRAL_LANTERN_TILE.get().create(pos, state);
    }
}
