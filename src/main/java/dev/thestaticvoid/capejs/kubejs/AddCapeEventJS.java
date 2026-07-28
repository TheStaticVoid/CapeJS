package dev.thestaticvoid.capejs.kubejs;

import dev.latvian.mods.kubejs.event.KubeEvent;
import dev.thestaticvoid.capejs.CapeWebHelper;
import dev.thestaticvoid.capejs.CapeRegistry;

public class AddCapeEventJS implements KubeEvent {
    public void register(String uuid, String type) {
        CapeRegistry.addCapeToMap(uuid, CapeRegistry.createCapeResource(type));
    }

    public void registerFromWeb(String url) throws Exception {
        CapeWebHelper.addCapesFromWeb(url);
    }
}
