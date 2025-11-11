package com.arsenal.shadows.items.weapons;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class RapierItem extends BaseWeaponItem {
    private static final int RIPOSTE_WINDOW_TICKS = 20; // 1 second window

    public RapierItem(Properties properties) {
        super(
                ModTiers.DARKSTEEL,
                4, // Lower base damage
                -1.8f, // Very fast attack speed
                15.0f, // Low stamina cost
                40, // Short cooldown (2 seconds)
                "Riposte",
                properties
        );
    }

    @Override
    protected void executeAbility(Level level, Player player, ItemStack stack) {
        // Activate riposte stance
        CompoundTag tag = stack.getOrCreateTag();
        tag.putLong("riposteActive", level.getGameTime());

        // Play sound
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.SHIELD_BLOCK, SoundSource.PLAYERS, 1.0f, 1.5f);

        player.displayClientMessage(
                net.minecraft.network.chat.Component.literal("Riposte Ready!").withStyle(net.minecraft.ChatFormatting.YELLOW),
                true
        );
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof Player player && !player.level().isClientSide) {
            CompoundTag tag = stack.getOrCreateTag();
            long riposteTime = tag.getLong("riposteActive");
            long currentTime = player.level().getGameTime();

            // Check if riposte is active
            if (riposteTime > 0 && (currentTime - riposteTime) <= RIPOSTE_WINDOW_TICKS) {
                // Critical hit!
                target.hurt(player.level().damageSources().playerAttack(player), 12.0f);
                target.knockback(1.0, -player.getLookAngle().x, -player.getLookAngle().z);

                // Effects
                player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.PLAYER_ATTACK_CRIT, SoundSource.PLAYERS, 1.0f, 1.5f);

                player.displayClientMessage(
                        net.minecraft.network.chat.Component.literal("RIPOSTE!").withStyle(net.minecraft.ChatFormatting.GOLD),
                        true
                );

                // Clear riposte
                tag.putLong("riposteActive", 0);
                return super.hurtEnemy(stack, target, attacker);
            }
        }
        return super.hurtEnemy(stack, target, attacker);
    }
}