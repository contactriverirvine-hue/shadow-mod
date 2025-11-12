package com.arsenal.shadows;

import com.arsenal.shadows.blocks.ShadowforgeBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, ArsenalOfShadows.MODID);

    // Shadowforge - Main crafting station with block entity
    public static final RegistryObject<Block> SHADOWFORGE = registerBlock("shadowforge",
            () -> new ShadowforgeBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BLACK)
                    .strength(5.0f, 6.0f)
                    .sound(SoundType.ANVIL)
                    .requiresCorrectToolForDrops()
                    .lightLevel(state -> state.getValue(ShadowforgeBlock.LIT) ? 13 : 0)
                    .noOcclusion()));

    // Helper method to register block + item
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }
}