package io.github.kydzombie.legacyluggage.gui.screen;

import io.github.kydzombie.legacyluggage.LegacyLuggage;
import io.github.kydzombie.legacyluggage.gui.screen.slot.LockedSlot;
import io.github.kydzombie.legacyluggage.inventory.BagInventory;
import io.github.kydzombie.legacyluggage.item.BagItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class BagScreenHandler extends ScreenHandler {
    private final PlayerInventory playerInventory;
    private final BagInventory bagInventory;

    public BagScreenHandler(PlayerInventory playerInventory, BagInventory bagInventory) {
        this.playerInventory = playerInventory;
        this.bagInventory = bagInventory;

        int size = bagInventory.size();
        int rowSize = bagInventory.rowSize();
        int rows = size / rowSize;

        for (int i = 0; i < size; i++) {
            // TODO: Centered
            int col = i % rowSize;
            int row = i / rowSize;
            this.addSlot(new Slot(bagInventory, i, 8 + col * 18, 18 + row * 18));
        }

        // TODO: Actually have the bag

        int playerInventoryOffset = (rows - 4) * 18;

        // Player inventory
        for(int row = 0; row < 3; ++row) {
            for(int col = 0; col < 9; ++col) {
                int slot = col + row * 9 + 9;
//                if (BagItem.isOpen(playerInventory.getStack(slot))) {
//                    addSlot(new LockedSlot(playerInventory, slot, 8 + col * 18, 103 + row * 18 + playerInventoryOffset));
//                } else {
//                    addSlot(new Slot(playerInventory, slot, 8 + col * 18, 103 + row * 18 + playerInventoryOffset));
//                }
            }
        }

        // Hotbar items
        for(int slot = 0; slot < 9; ++slot) {
//            if (BagItem.isOpen(playerInventory.getStack(slot))) {
//                addSlot(new LockedSlot(playerInventory, slot, 8 + slot * 18, 161 + playerInventoryOffset));
//            } else {
//                addSlot(new Slot(playerInventory, slot, 8 + slot * 18, 161 + playerInventoryOffset));
//            }
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        bagInventory.writeNbt();
        // TODO: Sound effect
//        if (BagItem.setOpen(bagInventory.bagStack, false)) {
//            playerInventory.setStack(playerInventory.selectedSlot, bagInventory.bagStack);
//        } else {
//            LegacyLuggage.LOGGER.error("Somehow it couldn't make the bag close?");
//        }
        playerInventory.markDirty();
        player.currentScreenHandler.sendContentUpdates();
    }
}
