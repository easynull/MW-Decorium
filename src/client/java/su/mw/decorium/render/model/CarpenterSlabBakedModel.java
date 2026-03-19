package su.mw.decorium.render.model;

import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.minecraft.block.*;
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
import su.mw.decorium.MWDecorium;
import su.mw.decorium.utils.CarpenterUtils;

import java.util.ArrayList;
import java.util.List;

public record CarpenterSlabBakedModel(BakedModel baseModel) implements BakedModel, FabricBakedModel {

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction direction, Random random) {
        if (state == null || !state.contains(CarpenterUtils.PROPERTY)) return baseModel.getQuads(state, direction, random);

        int rawId = state.get(CarpenterUtils.PROPERTY);
        Block mimickedBlock = Registries.BLOCK.get(rawId);

        if (mimickedBlock == Blocks.AIR || rawId == 0) return baseModel.getQuads(state, direction, random);

        BakedModel mimickedModel = MinecraftClient.getInstance().getBlockRenderManager().getModel(mimickedBlock.getDefaultState());
        Sprite sprite = mimickedModel.getParticleSprite();

        BlockState template = Blocks.OAK_SLAB.getDefaultState()
                .with(SlabBlock.TYPE, state.get(SlabBlock.TYPE))
                .with(SlabBlock.WATERLOGGED, state.get(SlabBlock.WATERLOGGED));

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

    public static BakedQuad retextureQuad(BakedQuad original, Sprite newSprite) {
        Sprite oldSprite = original.getSprite();
        if (oldSprite == null || newSprite == null) {
            return original;
        }

        int[] newData = original.getVertexData().clone();

        for (int vertex = 0; vertex < 4; vertex++) {
            int index = vertex * 8;

            float u = Float.intBitsToFloat(newData[index + 4]);
            float v = Float.intBitsToFloat(newData[index + 5]);

            float relU = (u - oldSprite.getMinU()) / (oldSprite.getMaxU() - oldSprite.getMinU());
            float relV = (v - oldSprite.getMinV()) / (oldSprite.getMaxV() - oldSprite.getMinV());

            float newU = newSprite.getMinU() + relU * (newSprite.getMaxU() - newSprite.getMinU());
            float newV = newSprite.getMinV() + relV * (newSprite.getMaxV() - newSprite.getMinV());

            newData[index + 4] = Float.floatToRawIntBits(newU);
            newData[index + 5] = Float.floatToRawIntBits(newV);
        }

        return new BakedQuad(newData, original.getColorIndex(), original.getFace(), newSprite, original.hasShade());
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
