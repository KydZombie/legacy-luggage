package io.github.kydzombie.legacyluggage.block.entity;

import io.github.kydzombie.legacyluggage.item.PouchBagItem;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;

import java.util.Arrays;

public class BagTableBlockEntity extends BlockEntity implements Inventory {
    // TODO: Decide if there should be overflow slots.
    // TODO: Adjustable size
    ItemStack[] inventory = new ItemStack[3];

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
        System.out.println("remove stack");

        if (inventory[slot] != null) {
            ItemStack retStack;
            if (inventory[slot].count <= amount) {
                retStack = inventory[slot];
                inventory[slot] = null;
            } else {
                retStack = inventory[slot].split(amount);
                if (inventory[slot].count == 0) {
                    inventory[slot] = null;
                }
            }
            if (slot == 0) {
                for (int i = 1; i < inventory.length; i++) {
                    setStack(i, null);
                }
            } else {
                ItemStack backpackStack = inventory[0];
                if (backpackStack != null && backpackStack.getItem() instanceof PouchBagItem pouchBagItem) {
                    ItemStack[] pouchStacks = Arrays.copyOfRange(inventory, 1, inventory.length);
                    pouchBagItem.setPouches(backpackStack, pouchStacks);
                }
            }
            markDirty();
            return retStack;
        } else {
            return null;
        }
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        System.out.println("set stack");
        this.inventory[slot] = stack;
        if (stack != null && stack.count > this.getMaxCountPerStack()) {
            stack.count = this.getMaxCountPerStack();
        }

        ItemStack backpackStack = inventory[0];
        if (slot == 0) {
            if (backpackStack != null && backpackStack.getItem() instanceof PouchBagItem pouchBagItem) {
                ItemStack[] pouches = pouchBagItem.getPouches(backpackStack);
                for (int i = 0; i < pouches.length; i++) {
                    setStack(i + 1, pouches[i]);
                }
            }
        } else {
            if (backpackStack != null && backpackStack.getItem() instanceof PouchBagItem pouchBagItem) {
                ItemStack[] pouchStacks = Arrays.copyOfRange(inventory, 1, inventory.length);
                pouchBagItem.setPouches(backpackStack, pouchStacks);
            }
        }


        this.markDirty();
    }

    @Override
    public String getName() {
        return "Bag Table";
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        NbtList var2 = nbt.getList("items");
        this.inventory = new ItemStack[this.size()];

        for (int var3 = 0; var3 < var2.size(); var3++) {
            NbtCompound var4 = (NbtCompound)var2.get(var3);
            int var5 = var4.getByte("slot") & 255;
            if (var5 >= 0 && var5 < this.inventory.length) {
                this.inventory[var5] = new ItemStack(var4);
            }
        }
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        NbtList var2 = new NbtList();

        for (int var3 = 0; var3 < this.inventory.length; var3++) {
            if (this.inventory[var3] != null) {
                NbtCompound var4 = new NbtCompound();
                var4.putByte("slot", (byte)var3);
                this.inventory[var3].writeNbt(var4);
                var2.add(var4);
            }
        }

        nbt.put("items", var2);
    }

    @Override
    public int getMaxCountPerStack() {
        return 64;
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return this.world.getBlockEntity(this.x, this.y, this.z) != this
                ? false
                : !(player.getSquaredDistance((double)this.x + 0.5, (double)this.y + 0.5, (double)this.z + 0.5) > 64.0);
    }
}
