package com.arsenal.shadows.capabilities;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class StaminaProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<StaminaData> STAMINA = CapabilityManager.get(new CapabilityToken<>() {});

    private StaminaData stamina = null;
    private final LazyOptional<StaminaData> optional = LazyOptional.of(this::createStamina);

    private StaminaData createStamina() {
        if (stamina == null) {
            stamina = new StaminaData();
        }
        return stamina;
    }

    @Override
    public @Nonnull <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == STAMINA) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createStamina().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createStamina().loadNBTData(nbt);
    }
}