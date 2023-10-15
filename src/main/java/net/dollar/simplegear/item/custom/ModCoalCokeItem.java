package net.dollar.simplegear.item.custom;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.Nullable;

/**
 * Used specifically for the Coal Coke Item, which can be used as fuel and thus overrides getBurnTime().
 */
public class ModCoalCokeItem extends Item {
    public ModCoalCokeItem(Properties properties) {
        super(properties);
    }



    /**
     * Gets burn time of this Item in a furnace (halved for Blast Furnace and Smoker, auto calculated).
     *  This value should correspond to default furnace burn time. (Is 1600, or 80 seconds, same as Coal).
     * @param itemStack ItemStack of this Item
     * @param recipeType Relevant recipe type, Nullable
     * @return Burn time in ticks
     */
    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return 1600;
    }
}
