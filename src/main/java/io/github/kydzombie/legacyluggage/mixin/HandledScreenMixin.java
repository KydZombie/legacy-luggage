package io.github.kydzombie.legacyluggage.mixin;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HandledScreen.class)
public class HandledScreenMixin {
//    @Inject(method="mouseClicked", locals = LocalCapture.CAPTURE_FAILHARD, at= @At(value = "INVOKE", target = "Lnet/minecraft/client/InteractionManager;clickSlot(IIIZLnet/minecraft/entity/player/PlayerEntity;)Lnet/minecraft/item/ItemStack;"))
//    private void injectRightClick(int mouseX, int mouseY, int button, CallbackInfo ci) {
//
//    }

//    @Inject(method="render", at=@At("TAIL"))
//    private void renderBagPopUps(int mouseX, int mouseY, float delta, CallbackInfo ci) {
//
//    }
}
