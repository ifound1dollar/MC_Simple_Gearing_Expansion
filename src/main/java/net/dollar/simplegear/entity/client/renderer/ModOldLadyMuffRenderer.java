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

/**
 * CLIENT-ONLY. Handles Old Lady Muff rendering, supporting custom texture.
 */
@OnlyIn(Dist.CLIENT)
public class ModOldLadyMuffRenderer extends MobRenderer<OldLadyMuffEntity, ModOldLadyMuffModel<OldLadyMuffEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(
            "textures/entity/witch.png");

    public ModOldLadyMuffRenderer(EntityRendererProvider.Context context) {
        super(context, new ModOldLadyMuffModel<>(context.bakeLayer(ModelLayers.WITCH)), 0.5f);  //shadow radius
    }



    /**
     * Gets texture ResourceLocation defined in top of class.
     * @param entity OldLadyMuffEntity being rendered
     * @return Texture ResourceLocation
     */
    @Override
    public ResourceLocation getTextureLocation(OldLadyMuffEntity entity) {
        return TEXTURE;
    }
}
