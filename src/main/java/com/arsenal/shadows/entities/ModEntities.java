package com.arsenal.shadows.entities;

import com.arsenal.shadows.ArsenalOfShadows;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ArsenalOfShadows.MODID);

    public static final RegistryObject<EntityType<JavelinEntity>> JAVELIN = ENTITIES.register("javelin",
            () -> EntityType.Builder.<JavelinEntity>of(JavelinEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build("javelin"));
}