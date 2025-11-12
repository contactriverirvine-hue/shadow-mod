package com.arsenal.shadows.menu;

import com.arsenal.shadows.blockentity.ShadowforgeBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.SlotItemHandler;

public class ShadowforgeMenu extends AbstractContainerMenu {
    private final ShadowforgeBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;

    public ShadowforgeMenu(int containerId, Inventory inv, FriendlyByteBuf extraData) {
        this(containerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(4));
    }

    public ShadowforgeMenu(int containerId, Inventory inv, BlockEntity entity, ContainerData data) {
        super(ModMenuTypes.SHADOWFORGE_MENU.get(), containerId);
        checkContainerSize(inv, 3);
        this.blockEntity = (ShadowforgeBlockEntity) entity;
        this.level = inv.player.level();
        this.data = data;

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        // Slot 0: Input weapon to upgrade
        this.addSlot(new SlotItemHandler(blockEntity.getItemHandler(), 0, 56, 35));

        // Slot 1: Upgrade material (Darksteel, Runic Stone, etc.)
        this.addSlot(new SlotItemHandler(blockEntity.getItemHandler(), 1, 116, 35));

        // Slot 2: Fuel (Shadow Ember, Coal, etc.)
        this.addSlot(new SlotItemHandler(blockEntity.getItemHandler(), 2, 86, 60));

        addDataSlots(data);
    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }

    public int getScaledProgress() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);
        int progressArrowSize = 24; // Size of arrow in pixels

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    public int getScaledFuelTime() {
        int fuelTime = this.data.get(2);
        int maxFuelTime = this.data.get(3);
        int fuelBarSize = 14; // Size of fuel bar in pixels

        return maxFuelTime != 0 ? fuelTime * fuelBarSize / maxFuelTime : 0;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasItem()) {
            ItemStack originalStack = slot.getItem();
            newStack = originalStack.copy();

            if (index < 36) {
                // Moving from player inventory to machine
                if (!this.moveItemStackTo(originalStack, 36, this.slots.size(), false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                // Moving from machine to player inventory
                if (!this.moveItemStackTo(originalStack, 0, 36, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (originalStack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return newStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player,
                com.arsenal.shadows.ModBlocks.SHADOWFORGE.get());
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    public ShadowforgeBlockEntity getBlockEntity() {
        return this.blockEntity;
    }
}