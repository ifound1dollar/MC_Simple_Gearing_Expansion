package net.dollar.simplegear.entity.client.models;

import net.dollar.simplegear.entity.custom.ObsidianGolemEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * CLIENT-ONLY. Defines Obsidian Golem model, copied largely from IronGolemModel but without flower layer.
 * @param <T> ObsidianGolemEntity that this model applies to
 */
@OnlyIn(Dist.CLIENT)
public class ModObsidianGolemModel<T extends ObsidianGolemEntity> extends HierarchicalModel<T> {
    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart rightArm;
    private final ModelPart leftArm;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;

    public ModObsidianGolemModel(ModelPart p_170697_) {
        this.root = p_170697_;
        this.head = p_170697_.getChild("head");
        this.rightArm = p_170697_.getChild("right_arm");
        this.leftArm = p_170697_.getChild("left_arm");
        this.rightLeg = p_170697_.getChild("right_leg");
        this.leftLeg = p_170697_.getChild("left_leg");
    }



    public ModelPart root() {
        return this.root;
    }

    public void setupAnim(T p_102962_, float p_102963_, float p_102964_, float p_102965_, float p_102966_, float p_102967_) {
        this.head.yRot = p_102966_ * ((float)Math.PI / 180F);
        this.head.xRot = p_102967_ * ((float)Math.PI / 180F);
        this.rightLeg.xRot = -1.5F * Mth.triangleWave(p_102963_, 13.0F) * p_102964_;
        this.leftLeg.xRot = 1.5F * Mth.triangleWave(p_102963_, 13.0F) * p_102964_;
        this.rightLeg.yRot = 0.0F;
        this.leftLeg.yRot = 0.0F;
    }

    public void prepareMobModel(T p_102957_, float p_102958_, float p_102959_, float p_102960_) {
        int i = p_102957_.getAttackAnimationTick();
        if (i > 0) {
            this.rightArm.xRot = -2.0F + 1.5F * Mth.triangleWave((float)i - p_102960_, 10.0F);
            this.leftArm.xRot = -2.0F + 1.5F * Mth.triangleWave((float)i - p_102960_, 10.0F);
        }
    }
}
