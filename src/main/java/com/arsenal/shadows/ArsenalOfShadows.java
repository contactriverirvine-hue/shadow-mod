package com.arsenal.shadows;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(ArsenalOfShadows.MODID)
public class ArsenalOfShadows {
    public static final String MODID = "arsenalofshadows";
    private static final Logger LOGGER = LogUtils.getLogger();

    public ArsenalOfShadows() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register deferred registries
        ModItems.ITEMS.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);
        ModCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);

        // Register event handlers
        MinecraftForge.EVENT_BUS.register(this);

        LOGGER.info("Arsenal of Shadows initializing...");
    }
}