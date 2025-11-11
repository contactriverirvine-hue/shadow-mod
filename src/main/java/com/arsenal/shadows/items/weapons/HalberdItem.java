package com.arsenal.shadows.items.weapons;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class HalberdItem extends BaseWeaponItem {
    public HalberdItem(Properties properties) {
        super(
                ModTiers.DARKSTEEL,
                7, // High damage
                -2.9f, // Slow
                25.0f, // Medium-high stamina
                70, // 3.5 second cooldown
                "Hook Strike",
                properties
        );
    }

    @Override
    protected void executeAbility(Level level, Player player, ItemStack stack) {
        Vec3 playerPos = player.position().add(0, 1, 0);
        Vec3 lookVec = player.getLookAngle();

        // Long reach in front of player
        Vec3 targetPos = playerPos.add(lookVec.scale(5.0));
        AABB searchBox = new AABB(targetPos, targetPos).inflate(2.0);

        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, searchBox,
                entity -> entity != player && player.hasLineOfSight(entity));

        for (LivingEntity entity : entities) {
            // Deal damage
            entity.hurt(level.damageSources().playerAttack(player), 10.0f);

            // Hook and pull entity toward player
            Vec3 pullVector = playerPos.subtract(entity.position()).normalize().scale(1.5);
            entity.setDeltaMovement(pullVector);
            entity.hurtMarked = true;

            // Play sound
            level.playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                    SoundEvents.FISHING_BOBBER_RETRIEVE, SoundSource.PLAYERS, 1.0f, 0.8f);
        }

        if (!entities.isEmpty()) {
            player.displayClientMessage(
                    net.minecraft.network.chat.Component.literal("Hooked!").withStyle(net.minecraft.ChatFormatting.GREEN),
                    true
            );
        }
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        // Halberd has extended reach bonus
        return super.hurtEnemy(stack, target, attacker);
    }
}