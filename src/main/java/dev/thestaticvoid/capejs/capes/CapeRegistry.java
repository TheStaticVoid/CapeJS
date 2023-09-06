package dev.thestaticvoid.capejs.capes;

import dev.thestaticvoid.capejs.client.CapeJS;
import dev.thestaticvoid.capejs.kubejs.AddCapeEventJS;
import dev.thestaticvoid.capejs.kubejs.CapeJSEvents;
import net.minecraft.resources.ResourceLocation;

import java.util.UUID;

public class CapeRegistry {
    public static void initialize() {
        CapeJS.LOGGER.debug("Registering custom capes for: " + CapeJS.MOD_ID);
        addToCapeMap("8c641065-dba3-41f3-864f-edea4ddfc8bb", createCapeResource("staitc"));
        CapeJSEvents.ADD_CAPE.post(new AddCapeEventJS());
    }

    public static ResourceLocation createCapeResource(String username) {
        return new ResourceLocation(CapeJS.MOD_ID, "textures/capes/" + username + ".png");
    }

    public static void addToCapeMap(String uuid, ResourceLocation identifier) {
        CapeJS.CUSTOM_CAPE_MAP.put(UUID.fromString(uuid), identifier);
    }
}
