package com.arsenal.shadows;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
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

    // ===== TIER 1 WEAPONS (IRON - NO ABILITIES) =====

    public static final RegistryObject<Item> IRON_PIKE = ITEMS.register("iron_pike",
            () -> new com.arsenal.shadows.items.weapons.IronPikeItem(Tiers.IRON, new Item.Properties()));

    public static final RegistryObject<Item> IRON_CLAYMORE = ITEMS.register("iron_claymore",
            () -> new com.arsenal.shadows.items.weapons.IronClaymoreItem(Tiers.IRON, new Item.Properties()));

    public static final RegistryObject<Item> IRON_WAR_AXE = ITEMS.register("iron_war_axe",
            () -> new com.arsenal.shadows.items.weapons.IronWarAxeItem(Tiers.IRON, new Item.Properties()));

    public static final RegistryObject<Item> IRON_RAPIER = ITEMS.register("iron_rapier",
            () -> new com.arsenal.shadows.items.weapons.IronRapierItem(Tiers.IRON, new Item.Properties()));

    public static final RegistryObject<Item> IRON_FLAIL = ITEMS.register("iron_flail",
            () -> new com.arsenal.shadows.items.weapons.IronFlailItem(Tiers.IRON, new Item.Properties()));

    public static final RegistryObject<Item> IRON_JAVELIN = ITEMS.register("iron_javelin",
            () -> new com.arsenal.shadows.items.weapons.IronJavelinItem(Tiers.IRON, new Item.Properties()));

    public static final RegistryObject<Item> IRON_HALBERD = ITEMS.register("iron_halberd",
            () -> new com.arsenal.shadows.items.weapons.IronHalberdItem(Tiers.IRON, new Item.Properties()));

    public static final RegistryObject<Item> IRON_SCYTHE = ITEMS.register("iron_scythe",
            () -> new com.arsenal.shadows.items.weapons.IronScytheItem(Tiers.IRON, new Item.Properties()));

    // ===== TIER 2 WEAPONS (DARKSTEEL - WITH ABILITIES) =====

    public static final RegistryObject<Item> PIKE = ITEMS.register("pike",
            () -> new com.arsenal.shadows.items.weapons.PikeItem(new Item.Properties()));

    public static final RegistryObject<Item> CLAYMORE = ITEMS.register("claymore",
            () -> new com.arsenal.shadows.items.weapons.ClaymoreItem(new Item.Properties()));

    public static final RegistryObject<Item> WAR_AXE = ITEMS.register("war_axe",
            () -> new com.arsenal.shadows.items.weapons.WarAxeItem(new Item.Properties()));

    public static final RegistryObject<Item> RAPIER = ITEMS.register("rapier",
            () -> new com.arsenal.shadows.items.weapons.RapierItem(new Item.Properties()));

    public static final RegistryObject<Item> FLAIL = ITEMS.register("flail",
            () -> new com.arsenal.shadows.items.weapons.FlailItem(new Item.Properties()));

    public static final RegistryObject<Item> JAVELIN = ITEMS.register("javelin",
            () -> new com.arsenal.shadows.items.weapons.JavelinItem(new Item.Properties()));

    public static final RegistryObject<Item> HALBERD = ITEMS.register("halberd",
            () -> new com.arsenal.shadows.items.weapons.HalberdItem(new Item.Properties()));

    public static final RegistryObject<Item> SCYTHE = ITEMS.register("scythe",
            () -> new com.arsenal.shadows.items.weapons.ScytheItem(new Item.Properties()));

    // ===== ARMOR =====

    public static final RegistryObject<Item> HELM_OF_DARKNESS = ITEMS.register("helm_of_darkness",
            () -> new com.arsenal.shadows.items.armor.HelmOfDarknessItem(new Item.Properties()));
}