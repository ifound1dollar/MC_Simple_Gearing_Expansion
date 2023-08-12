package net.dollar.simplegear.entity.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.dollar.simplegear.SimpleGearingExpansion;
import net.dollar.simplegear.entity.client.models.ModObsidianGolemModel;
import net.dollar.simplegear.entity.custom.ObsidianGolemEntity;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModObsidianGolemEntityRenderer extends MobRenderer<ObsidianGolemEntity, ModObsidianGolemModel<ObsidianGolemEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(
            SimpleGearingExpansion.MOD_ID, "textures/entities/obsidian_golem.png");

    public ModObsidianGolemEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new ModObsidianGolemModel<>(context.bakeLayer(ModelLayers.IRON_GOLEM)), 0.7f);  //shadow radius
        this.addLayer(new ModObsidianGolemCrackinessLayer(this));   //add custom crackiness layer
    }

    @Override
    public ResourceLocation getTextureLocation(ObsidianGolemEntity p_114482_) {
        return TEXTURE;
    }

    @Override
    protected void scale(ObsidianGolemEntity entity, PoseStack poseStack, float p_115316_) {
        //scaling this will change the size of the actual model & texture
        poseStack.scale(1.25f, 1.25f, 1.25f);
    }
}
