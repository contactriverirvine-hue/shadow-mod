package com.arsenal.shadows.client.renderer;

import com.arsenal.shadows.ArsenalOfShadows;
import com.arsenal.shadows.entities.JavelinEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class JavelinRenderer extends EntityRenderer<JavelinEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("minecraft", "textures/entity/trident.png");

    public JavelinRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(JavelinEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entity.yRotO, entity.getYRot()) - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTicks, entity.xRotO, entity.getXRot())));

        // Scale slightly smaller than trident
        poseStack.scale(0.8F, 0.8F, 0.8F);

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(JavelinEntity entity) {
        return TEXTURE;
    }
}