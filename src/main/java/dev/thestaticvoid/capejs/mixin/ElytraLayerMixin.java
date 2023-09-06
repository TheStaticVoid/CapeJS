package dev.thestaticvoid.capejs.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.thestaticvoid.capejs.CapeRegistry;
import net.minecraft.client.model.ElytraModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ElytraLayer.class)
public abstract class ElytraLayerMixin<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
    public ElytraLayerMixin(RenderLayerParent<T, M> renderLayerParent) {
        super(renderLayerParent);
    }

    @Shadow(remap = false) @Final
    private static final ResourceLocation WINGS_LOCATION = new ResourceLocation("textures/entity/elytra.png");

    @Shadow(remap = false) @Final
    private ElytraModel<T> elytraModel;

    @Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/ItemRenderer;getArmorFoilBuffer(Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/client/renderer/RenderType;ZZ)Lcom/mojang/blaze3d/vertex/VertexConsumer;",
            shift = At.Shift.BEFORE), cancellable = true, remap = false, locals = LocalCapture.CAPTURE_FAILHARD)
    private void renderMixin(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, T livingEntity, float limbSwing,
                             float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci,
                             ItemStack itemStack, ResourceLocation resourceLocation) {
        // If the player has a real cape and the resourceLocation is pointing to it, replace it with the custom one
        // Similar to the CapeLayerMixin, this is just replacing the end piece of that method
        if (resourceLocation != WINGS_LOCATION && CapeRegistry.mapContainsPlayer((AbstractClientPlayer) livingEntity)) {
            VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(buffer, RenderType.armorCutoutNoCull(CapeRegistry.getResourceByPlayer((AbstractClientPlayer) livingEntity)), false, itemStack.hasFoil());
            this.elytraModel.renderToBuffer(matrixStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
            matrixStack.popPose();
            ci.cancel();
        }
    }
}
