package com.arsenal.shadows.items.weapons;

import com.arsenal.shadows.ModItems;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;

public class ModTiers {
    // Base tier for all Arsenal weapons
    public static final Tier DARKSTEEL = new ForgeTier(
            3, // Mining level (same as diamond)
            1561, // Durability
            8.0f, // Mining speed
            3.0f, // Attack damage bonus
            14, // Enchantability
            null,
            () -> Ingredient.of(ModItems.DARKSTEEL_INGOT.get())
    );
}