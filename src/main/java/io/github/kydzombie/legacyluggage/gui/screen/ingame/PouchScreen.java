package io.github.kydzombie.legacyluggage.gui.screen.ingame;

import io.github.kydzombie.legacyluggage.gui.screen.PouchScreenHandler;
import io.github.kydzombie.legacyluggage.inventory.BagInventory;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import org.lwjgl.opengl.GL11;

public class PouchScreen extends HandledScreen {
    // TODO: Support different sizes of pouch
    public PouchScreen(PlayerInventory playerInventory, BagInventory pouchInventory) {
        super(new PouchScreenHandler(playerInventory, pouchInventory));
    }

    @Override
    protected void drawForeground() {
        int textWidth = textRenderer.getWidth("Small Pouch");
        int xOffset = (this.backgroundWidth / 2) - (textWidth / 2);
        this.textRenderer.draw("Small Pouch", xOffset, 6, 4210752);
        int inventoryYOffset = this.backgroundHeight - 96 + 2;
        this.textRenderer.draw("Inventory", 8, inventoryYOffset, 4210752);
    }

    @Override
    protected void drawBackground(float tickDelta) {
        // TODO: Automatically adjust to pouch size
        int var2 = this.minecraft.textureManager.getTextureId("/assets/legacyluggage/gui/small_pouch.png");
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
