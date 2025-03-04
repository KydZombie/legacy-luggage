package io.github.kydzombie.legacyluggage.gui.screen;

import com.matthewperiut.accessoryapi.api.helper.AccessoryAccess;
import io.github.kydzombie.legacyluggage.gui.screen.slot.LockedSlot;
import io.github.kydzombie.legacyluggage.gui.screen.slot.PouchSlot;
import io.github.kydzombie.legacyluggage.inventory.BagInventory;
import io.github.kydzombie.legacyluggage.item.IBagItem;
import io.github.kydzombie.legacyluggage.item.PouchBagItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class PouchBagScreenHandler extends ScreenHandler {
    public ItemStack bagStack;
    public PouchBagItem pouchBagItem;

    public ItemStack[] pouchStacks;
    public BagInventory[] pouchInventories;
    public PouchBagScreenHandler(PlayerInventory playerInventory, ItemStack bagStack) {
        this.bagStack = bagStack;
        pouchBagItem = (PouchBagItem) bagStack.getItem();
        pouchStacks = pouchBagItem.getPouches(bagStack);
        pouchInventories = new BagInventory[pouchStacks.length];
        for (int i = 0; i < pouchInventories.length; i++) {
            if (pouchStacks[i] == null) continue;
            pouchInventories[i] = new BagInventory(pouchStacks[i]);
        }

        for (int i = 0; i < pouchInventories.length; i++) {
            BagInventory pouchInventory = pouchInventories[i];
            if (pouchInventory == null) continue;
            for (int col = 0; col < pouchInventory.size(); col++) {
                addSlot(new PouchSlot(pouchInventory, col, col * 18, 24 + i * 18));
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
        ItemStack[] backStacks = AccessoryAccess.getAccessories(player, "back");
        return backStacks.length > 0 &&
                backStacks[0] != null &&
                backStacks[0].getItem() instanceof PouchBagItem;
    }

    @Override
    public void onClosed(PlayerEntity player) {
        for (int i = 0; i < pouchInventories.length; i++) {
            BagInventory pouchInventory = pouchInventories[i];
            if (pouchInventory == null) continue;
            pouchInventory.writeNbt(pouchStacks[i]);
        }
        pouchBagItem.setPouches(bagStack, pouchStacks);

        super.onClosed(player);
    }
}
