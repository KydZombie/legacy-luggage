package io.github.kydzombie.legacyluggage.item;

import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.template.item.TemplateArmorItem;
import net.modificationstation.stationapi.api.util.Identifier;

// TODO: Stop using ArmorItem hopefully
public class WearableBagItem extends TemplateArmorItem implements IBagItem {
    private final int width;
    private final int height;

    public WearableBagItem(Identifier identifier, int width, int height) {
        super(identifier, 0, 0, 1);
        setTranslationKey(identifier);
        setMaxCount(1);
        this.width = width;
        this.height = height;
        setMaxDamage(0);
    }

    @Override
    public int getInventoryWidth(ItemStack stack) {
        return width;
    }

    @Override
    public int getInventoryHeight(ItemStack stack) {
        return height;
    }

    @Override
    public boolean isDamageable() {
        return false;
    }
}
