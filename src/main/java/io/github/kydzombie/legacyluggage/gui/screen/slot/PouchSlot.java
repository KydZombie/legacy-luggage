package io.github.kydzombie.legacyluggage.gui.screen.slot;

import io.github.kydzombie.legacyluggage.item.IBagItem;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class PouchSlot extends Slot {
    public PouchSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        if (stack != null && stack.getItem() instanceof IBagItem) {
            return false;
        }
        return super.canInsert(stack);
    }
}
