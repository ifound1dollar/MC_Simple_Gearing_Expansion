package net.dollar.simplegear.loot.chests;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 * Handles Tungsten Ingot global loot modification, which injects loot into existing generated loot with
 *  Common frequency.
 */
public class TungstenIngotCommon extends LootModifier {
    public static final Supplier<Codec<TungstenIngotCommon>> CODEC = Suppliers.memoize(() ->
            RecordCodecBuilder.create(inst -> codecStart(inst).and(ForgeRegistries.ITEMS.getCodec()
                    .fieldOf("item").forGetter(m -> m.item)).apply(inst, TungstenIngotCommon::new)));
    final Item item;



    /**
     * Constructs a TungstenIngotCommon object.
     * @param conditionsIn The ILootConditions that need to be matched before the loot is modified
     * @param item The item that may be injected into the generated loot
     */
    protected TungstenIngotCommon(LootItemCondition[] conditionsIn, Item item) {
        super(conditionsIn);
        this.item = item;
    }

    /**
     * Handles application of this LootModifier, which rolls chance to add additional item(s) to existing generated loot.
     * @param generatedLoot The List of ItemStacks that will be dropped, generated by loot tables
     * @param context The LootContext, identical to what is passed to loot tables
     * @return The updated List of ItemStacks
     */
    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        //common is 50% chance to add ONE TO THREE
        if (context.getRandom().nextFloat() < 0.5f) {
            int randomInt = context.getRandom().nextInt(0, 3);
            for (int i = 0; i <= randomInt; i++) {
                generatedLoot.add(new ItemStack(item, 1));
            }
        }
        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
