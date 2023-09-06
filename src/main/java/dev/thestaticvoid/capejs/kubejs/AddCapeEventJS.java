package dev.thestaticvoid.capejs.kubejs;

import dev.latvian.mods.kubejs.event.EventJS;
import dev.thestaticvoid.capejs.capes.CapeRegistry;
public class AddCapeEventJS extends EventJS {
    public void addCape(String uuid, String username) {
        CapeRegistry.addToCapeMap(uuid, CapeRegistry.createCapeResource(username));
    }
}
