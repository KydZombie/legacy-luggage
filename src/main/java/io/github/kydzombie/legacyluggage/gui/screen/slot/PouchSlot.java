package io.github.kydzombie.legacyluggage.gui.screen.slot;

import io.github.kydzombie.legacyluggage.item.IBagItem;
import io.github.kydzombie.legacyluggage.item.PouchItem;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class PouchSlot extends Slot {
    ItemStack pouchStack;

    public PouchSlot(Inventory inventory, int index, int x, int y, ItemStack pouchStack) {
        super(inventory, index, x, y);
        this.pouchStack = pouchStack;
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return stack == null || ((PouchItem)pouchStack.getItem()).canInsert(pouchStack, stack);
    }
}
