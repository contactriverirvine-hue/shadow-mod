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

public class IronWarAxeItem extends SwordItem {
    public IronWarAxeItem(Tier tier, Properties properties) {
        super(tier, 7, -3.2f, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("A devastating battle axe.")
                .withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.literal("Upgrade at the Shadowforge")
                .withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.literal("to unlock Cleave!")
                .withStyle(ChatFormatting.GOLD));
        super.appendHoverText(stack, level, tooltip, flag);
    }
}