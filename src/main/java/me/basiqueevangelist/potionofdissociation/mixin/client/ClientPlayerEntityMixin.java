package me.basiqueevangelist.potionofdissociation.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.basiqueevangelist.potionofdissociation.client.FakeCameraEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin {
    @Shadow @Final protected MinecraftClient client;

    @SuppressWarnings("ConstantConditions")
    @ModifyExpressionValue(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerInteractionManager;isFlyingLocked()Z"))
    private boolean lockFlyingForCamera(boolean orig) {
        if ((Object) this instanceof FakeCameraEntity)
            return true;

        return orig;
    }

    @ModifyExpressionValue(method = "sendMovementPackets", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isCamera()Z"))
    private boolean sendMovementPackets(boolean orig) {
        if (client.getCameraEntity() instanceof FakeCameraEntity)
            return true;

        return orig;
    }

}
