package io.github.kydzombie.legacyluggage.mixin;

import io.github.kydzombie.legacyluggage.LegacyLuggage;
import io.github.kydzombie.legacyluggage.item.BagItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ScreenHandler.class)
public abstract class ScreenHandlerMixin {
//    @Shadow public List<Slot> slots;
//
//    @Inject(method="onSlotClick", at = @At("HEAD"), cancellable = true)
//    private void injectRightClick(int index, int button, boolean shift, PlayerEntity player, CallbackInfoReturnable<ItemStack> cir) {
//        if (button != 1) return;
//        if (index < 0 || index >= slots.size()) return;
//        PlayerInventory inventory = player.inventory;
//        if (inventory.getCursorStack() != null) return;
//        Slot slot = slots.get(index);
//        ItemStack stack = slot.getStack();
//        if (stack == null) return;
//        if (stack.getItem() instanceof BagItem) {
//            BagItem.setOpen(stack, !BagItem.isOpen(stack));
//            // Close all other bags
//            for (int i = 0; i < inventory.size(); i++) {
//                if (i == index) continue;
//                ItemStack otherStack = inventory.getStack(i);
//                if (otherStack == null) continue;
//                if (otherStack.getItem() instanceof BagItem) {
//                    BagItem.setOpen(otherStack, false);
//                }
//            }
//            cir.setReturnValue(null);
//        }
//    }
//
//    @Inject(method="onClosed", at=@At("TAIL"))
//    private void closeBags(PlayerEntity player, CallbackInfo ci) {
//        PlayerInventory inventory = player.inventory;
//        for (int i = 0; i < inventory.size(); i++) {
//            ItemStack stack = inventory.getStack(i);
//            if (stack == null) continue;
//            if (stack.getItem() instanceof BagItem) {
//                BagItem.setOpen(stack, false);
//            }
//        }
//    }
}
