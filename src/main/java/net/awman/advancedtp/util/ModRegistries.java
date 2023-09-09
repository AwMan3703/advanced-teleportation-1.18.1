package net.awman.advancedtp.util;

import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.awman.advancedtp.command.ReturnHomeCommand;
import net.awman.advancedtp.command.SetHomeCommand;
import net.awman.advancedtp.event.ModPlayerEventCopyFrom;

public class ModRegistries {
    public static void registerModStuffs() {
        registerCommands();
        registerEvents();
    }

    private static void registerCommands() {
        CommandRegistrationCallback.EVENT.register(SetHomeCommand::register);
        CommandRegistrationCallback.EVENT.register(ReturnHomeCommand::register);
    }

    private static void registerEvents() {
        ServerPlayerEvents.COPY_FROM.register(new ModPlayerEventCopyFrom());
    }
}
