package io.github.kydzombie.legacyluggage.gui.screen.ingame;

import io.github.kydzombie.legacyluggage.gui.screen.BackpackScreenHandler;
import io.github.kydzombie.legacyluggage.inventory.BagInventory;
import io.github.kydzombie.legacyluggage.item.PouchItem;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

// TODO: Rework the UI completely?
public class BackpackScreen extends HandledScreen {
    PlayerInventory playerInventory;
    int rows;

    public BackpackScreen(PlayerInventory playerInventory, ItemStack bagStack) {
        super(new BackpackScreenHandler(playerInventory, bagStack));
        this.playerInventory = playerInventory;

        BackpackScreenHandler handler = (BackpackScreenHandler) container;
        rows = 0;
        for (int i = 0; i < handler.pouchInventories.length; i++) {
            if (handler.pouchInventories[i] != null) rows++;
        }
        this.backgroundHeight = 114 + this.rows * 18;
    }

    @Override
    protected void drawForeground() {
        BackpackScreenHandler handler = (BackpackScreenHandler) container;
        String name = handler.bagStack.getItem().getTranslatedName();
        int textWidth = textRenderer.getWidth(name);
        int offsetX = (backgroundWidth - textWidth) / 2;
        textRenderer.draw(name, offsetX, 6, 4210752);
        textRenderer.draw(playerInventory.getName(), 8, backgroundHeight - 96 + 2, 4210752);
    }

    @Override
    protected void drawBackground(float tickDelta) {
        int textureId = minecraft.textureManager.getTextureId("/assets/legacyluggage/gui/general_bag.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.textureManager.bindTexture(textureId);
        int renderOffsetX = (width - backgroundWidth) / 2;
        int renderOffsetY = (height - backgroundHeight) / 2;
        this.drawTexture(renderOffsetX, renderOffsetY, 0, 0, backgroundWidth, rows * 18 + 17);
        this.drawTexture(renderOffsetX, renderOffsetY + rows * 18 + 17, 0, 126, backgroundWidth, 96);

        BackpackScreenHandler handler = (BackpackScreenHandler) container;
        BagInventory[] pouchInventories = handler.pouchInventories;
        ItemStack[] pouchStacks = handler.pouchStacks;

        int invRow = 0;
        for (int invIndex = 0; invIndex < pouchInventories.length; invIndex++) {
            BagInventory inventory = pouchInventories[invIndex];
            ItemStack pouchStack = pouchStacks[invIndex];
            if (inventory == null) continue;
            PouchItem pouchItem = (PouchItem) pouchStack.getItem();
            int pouchWidth = inventory.size() * 18;
            int offsetX = ((width - pouchWidth) / 2);
            int pouchU = 176;
            int pouchV = pouchItem.getSlotIcon(pouchStack) * 18;
            for (int slot = 0; slot < inventory.size(); slot++) {
                drawTexture(offsetX - 1 + slot * 18, renderOffsetY + 17 + invRow * 18, pouchU, pouchV, 18, 18);
            }
            invRow++;
        }
    }

    @Override
    public void removed() {
        // TODO: Should I be doing this??
        if (!minecraft.isWorldRemote()) {
            container.onClosed(minecraft.player);
        }
        super.removed();
    }
}
