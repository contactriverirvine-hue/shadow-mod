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

public class IronClaymoreItem extends SwordItem {
    public IronClaymoreItem(Tier tier, Properties properties) {
        super(tier, 6, -3.0f, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("A heavy two-handed sword.")
                .withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.literal("Upgrade at the Shadowforge")
                .withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.literal("to unlock Momentum Slash!")
                .withStyle(ChatFormatting.GOLD));
        super.appendHoverText(stack, level, tooltip, flag);
    }
}