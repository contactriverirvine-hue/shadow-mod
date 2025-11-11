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

public class FlailItem extends BaseWeaponItem {
    public FlailItem(Properties properties) {
        super(
                ModTiers.DARKSTEEL,
                6, // Medium-high damage
                -2.6f, // Slow but not too slow
                20.0f, // Medium stamina cost
                60, // 3 second cooldown
                "Chain Swing",
                properties
        );
    }

    @Override
    protected void executeAbility(Level level, Player player, ItemStack stack) {
        // Swing in a wide arc around player
        Vec3 playerPos = player.position().add(0, 1, 0);
        float playerYaw = player.getYRot();

        // Create a spinning hit area in front and around the player
        AABB searchBox = new AABB(playerPos, playerPos).inflate(3.0);
        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, searchBox,
                entity -> entity != player);

        for (LivingEntity entity : entities) {
            // Hit entity and apply knockback
            entity.hurt(level.damageSources().playerAttack(player), 9.0f);

            // Calculate knockback direction (away from player)
            Vec3 knockbackDir = entity.position().subtract(playerPos).normalize();
            entity.setDeltaMovement(entity.getDeltaMovement().add(knockbackDir.scale(0.8)));
            entity.hurtMarked = true;
        }

        // Sound effect
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.CHAIN_BREAK, SoundSource.PLAYERS, 1.0f, 0.8f);

        // Visual spinning effect with particles
        if (level instanceof net.minecraft.server.level.ServerLevel serverLevel) {
            for (int i = 0; i < 20; i++) {
                double angle = (i / 20.0) * Math.PI * 2;
                double x = playerPos.x + Math.cos(angle) * 3.0;
                double z = playerPos.z + Math.sin(angle) * 3.0;
                serverLevel.sendParticles(
                        net.minecraft.core.particles.ParticleTypes.SWEEP_ATTACK,
                        x, playerPos.y, z,
                        1, 0, 0, 0, 0.0
                );
            }
        }
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        // Flail has increased knockback on normal hits
        if (attacker instanceof Player player) {
            Vec3 knockbackDir = target.position().subtract(player.position()).normalize();
            target.knockback(0.7, -knockbackDir.x, -knockbackDir.z);
        }
        return super.hurtEnemy(stack, target, attacker);
    }
}