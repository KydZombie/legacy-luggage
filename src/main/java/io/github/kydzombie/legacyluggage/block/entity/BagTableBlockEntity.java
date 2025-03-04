package io.github.kydzombie.legacyluggage.block.entity;

import io.github.kydzombie.legacyluggage.item.BackpackItem;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BagTableBlockEntity extends BlockEntity implements Inventory {
    // TODO: Decide if there should be overflow slots.
    // TODO: Adjustable size
    ArrayList<ItemStack> inventory = new ArrayList<>(1);

    public BagTableBlockEntity() {
        inventory.add(null);
    }

    @Override
    public int size() {
        return inventory.size();
    }

    @Override
    public ItemStack getStack(int slot) {
        return inventory.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        System.out.println("remove stack");

        if (inventory.get(slot) != null) {
            ItemStack retStack;
            if (inventory.get(slot).count <= amount) {
                retStack = inventory.get(slot);
                inventory.set(slot, null);
            } else {
                retStack = inventory.get(slot).split(amount);
                if (inventory.get(slot).count == 0) {
                    inventory.set(slot, null);
                }
            }
            if (slot == 0) {
                inventory.clear();
                inventory.add(null);
            } else {
                ItemStack backpackStack = inventory.get(0);
                if (backpackStack != null && backpackStack.getItem() instanceof BackpackItem backpackItem) {
                    List<ItemStack> pouchStacks = inventory.subList(1, inventory.size());
                    backpackItem.setPouches(backpackStack, pouchStacks.toArray(ItemStack[]::new));
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
        inventory.set(slot, stack);
        if (stack != null && stack.count > this.getMaxCountPerStack()) {
            stack.count = this.getMaxCountPerStack();
        }

        ItemStack backpackStack = inventory.get(0);
        if (slot == 0) {
            if (backpackStack != null && backpackStack.getItem() instanceof BackpackItem backpackItem) {
                ItemStack[] pouches = backpackItem.getPouches(backpackStack);
                if (inventory.size() > 2) {
                    inventory.subList(1, inventory.size()).clear();
                }
                inventory.addAll(Arrays.asList(pouches));
            }
        } else {
            if (backpackStack != null && backpackStack.getItem() instanceof BackpackItem backpackItem) {
                List<ItemStack> pouchStacks = inventory.subList(1, inventory.size());
                backpackItem.setPouches(backpackStack, pouchStacks.toArray(ItemStack[]::new));
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
        this.inventory = new ArrayList<>(1);
        if (nbt.contains("backpack")) {
            NbtCompound backpackNbt = nbt.getCompound("backpack");
            ItemStack backpackStack = new ItemStack(backpackNbt);
            inventory.add(0, backpackStack);
            if (backpackStack.getItem() instanceof BackpackItem backpackItem) {
                ItemStack[] pouches = backpackItem.getPouches(backpackStack);
                if (inventory.size() > 2) {
                    inventory.subList(1, inventory.size()).clear();
                }
                inventory.addAll(Arrays.asList(pouches));
            }
        } else {
            inventory.add(0, null);
        }
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        if (inventory.get(0) != null) {
            nbt.put("backpack", inventory.get(0).writeNbt(new NbtCompound()));
        }
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
