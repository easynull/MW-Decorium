package su.mw.decorium;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.*;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.block.BlockState;
import su.mw.decorium.registry.MdBlocks;
import su.mw.decorium.render.model.CarpenterUnbakedModel;

public final class MWDecoriumClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModelLoadingPlugin.register(plugin -> {
            plugin.registerBlockStateResolver(MdBlocks.CARPENTER_BLOCK, context -> {
                for (BlockState state : MdBlocks.CARPENTER_BLOCK.getStateManager().getStates()) {
                    context.setModel(state, new CarpenterUnbakedModel(0));
                }
            });
//            plugin.registerBlockStateResolver(MdBlocks.CARPENTER_STAIRS, context -> {
//                for (BlockState state : MdBlocks.CARPENTER_STAIRS.getStateManager().getStates()) {
//                    context.setModel(state, new CarpenterUnbakedModel(1));
//                }
//            });
            plugin.registerBlockStateResolver(MdBlocks.CARPENTER_SLAB, context -> {
                for (BlockState state : MdBlocks.CARPENTER_SLAB.getStateManager().getStates()) {
                    context.setModel(state, new CarpenterUnbakedModel(2));
                }
            });
        });
    }
}
