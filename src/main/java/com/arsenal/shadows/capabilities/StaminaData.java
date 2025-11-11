package com.arsenal.shadows.capabilities;

import net.minecraft.nbt.CompoundTag;

public class StaminaData {
    private float stamina;
    private float maxStamina;
    private long lastRegenTime;
    private static final float REGEN_RATE = 5.0f; // Stamina per second
    private static final float REGEN_DELAY = 2000; // 2 seconds in milliseconds

    public StaminaData() {
        this.maxStamina = 100.0f;
        this.stamina = maxStamina;
        this.lastRegenTime = System.currentTimeMillis();
    }

    public float getStamina() {
        return stamina;
    }

    public float getMaxStamina() {
        return maxStamina;
    }

    public void setStamina(float stamina) {
        this.stamina = Math.max(0, Math.min(stamina, maxStamina));
    }

    public void setMaxStamina(float maxStamina) {
        this.maxStamina = maxStamina;
        if (stamina > maxStamina) {
            stamina = maxStamina;
        }
    }

    public boolean useStamina(float amount) {
        if (stamina >= amount) {
            stamina -= amount;
            lastRegenTime = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public void regenerate() {
        long currentTime = System.currentTimeMillis();
        long timeSinceLastUse = currentTime - lastRegenTime;

        if (timeSinceLastUse >= REGEN_DELAY && stamina < maxStamina) {
            float regenAmount = (REGEN_RATE * (timeSinceLastUse / 1000.0f));
            stamina = Math.min(stamina + regenAmount, maxStamina);
            lastRegenTime = currentTime;
        }
    }

    public void copyFrom(StaminaData source) {
        this.stamina = source.stamina;
        this.maxStamina = source.maxStamina;
        this.lastRegenTime = source.lastRegenTime;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putFloat("stamina", stamina);
        nbt.putFloat("maxStamina", maxStamina);
        nbt.putLong("lastRegenTime", lastRegenTime);
    }

    public void loadNBTData(CompoundTag nbt) {
        stamina = nbt.getFloat("stamina");
        maxStamina = nbt.getFloat("maxStamina");
        lastRegenTime = nbt.getLong("lastRegenTime");
    }
}