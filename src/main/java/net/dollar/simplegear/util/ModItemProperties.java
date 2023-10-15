package net.dollar.simplegear.util;

import net.dollar.simplegear.item.ModItems;
import net.dollar.simplegear.item.custom.bow.ModInfusedDiamondBowItem;
import net.dollar.simplegear.item.custom.bow.ModNetheriteBowItem;
import net.dollar.simplegear.item.custom.bow.ModSteelBowItem;
import net.dollar.simplegear.item.custom.bow.ModTungstenCarbideBowItem;
import net.dollar.simplegear.item.custom.crossbow.ModSteelCrossbowItem;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

/**
 * Handles custom ItemProperties, used by Bows and Crossbows for client-side rendering.
 */
public class ModItemProperties {
    /**
     * Generates client-side Item Properties for custom items (new Bows and Crossbows).
     */
    public static void addCustomItemProperties() {
        makeBowItemProperties(ModItems.STEEL_BOW.get(), (float)ModSteelBowItem.MAX_DRAW_DURATION);
        makeBowItemProperties(ModItems.INFUSED_DIAMOND_BOW.get(), (float)ModInfusedDiamondBowItem.MAX_DRAW_DURATION);
        makeBowItemProperties(ModItems.NETHERITE_BOW.get(), (float)ModNetheriteBowItem.MAX_DRAW_DURATION);
        makeBowItemProperties(ModItems.TUNGSTEN_CARBIDE_BOW.get(), (float)ModTungstenCarbideBowItem.MAX_DRAW_DURATION);
        makeCrossbowItemProperties(ModItems.STEEL_CROSSBOW.get());
        makeCrossbowItemProperties(ModItems.INFUSED_DIAMOND_CROSSBOW.get());
        makeCrossbowItemProperties(ModItems.NETHERITE_CROSSBOW.get());
        makeCrossbowItemProperties(ModItems.TUNGSTEN_CARBIDE_CROSSBOW.get());
    }

    /**
     * Creates ItemProperties for pull and pulling for Bow Items. Bows cannot alter draw duration easily
     *  in-class like Crossbows, and thus the pull duration must be manually implemented in this method.
     * @param item The corresponding Bow Item
     * @param maxDrawTicks The number of ticks required for max draw
     */
    private static void makeBowItemProperties(Item item, float maxDrawTicks) {
        ItemProperties.register(item, new ResourceLocation("pull"), (itemStack, level, userEntity, anInteger) -> {
            if (userEntity == null) {
                return 0.0f;
            } else {
                return userEntity.getUseItem() != itemStack ?
                        0.0f : (float)(itemStack.getUseDuration() - userEntity.getUseItemRemainingTicks()) / maxDrawTicks;
            }
        });

        ItemProperties.register(item, new ResourceLocation("pulling"), (itemStack, level, userEntity, anInteger) -> {
            return userEntity != null && userEntity.isUsingItem() && userEntity.getUseItem() == itemStack ?
                    1.0f : 0.0f;
        });
    }

    /**
     * Creates ItemProperties for pull, pulling, charged, and firework for a Crossbow Item. Crossbows implement
     *  charge duration directly in the class, but a custom ModUtils function is required to get each individual
     *  charge duration in ticks.
     * @param item The corresponding Crossbow Item
     */
    private static void makeCrossbowItemProperties(Item item) {
        ItemProperties.register(item, new ResourceLocation("pull"), (itemStack, level, userEntity, anInteger) -> {
            if (userEntity == null) {
                return 0.0F;
            } else {
                return CrossbowItem.isCharged(itemStack) ? 0.0F :
                        (float)(itemStack.getUseDuration() - userEntity.getUseItemRemainingTicks())
                                / ModUtils.getCrossbowChargeDurationTicks(item, itemStack);
            }
        });

        ItemProperties.register(item, new ResourceLocation("pulling"),(itemStack, level, userEntity, anInteger) -> {
            return userEntity != null && userEntity.isUsingItem() && userEntity.getUseItem() == itemStack
                    && !CrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F;
        });

        ItemProperties.register(item, new ResourceLocation("charged"), (itemStack, level, userEntity, anInteger) -> {
            return CrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F;
        });

        ItemProperties.register(item, new ResourceLocation("firework"), (itemStack, level, userEntity, anInteger) -> {
            return CrossbowItem.isCharged(itemStack)
                    && CrossbowItem.containsChargedProjectile(itemStack, Items.FIREWORK_ROCKET) ? 1.0F : 0.0F;
        });
    }
}
