package net.dollar.simplegear.item.custom.carbide;

import net.minecraft.network.chat.Component;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Used specifically for the Tungsten-Carbide Ingot, which is fire and explosion resistant and has
 *  custom hover text.
 */
public class ModTungstenCarbideIngotItem extends Item {
    public ModTungstenCarbideIngotItem(Properties p_41383_) {
        super(p_41383_);
    }



    /**
     * Gets whether Entities of this Item are resistant to fire and lava (true).
     * @return Whether this Item is fire-resistant.
     */
    @Override
    public boolean isFireResistant() {
        return true;
    }

    /**
     * Gets whether Entities of this Item can be hurt by a specific DamageSource (false for Fire and Explosion).
     * @param source DamageSource being checked
     * @return Whether this Item can be hurt by the DamageSource
     */
    @Override
    public boolean canBeHurtBy(DamageSource source) {
        //entity cannot be destroyed by explosions or fire
        return !(source.is(DamageTypeTags.IS_FIRE) || source.is(DamageTypeTags.IS_EXPLOSION));
    }

    /**
     * Appends text to the Item's hover tooltip (lore).
     * @param stack ItemStack corresponding to this Item
     * @param level Active level
     * @param components List of Components that make up the tooltip
     * @param flag TooltipFlag determining whether NORMAL or ADVANCED
     */
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.literal("§8The densest material known to exist"));
    }
}
