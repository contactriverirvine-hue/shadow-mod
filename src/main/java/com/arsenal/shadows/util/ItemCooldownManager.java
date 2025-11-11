package com.arsenal.shadows.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class ItemCooldownManager {

    public static boolean isOnCooldown(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        long cooldownEnd = tag.getLong("cooldownEnd");
        return System.currentTimeMillis() < cooldownEnd;
    }

    public static void setCooldown(ItemStack stack, int ticks) {
        CompoundTag tag = stack.getOrCreateTag();
        // Convert ticks to milliseconds (20 ticks = 1 second)
        long cooldownMs = (ticks * 50);
        tag.putLong("cooldownEnd", System.currentTimeMillis() + cooldownMs);
    }

    public static int getRemainingTicks(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        long cooldownEnd = tag.getLong("cooldownEnd");
        long remaining = cooldownEnd - System.currentTimeMillis();
        if (remaining <= 0) {
            return 0;
        }
        // Convert milliseconds back to ticks
        return (int) (remaining / 50);
    }

    public static float getCooldownPercent(ItemStack stack, int maxTicks) {
        int remaining = getRemainingTicks(stack);
        if (remaining <= 0) {
            return 0.0f;
        }
        return (float) remaining / maxTicks;
    }
}