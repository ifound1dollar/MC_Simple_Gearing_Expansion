package net.dollar.testmod.tile;

import net.dollar.testmod.entity.ModEntities;
import net.dollar.testmod.entity.custom.KathleenTheWickedEntity;
import net.dollar.testmod.entity.custom.OldLadyMuffEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.TimeUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.apache.commons.lang3.RandomUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

public class ShrineBlockTile extends BlockEntity {
    private Instant lastUsedInstant = Instant.MIN;  //far past, allow to work instantly

    public ShrineBlockTile(BlockPos p_155229_, BlockState p_155230_) {
        super(ModTileEntities.SHRINE_BLOCK.get(), p_155229_, p_155230_);
    }

    public boolean attemptSpawnBoss(UseOnContext context) {
        //only perform this logic client-side
        if (!context.getLevel().isClientSide) {
            //fail if last attempt was less than 15 seconds ago
            if (Duration.between(lastUsedInstant, Instant.now()).getSeconds() < 15) {
                return false;
            }

            //SEND MESSAGE IN CHAT TO NEARBY PLAYERS

            Monster entity = randomlySelectBoss(context);
            entity.setTarget(context.getPlayer());

            entity.setPos(context.getClickLocation().add(0.0d, 2.0d, 0.0d));    //move up 2
            context.getLevel().addFreshEntity(entity);

            //store timestamp for cooldown use
            lastUsedInstant = Instant.now();

            //consume one Compound Gemstone
            context.getItemInHand().shrink(1);

            return true;
        }
        return false;
    }

    private Monster randomlySelectBoss(UseOnContext context) {
        switch (RandomUtils.nextInt(0, 2)) {
            default -> {    //can interpret default as case 0, otherwise complains about duplicate
                return new KathleenTheWickedEntity(ModEntities.KATHLEEN_THE_WICKED.get(), context.getLevel());
            }
            case 1 -> {
                return new OldLadyMuffEntity(ModEntities.OLD_LADY_MUFF.get(), context.getLevel());
            }
        }
    }
}
