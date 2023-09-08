package dev.thestaticvoid.capejs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CapeJS
{
	public static final String MOD_ID = "capejs";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static void init() {
		LOGGER.info("Initializing mod: " + MOD_ID);
		CapeRegistry.initialize();
	}
}
