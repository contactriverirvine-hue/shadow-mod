package com.arsenal.shadows.items.armor;

import com.arsenal.shadows.capabilities.StaminaProvider;
import com.arsenal.shadows.util.ItemCooldownManager;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;

public class HelmOfDarknessItem extends ArmorItem {
    private static final float STAMINA_COST = 40.0f;
    private static final int COOLDOWN_TICKS = 200; // 10 seconds
    private static final int INVULNERABILITY_TICKS = 20; // 1 second

    public HelmOfDarknessItem(Properties properties) {
        super(ModArmorMaterials.DARKSTEEL, Type.HELMET, properties);
    }

    public static void activateShadowShroud(Player player, ItemStack helmet) {
        Level level = player.level();

        if (level.isClientSide) return;

        // Check cooldown
        if (ItemCooldownManager.isOnCooldown(helmet)) {
            int remaining = ItemCooldownManager.getRemainingTicks(helmet) / 20;
            player.displayClientMessage(
                    Component.literal("Shadow Shroud cooldown: " + remaining + "s").withStyle(ChatFormatting.GRAY),
                    true
            );
            return;
        }

        // Check stamina
        player.getCapability(StaminaProvider.STAMINA).ifPresent(stamina -> {
            if (!stamina.useStamina(STAMINA_COST)) {
                player.displayClientMessage(
                        Component.literal("Not enough stamina!").withStyle(ChatFormatting.RED),
                        true
                );
                return;
            }

            // Store original position
            Vec3 originalPos = player.position();

            // Make invulnerable briefly
            player.invulnerableTime = INVULNERABILITY_TICKS;

            // Teleport forward
            Vec3 lookVec = player.getLookAngle();
            Vec3 teleportPos = originalPos.add(lookVec.scale(8.0));
            player.teleportTo(teleportPos.x, teleportPos.y, teleportPos.z);

            // Particles at old position
            if (level instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.SMOKE,
                        originalPos.x, originalPos.y + 1, originalPos.z,
                        30, 0.5, 1.0, 0.5, 0.1);
                serverLevel.sendParticles(ParticleTypes.PORTAL,
                        originalPos.x, originalPos.y + 1, originalPos.z,
                        50, 0.5, 1.0, 0.5, 0.5);
            }

            // Particles at new position
            if (level instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.SMOKE,
                        teleportPos.x, teleportPos.y + 1, teleportPos.z,
                        30, 0.5, 1.0, 0.5, 0.1);
                serverLevel.sendParticles(ParticleTypes.PORTAL,
                        teleportPos.x, teleportPos.y + 1, teleportPos.z,
                        50, 0.5, 1.0, 0.5, 0.5);
            }

            // Sound effects
            level.playSound(null, originalPos.x, originalPos.y, originalPos.z,
                    SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.0f, 1.0f);
            level.playSound(null, teleportPos.x, teleportPos.y, teleportPos.z,
                    SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.0f, 1.2f);

            // Set cooldown
            ItemCooldownManager.setCooldown(helmet, COOLDOWN_TICKS);

            player.displayClientMessage(
                    Component.literal("SHADOW SHROUD!").withStyle(ChatFormatting.DARK_PURPLE),
                    true
            );
        });
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("Ability: Shadow Shroud").withStyle(ChatFormatting.GOLD));
        tooltip.add(Component.literal("Sneak + Right-Click to activate").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.literal("Teleport forward 8 blocks").withStyle(ChatFormatting.AQUA));
        tooltip.add(Component.literal("1 second invulnerability").withStyle(ChatFormatting.AQUA));
        tooltip.add(Component.literal("Stamina Cost: 40").withStyle(ChatFormatting.AQUA));
        tooltip.add(Component.literal("Cooldown: 10s").withStyle(ChatFormatting.GRAY));
        super.appendHoverText(stack, level, tooltip, flag);
    }
}