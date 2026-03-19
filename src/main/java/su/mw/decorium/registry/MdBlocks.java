package su.mw.decorium.registry;

import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import su.mw.decorium.MWDecorium;
import su.mw.decorium.registry.blocks.carpenter.CarpenterBlock;
import su.mw.decorium.registry.blocks.carpenter.CarpenterSlabBlock;
import su.mw.decorium.registry.blocks.carpenter.CarpenterStairsBlock;

public final class MdBlocks {
    public static final Block CARPENTER_BLOCK = registerBlock("carpenter_block", new CarpenterBlock(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS)));
    public static final Block CARPENTER_STAIRS = registerBlock("carpenter_stairs", new CarpenterStairsBlock(Blocks.OAK_PLANKS.getDefaultState(), AbstractBlock.Settings.copy(Blocks.OAK_STAIRS)));
    public static final Block CARPENTER_SLAB = registerBlock("carpenter_slab", new CarpenterSlabBlock(AbstractBlock.Settings.copy(Blocks.OAK_SLAB)));

    private static <T extends Block> T registerBlock(String name, T block, Item.Settings itemSettings) {
        RegistryKey<Block> blockKey = RegistryKey.of(RegistryKeys.BLOCK, MWDecorium.path(name));
        Registry.register(Registries.BLOCK, blockKey, block);

        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, MWDecorium.path(name));
        Registry.register(Registries.ITEM, itemKey, new BlockItem(block, itemSettings));

        return block;
    }

    private static <T extends Block> T registerBlock(String name, T block) {
        return registerBlock(name, block, new Item.Settings());
    }

    public static void onInit() {}
}