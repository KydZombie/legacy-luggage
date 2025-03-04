package io.github.kydzombie.legacyluggage.gui.screen.slot;

import io.github.kydzombie.legacyluggage.item.PouchItem;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class BagTablePouchSlot extends Slot {
    public BagTablePouchSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        if (stack == null) return true;
        return stack.getItem() instanceof PouchItem;
    }
}
