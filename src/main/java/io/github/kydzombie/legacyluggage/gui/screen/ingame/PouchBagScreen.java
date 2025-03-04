package io.github.kydzombie.legacyluggage.gui.screen.ingame;

import io.github.kydzombie.legacyluggage.gui.screen.PouchBagScreenHandler;
import io.github.kydzombie.legacyluggage.inventory.BagInventory;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class PouchBagScreen extends HandledScreen {
    public PouchBagScreen(PlayerInventory playerInventory, ItemStack bagStack) {
        super(new PouchBagScreenHandler(playerInventory, bagStack));
    }

    @Override
    protected void drawForeground() {
        PouchBagScreenHandler handler = (PouchBagScreenHandler) container;
        for (int i = 0; i < handler.pouchInventories.length; i++) {
            BagInventory inventory = handler.pouchInventories[i];
            if (inventory != null) {
                textRenderer.draw(String.format("%d: %s, %d", i, handler.pouchStacks[i].toString(), handler.pouchInventories.length), 0, i * 8, 0xFFFFFF);
            } else {
                textRenderer.draw(String.format("%d: null", i), 0, i * 8, 0xFFFFFF);
            }
        }
    }

    @Override
    protected void drawBackground(float tickDelta) {
        int var2 = this.minecraft.textureManager.getTextureId("/assets/legacyluggage/gui/pouch_bag.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.textureManager.bindTexture(var2);
        int var3 = (this.width - this.backgroundWidth) / 2;
        int var4 = (this.height - this.backgroundHeight) / 2;
        this.drawTexture(var3, var4, 0, 0, this.backgroundWidth, this.backgroundHeight);
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
