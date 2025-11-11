package com.arsenal.shadows.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class StaminaSyncPacket {
    private final float stamina;
    private final float maxStamina;

    public StaminaSyncPacket(float stamina, float maxStamina) {
        this.stamina = stamina;
        this.maxStamina = maxStamina;
    }

    public StaminaSyncPacket(FriendlyByteBuf buf) {
        this.stamina = buf.readFloat();
        this.maxStamina = buf.readFloat();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeFloat(stamina);
        buf.writeFloat(maxStamina);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            // Handle on client side
            com.arsenal.shadows.client.ClientStaminaData.setStamina(stamina);
            com.arsenal.shadows.client.ClientStaminaData.setMaxStamina(maxStamina);
        });
        return true;
    }
}