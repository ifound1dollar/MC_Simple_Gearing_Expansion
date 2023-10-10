package net.dollar.simplegear.entity.client.renderer;

import net.dollar.simplegear.SimpleGearingExpansion;
import net.dollar.simplegear.item.custom.arrow.ModTungstenArrowProjectile;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

;

@OnlyIn(Dist.CLIENT)
public class ModTungstenArrowRenderer extends ArrowRenderer<ModTungstenArrowProjectile> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(
            SimpleGearingExpansion.MOD_ID, "textures/entities/projectiles/tungsten_arrow.png");

    public ModTungstenArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    public ResourceLocation getTextureLocation(ModTungstenArrowProjectile projectile) {
        return TEXTURE;
    }
}