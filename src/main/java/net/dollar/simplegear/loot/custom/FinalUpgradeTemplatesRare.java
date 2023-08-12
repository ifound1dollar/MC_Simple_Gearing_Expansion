package net.dollar.simplegear.loot.custom;

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

public class FinalUpgradeTemplatesRare extends LootModifier {
    //this codec includes both fields 'item' and 'item2' in the JSON file, allowing any of the two to be added to loot
    public static final Supplier<Codec<FinalUpgradeTemplatesRare>> CODEC = Suppliers.memoize(() ->
            RecordCodecBuilder.create(inst -> codecStart(inst)
                    .and(ForgeRegistries.ITEMS.getCodec()
                            .fieldOf("item").forGetter(m -> m.item))
                    .and(ForgeRegistries.ITEMS.getCodec()
                            .fieldOf("item2").forGetter(m -> m.item))
                    .apply(inst, FinalUpgradeTemplatesRare::new)));
    final Item item;
    final Item item2;

    /**
     * Constructs a LootModifier.
     *
     * @param conditionsIn the ILootConditions that need to be matched before the loot is modified.
     */
    protected FinalUpgradeTemplatesRare(LootItemCondition[] conditionsIn, Item item, Item item2) {
        super(conditionsIn);
        this.item = item;
        this.item2 = item2;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        //rare is 33% chance to add ONE
        if (context.getRandom().nextFloat() < (1.0f / 3.0f)) {
            //will choose either Infused Diamond or Tungsten-Carbide here
            if (context.getRandom().nextBoolean()) {
                generatedLoot.add(new ItemStack(item, 1));
            } else {
                generatedLoot.add(new ItemStack(item2, 1));
            }
        }
        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
