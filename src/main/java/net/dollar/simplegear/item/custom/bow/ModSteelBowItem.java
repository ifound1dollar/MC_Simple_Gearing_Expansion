package net.dollar.simplegear.item.custom.bow;

import net.dollar.simplegear.util.ModUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

/**
 * Class corresponding specifically to the Steel Bow Item, derived directly from BowItem.
 */
public class ModSteelBowItem extends BowItem {
    public ModSteelBowItem(Item.Properties properties) {
        super(properties);
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
     * Appends text to the Item's hover tooltip (heavy bow).
     * @param stack ItemStack corresponding to this Item
     * @param level Active level
     * @param components List of Components that make up the tooltip
     * @param flag TooltipFlag determining whether NORMAL or ADVANCED
     */
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.literal("§8Heavy Bow "));
        super.appendHoverText(stack, level, components, flag);
    }
}
