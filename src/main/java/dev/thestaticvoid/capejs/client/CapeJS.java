package dev.thestaticvoid.capejs.client;

import dev.thestaticvoid.capejs.capes.CapeRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.UUID;
import java.util.function.BiConsumer;

public class CapeJS implements ClientModInitializer {
    public static final String MOD_ID = "capejs";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final HashMap<UUID, ResourceLocation> CUSTOM_CAPE_MAP = new HashMap<>();

    @Override
    public void onInitializeClient() {
        CapeRegistry.initialize();
        LOGGER.debug("Initializing mod: " + MOD_ID);
    }
}
