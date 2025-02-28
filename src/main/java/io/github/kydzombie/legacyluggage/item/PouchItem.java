package io.github.kydzombie.legacyluggage.item;

import io.github.kydzombie.legacyluggage.LegacyLuggage;
import io.github.kydzombie.legacyluggage.gui.screen.BagScreenHandler;
import io.github.kydzombie.legacyluggage.gui.screen.PouchScreenHandler;
import io.github.kydzombie.legacyluggage.inventory.BagInventory;
import io.github.kydzombie.legacyluggage.inventory.PouchInventory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.gui.screen.container.GuiHelper;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class PouchItem extends TemplateItem implements IBagItem {
    private final int width;
    private final int height;

    public PouchItem(Identifier identifier, int width, int height) {
        super(identifier);
        this.width = width;
        this.height = height;
        setTranslationKey(identifier);
        setMaxCount(1);
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
    public ItemStack use(ItemStack stack, World world, PlayerEntity user) {
        // TODO: Sound effect
        setOpen(stack, true);
        PouchInventory inventory = new PouchInventory(stack);
        GuiHelper.openGUI(
                user,
                LegacyLuggage.NAMESPACE.id("pouch"),
                inventory,
                new PouchScreenHandler(user, inventory)
        );
        return super.use(stack, world, user);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        // TODO: I don't want to need this
        if (!selected) setOpen(stack, false);
        else if (entity instanceof PlayerEntity player) {
            if (!(player.currentScreenHandler instanceof PouchScreenHandler)) {
                setOpen(stack, false);
            }
        } else {
            setOpen(stack, false);
        }

        super.inventoryTick(stack, world, entity, slot, selected);
    }
}
