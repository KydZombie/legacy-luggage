package io.github.kydzombie.legacyluggage;

import io.github.kydzombie.legacyluggage.gui.screen.ingame.BagScreen;
import io.github.kydzombie.legacyluggage.gui.screen.ingame.PouchScreen;
import io.github.kydzombie.legacyluggage.inventory.BagInventory;
import io.github.kydzombie.legacyluggage.inventory.PouchInventory;
import io.github.kydzombie.legacyluggage.item.BagItem;
import io.github.kydzombie.legacyluggage.item.IBagItem;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.BlockView;
import net.modificationstation.stationapi.api.client.event.render.model.ItemModelPredicateProviderRegistryEvent;
import net.modificationstation.stationapi.api.client.gui.screen.GuiHandler;
import net.modificationstation.stationapi.api.event.registry.GuiHandlerRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.EntrypointManager;
import org.jetbrains.annotations.Nullable;

import java.lang.invoke.MethodHandles;

public class LegacyLuggageClient {
    static {
        EntrypointManager.registerLookup(MethodHandles.lookup());
    }

    @EventListener
    private static void registerItemPredicates(ItemModelPredicateProviderRegistryEvent event) {
        event.register(
                LegacyLuggage.NAMESPACE.id("open"),
                (ItemStack stack, @Nullable BlockView world, @Nullable LivingEntity entity, int seed) -> {
                    if (stack == null) return 0;
                    if (stack.getItem() instanceof IBagItem bagItem) {
                        return bagItem.isOpen(stack) ? 1 : 0;
                    } else {
                        return 0;
                    }
                }
        );
    }

    @EventListener
    private static void registerGuiHandlers(GuiHandlerRegistryEvent event) {
        event.register(LegacyLuggage.NAMESPACE.id("bag"), new GuiHandler((GuiHandler.ScreenFactoryNoMessage) (player, inventory) -> {
            return new BagScreen(player.inventory, new BagInventory(player.inventory.getStack(player.inventory.selectedSlot)));
        }, () -> null));

        event.register(
                LegacyLuggage.NAMESPACE.id("pouch"),
                new GuiHandler(
                        (GuiHandler.ScreenFactoryNoMessage) (player, inventory) -> new PouchScreen(player, new PouchInventory(player.getHand())),
                        () -> null
                )
        );
    }
}
