package io.github.kydzombie.legacyluggage.gui.screen.ingame;

import io.github.kydzombie.legacyluggage.gui.screen.BackpackScreenHandler;
import io.github.kydzombie.legacyluggage.inventory.BagInventory;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class BackpackScreen extends HandledScreen {
    public BackpackScreen(PlayerInventory playerInventory, ItemStack bagStack) {
        super(new BackpackScreenHandler(playerInventory, bagStack));
    }

    @Override
    protected void drawForeground() {
    }

    @Override
    protected void drawBackground(float tickDelta) {
        int textureId = minecraft.textureManager.getTextureId("/assets/legacyluggage/gui/general_bag.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.textureManager.bindTexture(textureId);
        int renderOffsetX = (width - backgroundWidth) / 2;
        int renderOffsetY = (height - backgroundHeight) / 2;
        drawTexture(renderOffsetX, renderOffsetY, 0, 0, backgroundWidth, backgroundHeight);

        BackpackScreenHandler handler = (BackpackScreenHandler) container;
        BagInventory[] pouchInventories = handler.pouchInventories;
        for (int invIndex = 0; invIndex < pouchInventories.length; invIndex++) {
            BagInventory inventory = pouchInventories[invIndex];
            if (inventory == null) continue;
            int pouchU = 176;
            int pouchV = 0;
            for (int slot = 0; slot < inventory.size(); slot++) {
                drawTexture(5 + renderOffsetX + slot * 18, 5 + renderOffsetY + invIndex * 18, pouchU, pouchV, 18, 18);
            }
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
