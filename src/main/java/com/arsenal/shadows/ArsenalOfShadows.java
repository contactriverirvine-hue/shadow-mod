package com.arsenal.shadows;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
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
        com.arsenal.shadows.entities.ModEntities.ENTITIES.register(modEventBus);
        com.arsenal.shadows.blockentity.ModBlockEntities.BLOCK_ENTITIES.register(modEventBus); // ADD THIS LINE!
        com.arsenal.shadows.menu.ModMenuTypes.MENUS.register(modEventBus);

        // Register event handlers
        MinecraftForge.EVENT_BUS.register(this);

        // Register common setup
        modEventBus.addListener(this::commonSetup);

        // Register network
        com.arsenal.shadows.network.ModNetwork.register();

        LOGGER.info("Arsenal of Shadows initializing...");
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            // Register Shadowforge recipes
            com.arsenal.shadows.recipe.ShadowforgeRecipe.registerRecipes();
            LOGGER.info("Shadowforge recipes registered!");
        });
    }
}