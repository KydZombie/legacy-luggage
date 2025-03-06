package io.github.kydzombie.legacyluggage.gui.screen;

import com.matthewperiut.accessoryapi.api.helper.AccessoryAccess;
import io.github.kydzombie.legacyluggage.gui.screen.slot.LockedSlot;
import io.github.kydzombie.legacyluggage.gui.screen.slot.PouchSlot;
import io.github.kydzombie.legacyluggage.inventory.BagInventory;
import io.github.kydzombie.legacyluggage.item.IBagItem;
import io.github.kydzombie.legacyluggage.item.BackpackItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class BackpackScreenHandler extends ScreenHandler {
    public ItemStack bagStack;
    public BackpackItem backpackItem;

    public ItemStack[] pouchStacks;
    public BagInventory[] pouchInventories;

    private int rows;

    public BackpackScreenHandler(PlayerInventory playerInventory, ItemStack bagStack) {
        this.bagStack = bagStack;
        backpackItem = (BackpackItem) bagStack.getItem();
        pouchStacks = backpackItem.getPouches(bagStack);
        pouchInventories = new BagInventory[pouchStacks.length];
        rows = 0;
        for (int i = 0; i < pouchInventories.length; i++) {
            if (pouchStacks[i] == null) continue;
            rows++;
            pouchInventories[i] = new BagInventory(pouchStacks[i]);
        }

        int invRow = 0;
        for (int invIndex = 0; invIndex < pouchInventories.length; invIndex++) {
            BagInventory pouchInventory = pouchInventories[invIndex];
            if (pouchInventory == null) continue;
            final int INVENTORY_WIDTH = (9 * 18) + 14;
            int width = pouchInventory.size() * 18;
            int offsetX = ((INVENTORY_WIDTH - width) / 2);
            for (int col = 0; col < pouchInventory.size(); col++) {
                addSlot(new PouchSlot(pouchInventory, col, offsetX + col * 18, 18 + invRow * 18, pouchStacks[invIndex]));
            }
            invRow++;
        }

        int playerOffset = (this.rows - 4) * 18;

        // Player inventory
        for(int row = 0; row < 3; ++row) {
            for(int col = 0; col < 9; ++col) {
                int slot = col + row * 9 + 9;
                ItemStack stack = playerInventory.getStack(slot);
                if (stack != null && stack.getItem() instanceof IBagItem bagItem && bagItem.isOpen(stack)) {
                    addSlot(new LockedSlot(playerInventory, slot, 8 + col * 18, 103 + row * 18 + playerOffset));
                } else {
                    addSlot(new Slot(playerInventory, slot, 8 + col * 18, 103 + row * 18 + playerOffset));
                }
            }
        }

        // Hotbar items
        for(int slot = 0; slot < 9; ++slot) {
            ItemStack stack = playerInventory.getStack(slot);
            if (stack != null && stack.getItem() instanceof IBagItem bagItem && bagItem.isOpen(stack)) {
                addSlot(new LockedSlot(playerInventory, slot, 8 + slot * 18, 161 + playerOffset));
            } else {
                addSlot(new Slot(playerInventory, slot, 8 + slot * 18, 161 + playerOffset));
            }
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        ItemStack[] backStacks = AccessoryAccess.getAccessories(player, "back");
        return backStacks.length > 0 &&
                backStacks[0] != null &&
                backStacks[0].getItem() instanceof BackpackItem;
    }

    @Override
    public void onClosed(PlayerEntity player) {
        for (int i = 0; i < pouchInventories.length; i++) {
            BagInventory pouchInventory = pouchInventories[i];
            if (pouchInventory == null) continue;
            pouchInventory.writeNbt(pouchStacks[i]);
        }
        backpackItem.setPouches(bagStack, pouchStacks);

        super.onClosed(player);
    }
}
