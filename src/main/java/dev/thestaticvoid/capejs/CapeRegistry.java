package dev.thestaticvoid.capejs;

import dev.thestaticvoid.capejs.kubejs.AddCapeEventJS;
import dev.thestaticvoid.capejs.kubejs.CapeJSEvents;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.UUID;

public class CapeRegistry {
    private static final HashMap<UUID, ResourceLocation> CUSTOM_CAPE_MAP = new HashMap<>();
    private static final ResourceLocation DEV_CAPE = new ResourceLocation(CapeJS.MOD_ID, locationString("dev_cape"));

    public static void initialize() {
        CapeJS.LOGGER.info("Registering custom capes for: " + CapeJS.MOD_ID);
        // give me a cape :)
        addToCapeMap("8c641065-dba3-41f3-864f-edea4ddfc8bb", DEV_CAPE);
        CapeJSEvents.ADD_CAPE.post(new AddCapeEventJS());
    }

    private static String locationString(String type) {
        return ("textures/capes/" + type + ".png");
    }

    public static ResourceLocation createCapeResource(String type) {
        String textureLocation = locationString(type);
        if (type.equals("dev_cape")) {
            throw new IllegalArgumentException("dev_cape is reserved for mod author!");
        }
        if (!ResourceLocation.isValidResourceLocation(textureLocation)) {
            throw new IllegalArgumentException(type + ".png is not found in " + CapeJS.MOD_ID + "/textures/capes/");
        }
        return new ResourceLocation(CapeJS.MOD_ID, textureLocation);
    }

    public static void addToCapeMap(String uuid, ResourceLocation identifier) {
        CapeJS.LOGGER.info("Adding custom cape for: " + uuid + ", at: " + identifier);
        CUSTOM_CAPE_MAP.put(UUID.fromString(uuid), identifier);
    }

    public static boolean mapContainsPlayer(AbstractClientPlayer player) {
        return CUSTOM_CAPE_MAP.containsKey(player.getGameProfile().getId());
    }

    public static ResourceLocation getResourceByPlayer(AbstractClientPlayer player) {
        UUID uuid = player.getGameProfile().getId();
        if (CUSTOM_CAPE_MAP.containsKey(uuid)) {
            return CUSTOM_CAPE_MAP.get(uuid);
        }
        return null;
    }
}
