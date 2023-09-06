package dev.thestaticvoid.capejs.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

@Environment(value = EnvType.CLIENT)
public class CustomCapeLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    public CustomCapeLayer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> renderLayerParent) {
        super(renderLayerParent);
    }

    @Override
    public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, AbstractClientPlayer livingEntity,
                       float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw,
                       float headPitch) {
        if (livingEntity.isInvisible() || !livingEntity.isModelPartShown(PlayerModelPart.CAPE)
                || !CapeJS.CUSTOM_CAPE_MAP.containsKey(livingEntity.getGameProfile().getId())) {
            return;
        }
        ItemStack itemStack = livingEntity.getItemBySlot(EquipmentSlot.CHEST);
        if (itemStack.is(Items.ELYTRA)) {
            return;
        }
        matrixStack.pushPose();
        matrixStack.translate(0.0, 0.0, 0.125);
        double d = Mth.lerp((double)partialTicks, livingEntity.xCloakO, livingEntity.xCloak) - Mth.lerp((double)partialTicks, livingEntity.xo, livingEntity.getX());
        double e = Mth.lerp((double)partialTicks, livingEntity.yCloakO, livingEntity.yCloak) - Mth.lerp((double)partialTicks, livingEntity.yo, livingEntity.getY());
        double f = Mth.lerp((double)partialTicks, livingEntity.zCloakO, livingEntity.zCloak) - Mth.lerp((double)partialTicks, livingEntity.zo, livingEntity.getZ());
        float g = livingEntity.yBodyRotO + (livingEntity.yBodyRot - livingEntity.yBodyRotO);
        double h = Mth.sin(g * ((float)Math.PI / 180));
        double i = -Mth.cos(g * ((float)Math.PI / 180));
        float j = (float)e * 10.0f;
        j = Mth.clamp(j, -6.0f, 32.0f);
        float k = (float)(d * h + f * i) * 100.0f;
        k = Mth.clamp(k, 0.0f, 150.0f);
        float l = (float)(d * i - f * h) * 100.0f;
        l = Mth.clamp(l, -20.0f, 20.0f);
        if (k < 0.0f) {
            k = 0.0f;
        }
        float m = Mth.lerp(partialTicks, livingEntity.oBob, livingEntity.bob);
        j += Mth.sin(Mth.lerp(partialTicks, livingEntity.walkDistO, livingEntity.walkDist) * 6.0f) * 32.0f * m;
        if (livingEntity.isCrouching()) {
            j += 25.0f;
        }
        matrixStack.mulPose(Vector3f.XP.rotationDegrees(6.0f + k / 2.0f + j));
        matrixStack.mulPose(Vector3f.ZP.rotationDegrees(l / 2.0f));
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(180.0f - l / 2.0f));
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entitySolid(CapeJS.CUSTOM_CAPE_MAP.get(livingEntity.getGameProfile().getId())));
        ((PlayerModel)this.getParentModel()).renderCloak(matrixStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
        matrixStack.popPose();
    }
}
