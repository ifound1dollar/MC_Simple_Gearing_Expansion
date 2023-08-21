package net.dollar.simplegear.item.custom.carbide;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Used specifically for the Netherite Ingot, which is fire-resistant and has custom hover text.
 */
public class ModMoltenCoreItem extends Item {
    public ModMoltenCoreItem(Properties p_41383_) {
        super(p_41383_);
    }



    /**
     * Gets burn time of this Item in a furnace (halved for Blast Furnace and Smoker, auto calculated).
     *  This value should correspond to default furnace burn time. (Is 30000, or 25 minutes).
     * @param itemStack ItemStack of this Item
     * @param recipeType Relevant recipe type, Nullable
     * @return Burn time in ticks
     */
    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return 30000;
    }

    /**
     * Gets whether Entities of this Item are resistant to fire and lava (true).
     * @return Whether this Item is fire-resistant.
     */
    @Override
    public boolean isFireResistant() {
        return true;
    }
}
