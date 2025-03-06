package io.github.kydzombie.legacyluggage.gui.screen;

import io.github.kydzombie.legacyluggage.LegacyLuggage;
import io.github.kydzombie.legacyluggage.gui.screen.slot.LockedSlot;
import io.github.kydzombie.legacyluggage.gui.screen.slot.PouchSlot;
import io.github.kydzombie.legacyluggage.inventory.BagInventory;
import io.github.kydzombie.legacyluggage.item.IBagItem;
import io.github.kydzombie.legacyluggage.item.PouchItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class PouchScreenHandler extends ScreenHandler {
    public PouchItem pouchItem;

    BagInventory pouchInventory;

    public PouchScreenHandler(PlayerInventory playerInventory, BagInventory pouchInventory) {
        ItemStack pouchStack = playerInventory.player.getHand();
        this.pouchInventory = pouchInventory;

        if (pouchStack == null || !(pouchStack.getItem() instanceof PouchItem)) {
            LegacyLuggage.LOGGER.error("Attempted to open pouch screen but the player wasn't holding a pouch!");
            return;
        }
        pouchItem = (PouchItem) pouchStack.getItem();

        for (int i = 0; i < 3; i++) {
            addSlot(new PouchSlot(pouchInventory, i, 62 + (i * 18), 29, pouchStack));
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
        return pouchItem != null;
    }

    @Override
    public void onClosed(PlayerEntity player) {
        pouchInventory.writeNbt(player.getHand());
        super.onClosed(player);
    }
}
