package io.github.kydzombie.legacyluggage.item;

import net.minecraft.item.ItemStack;

import java.util.Objects;

public interface IBagItem {
    String OPEN_NBT = "ll_open";

    default boolean isOpen(ItemStack stack) {
        if (!Objects.equals(stack.getItem(), this)) return false;
        return stack.getStationNbt().getBoolean(OPEN_NBT);
    }

    default boolean setOpen(ItemStack stack, boolean open) {
        if (!Objects.equals(stack.getItem(), this)) return false;
        stack.getStationNbt().putBoolean(OPEN_NBT, open);
        return true;
    }

    default int getInventorySize(ItemStack stack) {
        return getInventoryWidth(stack) * getInventoryHeight(stack);
    }

    int getInventoryWidth(ItemStack stack);
    int getInventoryHeight(ItemStack stack);
}
