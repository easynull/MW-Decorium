package su.mw.decorium.registry;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import su.mw.decorium.MWDecorium;

import static su.mw.decorium.registry.MdBlocks.*;

public final class MdItems {
//    public static final RegistryKey<ItemGroup> TAB = registerTab("hemomancy", Text.translatable("tab.hemomancy"), CARPENTER_BLOCK,
//            CARPENTER_BLOCK, CARPENTER_SLAB, CARPENTER_STAIRS
//    );

    private static <T extends Item> T registerItem(String name, T item) {
        var id = MWDecorium.path(name);
        return Registry.register(Registries.ITEM, id, item);
    }

    private static RegistryKey<ItemGroup> registerTab(String name, Text title, ItemConvertible icon, ItemConvertible... items) {
        var key = RegistryKey.of(RegistryKeys.ITEM_GROUP, MWDecorium.path(name));

        var group = FabricItemGroup.builder().displayName(title).icon(() -> new ItemStack(icon))
                .entries((ctx, entries) -> {
                    for (var item : items) {
                        if (item != null) entries.add(item);
                    }
                }).build();

        Registry.register(Registries.ITEM_GROUP, key, group);
        return key;
    }

    public static void onInit() {}
}