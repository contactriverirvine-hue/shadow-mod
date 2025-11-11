package com.arsenal.shadows;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ArsenalOfShadows.MODID);

    public static final RegistryObject<CreativeModeTab> ARSENAL_TAB = CREATIVE_MODE_TABS.register("arsenal_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.DARKSTEEL_INGOT.get()))
                    .title(Component.translatable("itemGroup.arsenalofshadows"))
                    .displayItems((parameters, output) -> {
                        // Materials
                        output.accept(ModItems.DARKSTEEL_INGOT.get());
                        output.accept(ModItems.DARKSTEEL_SHARD.get());
                        output.accept(ModItems.SHADE_FRAGMENT.get());
                        output.accept(ModItems.RUNIC_STONE.get());
                        output.accept(ModItems.RUNIC_STONE_FRAGMENT.get());
                        output.accept(ModItems.CELESTIAL_CRYSTAL.get());
                        output.accept(ModItems.SHADOW_EMBER.get());
                        output.accept(ModItems.SOLAR_EMBER.get());
                        output.accept(ModItems.SOUL_CORE.get());
                        output.accept(ModItems.LEATHER_CORE.get());
                        output.accept(ModItems.ICE_SHARD.get());

                        // Blocks
                        output.accept(ModBlocks.SHADOWFORGE.get());

                        // Weapons
                        output.accept(ModItems.PIKE.get());
                        output.accept(ModItems.CLAYMORE.get());
                        output.accept(ModItems.WAR_AXE.get());
                    })
                    .build());
}