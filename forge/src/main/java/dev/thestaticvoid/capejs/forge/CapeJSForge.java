package dev.thestaticvoid.capejs.forge;

import dev.architectury.platform.forge.EventBuses;
import dev.thestaticvoid.capejs.CapeJS;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(CapeJS.MOD_ID)
public class CapeJSForge {
    public CapeJSForge() {
		// Submit our event bus to let architectury register our content on the right time
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(CapeJSForge::onClientSetup);
        EventBuses.registerModEventBus(CapeJS.MOD_ID, bus);

    }

    public static void onClientSetup(FMLClientSetupEvent event) {
        CapeJS.init();
    }
}