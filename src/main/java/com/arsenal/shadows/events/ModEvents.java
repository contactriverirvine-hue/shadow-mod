package com.arsenal.shadows.events;

import com.arsenal.shadows.ArsenalOfShadows;
import com.arsenal.shadows.capabilities.StaminaData;
import com.arsenal.shadows.capabilities.StaminaProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ArsenalOfShadows.MODID)
public class ModEvents {

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            if (!event.getObject().getCapability(StaminaProvider.STAMINA).isPresent()) {
                event.addCapability(new ResourceLocation(ArsenalOfShadows.MODID, "stamina"),
                        new StaminaProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            event.getOriginal().getCapability(StaminaProvider.STAMINA).ifPresent(oldStore -> {
                event.getEntity().getCapability(StaminaProvider.STAMINA).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && !event.player.level().isClientSide) {
            event.player.getCapability(StaminaProvider.STAMINA).ifPresent(StaminaData::regenerate);
        }
    }
}