package dev.thestaticvoid.capejs.mixin.client;

import com.mojang.authlib.GameProfile;
import dev.latvian.mods.kubejs.core.ClientPlayerKJS;
import dev.thestaticvoid.capejs.CapeRegistry;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.ProfilePublicKey;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(AbstractClientPlayer.class)
public abstract class AbstractClientPlayerFabricMixin
        extends Player
        implements ClientPlayerKJS {

    public AbstractClientPlayerFabricMixin(Level level, BlockPos blockPos, float f, GameProfile gameProfile) {
        super(level, blockPos, f, gameProfile);
    }

    @Inject(method = "getCloakTextureLocation", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void getCloakTextureLocationMixin(CallbackInfoReturnable<ResourceLocation> cir, PlayerInfo playerInfo) {
        if (playerInfo != null && CapeRegistry.mapContainsPlayer((AbstractClientPlayer) (Object)this)) {
            cir.setReturnValue(CapeRegistry.getResourceByPlayer((AbstractClientPlayer) (Object)this));
        }
    }

    @Inject(method = "getElytraTextureLocation", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void getElytraTextureLocationMixin(CallbackInfoReturnable<ResourceLocation> cir, PlayerInfo playerInfo) {
        if (playerInfo != null && CapeRegistry.mapContainsPlayer((AbstractClientPlayer) (Object) this)) {
            cir.setReturnValue(CapeRegistry.getResourceByPlayer((AbstractClientPlayer) (Object)this));
        }
    }
}
