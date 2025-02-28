package io.github.kydzombie.legacyluggage.gui.screen.ingame;

import io.github.kydzombie.legacyluggage.gui.screen.BagScreenHandler;
import io.github.kydzombie.legacyluggage.inventory.BagInventory;
import io.github.kydzombie.legacyluggage.item.BagItem;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import org.lwjgl.opengl.GL11;

public class BagScreen extends HandledScreen {
    private final PlayerInventory playerInventory;
    private final BagInventory bagInventory;
    private final int rows;

    public BagScreen(PlayerInventory playerInventory, BagInventory bagInventory) {
        super(new BagScreenHandler(playerInventory, bagInventory));
        this.playerInventory = playerInventory;
        this.bagInventory = bagInventory;

        this.rows = bagInventory.size() / 3;

        short var3 = 222;
        int var4 = var3 - 108;
        this.backgroundHeight = var4 + (rows) * 18;
    }

    @Override
    protected void drawBackground(float tickDelta) {
        int textureId = minecraft.textureManager.getTextureId("/gui/container.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.textureManager.bindTexture(textureId);
        int centerX = (width - backgroundWidth) / 2;
        int centerY = (height - backgroundHeight) / 2;
        drawTexture(centerX, centerY, 0, 0, backgroundWidth, rows * 18 + 17);
        drawTexture(centerX, centerY + rows * 18 + 17, 0, 126, backgroundWidth, 96);
    }

    @Override
    public void removed() {
        if (!playerInventory.player.world.isRemote) {
//            BagItem.setOpen(bagInventory.bagStack, false);
            bagInventory.writeNbt();
        }
        super.removed();
    }
}
