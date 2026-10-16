package com.example.waystonefinder.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.fml.config.ModConfig;

public final class FinderConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.IntValue LOCATOR_DURABILITY;
    public static final ModConfigSpec.IntValue SEARCH_RADIUS;
    public static final ModConfigSpec.IntValue PROXIMITY_THRESHOLD;
    public static final ModConfigSpec.BooleanValue BREAKABLE_LOCATOR;
    public static final ModConfigSpec SPEC;

    static {
        BUILDER.push("locator");

        LOCATOR_DURABILITY = BUILDER
                .comment("Maximum durability of the Waystone Locator. Takes effect when the item is registered; restart after changing.")
                .defineInRange("durability", 32, 16, 256);

        SEARCH_RADIUS = BUILDER
                .comment("Maximum distance in blocks searched by the Waystone Locator.")
                .defineInRange("searchRadius", 128, 1, 6666);

        PROXIMITY_THRESHOLD = BUILDER
                .comment("Distance threshold Waystone Locator stops telling the actual distance.")
                .defineInRange("proximityThreshold", 12, 1, 256);

        BREAKABLE_LOCATOR = BUILDER
                .comment("Whether the Waystone Locator can break when its durability is depleted.")
                .define("breakableLocator", true);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }

    private FinderConfig() {
    }
}
