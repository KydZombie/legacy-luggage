package io.github.kydzombie.legacyluggage;

import com.matthewperiut.accessoryapi.api.helper.AccessoryAccess;
import io.github.kydzombie.legacyluggage.block.entity.BagTableBlockEntity;
import io.github.kydzombie.legacyluggage.gui.screen.ingame.BackpackScreen;
import io.github.kydzombie.legacyluggage.gui.screen.ingame.BagTableScreen;
import io.github.kydzombie.legacyluggage.gui.screen.ingame.PouchBagScreen;
import io.github.kydzombie.legacyluggage.gui.screen.ingame.PouchScreen;
import io.github.kydzombie.legacyluggage.inventory.BagInventory;
import io.github.kydzombie.legacyluggage.item.IBagItem;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.BlockView;
import net.modificationstation.stationapi.api.client.event.keyboard.KeyStateChangedEvent;
import net.modificationstation.stationapi.api.client.event.option.KeyBindingRegisterEvent;
import net.modificationstation.stationapi.api.client.event.render.model.ItemModelPredicateProviderRegistryEvent;
import net.modificationstation.stationapi.api.client.gui.screen.GuiHandler;
import net.modificationstation.stationapi.api.event.registry.GuiHandlerRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.EntrypointManager;
import net.modificationstation.stationapi.api.network.packet.MessagePacket;
import net.modificationstation.stationapi.api.network.packet.PacketHelper;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.input.Keyboard;

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
        event.register(
                LegacyLuggage.NAMESPACE.id("open_pouch"),
                new GuiHandler(
                        (GuiHandler.ScreenFactoryNoMessage) (player, inventory) -> new PouchScreen(player.inventory, new BagInventory(player.getHand())),
                        () -> null
                )
        );

        event.register(
                LegacyLuggage.NAMESPACE.id("open_backpack"),
                new GuiHandler(
                        (GuiHandler.ScreenFactoryNoMessage) (player, inventory) -> new BackpackScreen(player.inventory, new BagInventory(player.inventory.armor[2])),
                        () -> null
                )
        );

        event.register(
                LegacyLuggage.NAMESPACE.id("open_pouch_bag"),
                new GuiHandler(
                        (GuiHandler.ScreenFactoryNoMessage) (player, inventory) -> new PouchBagScreen(player.inventory, AccessoryAccess.getAccessories(player, "back")[0]),
                        () -> null
                )
        );

        event.register(
                LegacyLuggage.NAMESPACE.id("open_bag_table"),
                new GuiHandler(
                        (GuiHandler.ScreenFactoryNoMessage) (player, inventory) -> new BagTableScreen(player.inventory, (BagTableBlockEntity) inventory),
                        BagTableBlockEntity::new
                )
        );
    }

    private static KeyBinding openBackpackBind;

    @EventListener
    private static void registerKeybinds(KeyBindingRegisterEvent event) {
        openBackpackBind = new KeyBinding("key.legacyluggage.open_backpack", Keyboard.KEY_B);
        event.keyBindings.add(openBackpackBind);
    }

    // TODO: Figure out if I want hold behavior,
    //  and if so, what it should do.
    @EventListener
    private static void handleKeyboardState(KeyStateChangedEvent event) {
        if (event.environment == KeyStateChangedEvent.Environment.IN_GAME && Keyboard.getEventKey() == openBackpackBind.code) {
            if (Keyboard.getEventKeyState()) {
                PacketHelper.send(new MessagePacket(LegacyLuggage.NAMESPACE.id("open_backpack")));
            }
        }
    }
}
