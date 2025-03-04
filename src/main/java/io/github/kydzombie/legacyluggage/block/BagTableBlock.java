package io.github.kydzombie.legacyluggage.block;

import io.github.kydzombie.legacyluggage.LegacyLuggage;
import io.github.kydzombie.legacyluggage.block.entity.BagTableBlockEntity;
import io.github.kydzombie.legacyluggage.gui.screen.BagTableScreenHandler;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.gui.screen.container.GuiHelper;
import net.modificationstation.stationapi.api.template.block.TemplateBlockWithEntity;
import net.modificationstation.stationapi.api.util.Identifier;

public class BagTableBlock extends TemplateBlockWithEntity {
    public BagTableBlock(Identifier identifier, Material material) {
        super(identifier, material);
        setTranslationKey(identifier);
        setHardness(2.0f);
    }

    @Override
    protected BlockEntity createBlockEntity() {
        return new BagTableBlockEntity();
    }

    @Override
    public boolean onUse(World world, int x, int y, int z, PlayerEntity player) {
        BagTableBlockEntity blockEntity = (BagTableBlockEntity) world.getBlockEntity(x, y, z);
        GuiHelper.openGUI(
                player,
                LegacyLuggage.NAMESPACE.id("open_bag_table"),
                blockEntity,
                new BagTableScreenHandler(player.inventory, blockEntity)
        );
        return super.onUse(world, x, y, z, player);
    }
}
