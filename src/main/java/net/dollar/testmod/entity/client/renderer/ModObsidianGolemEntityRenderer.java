package net.dollar.testmod.entity.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.dollar.testmod.TestMod;
import net.dollar.testmod.entity.custom.ObsidianGolemEntity;
import net.minecraft.client.model.IronGolemModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ModObsidianGolemEntityRenderer extends MobRenderer<ObsidianGolemEntity, IronGolemModel<ObsidianGolemEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(
            TestMod.MOD_ID, "textures/entities/obsidian_golem.png");

    public ModObsidianGolemEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new IronGolemModel<>(context.bakeLayer(ModelLayers.IRON_GOLEM)), 0.7f);
    }

    @Override
    public ResourceLocation getTextureLocation(ObsidianGolemEntity p_114482_) {
        return TEXTURE;
    }

    @Override
    protected void scale(ObsidianGolemEntity entity, PoseStack poseStack, float p_115316_) {
        //scaling this will increase the size of the actual model & texture
        poseStack.scale(1.25f, 1.25f, 1.25f);
    }
}
