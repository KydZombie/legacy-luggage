package io.github.kydzombie.legacyluggage;

import io.github.kydzombie.legacyluggage.item.BagItem;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.BlockView;
import net.modificationstation.stationapi.api.client.event.render.model.ItemModelPredicateProviderRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.EntrypointManager;
import org.jetbrains.annotations.Nullable;

import java.lang.invoke.MethodHandles;

public class LegacyLuggageClient {
    static {
        EntrypointManager.registerLookup(MethodHandles.lookup());
    }

    @EventListener
    private static void registerItemPredicates(ItemModelPredicateProviderRegistryEvent event) {
        event.register(LegacyLuggage.NAMESPACE.id("open"), (ItemStack stack, @Nullable BlockView world, @Nullable LivingEntity entity, int seed) ->
                BagItem.isOpen(stack) ? 1.0f : 0.0f
        );
    }
}
