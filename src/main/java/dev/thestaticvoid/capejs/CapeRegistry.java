package dev.thestaticvoid.capejs;

import dev.thestaticvoid.capejs.kubejs.AddCapeEventJS;
import dev.thestaticvoid.capejs.kubejs.CapeJSEvents;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.UUID;

public class CapeRegistry {
    private static final HashMap<UUID, ResourceLocation> CUSTOM_CAPE_MAP = new HashMap<>();
    private static final ResourceLocation DEV_CAPE = CapeJS.id(locationString("dev_cape"));

    public static void initialize() {
        addCapeToMap("8c641065-dba3-41f3-864f-edea4ddfc8bb", DEV_CAPE);
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

        if (!ResourceLocation.isValidPath(textureLocation)) {
            throw new IllegalArgumentException(type + ".png is not found in " + CapeJS.MOD_ID + "/textures/capes/");
        }
        return CapeJS.id(textureLocation);
    }

    public static void addCapeToMap(String uuid, ResourceLocation identifier) {
        if (!CUSTOM_CAPE_MAP.containsKey(UUID.fromString(uuid))) {
            CUSTOM_CAPE_MAP.put(UUID.fromString(uuid), identifier);
            CapeJS.LOGGER.debug("Registered \"{}\" cape to \"{}\"", identifier.getPath(), uuid);
        } else {
            CapeJS.LOGGER.error("Attempted to add cape for existing UUID: {}", uuid);
        }
    }

    public static boolean mapContainsPlayer(AbstractClientPlayer player) {
        return mapContainsPlayer(player.getGameProfile().getId());
    }

    public static boolean mapContainsPlayer(UUID uuid) {
        return CUSTOM_CAPE_MAP.containsKey(uuid);
    }

    public static ResourceLocation getResourceByPlayer(AbstractClientPlayer player) {
        return getResourceByPlayer(player.getGameProfile().getId());
    }

    public static ResourceLocation getResourceByPlayer(UUID uuid) {
        if (CUSTOM_CAPE_MAP.containsKey(uuid)) {
            return CUSTOM_CAPE_MAP.get(uuid);
        }
        return null;
    }
}
