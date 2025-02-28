package io.github.kydzombie.legacyluggage.inventory;

import io.github.kydzombie.legacyluggage.LegacyLuggage;
import io.github.kydzombie.legacyluggage.item.PouchItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;

public class PouchInventory implements Inventory {
    private final ItemStack[] inventory;

    // TODO: Decide if using @NotNull and stuff
    public PouchInventory(ItemStack pouchStack) {
        PouchItem pouchItem = (PouchItem) pouchStack.getItem();
        inventory = new ItemStack[pouchItem.getInventorySize(pouchStack)];
        readNbt(pouchStack);
    }

    @Override
    public int size() {
        return inventory.length;
    }

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
        return "Pouch";
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

    public void writeNbt(ItemStack itemStack) {
        NbtCompound nbt = itemStack.getStationNbt();
        NbtList nbtList = new NbtList();
        for (byte slot = 0; slot < inventory.length; slot++) {
            if (inventory[slot] == null) continue;
            NbtCompound itemNbt = new NbtCompound();
            itemNbt.putByte("slot", slot);
            itemNbt.put("item", inventory[slot].writeNbt(new NbtCompound()));
            nbtList.add(itemNbt);
        }
        nbt.put("items", nbtList);
    }

    public void readNbt(ItemStack itemStack) {
        NbtCompound nbt = itemStack.getStationNbt();
        NbtList nbtList = nbt.getList("items");
        for (int i = 0; i < nbtList.size(); i++) {
            NbtCompound itemNbt = (NbtCompound) nbtList.get(i);
            byte slot = itemNbt.getByte("slot");
            if (slot < 0 || slot > inventory.length) {
                LegacyLuggage.LOGGER.error(
                        "Tried to load a stack from {} of slot {} but " +
                                "that is too high for an inventory of size {}!",
                        itemStack, slot, inventory.length
                );
                continue;
            }
            inventory[slot] = new ItemStack(itemNbt.getCompound("item"));
        }
    }
}
