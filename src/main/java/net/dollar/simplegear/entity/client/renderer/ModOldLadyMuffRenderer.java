package net.dollar.simplegear.entity.client.renderer;

import net.dollar.simplegear.entity.client.models.ModKathleenTheWickedModel;
import net.dollar.simplegear.entity.client.models.ModOldLadyMuffModel;
import net.dollar.simplegear.entity.custom.KathleenTheWickedEntity;
import net.dollar.simplegear.entity.custom.OldLadyMuffEntity;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModOldLadyMuffRenderer extends MobRenderer<OldLadyMuffEntity, ModOldLadyMuffModel<OldLadyMuffEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(
            "textures/entity/witch.png");

    public ModOldLadyMuffRenderer(EntityRendererProvider.Context context) {
        super(context, new ModOldLadyMuffModel<>(context.bakeLayer(ModelLayers.WITCH)), 0.5f);  //shadow radius
    }

    @Override
    public ResourceLocation getTextureLocation(OldLadyMuffEntity p_114482_) {
        return TEXTURE;
    }
}
