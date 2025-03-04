package io.github.kydzombie.legacyluggage.gui.screen;

import io.github.kydzombie.legacyluggage.block.entity.BagTableBlockEntity;
import io.github.kydzombie.legacyluggage.gui.screen.slot.BagTableBagSlot;
import io.github.kydzombie.legacyluggage.gui.screen.slot.BagTablePouchSlot;
import io.github.kydzombie.legacyluggage.gui.screen.slot.LockedSlot;
import io.github.kydzombie.legacyluggage.item.IBagItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class BagTableScreenHandler extends ScreenHandler {
    public BagTableScreenHandler(PlayerInventory playerInventory, BagTableBlockEntity blockEntity) {
        addSlot(new BagTableBagSlot(blockEntity, 0, 0, 0));
        addSlot(new BagTablePouchSlot(blockEntity, 1, 36, 0));
        addSlot(new BagTablePouchSlot(blockEntity, 2, 54, 0));

        // Player inventory
        for(int row = 0; row < 3; ++row) {
            for(int col = 0; col < 9; ++col) {
                int slot = col + row * 9 + 9;
                ItemStack stack = playerInventory.getStack(slot);
                if (stack != null && stack.getItem() instanceof IBagItem bagItem && bagItem.isOpen(stack)) {
                    addSlot(new LockedSlot(playerInventory, slot, 8 + col * 18, 84 + row * 18));
                } else {
                    addSlot(new Slot(playerInventory, slot, 8 + col * 18, 84 + row * 18));
                }
            }
        }

        // Hotbar items
        for(int slot = 0; slot < 9; ++slot) {
            ItemStack stack = playerInventory.getStack(slot);
            if (stack != null && stack.getItem() instanceof IBagItem bagItem && bagItem.isOpen(stack)) {
                addSlot(new LockedSlot(playerInventory, slot, 8 + slot * 18, 142));
            } else {
                addSlot(new Slot(playerInventory, slot, 8 + slot * 18, 142));
            }
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        // TODO: Yeah
        return true;
    }
}
