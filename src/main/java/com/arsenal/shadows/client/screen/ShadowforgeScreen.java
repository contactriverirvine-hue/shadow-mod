package com.arsenal.shadows.client.screen;

import com.arsenal.shadows.ArsenalOfShadows;
import com.arsenal.shadows.menu.ShadowforgeMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ShadowforgeScreen extends AbstractContainerScreen<ShadowforgeMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(ArsenalOfShadows.MODID, "textures/gui/shadowforge_gui.png");

    public ShadowforgeScreen(ShadowforgeMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
        this.imageHeight = 166;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        // Render progress arrow
        if (menu.isCrafting()) {
            guiGraphics.blit(TEXTURE, x + 86, y + 35, 176, 0, menu.getScaledProgress(), 16);
        }

        // Render fuel flame
        if (menu.getScaledFuelTime() > 0) {
            guiGraphics.blit(TEXTURE, x + 86, y + 60 + 14 - menu.getScaledFuelTime(),
                    176, 14 - menu.getScaledFuelTime(), 14, menu.getScaledFuelTime());
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }
}