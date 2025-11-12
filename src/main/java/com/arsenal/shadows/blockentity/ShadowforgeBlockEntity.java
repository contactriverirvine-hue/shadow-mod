package com.arsenal.shadows.blockentity;

import com.arsenal.shadows.blocks.ShadowforgeBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ShadowforgeBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (level != null && !level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return switch (slot) {
                case 0 -> true; // Input slot - weapon to upgrade
                case 1 -> isUpgradeMaterial(stack); // Material slot - Darksteel/Runic
                case 2 -> isFuel(stack); // Fuel slot - Shadow Ember/Coal
                default -> false;
            };
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    private int progress = 0;
    private int maxProgress = 100; // 5 seconds at 20 ticks/sec
    private int fuelTime = 0;
    private int maxFuelTime = 0;

    // ContainerData for syncing to client
    private final net.minecraft.world.inventory.ContainerData data = new net.minecraft.world.inventory.ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> ShadowforgeBlockEntity.this.progress;
                case 1 -> ShadowforgeBlockEntity.this.maxProgress;
                case 2 -> ShadowforgeBlockEntity.this.fuelTime;
                case 3 -> ShadowforgeBlockEntity.this.maxFuelTime;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> ShadowforgeBlockEntity.this.progress = value;
                case 1 -> ShadowforgeBlockEntity.this.maxProgress = value;
                case 2 -> ShadowforgeBlockEntity.this.fuelTime = value;
                case 3 -> ShadowforgeBlockEntity.this.maxFuelTime = value;
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    };

    public ShadowforgeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SHADOWFORGE_BE.get(), pos, state);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Shadowforge");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new com.arsenal.shadows.menu.ShadowforgeMenu(containerId, playerInventory, this, this.data);
    }

    @Override
    public @Nonnull <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.put("inventory", itemHandler.serializeNBT());
        tag.putInt("shadowforge.progress", progress);
        tag.putInt("shadowforge.fuelTime", fuelTime);
        tag.putInt("shadowforge.maxFuelTime", maxFuelTime);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        itemHandler.deserializeNBT(tag.getCompound("inventory"));
        progress = tag.getInt("shadowforge.progress");
        fuelTime = tag.getInt("shadowforge.fuelTime");
        maxFuelTime = tag.getInt("shadowforge.maxFuelTime");
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        saveAdditional(tag);
        return tag;
    }

    @Override
    public net.minecraft.network.protocol.Packet<net.minecraft.network.protocol.game.ClientGamePacketListener> getUpdatePacket() {
        return net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket.create(this);
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, ShadowforgeBlockEntity entity) {
        if (level.isClientSide()) {
            return;
        }

        boolean hasRecipe = hasRecipe(entity);
        boolean isBurning = entity.fuelTime > 0;

        if (hasRecipe) {
            if (!isBurning && hasFuel(entity)) {
                // Consume fuel
                consumeFuel(entity);
                setChanged(level, pos, state);
            }

            if (isBurning) {
                entity.progress++;
                entity.fuelTime--;

                if (entity.progress >= entity.maxProgress) {
                    // Craft the item
                    craftItem(entity);
                    entity.progress = 0;
                }
                setChanged(level, pos, state);
            }
        } else {
            entity.progress = 0;
            if (isBurning) {
                entity.fuelTime--;
            }
            setChanged(level, pos, state);
        }

        // Update block state for visual effects
        if (isBurning != state.getValue(ShadowforgeBlock.LIT)) {
            level.setBlock(pos, state.setValue(ShadowforgeBlock.LIT, isBurning), 3);
        }
    }

    private static boolean hasRecipe(ShadowforgeBlockEntity entity) {
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        return com.arsenal.shadows.recipe.ShadowforgeRecipe.getRecipe(inventory, entity.level).isPresent();
    }

    private static boolean hasFuel(ShadowforgeBlockEntity entity) {
        return !entity.itemHandler.getStackInSlot(2).isEmpty();
    }

    private static void consumeFuel(ShadowforgeBlockEntity entity) {
        ItemStack fuel = entity.itemHandler.getStackInSlot(2);
        entity.maxFuelTime = getFuelTime(fuel);
        entity.fuelTime = entity.maxFuelTime;
        fuel.shrink(1);
    }

    private static int getFuelTime(ItemStack stack) {
        // Shadow Ember: 200 ticks (10 seconds)
        // Coal/Charcoal: 100 ticks (5 seconds)
        if (stack.getItem() == com.arsenal.shadows.ModItems.SHADOW_EMBER.get()) {
            return 200;
        } else if (stack.getItem() == net.minecraft.world.item.Items.COAL ||
                stack.getItem() == net.minecraft.world.item.Items.CHARCOAL) {
            return 100;
        }
        return 0;
    }

    private static void craftItem(ShadowforgeBlockEntity entity) {
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        java.util.Optional<com.arsenal.shadows.recipe.ShadowforgeRecipe> recipe =
                com.arsenal.shadows.recipe.ShadowforgeRecipe.getRecipe(inventory, entity.level);

        if (recipe.isPresent()) {
            // Get input count before consuming
            int inputCount = entity.itemHandler.getStackInSlot(0).getCount();

            // Consume input and material
            entity.itemHandler.extractItem(0, 1, false);
            entity.itemHandler.extractItem(1, recipe.get().getMaterialCount(), false);

            // Output result (same count as input had)
            entity.itemHandler.setStackInSlot(0, new ItemStack(recipe.get().getResultItem(null).getItem(), inputCount));
        }
    }

    private static boolean isUpgradeMaterial(ItemStack stack) {
        return stack.getItem() == com.arsenal.shadows.ModItems.DARKSTEEL_INGOT.get() ||
                stack.getItem() == com.arsenal.shadows.ModItems.RUNIC_STONE.get() ||
                stack.getItem() == com.arsenal.shadows.ModItems.CELESTIAL_CRYSTAL.get();
    }

    private static boolean isFuel(ItemStack stack) {
        return stack.getItem() == com.arsenal.shadows.ModItems.SHADOW_EMBER.get() ||
                stack.getItem() == com.arsenal.shadows.ModItems.SOLAR_EMBER.get() ||
                stack.getItem() == net.minecraft.world.item.Items.COAL ||
                stack.getItem() == net.minecraft.world.item.Items.CHARCOAL;
    }

    public ItemStackHandler getItemHandler() {
        return itemHandler;
    }

    public int getProgress() {
        return progress;
    }

    public int getMaxProgress() {
        return maxProgress;
    }

    public int getFuelTime() {
        return fuelTime;
    }

    public int getMaxFuelTime() {
        return maxFuelTime;
    }
}