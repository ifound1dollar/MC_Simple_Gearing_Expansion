package net.dollar.simplegear.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Used specifically for collector items dropped from Spectral Lantern mobs, extends Item class and
 *  implements custom stack size and hover text.
 */
public class ModCollectorItem extends Item {
    private final String numberString;

    /**
     * Constructs a new ModCollectorItem object.
     * @param properties Item properties
     * @param numberString Collector item number that will be appended to the Item hover tooltip
     */
    public ModCollectorItem(Properties properties, String numberString) {
        super(properties);
        this.numberString = numberString;
    }

    /**
     * Gets the maximum stack size of ItemStacks of this Item.
     * @param stack ItemStack corresponding to this Item
     * @return The maximum stack size
     */
    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }

    /**
     * Appends 'Collector item #' to the Item's hover tooltip.
     * @param stack ItemStack corresponding to this Item
     * @param level Active level
     * @param components List of Components that make up the tooltip
     * @param flag TooltipFlag determining whether NORMAL or ADVANCED
     */
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.literal("§6Collector item #" + numberString));
        super.appendHoverText(stack, level, components, flag);
    }
}
