package io.github.kydzombie.legacyluggage;

import com.matthewperiut.accessoryapi.api.AccessoryRegister;
import com.matthewperiut.accessoryapi.api.helper.AccessoryAccess;
import io.github.kydzombie.legacyluggage.block.BagTableBlock;
import io.github.kydzombie.legacyluggage.block.entity.BagTableBlockEntity;
import io.github.kydzombie.legacyluggage.gui.screen.BackpackScreenHandler;
import io.github.kydzombie.legacyluggage.item.BackpackItem;
import io.github.kydzombie.legacyluggage.item.PouchItem;
import io.github.kydzombie.legacyluggage.item.TagFilteredPouchItem;
import net.fabricmc.api.ModInitializer;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.event.block.entity.BlockEntityRegisterEvent;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.event.registry.MessageListenerRegistryEvent;
import net.modificationstation.stationapi.api.gui.screen.container.GuiHelper;
import net.modificationstation.stationapi.api.mod.entrypoint.EntrypointManager;
import net.modificationstation.stationapi.api.network.packet.MessagePacket;
import net.modificationstation.stationapi.api.registry.ItemRegistry;
import net.modificationstation.stationapi.api.registry.tag.ItemTags;
import net.modificationstation.stationapi.api.tag.TagKey;
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

    public static BagTableBlock bagTableBlock;

    @EventListener
    private static void registerBlocks(BlockRegistryEvent event) {
        bagTableBlock = new BagTableBlock(NAMESPACE.id("bag_table"), Material.WOOD);
    }

    @EventListener
    private static void registerBlockEntities(BlockEntityRegisterEvent event) {
        event.register(BagTableBlockEntity.class, NAMESPACE.id("bag_table").toString());
    }

    public static PouchItem smallPouchItem;
    public static TagFilteredPouchItem smallMiningPouchItem;
    public static TagFilteredPouchItem smallLumberPouchItem;

//    public static ClassicBackpackItem smallBackpackItem;

    public static BackpackItem smallBackpackItem;
    public static BackpackItem largeBackpackItem;

    public static TagKey<Item> miningPouchTag;
    public static TagKey<Item> lumberPouchTag;

    @EventListener
    private static void registerItems(ItemRegistryEvent event) {
        miningPouchTag = TagKey.of(ItemRegistry.KEY, NAMESPACE.id("mining_pouch_items"));
        lumberPouchTag = TagKey.of(ItemRegistry.KEY, NAMESPACE.id("lumber_pouch_items"));

        // TODO: Finalize sizes
        smallPouchItem = new PouchItem(NAMESPACE.id("small_pouch"), 3, 1);
        smallMiningPouchItem = new TagFilteredPouchItem(NAMESPACE.id("small_mining_pouch"), 3, 2, miningPouchTag, 1);
        smallLumberPouchItem = new TagFilteredPouchItem(NAMESPACE.id("small_lumber_pouch"), 3, 2, lumberPouchTag, 2);

//        smallBackpackItem = new ClassicBackpackItem(NAMESPACE.id("small_backpack"), 3, 2);

        smallBackpackItem = new BackpackItem(NAMESPACE.id("small_backpack"), 2);
        largeBackpackItem = new BackpackItem(NAMESPACE.id("large_backpack"), 4);
    }

    @EventListener
    private void registerMessageListeners(MessageListenerRegistryEvent event) {
        event.register(NAMESPACE)
                .accept("open_backpack", this::handleOpenBackpack);
    }

    private void handleOpenBackpack(PlayerEntity player, MessagePacket packet) {
        ItemStack[] backStacks = AccessoryAccess.getAccessories(player, "back");
        if (backStacks.length > 0) {
            ItemStack pouchBagStack = backStacks[0];
            if (pouchBagStack != null && pouchBagStack.getItem() instanceof BackpackItem) {
                GuiHelper.openGUI(
                        player,
                        NAMESPACE.id("open_pouch_bag"),
                        null,
                        new BackpackScreenHandler(player.inventory, pouchBagStack)
                );
            }
        }

//        ItemStack backpackStack = player.inventory.armor[2];
//        if (backpackStack != null && backpackStack.getItem() instanceof WearableBagItem) {
//            BagInventory backpackInventory = new BagInventory(backpackStack);
//
//            GuiHelper.openGUI(
//                    player,
//                    NAMESPACE.id("open_backpack"),
//                    backpackInventory,
//                    new BackpackScreenHandler(player.inventory, backpackInventory)
//            );
//        }
    }
}
