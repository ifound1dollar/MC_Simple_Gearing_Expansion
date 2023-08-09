package net.dollar.testmod.entity.client.renderer;

import net.dollar.testmod.entity.client.models.ModRoosterFromHellModel;
import net.dollar.testmod.entity.custom.RoosterFromHellEntity;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModRoosterFromHellRenderer extends MobRenderer<RoosterFromHellEntity, ModRoosterFromHellModel<RoosterFromHellEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(
            "textures/entity/chicken.png");

    public ModRoosterFromHellRenderer(EntityRendererProvider.Context context) {
        super(context, new ModRoosterFromHellModel<>(context.bakeLayer(ModelLayers.CHICKEN)), 0.3f);  //shadow radius
    }

    @Override
    public ResourceLocation getTextureLocation(RoosterFromHellEntity p_114482_) {
        return TEXTURE;
    }

    protected float getBob(Chicken p_114000_, float p_114001_) {
        float f = Mth.lerp(p_114001_, p_114000_.oFlap, p_114000_.flap);
        float f1 = Mth.lerp(p_114001_, p_114000_.oFlapSpeed, p_114000_.flapSpeed);
        return (Mth.sin(f) + 1.0F) * f1;
    }
}
