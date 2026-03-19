package su.mw.decorium.render.model;

import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.Baker;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import su.mw.decorium.MWDecorium;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public record CarpenterUnbakedModel(int id) implements UnbakedModel {

    @Override
    public Collection<Identifier> getModelDependencies() {
        return List.of();
    }

    @Override
    public void setParents(Function<Identifier, UnbakedModel> modelGetter) {
    }

    @Override
    public BakedModel bake(Baker baker, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
        BakedModel base = baker.bake(MWDecorium.path("block/carpenter_block"), rotationContainer);
        if (id == 1) {
            base = baker.bake(MWDecorium.path("block/carpenter_stairs"), rotationContainer);
            return new CarpenterStairsBakedModel(base);
        } else if (id == 2) {
            base = baker.bake(MWDecorium.path("block/carpenter_slab"), rotationContainer);
            return new CarpenterSlabBakedModel(base);
        }
        return new CarpenterBlockBakedModel(base);
    }
}
