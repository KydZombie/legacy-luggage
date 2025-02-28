package io.github.kydzombie.legacyluggage.gui.screen.ingame;

import io.github.kydzombie.legacyluggage.gui.screen.PouchScreenHandler;
import io.github.kydzombie.legacyluggage.inventory.PouchInventory;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerEntity;
import org.lwjgl.opengl.GL11;

public class PouchScreen extends HandledScreen {
    public PouchScreen(PlayerEntity playerEntity, PouchInventory pouchInventory) {
        super(new PouchScreenHandler(playerEntity, pouchInventory));
    }

    @Override
    protected void drawBackground(float tickDelta) {
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
