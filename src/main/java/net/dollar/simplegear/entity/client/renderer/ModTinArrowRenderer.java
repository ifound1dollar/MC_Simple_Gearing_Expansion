package net.dollar.simplegear.entity.client.renderer;;

import net.dollar.simplegear.SimpleGearingExpansion;
import net.dollar.simplegear.item.custom.arrow.ModTinArrowProjectile;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModTinArrowRenderer extends ArrowRenderer<ModTinArrowProjectile> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(
            SimpleGearingExpansion.MOD_ID, "textures/entities/projectiles/tin_arrow.png");

    public ModTinArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    public ResourceLocation getTextureLocation(ModTinArrowProjectile projectile) {
        return TEXTURE;
    }
}