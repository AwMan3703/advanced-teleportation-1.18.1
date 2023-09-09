package net.awman.advancedtp;

import net.fabricmc.api.ClientModInitializer;
import net.awman.advancedtp.util.ModModelPredicateProvider;

public class AdvancedTpClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModModelPredicateProvider.registerModModels();
    }
}
