package org.polyfrost.evergreenhud.mixins;

import org.polyfrost.evergreenhud.config.ModConfig;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RendererLivingEntity.class)
public class RendererLivingEntityMixin {
    @Inject(method = "canRenderName(Lnet/minecraft/entity/EntityLivingBase;)Z", at = @At("HEAD"), cancellable = true)
    private void onCanRenderName(CallbackInfoReturnable<Boolean> cir) {
        if (ModConfig.INSTANCE.getPlayerPreview().getSelfPreview().getCancelNametags()) {
            cir.setReturnValue(false);
        }
    }
}
