package net.dollar.testmod.entity.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.dollar.testmod.TestMod;
import net.dollar.testmod.entity.client.models.ModKathleenTheWickedModel;
import net.dollar.testmod.entity.client.models.ModObsidianGolemModel;
import net.dollar.testmod.entity.custom.KathleenTheWickedEntity;
import net.dollar.testmod.entity.custom.ObsidianGolemEntity;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModKathleenTheWickedRenderer extends MobRenderer<KathleenTheWickedEntity, ModKathleenTheWickedModel<KathleenTheWickedEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(
            "textures/entity/cow/red_mooshroom.png");

    public ModKathleenTheWickedRenderer(EntityRendererProvider.Context context) {
        super(context, new ModKathleenTheWickedModel<>(context.bakeLayer(ModelLayers.COW)), 0.7f);  //shadow radius
    }

    @Override
    public ResourceLocation getTextureLocation(KathleenTheWickedEntity p_114482_) {
        return TEXTURE;
    }
}
