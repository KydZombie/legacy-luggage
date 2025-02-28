package io.github.kydzombie.legacyluggage.item;

import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

// TODO: Maybe this should be an interface? Dunno
public class BagItem extends TemplateItem {

    private final int space;
    private final int rowSize;

    public BagItem(Identifier identifier, int space) {
        this(identifier, space, 9);
    }

    // TODO: Maybe a better way to do this?
    public BagItem(Identifier identifier, int space, int rowSize) {
        super(identifier);
        this.space = space;
        this.rowSize = rowSize;
        setTranslationKey(identifier);
        setMaxCount(1);
    }

    public int getSize(ItemStack stack) {
        return space;
    }

    public int getRowSize(ItemStack stack) {
        return rowSize;
    }
}
