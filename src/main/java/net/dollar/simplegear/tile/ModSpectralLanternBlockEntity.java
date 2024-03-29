package net.dollar.simplegear.tile;

import net.dollar.simplegear.entity.ModEntities;
import net.dollar.simplegear.entity.custom.KathleenTheWickedEntity;
import net.dollar.simplegear.entity.custom.OldLadyMuffEntity;
import net.dollar.simplegear.entity.custom.TheHeliroosterEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.lang3.RandomUtils;

import java.time.Duration;
import java.time.Instant;

/**
 * Custom block entity for the Spectral Lantern, which allows interaction and conditional boss mob spawning.
 */
public class ModSpectralLanternBlockEntity extends BlockEntity {
    private Instant lastUsedInstant = Instant.MIN;  //far past, allow to work instantly

    public ModSpectralLanternBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(ModTileEntities.SPECTRAL_LANTERN_TILE.get(), p_155229_, p_155230_);
    }



    /**
     * Called when a player uses a Compound Gemstone on a Spectral Lantern. This method will verify that it is
     *  called server-side only, the Spectral Lantern was not used less than 15 seconds ago, and that there is
     *  valid space underneath the tile. If these conditions are met, select one boss from the pool of bosses,
     *  set its data, and consume the user's Compound Gemstone.
     * @param context The UseOnContext generated by the player using the Compound Gemstone on this tile entity
     * @return Whether the spawn was successful
     */
    public boolean attemptSpawnBoss(UseOnContext context) {
        //only perform this logic server-side
        if (!context.getLevel().isClientSide && context.getPlayer() != null) {  //check for null to silence warnings
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
            entity.setInvisible(true);
            entity.setSilent(true);

            entity.setPos(context.getClickLocation().add(0.0d, -2.0d, 0.0d));    //move down 2
            context.getLevel().addFreshEntity(entity);

            //if player not in creative, set target
            if (!context.getPlayer().isCreative()) {
                entity.setTarget(context.getPlayer());
            }

            //store timestamp for cooldown use
            lastUsedInstant = Instant.now();

            //consume one Compound Gemstone (guaranteed to still be Compound Gemstone in interactionHand)
            context.getItemInHand().shrink(1);

            return true;
        }
        return false;
    }

    /**
     * Select a random boss from the pool of bosses, spawn it, send a system message, and return the new Monster Entity.
     * @param context The UseOnContext generated from the player using the Compound Gemstone
     * @return The spawned boss mob (Monster)
     */
    private Monster randomlySelectBoss(UseOnContext context) {
        switch (RandomUtils.nextInt(0, 3)) {
            default -> {    //can interpret default as case 0, otherwise complains about duplicate
                //SEND MESSAGE IN CHAT TO NEARBY PLAYERS
                context.getPlayer().sendSystemMessage(Component.literal("§cKathleen is coming..."));
                return new KathleenTheWickedEntity(ModEntities.KATHLEEN_THE_WICKED.get(), context.getLevel());
            }
            case 1 -> {
                context.getPlayer().sendSystemMessage(Component.literal("§dShe's back..."));
                return new OldLadyMuffEntity(ModEntities.OLD_LADY_MUFF.get(), context.getLevel());
            }
            case 2 -> {
                context.getPlayer().sendSystemMessage(Component.literal("§dSpicy nuggets, anyone?"));
                return new TheHeliroosterEntity(ModEntities.THE_HELIROOSTER.get(), context.getLevel());
            }
        }
    }
}
