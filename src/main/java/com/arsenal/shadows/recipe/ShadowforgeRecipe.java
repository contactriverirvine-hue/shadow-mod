package com.arsenal.shadows.recipe;

import com.arsenal.shadows.ModItems;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ShadowforgeRecipe {
    private final Item input;
    private final Item material;
    private final int materialCount;
    private final Item result;

    private static final Map<Item, ShadowforgeRecipe> RECIPES = new HashMap<>();

    public ShadowforgeRecipe(Item input, Item material, int materialCount, Item result) {
        this.input = input;
        this.material = material;
        this.materialCount = materialCount;
        this.result = result;
    }

    public static void registerRecipes() {
        // Pike: Iron -> Darksteel
        register(ModItems.IRON_PIKE.get(), ModItems.DARKSTEEL_INGOT.get(), 2, ModItems.PIKE.get());

        // Claymore: Iron -> Darksteel
        register(ModItems.IRON_CLAYMORE.get(), ModItems.DARKSTEEL_INGOT.get(), 3, ModItems.CLAYMORE.get());

        // War Axe: Iron -> Darksteel
        register(ModItems.IRON_WAR_AXE.get(), ModItems.DARKSTEEL_INGOT.get(), 2, ModItems.WAR_AXE.get());

        // Rapier: Iron -> Darksteel
        register(ModItems.IRON_RAPIER.get(), ModItems.DARKSTEEL_INGOT.get(), 2, ModItems.RAPIER.get());

        // Flail: Iron -> Darksteel
        register(ModItems.IRON_FLAIL.get(), ModItems.DARKSTEEL_INGOT.get(), 2, ModItems.FLAIL.get());

        // Javelin: Iron -> Darksteel
        register(ModItems.IRON_JAVELIN.get(), ModItems.DARKSTEEL_INGOT.get(), 1, ModItems.JAVELIN.get());

        // Halberd: Iron -> Darksteel
        register(ModItems.IRON_HALBERD.get(), ModItems.DARKSTEEL_INGOT.get(), 3, ModItems.HALBERD.get());

        // Scythe: Iron -> Darksteel
        register(ModItems.IRON_SCYTHE.get(), ModItems.DARKSTEEL_INGOT.get(), 2, ModItems.SCYTHE.get());

        // TODO: Add Tier 3 upgrades (Darksteel -> Runic) in future
        // Example: register(ModItems.PIKE.get(), ModItems.RUNIC_STONE.get(), 1, ModItems.RUNIC_PIKE.get());
    }

    private static void register(Item input, Item material, int materialCount, Item result) {
        RECIPES.put(input, new ShadowforgeRecipe(input, material, materialCount, result));
    }

    public static Optional<ShadowforgeRecipe> getRecipe(Container container, Level level) {
        ItemStack inputStack = container.getItem(0);
        ItemStack materialStack = container.getItem(1);

        if (inputStack.isEmpty() || materialStack.isEmpty()) {
            return Optional.empty();
        }

        ShadowforgeRecipe recipe = RECIPES.get(inputStack.getItem());

        if (recipe != null &&
                materialStack.getItem() == recipe.material &&
                materialStack.getCount() >= recipe.materialCount) {
            return Optional.of(recipe);
        }

        return Optional.empty();
    }

    public Item getInput() {
        return input;
    }

    public Item getMaterial() {
        return material;
    }

    public int getMaterialCount() {
        return materialCount;
    }

    public ItemStack getResultItem(net.minecraft.core.RegistryAccess access) {
        return new ItemStack(result);
    }
}