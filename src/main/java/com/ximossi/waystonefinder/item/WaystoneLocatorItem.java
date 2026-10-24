package com.ximossi.waystonefinder.item;

import com.ximossi.waystonefinder.config.FinderConfig;
import com.ximossi.waystonefinder.tracking.WaystoneTracker;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;


public final class WaystoneLocatorItem extends Item {
    public WaystoneLocatorItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return FinderConfig.getActive().durability;
    }


    @Override
    public InteractionResultHolder<ItemStack> use(
            Level level,
            Player player,
            InteractionHand hand
    ) {
        level.playSound(
                null,
                player.blockPosition(),
                SoundEvents.CHORUS_FRUIT_TELEPORT,
                SoundSource.PLAYERS,
                0.7F,
                0.8F
        );

        ItemStack stack = player.getItemInHand(hand);

        if (level.isClientSide) {
            return InteractionResultHolder.success(stack);
        }

        if (!(player instanceof ServerPlayer serverPlayer)) {
            return InteractionResultHolder.pass(stack);
        }

        int radius = FinderConfig.getActive().searchRadius;
        int proximity = FinderConfig.getActive().proximityThreshold;
        var nearestDistance = WaystoneTracker.findNearestDistance(serverPlayer, radius);

        if (nearestDistance.isPresent()) {
            if (nearestDistance.get() < proximity) {
                serverPlayer.displayClientMessage(
                        Component.translatable(
                                "message.waystonefinder.nearby"
                        ),
                        true
                );
            } else {
                serverPlayer.displayClientMessage(
                        Component.translatable(
                                "message.waystonefinder.found",
                                nearestDistance.get()
                        ),
                        true
                );
            }
        } else {
            serverPlayer.displayClientMessage(
                    Component.translatable(
                            "message.waystonefinder.not_found"
                    ),
                    true
            );
        }

        EquipmentSlot slot = hand == InteractionHand.MAIN_HAND
                ? EquipmentSlot.MAINHAND
                : EquipmentSlot.OFFHAND;

        if (FinderConfig.getActive().breakableLocator) {
            stack.hurtAndBreak(1, serverPlayer, slot);
        }
        return InteractionResultHolder.success(stack);
    }
}

