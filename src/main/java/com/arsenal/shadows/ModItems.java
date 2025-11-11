package com.arsenal.shadows;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, ArsenalOfShadows.MODID);

    // ===== CRAFTING MATERIALS =====

    // Primary Materials
    public static final RegistryObject<Item> DARKSTEEL_INGOT = ITEMS.register("darksteel_ingot",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> DARKSTEEL_SHARD = ITEMS.register("darksteel_shard",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SHADE_FRAGMENT = ITEMS.register("shade_fragment",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> RUNIC_STONE = ITEMS.register("runic_stone",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> RUNIC_STONE_FRAGMENT = ITEMS.register("runic_stone_fragment",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> CELESTIAL_CRYSTAL = ITEMS.register("celestial_crystal",
            () -> new Item(new Item.Properties()));

    // Fuel & Embers
    public static final RegistryObject<Item> SHADOW_EMBER = ITEMS.register("shadow_ember",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SOLAR_EMBER = ITEMS.register("solar_ember",
            () -> new Item(new Item.Properties()));

    // Cores
    public static final RegistryObject<Item> SOUL_CORE = ITEMS.register("soul_core",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> LEATHER_CORE = ITEMS.register("leather_core",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ICE_SHARD = ITEMS.register("ice_shard",
            () -> new Item(new Item.Properties()));

    // ===== WEAPONS =====

    public static final RegistryObject<Item> PIKE = ITEMS.register("pike",
            () -> new com.arsenal.shadows.items.weapons.PikeItem(new Item.Properties()));

    public static final RegistryObject<Item> CLAYMORE = ITEMS.register("claymore",
            () -> new com.arsenal.shadows.items.weapons.ClaymoreItem(new Item.Properties()));

    public static final RegistryObject<Item> WAR_AXE = ITEMS.register("war_axe",
            () -> new com.arsenal.shadows.items.weapons.WarAxeItem(new Item.Properties()));
}