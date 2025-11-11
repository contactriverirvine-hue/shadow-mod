package com.arsenal.shadows.events;

import com.arsenal.shadows.ArsenalOfShadows;
import com.arsenal.shadows.items.armor.HelmOfDarknessItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ArsenalOfShadows.MODID)
public class ArmorAbilityHandler {

    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();

        // Check if player is sneaking
        if (!player.isShiftKeyDown()) {
            return;
        }

        // Check helmet
        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
        if (helmet.getItem() instanceof HelmOfDarknessItem) {
            HelmOfDarknessItem.activateShadowShroud(player, helmet);
            event.setCanceled(true);
        }
    }
}