package com.arsenal.shadows.items.weapons;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class IronPikeItem extends SwordItem {
    public IronPikeItem(Tier tier, Properties properties) {
        super(tier, 4, -2.8f, properties); // Same stats as before but no ability
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("A basic pike. Upgrade at the Shadowforge")
                .withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.literal("to unlock special abilities!")
                .withStyle(ChatFormatting.GOLD));
        super.appendHoverText(stack, level, tooltip, flag);
    }
}