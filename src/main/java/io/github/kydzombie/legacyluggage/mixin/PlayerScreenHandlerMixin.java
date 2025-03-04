package io.github.kydzombie.legacyluggage.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerScreenHandler.class)
public abstract class PlayerScreenHandlerMixin extends ScreenHandler {
//    @Unique
//    NewBagInventory bagInventory = null;

    @Inject(
            method="<init>(Lnet/minecraft/entity/player/PlayerInventory;Z)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/screen/PlayerScreenHandler;onSlotUpdate(Lnet/minecraft/inventory/Inventory;)V"
            )
    )
    private void injectSlots(PlayerInventory inventory, boolean isLocal, CallbackInfo ci) {
        // TODO: Make it use a special slot or armor or something?
//        System.out.println("INJECTAAAA");
//        for (int i = 0; i < inventory.size(); i++) {
//            System.out.println(inventory.getStack(i));
//        }
//        ItemStack handStack = inventory.getStack(inventory.selectedSlot);
////        ItemStack handStack = inventory.player.getHand();
//        if (handStack == null) {
//            System.out.println("It is null");
//            return;
//        }
//        if (handStack.getItem() instanceof BagItem) {
//            System.out.println("FOUND BAG!!!");
//            BagItem.setOpen(handStack, true);
//            bagInventory = new NewBagInventory(handStack);
//            //noinspection unchecked
//            slots.add(new BagSlot(bagInventory, 0, 0, 0));
//        }
    }

    @Inject(method="onClosed", at=@At("TAIL"))
    private void closeBags(PlayerEntity player, CallbackInfo ci) {
//        PlayerInventory inventory = player.inventory;
//        for (int i = 0; i < inventory.size(); i++) {
//            ItemStack stack = inventory.getStack(i);
//            if (stack == null) continue;
//            if (stack.getItem() instanceof BagItem) {
//                BagItem.setOpen(stack, false);
//            }
//        }
    }
}
