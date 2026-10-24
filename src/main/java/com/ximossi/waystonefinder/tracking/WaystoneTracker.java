package com.ximossi.waystonefinder.tracking;

import net.blay09.mods.waystones.api.Waystone;
import net.blay09.mods.waystones.api.WaystoneTypes;
import net.blay09.mods.waystones.api.WaystonesAPI;
import net.minecraft.server.level.ServerPlayer;

import java.util.Comparator;
import java.util.Optional;

public final class WaystoneTracker {
    private WaystoneTracker() {
    }

    /**
     * Finds the nearest valid, generated, regular Waystone in the player's
     * current dimension that this player has not activated.
     *
     * Waystones maintains the persistent registry and removes entries when
     * Waystones are removed, so this add-on does not need a duplicate SavedData index.
     */
    public static Optional<Integer> findNearestDistance(
            ServerPlayer player,
            int radius
    ) {
        if (player.getServer() == null || radius < 0) {
            return Optional.empty();
        }

        double radiusSquared = (double) radius * radius;

        return WaystonesAPI.getAllWaystones(player.getServer())
                .filter(Waystone::isValid)
                .filter(Waystone::wasGenerated)
                .filter(waystone ->
                        WaystoneTypes.WAYSTONE.equals(waystone.getWaystoneType())
                )
                .filter(waystone ->
                        waystone.getDimension().equals(player.serverLevel().dimension())
                )
                .filter(waystone ->
                        !WaystonesAPI.isWaystoneActivated(player, waystone)
                )
                .map(waystone -> new Candidate(
                        waystone,
                        player.distanceToSqr(waystone.getPos().getCenter())
                ))
                .filter(candidate -> candidate.distanceSquared() <= radiusSquared)
                .min(Comparator.comparingDouble(Candidate::distanceSquared))
                .map(candidate ->
                        (int) Math.round(Math.sqrt(candidate.distanceSquared()))
                );
    }

    private record Candidate(Waystone waystone, double distanceSquared) {
    }
}
