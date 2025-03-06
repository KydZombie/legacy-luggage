package io.github.kydzombie.legacyluggage.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.tag.TagKey;
import net.modificationstation.stationapi.api.util.Identifier;

public class TagFilteredPouchItem extends PouchItem {
    final TagKey<Item> acceptedTag;
    final int slotIcon;

    public TagFilteredPouchItem(Identifier identifier, int width, int height, TagKey<Item> acceptedTag, int slotIcon) {
        super(identifier, width, height);
        this.acceptedTag = acceptedTag;
        this.slotIcon = slotIcon;
    }

    @Override
    public int getSlotIcon(ItemStack pouch) {
        return slotIcon;
    }

    @Override
    public boolean canInsert(ItemStack pouch, ItemStack stack) {
        return super.canInsert(pouch, stack) && stack.isIn(acceptedTag);
    }
}
