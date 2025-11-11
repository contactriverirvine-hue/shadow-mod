package com.arsenal.shadows.util;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class AbilityEffects {

    public static void spawnStaminaUseParticles(Player player) {
        if (player.level() instanceof ServerLevel serverLevel) {
            Vec3 pos = player.position().add(0, 1, 0);

            // Cyan particles for stamina use
            for (int i = 0; i < 10; i++) {
                double offsetX = (Math.random() - 0.5) * 0.5;
                double offsetY = (Math.random() - 0.5) * 0.5;
                double offsetZ = (Math.random() - 0.5) * 0.5;

                serverLevel.sendParticles(
                        ParticleTypes.ELECTRIC_SPARK,
                        pos.x + offsetX, pos.y + offsetY, pos.z + offsetZ,
                        1, 0, 0, 0, 0.0
                );
            }
        }
    }

    public static void spawnAbilityActivationParticles(Player player, ParticleOptions particleType) {
        if (player.level() instanceof ServerLevel serverLevel) {
            Vec3 pos = player.position().add(0, 1, 0);

            // Circle of particles
            for (int i = 0; i < 16; i++) {
                double angle = (i / 16.0) * Math.PI * 2;
                double x = pos.x + Math.cos(angle) * 1.5;
                double z = pos.z + Math.sin(angle) * 1.5;

                serverLevel.sendParticles(
                        particleType,
                        x, pos.y, z,
                        1, 0, 0.2, 0, 0.05
                );
            }
        }
    }

    public static void spawnCriticalHitParticles(Player player, Vec3 targetPos) {
        if (player.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
                    ParticleTypes.CRIT,
                    targetPos.x, targetPos.y + 1, targetPos.z,
                    20, 0.3, 0.3, 0.3, 0.1
            );

            serverLevel.sendParticles(
                    ParticleTypes.ENCHANTED_HIT,
                    targetPos.x, targetPos.y + 1, targetPos.z,
                    15, 0.3, 0.3, 0.3, 0.1
            );
        }
    }
}