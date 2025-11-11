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

public class PikeItem extends BaseWeaponItem {
    public PikeItem(Properties properties) {
        super(
                ModTiers.DARKSTEEL,
                5, // Attack damage
                -2.8f, // Attack speed
                20.0f, // Stamina cost
                60, // Cooldown (3 seconds)
                "Lunge",
                properties
        );
    }

    @Override
    protected void executeAbility(Level level, Player player, ItemStack stack) {
        // Lunge forward
        Vec3 lookVec = player.getLookAngle();
        Vec3 motion = lookVec.scale(1.5); // Lunge distance

        player.setDeltaMovement(motion.x, 0.3, motion.z);
        player.hurtMarked = true;

        // Play sound
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 1.0f, 1.0f);

        // Damage entities in path
        Vec3 start = player.position();
        Vec3 end = start.add(lookVec.scale(3.0));

        AABB searchBox = new AABB(start, end).inflate(1.0);
        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, searchBox,
                entity -> entity != player && player.hasLineOfSight(entity));

        for (LivingEntity entity : entities) {
            entity.hurt(level.damageSources().playerAttack(player), 8.0f);
            entity.knockback(0.5, -lookVec.x, -lookVec.z);
        }
    }
}