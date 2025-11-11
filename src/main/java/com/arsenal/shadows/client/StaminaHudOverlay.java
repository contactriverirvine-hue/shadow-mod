package com.arsenal.shadows.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class StaminaHudOverlay implements IGuiOverlay {
    private static final ResourceLocation STAMINA_TEXTURE = new ResourceLocation("arsenalofshadows", "textures/gui/stamina_bar.png");

    @Override
    public void render(net.minecraftforge.client.gui.overlay.ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
        Minecraft mc = Minecraft.getInstance();

        if (mc.player == null || mc.options.hideGui) {
            return;
        }

        // Get stamina data
        float stamina = ClientStaminaData.getStamina();
        float maxStamina = ClientStaminaData.getMaxStamina();
        float percentage = ClientStaminaData.getStaminaPercentage();

        // Position (bottom left, above hotbar)
        int x = 10;
        int y = screenHeight - 60;
        int barWidth = 100;
        int barHeight = 10;

        // Background (dark gray)
        guiGraphics.fill(x, y, x + barWidth, y + barHeight, 0xFF2C2C2C);

        // Stamina bar color based on percentage
        int color;
        if (percentage > 0.6f) {
            color = 0xFF00D4FF; // Cyan (high stamina)
        } else if (percentage > 0.3f) {
            color = 0xFFFFAA00; // Orange (medium stamina)
        } else {
            color = 0xFFFF0000; // Red (low stamina)
        }

        // Filled portion
        int filledWidth = (int) (barWidth * percentage);
        guiGraphics.fill(x, y, x + filledWidth, y + barHeight, color);

        // Border
        guiGraphics.fill(x - 1, y - 1, x + barWidth + 1, y, 0xFFFFFFFF); // Top
        guiGraphics.fill(x - 1, y + barHeight, x + barWidth + 1, y + barHeight + 1, 0xFFFFFFFF); // Bottom
        guiGraphics.fill(x - 1, y, x, y + barHeight, 0xFFFFFFFF); // Left
        guiGraphics.fill(x + barWidth, y, x + barWidth + 1, y + barHeight, 0xFFFFFFFF); // Right

        // Text (stamina value)
        String staminaText = String.format("%.0f/%.0f", stamina, maxStamina);
        guiGraphics.drawString(mc.font, staminaText, x + 2, y + 1, 0xFFFFFFFF, true);

        // Label
        guiGraphics.drawString(mc.font, "Stamina", x, y - 10, 0xFFAAAAAA, true);
    }
}