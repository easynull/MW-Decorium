package su.mw.decorium.render.model;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;
import su.mw.decorium.MWDecorium;
import su.mw.decorium.registry.blocks.carpenter.CarpenterBlock;

import java.util.ArrayList;
import java.util.List;

public record CarpenterWrappedBakedModel(BakedModel original) implements BakedModel {
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction face, Random random) {
        return new ArrayList<>();
    }

    private BakedQuad createQuad(Direction face, Sprite sprite) {
        float min = 0.0f;
        float max = 1.0f;
        float[][] vertices;
        switch (face) {
            case DOWN:  vertices = new float[][]{{min, min, min}, {max, min, min}, {max, min, max}, {min, min, max}}; break;
            case UP:    vertices = new float[][]{{min, max, min}, {min, max, max}, {max, max, max}, {max, max, min}}; break;
            case NORTH: vertices = new float[][]{{min, min, min}, {min, max, min}, {max, max, min}, {max, min, min}}; break;
            case SOUTH: vertices = new float[][]{{max, min, max}, {max, max, max}, {min, max, max}, {min, min, max}}; break;
            case WEST:  vertices = new float[][]{{min, min, min}, {min, max, min}, {min, max, max}, {min, min, max}}; break;
            case EAST:  vertices = new float[][]{{max, min, max}, {max, max, max}, {max, max, min}, {max, min, min}}; break;
            default: return null;
        }

        float[][] uv = {{0,0},{1,0},{1,1},{0,1}};
        int[] vertexData = new int[32];
        for (int i = 0; i < 4; i++) {
            int offset = i * 8;
            vertexData[offset]   = Float.floatToRawIntBits(vertices[i][0]);
            vertexData[offset+1] = Float.floatToRawIntBits(vertices[i][1]);
            vertexData[offset+2] = Float.floatToRawIntBits(vertices[i][2]);
            vertexData[offset+3] = -1; // цвет белый
            vertexData[offset+4] = Float.floatToRawIntBits(uv[i][0]);
            vertexData[offset+5] = Float.floatToRawIntBits(uv[i][1]);
            vertexData[offset+6] = 0;
            vertexData[offset+7] = 0;
        }
        return new BakedQuad(vertexData, -1, face, sprite, false);
    }

    // Делегируем остальные методы оригиналу
    @Override public boolean useAmbientOcclusion() { return original.useAmbientOcclusion(); }
    @Override public boolean hasDepth() { return original.hasDepth(); }
    @Override public boolean isSideLit() { return original.isSideLit(); }
    @Override public boolean isBuiltin() { return original.isBuiltin(); }
    @Override public Sprite getParticleSprite() { return original.getParticleSprite(); }
    @Override public ModelTransformation getTransformation() { return original.getTransformation(); }
    @Override public ModelOverrideList getOverrides() { return original.getOverrides(); }
}
