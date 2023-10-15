package net.dollar.simplegear.item.custom.bow;

import net.dollar.simplegear.util.ModUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

/**
 * Class corresponding specifically to the Steel Bow Item, derived directly from BowItem.
 */
public class ModSteelBowItem extends BowItem {
    public static final int MAX_DRAW_DURATION = 20;
    public static final int DEFAULT_RANGE = 15;


    public ModSteelBowItem(Item.Properties properties) {
        super(properties);
    }



    /**
     * Handles operation of Bow's release (firing Arrow). Overridden specifically to allow custom draw duration
     *  using the locally-defined getPowerForTime() method.
     * @param itemStack The ItemStack corresponding to this item
     * @param level The current level
     * @param userEntity The user LivingEntity
     * @param useRemainingTicks The remaining number of ticks for use (weird implementation of this)
     */
    public void releaseUsing(ItemStack itemStack, Level level, LivingEntity userEntity, int useRemainingTicks) {
        if (userEntity instanceof Player player) {
            boolean flag = player.getAbilities().instabuild || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, itemStack) > 0;
            ItemStack itemstack = player.getProjectile(itemStack);

            int i = this.getUseDuration(itemStack) - useRemainingTicks;
            i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(itemStack, level, player, i, !itemstack.isEmpty() || flag);
            if (i < 0) return;

            if (!itemstack.isEmpty() || flag) {
                if (itemstack.isEmpty()) {
                    itemstack = new ItemStack(Items.ARROW);
                }

                float f = getPowerForTime(i);
                if (!((double)f < 0.1D)) {
                    boolean flag1 = player.getAbilities().instabuild || (itemstack.getItem() instanceof ArrowItem && ((ArrowItem)itemstack.getItem()).isInfinite(itemstack, itemStack, player));
                    if (!level.isClientSide) {
                        ArrowItem arrowitem = (ArrowItem)(itemstack.getItem() instanceof ArrowItem ? itemstack.getItem() : Items.ARROW);
                        AbstractArrow abstractarrow = arrowitem.createArrow(level, itemstack, player);
                        abstractarrow = customArrow(abstractarrow);
                        abstractarrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, f * 3.0F, 1.0F);
                        if (f == 1.0F) {
                            abstractarrow.setCritArrow(true);
                        }

                        int j = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, itemStack);
                        if (j > 0) {
                            abstractarrow.setBaseDamage(abstractarrow.getBaseDamage() + (double)j * 0.5D + 0.5D);
                        }

                        int k = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, itemStack);
                        if (k > 0) {
                            abstractarrow.setKnockback(k);
                        }

                        if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, itemStack) > 0) {
                            abstractarrow.setSecondsOnFire(100);
                        }

                        itemStack.hurtAndBreak(1, player, (p_289501_) -> {
                            p_289501_.broadcastBreakEvent(player.getUsedItemHand());
                        });
                        if (flag1 || player.getAbilities().instabuild && (itemstack.is(Items.SPECTRAL_ARROW) || itemstack.is(Items.TIPPED_ARROW))) {
                            abstractarrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                        }

                        level.addFreshEntity(abstractarrow);
                    }

                    level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                    if (!flag1 && !player.getAbilities().instabuild) {
                        itemstack.shrink(1);
                        if (itemstack.isEmpty()) {
                            player.getInventory().removeItem(itemstack);
                        }
                    }

                    player.awardStat(Stats.ITEM_USED.get(this));
                }
            }
        }
    }

    /**
     * Gets power as float out of 1.0 based on the amount of time charged. Manually implemented in this class
     *  specifically to use custom draw duration (static method cannot technically be overridden).
     * @param drawTicks Number of ticks currently drawn
     * @return Power as float out of 1.0
     */
    public static float getPowerForTime(int drawTicks) {
        float f = (float)drawTicks / MAX_DRAW_DURATION;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
    }

    /**
     * Returns a ItemStack Predicate that determines what kind of projectiles can be fired (heavy and light).
     * @return The ItemStack Predicate
     */
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return ModUtils.HEAVY_AND_LIGHT_ARROWS;
    }

    /**
     * Allows modifying or replacing the AbstractArrow generated on weapon release.
     * @param arrow The AbstractArrow generated on this weapon's release
     * @return The modified or replacement AbstractArrow
     */
    public AbstractArrow customArrow(AbstractArrow arrow) {
        return arrow;
    }

    /**
     * Returns the default projectile range of this Bow, which affects the targeting range of mobs
     *  wielding this Bow.
     * @return The default projectile range
     */
    public int getDefaultProjectileRange() {
        return DEFAULT_RANGE;
    }

    /**
     * Appends text to the Item's hover tooltip (heavy bow).
     * @param stack ItemStack corresponding to this Item
     * @param level Active level
     * @param components List of Components that make up the tooltip
     * @param flag TooltipFlag determining whether NORMAL or ADVANCED
     */
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.literal("§7Heavy Bow "));
        super.appendHoverText(stack, level, components, flag);
    }
}
