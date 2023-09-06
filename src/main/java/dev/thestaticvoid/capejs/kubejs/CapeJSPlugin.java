package dev.thestaticvoid.capejs.kubejs;

import dev.latvian.mods.kubejs.KubeJSPlugin;

public class CapeJSPlugin extends KubeJSPlugin {
    @Override
    public void registerEvents() {
        CapeJSEvents.EVENT_GROUP.register();
    }
}
