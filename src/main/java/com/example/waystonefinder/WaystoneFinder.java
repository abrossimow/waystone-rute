package com.example.waystonefinder;

import com.example.waystonefinder.config.FinderConfig;
import com.example.waystonefinder.registry.ModItems;
import net.blay09.mods.balm.api.Balm;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(WaystoneFinder.MOD_ID)
public final class WaystoneFinder {
    public static final String MOD_ID = "waystonefinder";

    public WaystoneFinder(IEventBus modEventBus, ModContainer modContainer) {
        Balm.config().registerConfig(FinderConfig.class);
        Balm.config().onConfigAvailable(
                FinderConfig.class,
                FinderConfig::setActive
        );

        ModItems.ITEMS.register(modEventBus);
    }
}
