package net.dollar.testmod.util;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.Item;

public class ModUtils {
    public enum DamageCategory { SHARP, BLUNT, MAGIC, FIRE, EXPLOSION }

    public static DamageCategory getDamageCategory(Item item, DamageSource source) {
        //category checks first (because damage may not have been directly dealt by attack this time,
        //  like for burning or poison damage)

        //THEN, sharp held item checks if the category is generic (if still generic after, assume blunt)

        return DamageCategory.SHARP;
    }
}
