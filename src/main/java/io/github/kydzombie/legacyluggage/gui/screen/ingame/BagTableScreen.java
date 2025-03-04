package io.github.kydzombie.legacyluggage.gui.screen.ingame;

import io.github.kydzombie.legacyluggage.block.entity.BagTableBlockEntity;
import io.github.kydzombie.legacyluggage.gui.screen.BagTableScreenHandler;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import org.lwjgl.opengl.GL11;

public class BagTableScreen extends HandledScreen {
    public BagTableScreen(PlayerInventory playerInventory, BagTableBlockEntity blockEntity) {
        super(new BagTableScreenHandler(playerInventory, blockEntity));
    }

    @Override
    protected void drawBackground(float tickDelta) {
        int var2 = this.minecraft.textureManager.getTextureId("/assets/legacyluggage/gui/bag_table.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.textureManager.bindTexture(var2);
        int var3 = (this.width - this.backgroundWidth) / 2;
        int var4 = (this.height - this.backgroundHeight) / 2;
        this.drawTexture(var3, var4, 0, 0, this.backgroundWidth, this.backgroundHeight);
    }

    @Override
    public void tick() {
        super.tick();
        BagTableScreenHandler handler = (BagTableScreenHandler) container;
        handler.updateSlots();
    }
}
