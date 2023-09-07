package dev.thestaticvoid.capejs.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.thestaticvoid.capejs.CapeRegistry;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.CapeLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CapeLayer.class)
public abstract class CapeLayerMixin extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
    public CapeLayerMixin(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> renderLayerParent) {
        super(renderLayerParent);
    }

    @Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/player/AbstractClientPlayer;FFFFFF)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/AbstractClientPlayer;getCloakTextureLocation()Lnet/minecraft/resources/ResourceLocation;", ordinal = 0),
            cancellable = true)
    private void renderCancelMixin(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, AbstractClientPlayer livingEntity,
                                   float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw,
                                   float headPitch, CallbackInfo ci) {
        // If the player's cloak texture is missing, but they have a custom cape, do not return out of this render method
        if (livingEntity.getCloakTextureLocation() == null && CapeRegistry.mapContainsPlayer(livingEntity)) {
            ci.cancel();
        }
    }

    @Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/player/AbstractClientPlayer;FFFFFF)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/PlayerModel;renderCloak(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;II)V",
            shift = At.Shift.BEFORE), cancellable = true)
    private void renderMixin(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, AbstractClientPlayer livingEntity,
                             float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw,
                             float headPitch, CallbackInfo ci) {
        // This was a pain to mixin to, basically just rewriting the last few lines of the original render method and providing
        // The custom cape resource rather than what is normally obtained
        if (CapeRegistry.mapContainsPlayer(livingEntity)) {
            VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entitySolid(CapeRegistry.getResourceByPlayer(livingEntity)));
            ((PlayerModel)this.getParentModel()).renderCloak(matrixStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
            matrixStack.popPose();
            ci.cancel();
        }
    }
}
