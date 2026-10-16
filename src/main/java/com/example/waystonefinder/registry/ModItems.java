package com.example.waystonefinder.registry;

import com.example.waystonefinder.WaystoneFinder;
import com.example.waystonefinder.config.FinderConfig;
import com.example.waystonefinder.item.WaystoneLocatorItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModItems {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(WaystoneFinder.MOD_ID);

    public static final DeferredItem<WaystoneLocatorItem> WAYSTONE_LOCATOR =
            ITEMS.register("waystone_locator", () ->
                    new WaystoneLocatorItem(
                            new Item.Properties().durability(32)
                    )
            );

    private ModItems() {
    }
}
