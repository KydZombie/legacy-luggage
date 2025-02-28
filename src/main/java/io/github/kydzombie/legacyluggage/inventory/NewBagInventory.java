package io.github.kydzombie.legacyluggage.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

public class NewBagInventory implements Inventory {
    ItemStack bagStack;
    ItemStack[] inventory;
    int size;

    public NewBagInventory(ItemStack bagStack) {
        this.bagStack = bagStack;
        size = 4;
        inventory = new ItemStack[size];
    }

    @Override
    public int size() {
        return size;
    }

//    public int rowSize() {
//        return rowSize;
//    }

    @Override
    public ItemStack getStack(int slot) {
        return inventory[slot];
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        if (inventory[slot] != null) {
            if (inventory[slot].count <= amount) {
                ItemStack var4 = inventory[slot];
                inventory[slot] = null;
                markDirty();
                return var4;
            } else {
                ItemStack var3 = inventory[slot].split(amount);
                if (inventory[slot].count == 0) {
                    inventory[slot] = null;
                }

                markDirty();
                return var3;
            }
        } else {
            return null;
        }
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        inventory[slot] = stack;
        if (stack != null && stack.count > getMaxCountPerStack()) {
            stack.count = getMaxCountPerStack();
        }

        markDirty();
    }

    @Override
    public String getName() {
        return "Bag";
    }

    @Override
    public int getMaxCountPerStack() {
        return 64;
    }

    @Override
    public void markDirty() {

    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }
}
