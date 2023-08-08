package net.dollar.testmod.tile;

import net.dollar.testmod.entity.ModEntities;
import net.dollar.testmod.entity.custom.KathleenTheWickedEntity;
import net.dollar.testmod.entity.custom.OldLadyMuffEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.lang3.RandomUtils;

import java.time.Duration;
import java.time.Instant;

public class ModSpectralLanternBlockEntity extends BlockEntity {
    private Instant lastUsedInstant = Instant.MIN;  //far past, allow to work instantly

    public ModSpectralLanternBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(ModTileEntities.SPECTRAL_LANTERN_TILE.get(), p_155229_, p_155230_);
    }

    public boolean attemptSpawnBoss(UseOnContext context) {
        //only perform this logic client-side
        if (!context.getLevel().isClientSide && context.getPlayer() != null) {  //check for null to silence warnings
            //fail if player is creative mode
            if (context.getPlayer().isCreative()) {
                context.getPlayer().sendSystemMessage(Component.literal("§4The Spectral Lantern cannot be used" +
                        " in creative mode."));
                return false;
            }

            //fail if last attempt was less than 15 seconds ago
            if (Duration.between(lastUsedInstant, Instant.now()).getSeconds() < 15) {
                context.getPlayer().sendSystemMessage(Component.literal("§4The Spectral Lantern is not ready yet!"));
                return false;
            }

            //if below is not empty block, fail and send message
            if (!context.getLevel().isEmptyBlock(context.getClickedPos().below(2))) {
                context.getPlayer().sendSystemMessage(Component.literal("§4The Spectral Lantern rejected the " +
                        "Compound Gemstone! The area underneath is not clear."));
                return false;
            }

            Monster entity = randomlySelectBoss(context);
            entity.setTarget(context.getPlayer());
            entity.setInvisible(true);
            entity.setSilent(true);

            entity.setPos(context.getClickLocation().add(0.0d, -2.0d, 0.0d));    //move down 2
            context.getLevel().addFreshEntity(entity);

            //store timestamp for cooldown use
            lastUsedInstant = Instant.now();

            //consume one Compound Gemstone (guaranteed to still be Compound Gemstone in interactionHand)
            context.getItemInHand().shrink(1);

//            this.level.playSound(context.getPlayer(), this.worldPosition.getX(), this.worldPosition.getY(),
//                    this.worldPosition.getZ(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS,
//                    5.0F, 1.0F);

            return true;
        }
        return false;
    }

    private Monster randomlySelectBoss(UseOnContext context) {
        switch (RandomUtils.nextInt(0, 2)) {
            default -> {    //can interpret default as case 0, otherwise complains about duplicate
                //SEND MESSAGE IN CHAT TO NEARBY PLAYERS
                context.getPlayer().sendSystemMessage(Component.literal("§cKathleen is coming..."));
                return new KathleenTheWickedEntity(ModEntities.KATHLEEN_THE_WICKED.get(), context.getLevel());
            }
            case 1 -> {
                context.getPlayer().sendSystemMessage(Component.literal("§dShe's back..."));
                return new OldLadyMuffEntity(ModEntities.OLD_LADY_MUFF.get(), context.getLevel());
            }
        }
    }
}
