package dev.thestaticvoid.capejs.fabric;

import dev.thestaticvoid.capejs.CapeJS;
import net.fabricmc.api.ClientModInitializer;

public class CapeJSFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CapeJS.init();
    }
}