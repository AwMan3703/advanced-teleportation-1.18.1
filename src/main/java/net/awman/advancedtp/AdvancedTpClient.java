package net.awman.advancedtp;

import net.fabricmc.api.ClientModInitializer;

public class AdvancedTpClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        AdvancedTp.LOGGER.debug("Initializing " + AdvancedTp.MOD_ID + " client...");
    }
}
