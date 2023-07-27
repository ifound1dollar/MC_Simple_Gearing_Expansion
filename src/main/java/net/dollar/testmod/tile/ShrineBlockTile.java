package net.dollar.testmod.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ShrineBlockTile extends BlockEntity {
    public ShrineBlockTile(BlockPos p_155229_, BlockState p_155230_) {
        super(ModTileEntities.SHRINE_BLOCK.get(), p_155229_, p_155230_);
    }

    @Override
    public void onChunkUnloaded() {
        super.onChunkUnloaded();    //fail on chunk unloaded if still active
    }
}
