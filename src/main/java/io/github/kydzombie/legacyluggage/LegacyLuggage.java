package io.github.kydzombie.legacyluggage;

import com.matthewperiut.accessoryapi.api.AccessoryRegister;
import io.github.kydzombie.legacyluggage.gui.screen.BackpackScreenHandler;
import io.github.kydzombie.legacyluggage.inventory.BagInventory;
import io.github.kydzombie.legacyluggage.item.PouchBagItem;
import io.github.kydzombie.legacyluggage.item.PouchItem;
import io.github.kydzombie.legacyluggage.item.WearableBagItem;
import net.fabricmc.api.ModInitializer;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.event.registry.MessageListenerRegistryEvent;
import net.modificationstation.stationapi.api.gui.screen.container.GuiHelper;
import net.modificationstation.stationapi.api.mod.entrypoint.EntrypointManager;
import net.modificationstation.stationapi.api.network.packet.MessagePacket;
import net.modificationstation.stationapi.api.util.Namespace;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandles;

public class LegacyLuggage implements ModInitializer {
    static {
        EntrypointManager.registerLookup(MethodHandles.lookup());
    }

    @SuppressWarnings("UnstableApiUsage")
    public static final Namespace NAMESPACE = Namespace.resolve();

    public static final Logger LOGGER = NAMESPACE.getLogger();

    @Override
    public void onInitialize() {
        // TODO: Decide if backpacks are
        //  equipped in the chestplate slot
        //  or as an accessory.
        AccessoryRegister.add("back");
    }

    public static PouchItem smallPouchItem;

    public static WearableBagItem smallBackpackItem;
//    public static BagItem largeBackpackItem;

    public static PouchBagItem pouchBagItem;

    @EventListener
    private static void registerItems(ItemRegistryEvent event) {
        // TODO: Finalize sizes
        smallPouchItem = new PouchItem(NAMESPACE.id("small_pouch"), 3, 1);

        smallBackpackItem = new WearableBagItem(NAMESPACE.id("small_backpack"), 3, 2);

        pouchBagItem = new PouchBagItem(NAMESPACE.id("pouch_bag"), 2);
    }

    @EventListener
    private void registerMessageListeners(MessageListenerRegistryEvent event) {
        event.register(NAMESPACE)
                .accept("open_backpack", this::handleOpenBackpack);
    }

    private void handleOpenBackpack(PlayerEntity player, MessagePacket packet) {
        ItemStack backpackStack = player.inventory.armor[2];
        if (backpackStack != null && backpackStack.getItem() instanceof WearableBagItem) {
            BagInventory backpackInventory = new BagInventory(backpackStack);

            GuiHelper.openGUI(
                    player,
                    NAMESPACE.id("open_backpack"),
                    backpackInventory,
                    new BackpackScreenHandler(player.inventory, backpackInventory)
            );
        }
    }
}
