package io.github.kydzombie.legacyluggage.gui.screen.slot;

import io.github.kydzombie.legacyluggage.item.BackpackItem;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class BagTableBagSlot extends Slot {
    public BagTableBagSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        if (stack == null) return true;
        return stack.getItem() instanceof BackpackItem;
    }
}
