package dev.thestaticvoid.capejs;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CapeJS implements ClientModInitializer {
    public static final String MOD_ID = "capejs";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        CapeRegistry.initialize();
        LOGGER.info("Initializing mod: " + MOD_ID);
    }
}
