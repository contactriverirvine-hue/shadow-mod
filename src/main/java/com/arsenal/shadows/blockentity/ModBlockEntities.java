package com.arsenal.shadows.blockentity;

import com.arsenal.shadows.ArsenalOfShadows;
import com.arsenal.shadows.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ArsenalOfShadows.MODID);

    public static final RegistryObject<BlockEntityType<ShadowforgeBlockEntity>> SHADOWFORGE_BE =
            BLOCK_ENTITIES.register("shadowforge_be", () ->
                    BlockEntityType.Builder.of(ShadowforgeBlockEntity::new,
                            ModBlocks.SHADOWFORGE.get()).build(null));
}