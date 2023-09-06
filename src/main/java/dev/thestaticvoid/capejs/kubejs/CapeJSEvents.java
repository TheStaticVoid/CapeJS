package dev.thestaticvoid.capejs.kubejs;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;

public interface CapeJSEvents {
    EventGroup EVENT_GROUP = EventGroup.of("CapeJS");
    EventHandler ADD_CAPE = EVENT_GROUP.client("addCape", () -> AddCapeEventJS.class);
}
