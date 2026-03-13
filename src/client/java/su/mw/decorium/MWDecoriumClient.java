package su.mw.decorium;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.*;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import su.mw.decorium.render.model.CarpenterUnbakedModel;

public final class MWDecoriumClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        MWDecorium.LOGGER.info("Client");
        ModelLoadingRegistry.INSTANCE.registerResourceProvider(rm -> new ModelResourceProvider() {
            @Override
            public @Nullable UnbakedModel loadModelResource(Identifier resourceId, ModelProviderContext context) throws ModelProviderException {
                MWDecorium.LOGGER.info("Resource requested: {}", resourceId);
                if (resourceId.getNamespace().equals(MWDecorium.ID)) {
                    MWDecorium.LOGGER.info("Intercepting resource model: {}", resourceId);
                    return new CarpenterUnbakedModel(new Identifier("minecraft", "block/cube_all"));
                }
                return null;
            }
        });

        // Регистрируем VariantProvider (для идентификаторов с вариантами)
        ModelLoadingRegistry.INSTANCE.registerVariantProvider(rm -> new ModelVariantProvider() {
            @Override
            public @Nullable UnbakedModel loadModelVariant(ModelIdentifier modelId, ModelProviderContext context) throws ModelProviderException {
                MWDecorium.LOGGER.info("Variant requested: {}", modelId);
                String path = modelId.getPath();
                // Убираем часть после '#' (вариант)
                if (path.contains("#")) {
                    path = path.substring(0, path.indexOf('#'));
                }
                if (modelId.getNamespace().equals(MWDecorium.ID) && path.equals("block/carpenter_block")) {
                    MWDecorium.LOGGER.info("Intercepting variant model: {}", modelId);
                    return new CarpenterUnbakedModel(new Identifier("minecraft", "block/cube_all"));
                }
                return null;
            }
        });
    }
}
