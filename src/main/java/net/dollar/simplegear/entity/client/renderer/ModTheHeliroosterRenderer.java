package net.dollar.simplegear.entity.client.renderer;

import net.dollar.simplegear.entity.client.models.ModTheHeliroosterModel;
import net.dollar.simplegear.entity.custom.TheHeliroosterEntity;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModTheHeliroosterRenderer extends MobRenderer<TheHeliroosterEntity, ModTheHeliroosterModel<TheHeliroosterEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(
            "textures/entity/chicken.png");

    public ModTheHeliroosterRenderer(EntityRendererProvider.Context context) {
        super(context, new ModTheHeliroosterModel<>(context.bakeLayer(ModelLayers.CHICKEN)), 0.3f);  //shadow radius
    }

    @Override
    public ResourceLocation getTextureLocation(TheHeliroosterEntity p_114482_) {
        return TEXTURE;
    }

    protected float getBob(Chicken p_114000_, float p_114001_) {
        float f = Mth.lerp(p_114001_, p_114000_.oFlap, p_114000_.flap);
        float f1 = Mth.lerp(p_114001_, p_114000_.oFlapSpeed, p_114000_.flapSpeed);
        return (Mth.sin(f) + 1.0F) * f1;
    }
}
