package su.mw.decorium;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.mw.decorium.registry.MdBlocks;
import su.mw.decorium.registry.MdItems;

public final class MWDecorium implements ModInitializer {
    public static final String ID = "mwdecorium";
    public static final Logger LOGGER = LoggerFactory.getLogger("MW Decorium");

    @Override
    public void onInitialize() {
        MdBlocks.onInit();
        MdItems.onInit();
    }

    public static Identifier path(String path){
        return Identifier.of(ID, path);
    }
}
