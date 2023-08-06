package net.dollar.testmod.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ModCollectorItem extends Item {
    private final String numberString;

    public ModCollectorItem(Properties properties, String numberString) {
        super(properties);
        this.numberString = numberString;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.literal("§6Collector item #" + numberString));
        super.appendHoverText(stack, level, components, flag);
    }
}
