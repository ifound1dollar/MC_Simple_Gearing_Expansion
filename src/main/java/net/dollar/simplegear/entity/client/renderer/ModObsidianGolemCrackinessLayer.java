package net.dollar.simplegear.entity.client.renderer;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;

import net.dollar.simplegear.SimpleGearingExpansion;
import net.dollar.simplegear.entity.client.models.ModObsidianGolemModel;
import net.dollar.simplegear.entity.custom.ObsidianGolemEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * CLIENT-ONLY. Handles Crackiness layer applied to ModObsidianGolemEntityRenderer.
 */
@OnlyIn(Dist.CLIENT)
public class ModObsidianGolemCrackinessLayer extends RenderLayer<ObsidianGolemEntity, ModObsidianGolemModel<ObsidianGolemEntity>> {
    private static final Map<IronGolem.Crackiness, ResourceLocation> resourceLocations = ImmutableMap.of(
            IronGolem.Crackiness.LOW, new ResourceLocation(SimpleGearingExpansion.MOD_ID, "textures/entities/obsidian_golem_crackiness_low.png"),
            IronGolem.Crackiness.MEDIUM, new ResourceLocation(SimpleGearingExpansion.MOD_ID, "textures/entities/obsidian_golem_crackiness_medium.png"),
            IronGolem.Crackiness.HIGH, new ResourceLocation(SimpleGearingExpansion.MOD_ID, "textures/entities/obsidian_golem_crackiness_high.png"));

    public ModObsidianGolemCrackinessLayer(RenderLayerParent<ObsidianGolemEntity, ModObsidianGolemModel<ObsidianGolemEntity>> p_117135_) {
        super(p_117135_);
    }



    public void render(PoseStack poseStack, MultiBufferSource source, int p_117150_,
                       ObsidianGolemEntity entity, float p_117152_, float p_117153_, float p_117154_,
                       float p_117155_, float p_117156_, float p_117157_) {
        if (!entity.isInvisible()) {
            IronGolem.Crackiness irongolem$crackiness = entity.getCrackiness();
            if (irongolem$crackiness != IronGolem.Crackiness.NONE) {
                ResourceLocation resourcelocation = resourceLocations.get(irongolem$crackiness);
                renderColoredCutoutModel(this.getParentModel(), resourcelocation, poseStack, source, p_117150_, entity, 1.0F, 1.0F, 1.0F);
            }
        }
    }
}
