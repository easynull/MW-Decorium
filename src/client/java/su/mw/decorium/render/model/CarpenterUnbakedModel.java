package su.mw.decorium.render.model;

import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.Baker;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import su.mw.decorium.MWDecorium;

import java.util.Collection;
import java.util.Set;
import java.util.function.Function;

public record CarpenterUnbakedModel(Identifier parentModelId) implements UnbakedModel {
    @Override
    public Collection<Identifier> getModelDependencies() {
        return Set.of(parentModelId);
    }

    @Override
    public void setParents(Function<Identifier, UnbakedModel> modelLoader) {
        modelLoader.apply(parentModelId);
    }

    @Override
    public BakedModel bake(Baker loader, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
        MWDecorium.LOGGER.info("Baking model for {}", modelId);
        UnbakedModel parentUnbaked = loader.getOrLoadModel(parentModelId);
        BakedModel parentBaked = parentUnbaked.bake(loader, textureGetter, rotationContainer, parentModelId);
        return new CarpenterWrappedBakedModel(parentBaked);
    }
}
