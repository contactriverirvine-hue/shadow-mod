package com.arsenal.shadows.menu;

import com.arsenal.shadows.ArsenalOfShadows;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, ArsenalOfShadows.MODID);

    public static final RegistryObject<MenuType<ShadowforgeMenu>> SHADOWFORGE_MENU =
            MENUS.register("shadowforge_menu",
                    () -> IForgeMenuType.create(ShadowforgeMenu::new));
}