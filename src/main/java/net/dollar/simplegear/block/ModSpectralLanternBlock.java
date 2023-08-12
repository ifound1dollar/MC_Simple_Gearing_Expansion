package net.dollar.simplegear.block;

import net.dollar.simplegear.tile.ModTileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class ModSpectralLanternBlock extends LanternBlock implements EntityBlock {
    public ModSpectralLanternBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return ModTileEntities.SPECTRAL_LANTERN_TILE.get().create(pos, state);
    }
}
