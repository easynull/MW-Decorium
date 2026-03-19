package su.mw.decorium.render.model;

import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.StairsBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;
import su.mw.decorium.utils.CarpenterUtils;

import java.util.ArrayList;
import java.util.List;

import static su.mw.decorium.render.model.CarpenterSlabBakedModel.retextureQuad;

public record CarpenterStairsBakedModel(BakedModel baseModel) implements BakedModel, FabricBakedModel {

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction direction, Random random) {
        if (state == null || !state.contains(CarpenterUtils.PROPERTY)) return baseModel.getQuads(state, direction, random);

        int rawId = state.get(CarpenterUtils.PROPERTY);
        Block mimickedBlock = Registries.BLOCK.get(rawId);

        if (mimickedBlock == Blocks.AIR || rawId == 0) return baseModel.getQuads(state, direction, random);

        BakedModel mimickedModel = MinecraftClient.getInstance().getBlockRenderManager().getModel(mimickedBlock.getDefaultState());
        Sprite sprite = mimickedModel.getParticleSprite();

        BlockState template = Blocks.OAK_STAIRS.getDefaultState()
                .with(StairsBlock.FACING, state.get(StairsBlock.FACING))
                .with(StairsBlock.HALF, state.get(StairsBlock.HALF))
                .with(StairsBlock.SHAPE, state.get(StairsBlock.SHAPE))
                .with(StairsBlock.WATERLOGGED, state.get(StairsBlock.WATERLOGGED));

        BakedModel templateModel = MinecraftClient.getInstance()
                .getBlockRenderManager()
                .getModel(template);

        List<BakedQuad> originalQuads = templateModel.getQuads(template, direction, random);

        List<BakedQuad> retextured = new ArrayList<>(originalQuads.size());

        for (BakedQuad quad : originalQuads) {
            retextured.add(retextureQuad(quad, sprite));
        }

        return retextured;
    }

    @Override
    public boolean useAmbientOcclusion() {
        return true;
    }

    @Override
    public boolean isSideLit() {
        return true;
    }

    @Override
    public boolean hasDepth() {
        return true;
    }

    @Override
    public boolean isBuiltin() {
        return false;
    }

    @Override
    public Sprite getParticleSprite() {
        return baseModel.getParticleSprite();
    }

    @Override
    public ModelTransformation getTransformation() {
        return baseModel.getTransformation();
    }

    @Override
    public ModelOverrideList getOverrides() {
        return ModelOverrideList.EMPTY;
    }

    @Override
    public boolean isVanillaAdapter() {
        return false;
    }
}
