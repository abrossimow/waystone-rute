package com.example.waystonefinder.config;

import net.blay09.mods.balm.api.config.*;

@Config("waystonefinder")
public class FinderConfig {

    @Comment("Maximum durability of the Waystone Locator.")
    public int durability = 32;

    @Comment("Maximum distance in blocks searched by the Waystone Locator.")
    public int searchRadius = 128;

    @Comment("Minimum Distance before Locator stops giving actual distance.")
    public int proximityThreshold = 16;

    @Comment("Whether the Waystone Locator can break when its durability is depleted.")
    public boolean breakableLocator = true;

    private static volatile FinderConfig active = new FinderConfig();

    public static FinderConfig getActive() {
        return active;
    }

    public static void setActive(FinderConfig config) {
        active = config;
    }
}

