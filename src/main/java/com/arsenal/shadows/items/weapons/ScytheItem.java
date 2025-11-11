package com.arsenal.shadows.items.weapons;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class ScytheItem extends BaseWeaponItem {
    public ScytheItem(Properties properties) {
        super(
                ModTiers.DARKSTEEL,
                6, // Medium damage
                -2.5f, // Medium speed
                20.0f, // Medium stamina
                60, // 3 second cooldown
                "Soul Reap",
                properties
        );
    }

    @Override
    protected void executeAbility(Level level, Player player, ItemStack stack) {
        Vec3 playerPos = player.position().add(0, 1, 0);
        Vec3 lookVec = player.getLookAngle();

        // Wide sweeping arc
        AABB searchBox = new AABB(playerPos, playerPos).inflate(4.0);
        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, searchBox,
                entity -> entity != player);

        int undeadKilled = 0;

        for (LivingEntity entity : entities) {
            Vec3 toEntity = entity.position().subtract(playerPos).normalize();
            double dot = lookVec.dot(toEntity);

            // Hit if in front (wide arc)
            if (dot > -0.7) {
                float damage = 8.0f;

                // Bonus damage vs undead
                if (entity.getMobType() == MobType.UNDEAD) {
                    damage = 14.0f;
                    undeadKilled++;

                    // Dark particles for undead
                    if (level instanceof ServerLevel serverLevel) {
                        serverLevel.sendParticles(ParticleTypes.SOUL,
                                entity.getX(), entity.getY() + 1, entity.getZ(),
                                10, 0.3, 0.3, 0.3, 0.05);
                    }
                }

                entity.hurt(level.damageSources().playerAttack(player), damage);

                // Sweep effect
                if (level instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(ParticleTypes.SWEEP_ATTACK,
                            entity.getX(), entity.getY() + 1, entity.getZ(),
                            2, 0.3, 0.3, 0.3, 0.0);
                }
            }
        }

        // Play sound
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 1.0f, 0.8f);

        if (undeadKilled > 0) {
            player.displayClientMessage(
                    net.minecraft.network.chat.Component.literal("+" + undeadKilled + " Undead Souls Reaped!").withStyle(net.minecraft.ChatFormatting.DARK_PURPLE),
                    true
            );
        }
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        // Bonus damage to undead on normal hits
        if (target.getMobType() == MobType.UNDEAD) {
            target.hurt(attacker.level().damageSources().magic(), 2.0f);
        }
        return super.hurtEnemy(stack, target, attacker);
    }
}