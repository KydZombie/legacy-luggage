package io.github.kydzombie.legacyluggage.gui.screen;

import io.github.kydzombie.legacyluggage.gui.screen.slot.LockedSlot;
import io.github.kydzombie.legacyluggage.gui.screen.slot.PouchSlot;
import io.github.kydzombie.legacyluggage.inventory.BagInventory;
import io.github.kydzombie.legacyluggage.item.IBagItem;
import io.github.kydzombie.legacyluggage.item.ClassicBackpackItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class ClassicBackpackScreenHandler extends ScreenHandler {
    public ClassicBackpackItem backpackItem;

    BagInventory backpackInventory;

    public ClassicBackpackScreenHandler(PlayerInventory playerInventory, BagInventory backpackInventory) {
        ItemStack backpackStack = playerInventory.armor[2];
        backpackItem = (ClassicBackpackItem) backpackStack.getItem();
        this.backpackInventory = backpackInventory;

        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 3; col++) {
                int slot = col + row * 3;
                // TODO: null is not intended
                addSlot(new PouchSlot(backpackInventory, slot, 62 + (col * 18), 26 + (row * 18), null));
            }
        }

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
        ItemStack stack = player.inventory.armor[2];
        return stack != null && stack.getItem() instanceof ClassicBackpackItem;
    }

    @Override
    public void onClosed(PlayerEntity player) {
        backpackInventory.writeNbt(player.inventory.armor[2]);
        super.onClosed(player);
    }
}
