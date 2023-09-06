package dev.thestaticvoid.capejs.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.thestaticvoid.capejs.client.CapeJS;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.CapeLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CapeLayer.class)
public abstract class CapeLayerMixin extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
    public CapeLayerMixin(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> renderLayerParent) {
        super(renderLayerParent);
    }

    @Inject(method = "render*", at = @At("HEAD"), cancellable = true)
    private void renderMixin(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, AbstractClientPlayer livingEntity,
                             float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw,
                             float headPitch, CallbackInfo ci) {
        if (CapeJS.CUSTOM_CAPE_MAP.containsKey(livingEntity.getGameProfile().getId())) {
            ci.cancel();
        }
    }
}
