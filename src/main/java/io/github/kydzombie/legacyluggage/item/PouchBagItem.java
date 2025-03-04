package io.github.kydzombie.legacyluggage.item;

import com.matthewperiut.accessoryapi.api.Accessory;
import io.github.kydzombie.legacyluggage.LegacyLuggage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

// TODO: Make it no longer an accessory, or keep it idk
public class PouchBagItem extends TemplateItem implements Accessory {
    int maxPouches;

    public PouchBagItem(Identifier identifier, int maxPouches) {
        super(identifier);
        setTranslationKey(identifier);
        setMaxCount(1);
        this.maxPouches = maxPouches;
    }

    // TODO: Decide if this should stay
    public ItemStack[] getPouches(ItemStack bagStack) {
        NbtCompound nbt = bagStack.getStationNbt();
        NbtList pouches = nbt.getList("pouches");
        ItemStack[] pouchStacks = new ItemStack[maxPouches];
        for (int i = 0; i < pouches.size(); i++) {
            NbtCompound pouchNbt = (NbtCompound) pouches.get(i);
            byte index = pouchNbt.getByte("slot");
            ItemStack pouchStack = new ItemStack(pouchNbt.getCompound("item"));
            pouchStacks[index] = pouchStack;
        }
        return pouchStacks;
    }

    // TODO: Decide if this should stay
    public void setPouches(ItemStack bagStack, ItemStack[] pouchStacks) {
        NbtCompound nbt = bagStack.getStationNbt();
        NbtList pouches = new NbtList();
        for (int i = 0; i < maxPouches; i++) {
            ItemStack pouchStack = pouchStacks[i];
            if (pouchStack == null) continue;
            NbtCompound pouchNbt = new NbtCompound();
            pouchNbt.putByte("slot", (byte) i);
            pouchNbt.put("item", pouchStacks[i].writeNbt(new NbtCompound()));
            pouches.add(pouchNbt);
        }
        nbt.put("pouches", pouches);
    }

    @Override
    public ItemStack use(ItemStack stack, World world, PlayerEntity user) {
        if (user.isSneaking()) {
            ItemStack[] pouchStacks = new ItemStack[maxPouches];
            ItemStack firstPouchStack = new ItemStack(LegacyLuggage.pouchBagItem);
            pouchStacks[0] = firstPouchStack;
            setPouches(stack, pouchStacks);
            LegacyLuggage.LOGGER.info("Added pouches");
        } else {
            LegacyLuggage.LOGGER.info("Printing pouches");
            ItemStack[] pouchStacks = getPouches(stack);
            for (int i = 0; i < pouchStacks.length; i++) {
                LegacyLuggage.LOGGER.info("\t{}. {}", i, pouchStacks[i] == null ? "null" : pouchStacks[i].toString());
            }
        }

        return super.use(stack, world, user);
    }

    @Override
    public String[] getAccessoryTypes(ItemStack itemStack) {
        return new String[]{
                "back"
        };
    }
}
