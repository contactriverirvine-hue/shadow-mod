package com.arsenal.shadows.client;

public class ClientStaminaData {
    private static float stamina = 100.0f;
    private static float maxStamina = 100.0f;

    public static float getStamina() {
        return stamina;
    }

    public static void setStamina(float value) {
        stamina = value;
    }

    public static float getMaxStamina() {
        return maxStamina;
    }

    public static void setMaxStamina(float value) {
        maxStamina = value;
    }

    public static float getStaminaPercentage() {
        return maxStamina > 0 ? stamina / maxStamina : 0;
    }
}