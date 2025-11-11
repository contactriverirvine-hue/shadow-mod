package com.arsenal.shadows.items.weapons;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class WarAxeItem extends BaseWeaponItem {
    public WarAxeItem(Properties properties) {
        super(
                ModTiers.DARKSTEEL,
                8, // Very high damage
                -3.2f, // Very slow
                30.0f, // High stamina cost
                100, // Cooldown (5 seconds)
                "Cleave",
                properties
        );
    }

    @Override
    protected void executeAbility(Level level, Player player, ItemStack stack) {
        Vec3 playerPos = player.position().add(0, 1, 0);

        // Large AoE around player
        AABB searchBox = new AABB(playerPos, playerPos).inflate(3.5);
        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, searchBox,
                entity -> entity != player);

        for (LivingEntity entity : entities) {
            // Deal heavy damage
            entity.hurt(level.damageSources().playerAttack(player), 12.0f);

            // Knockback away from player
            Vec3 knockbackDir = entity.position().subtract(playerPos).normalize();
            entity.knockback(1.2, -knockbackDir.x, -knockbackDir.z);

            // Particles
            if (level instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.SWEEP_ATTACK,
                        entity.getX(), entity.getY() + 1, entity.getZ(),
                        3, 0.3, 0.3, 0.3, 0.0);
            }
        }

        // Play sound
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.PLAYER_ATTACK_KNOCKBACK, SoundSource.PLAYERS, 1.5f, 0.7f);

        // Particles around player
        if (level instanceof ServerLevel serverLevel) {
            for (int i = 0; i < 20; i++) {
                double angle = (i / 20.0) * Math.PI * 2;
                double x = playerPos.x + Math.cos(angle) * 3.0;
                double z = playerPos.z + Math.sin(angle) * 3.0;
                serverLevel.sendParticles(ParticleTypes.CRIT,
                        x, playerPos.y, z,
                        1, 0, 0, 0, 0.0);
            }
        }
    }
}