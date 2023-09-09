package net.awman.advancedtp.item;

import net.awman.advancedtp.AdvancedTp;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {
    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(AdvancedTp.MOD_ID, name), item);
    }

    public static void registerModItems() {
        AdvancedTp.LOGGER.debug("Registering Mod Items for " + AdvancedTp.MOD_ID);
    }
}