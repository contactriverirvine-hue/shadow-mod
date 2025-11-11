package com.arsenal.shadows.items.weapons;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;

public class ClaymoreItem extends BaseWeaponItem {
    private static final int MAX_MOMENTUM_STACKS = 5;
    private static final float DAMAGE_PER_STACK = 2.0f;

    public ClaymoreItem(Properties properties) {
        super(
                ModTiers.DARKSTEEL,
                7, // High base damage
                -3.0f, // Slow attack speed
                25.0f, // Stamina cost
                80, // Cooldown (4 seconds)
                "Momentum Slash",
                properties
        );
    }

    @Override
    protected void executeAbility(Level level, Player player, ItemStack stack) {
        // Get momentum stacks
        CompoundTag tag = stack.getOrCreateTag();
        int momentum = Math.min(tag.getInt("momentum"), MAX_MOMENTUM_STACKS);

        // Calculate damage
        float totalDamage = 10.0f + (momentum * DAMAGE_PER_STACK);

        // Wide arc slash
        Vec3 lookVec = player.getLookAngle();
        Vec3 playerPos = player.position().add(0, 1, 0);

        AABB searchBox = new AABB(playerPos, playerPos).inflate(4.0);
        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, searchBox,
                entity -> entity != player);

        int hitCount = 0;
        for (LivingEntity entity : entities) {
            Vec3 toEntity = entity.position().subtract(playerPos).normalize();
            double dot = lookVec.dot(toEntity);

            // Hit if in front (120 degree arc)
            if (dot > -0.5) {
                entity.hurt(level.damageSources().playerAttack(player), totalDamage);
                entity.knockback(0.8 + (momentum * 0.1), -lookVec.x, -lookVec.z);
                hitCount++;
            }
        }

        // Play sound
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.PLAYER_ATTACK_STRONG, SoundSource.PLAYERS, 1.0f, 0.8f);

        // Show momentum message
        if (momentum > 0) {
            player.displayClientMessage(
                    Component.literal("Momentum Slash! (x" + momentum + " stacks) - " + hitCount + " enemies hit!")
                            .withStyle(ChatFormatting.GOLD),
                    true
            );
        }

        // Reset momentum stacks
        tag.putInt("momentum", 0);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        // Build momentum on normal attacks (left-click)
        CompoundTag tag = stack.getOrCreateTag();
        int momentum = tag.getInt("momentum");
        if (momentum < MAX_MOMENTUM_STACKS) {
            tag.putInt("momentum", momentum + 1);

            // Show stack gain message
            if (attacker instanceof Player player) {
                player.displayClientMessage(
                        Component.literal("Momentum: " + (momentum + 1) + "/" + MAX_MOMENTUM_STACKS)
                                .withStyle(ChatFormatting.YELLOW),
                        true
                );
            }
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        // Show current momentum stacks
        CompoundTag tag = stack.getOrCreateTag();
        int momentum = tag.getInt("momentum");
        if (momentum > 0) {
            tooltip.add(Component.literal("Momentum Stacks: " + momentum + "/" + MAX_MOMENTUM_STACKS)
                    .withStyle(ChatFormatting.GOLD));
        }
    }
}