package net.dollar.testmod.entity.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.dollar.testmod.TestMod;
import net.dollar.testmod.entity.custom.ObsidianGolemEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.IronGolemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.IronGolem;

public class ModObsidianGolemEntityRenderer extends IronGolemRenderer {
    private static final ResourceLocation TEXTURE = new ResourceLocation(
            TestMod.MOD_ID, "textures/entities/obsidian_golem.png");

    public ModObsidianGolemEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(IronGolem ironGolem) {
        return TEXTURE;
    }
}
