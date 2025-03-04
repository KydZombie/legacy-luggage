package io.github.kydzombie.legacyluggage.gui.screen;

import io.github.kydzombie.legacyluggage.block.entity.BagTableBlockEntity;
import io.github.kydzombie.legacyluggage.gui.screen.slot.BagTableBagSlot;
import io.github.kydzombie.legacyluggage.gui.screen.slot.BagTablePouchSlot;
import io.github.kydzombie.legacyluggage.gui.screen.slot.LockedSlot;
import io.github.kydzombie.legacyluggage.item.BackpackItem;
import io.github.kydzombie.legacyluggage.item.IBagItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class BagTableScreenHandler extends ScreenHandler {
    PlayerInventory playerInventory;
    BagTableBlockEntity blockEntity;
    public BagTableScreenHandler(PlayerInventory playerInventory, BagTableBlockEntity blockEntity) {
        this.playerInventory = playerInventory;
        this.blockEntity = blockEntity;

        updateSlots();
    }

    // TODO: Idk why it doesn't work on server.
    //  Just crashes the client with an OOB error.
    public void updateSlots() {
        slots.clear();
        this.trackedStacks.clear();

        addSlot(new BagTableBagSlot(blockEntity, 0, 0, 0));
        ItemStack bagStack = blockEntity.getStack(0);
        if (bagStack != null && bagStack.getItem() instanceof BackpackItem backpackItem) {
            for (int i = 0; i < backpackItem.getPouches(bagStack).length; i++) {
                addSlot(new BagTablePouchSlot(blockEntity, i + 1, 36 + 18 * i, 0));
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
    public void onSlotUpdate(Inventory inventory) {
        updateSlots();
        super.onSlotUpdate(inventory);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void updateSlotStacks(ItemStack[] stacks) {
        updateSlots();
        super.updateSlotStacks(stacks);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        // TODO: Yeah
        return true;
    }
}
