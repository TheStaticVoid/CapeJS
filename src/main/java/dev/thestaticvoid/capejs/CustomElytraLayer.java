//package dev.thestaticvoid.capejs;
//
//import com.mojang.blaze3d.vertex.PoseStack;
//import com.mojang.blaze3d.vertex.VertexConsumer;
//import net.fabricmc.api.EnvType;
//import net.fabricmc.api.Environment;
//import net.minecraft.client.model.ElytraModel;
//import net.minecraft.client.model.EntityModel;
//import net.minecraft.client.model.PlayerModel;
//import net.minecraft.client.model.geom.EntityModelSet;
//import net.minecraft.client.model.geom.ModelLayers;
//import net.minecraft.client.player.AbstractClientPlayer;
//import net.minecraft.client.renderer.MultiBufferSource;
//import net.minecraft.client.renderer.RenderType;
//import net.minecraft.client.renderer.entity.ItemRenderer;
//import net.minecraft.client.renderer.entity.RenderLayerParent;
//import net.minecraft.client.renderer.entity.layers.RenderLayer;
//import net.minecraft.client.renderer.texture.OverlayTexture;
//import net.minecraft.world.entity.EquipmentSlot;
//import net.minecraft.world.entity.player.PlayerModelPart;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.Items;
//
//@Environment(value = EnvType.CLIENT)
//public class CustomElytraLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
//    private final ElytraModel<AbstractClientPlayer> elytraModel;
//    public CustomElytraLayer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> renderLayerParent, EntityModelSet entityModelSet) {
//        super(renderLayerParent);
//        this.elytraModel = new ElytraModel(entityModelSet.bakeLayer(ModelLayers.ELYTRA));
//    }
//
//    @Override
//    public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, AbstractClientPlayer livingEntity,
//                       float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
//        if (livingEntity.isModelPartShown(PlayerModelPart.CAPE) && CapeRegistry.mapContainsPlayer(livingEntity)) {
//            ItemStack itemStack = livingEntity.getItemBySlot(EquipmentSlot.CHEST);
//            if (!itemStack.is(Items.ELYTRA)) {
//                return;
//            }
//
//            matrixStack.pushPose();
//            matrixStack.translate(0.0, 0.0, 0.125);
//            ((EntityModel) this.getParentModel()).copyPropertiesTo(this.elytraModel);
//            this.elytraModel.setupAnim(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
//            VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(buffer, RenderType.armorCutoutNoCull(CapeRegistry.getResourceByPlayer(livingEntity)), false, itemStack.hasFoil());
//            this.elytraModel.renderToBuffer(matrixStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
//            matrixStack.popPose();
//        }
//    }
//}
