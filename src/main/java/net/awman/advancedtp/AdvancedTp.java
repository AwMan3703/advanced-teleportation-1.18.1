package net.awman.advancedtp;

import net.fabricmc.api.ModInitializer;
import net.awman.advancedtp.item.ModItems;
import net.awman.advancedtp.util.ModRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AdvancedTp implements ModInitializer {
	public static final String MOD_ID = "advancedtp";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		ModItems.registerModItems();

		ModRegistries.registerModStuffs();

	}
}
