package com.arsenal.shadows.items.weapons;

import com.arsenal.shadows.entities.JavelinEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class JavelinItem extends BaseWeaponItem {
    public JavelinItem(Properties properties) {
        super(
                ModTiers.DARKSTEEL,
                5, // Medium damage for melee
                -2.4f, // Medium speed
                25.0f, // Medium-high stamina for throw
                80, // 4 second cooldown
                "Throw Javelin",
                properties
        );
    }

    @Override
    protected void executeAbility(Level level, Player player, ItemStack stack) {
        if (!level.isClientSide) {
            // Create and throw javelin entity
            JavelinEntity javelin = new JavelinEntity(level, player);
            javelin.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 2.5f, 1.0f);

            level.addFreshEntity(javelin);

            // Sound
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.TRIDENT_THROW, SoundSource.PLAYERS, 1.0f, 1.0f);

            // Remove javelin from hand (consume the item)
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
        }
    }
}