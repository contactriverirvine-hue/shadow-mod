package com.arsenal.shadows.items.weapons;

import com.arsenal.shadows.capabilities.StaminaProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

import java.util.List;

public abstract class BaseWeaponItem extends SwordItem {
    protected final float staminaCost;
    protected final int cooldownTicks;
    protected final String abilityName;

    public BaseWeaponItem(Tier tier, int attackDamage, float attackSpeed, float staminaCost, int cooldownTicks, String abilityName, Properties properties) {
        super(tier, attackDamage, attackSpeed, properties);
        this.staminaCost = staminaCost;
        this.cooldownTicks = cooldownTicks;
        this.abilityName = abilityName;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (!level.isClientSide) {
            // Check cooldown
            if (player.getCooldowns().isOnCooldown(this)) {
                return InteractionResultHolder.fail(itemStack);
            }

            // Check stamina
            player.getCapability(StaminaProvider.STAMINA).ifPresent(stamina -> {
                if (stamina.useStamina(staminaCost)) {
                    // Execute ability
                    executeAbility(level, player, itemStack);

                    // Set cooldown
                    player.getCooldowns().addCooldown(this, cooldownTicks);
                } else {
                    player.displayClientMessage(
                            Component.literal("Not enough stamina!").withStyle(ChatFormatting.RED),
                            true
                    );
                }
            });
        }

        return InteractionResultHolder.success(itemStack);
    }

    protected abstract void executeAbility(Level level, Player player, ItemStack stack);

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("Ability: " + abilityName).withStyle(ChatFormatting.GOLD));
        tooltip.add(Component.literal("Stamina Cost: " + (int)staminaCost).withStyle(ChatFormatting.AQUA));
        tooltip.add(Component.literal("Cooldown: " + (cooldownTicks / 20) + "s").withStyle(ChatFormatting.GRAY));
        super.appendHoverText(stack, level, tooltip, flag);
    }
}