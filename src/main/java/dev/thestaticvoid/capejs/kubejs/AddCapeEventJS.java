package dev.thestaticvoid.capejs.kubejs;

import dev.latvian.mods.kubejs.event.EventJS;
import dev.thestaticvoid.capejs.CapeRegistry;
public class AddCapeEventJS extends EventJS {
    public void register(String uuid, String type) {
        CapeRegistry.addToCapeMap(uuid, CapeRegistry.createCapeResource(type));
    }
}
