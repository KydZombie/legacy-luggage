package io.github.kydzombie.legacyluggage.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class BagItem extends TemplateItem {
    public static final String OPEN_NBT = "ll_open";
    final int space;

    public BagItem(Identifier identifier, int space) {
        super(identifier);
        this.space = space;
        setTranslationKey(identifier);
        setMaxCount(1);
    }

    public static boolean isOpen(ItemStack stack) {
        return stack.getStationNbt().getBoolean(OPEN_NBT);
    }

    @Override
    public ItemStack use(ItemStack stack, World world, PlayerEntity user) {
        NbtCompound nbt = stack.getStationNbt();
        nbt.putBoolean(OPEN_NBT, !nbt.getBoolean(OPEN_NBT));
        return super.use(stack, world, user);
    }
}
