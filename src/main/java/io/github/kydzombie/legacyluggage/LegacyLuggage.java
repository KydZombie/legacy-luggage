package io.github.kydzombie.legacyluggage;

import io.github.kydzombie.legacyluggage.item.BagItem;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.EntrypointManager;
import net.modificationstation.stationapi.api.util.Namespace;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandles;

public class LegacyLuggage {
    static {
        EntrypointManager.registerLookup(MethodHandles.lookup());
    }

    @SuppressWarnings("UnstableApiUsage")
    public static final Namespace NAMESPACE = Namespace.resolve();

    public static final Logger LOGGER = NAMESPACE.getLogger();

    public static BagItem pouchItem;
    public static BagItem smallBackpackItem;
    public static BagItem largeBackpackItem;

    @EventListener
    private static void registerItems(ItemRegistryEvent event) {
        // TODO: Finalize sizes
        pouchItem = new BagItem(NAMESPACE.id("pouch"), 3);
        smallBackpackItem = new BagItem(NAMESPACE.id("small_backpack"), 5);
        largeBackpackItem = new BagItem(NAMESPACE.id("large_backpack"), 8);
    }
}
