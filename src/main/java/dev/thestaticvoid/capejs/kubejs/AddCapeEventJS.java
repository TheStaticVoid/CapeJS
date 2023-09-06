package dev.thestaticvoid.capejs.kubejs;

import dev.latvian.mods.kubejs.event.EventJS;
import dev.thestaticvoid.capejs.client.CapeJS;
import net.minecraft.resources.ResourceLocation;

import java.util.UUID;

public class AddCapeEventJS extends EventJS {
    public void addCape(UUID uuid, ResourceLocation texture) {
        CapeJS.CUSTOM_CAPE_MAP.put(uuid, texture);
    }
}
