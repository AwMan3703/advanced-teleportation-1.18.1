package net.awman.advancedtp.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.awman.advancedtp.util.IEntityDataSaver;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;

public class TpWaypointCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
        dispatcher.register(CommandManager.literal("waypoint")
                .then(CommandManager.literal("tp")
                        .then(CommandManager.argument("waypoint_id", StringArgumentType.string()))
                            .executes(TpWaypointCommand::run)));
    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        // Get the player handle, to read their persistent data
        IEntityDataSaver player = (IEntityDataSaver) context.getSource().getPlayer();

        // Get the waypoint id from the command
        final String waypointId = getString(context, "waypoint_id");

        // Get the waypoint position from the player's persistentData.
        // This is achieved by requesting an IntArray, with the waypoint_id as key
        int[] waypointCoords = player.getPersistentData().getIntArray(waypointId);

        // If the waypoint coordinates' length is > 0 means it contains SOMETHING
        if(waypointCoords.length > 0) {

            // Get the waypoint's coordinates
            int[] playerPos = player.getPersistentData().getIntArray(waypointId);

            // Teleport the player there
            context.getSource().getPlayer().requestTeleport(playerPos[0], playerPos[1], playerPos[2]);

            // Write feedback in chat ("Teleported to <waypoint id>")
            context.getSource().sendFeedback(new LiteralText("Teleported to " + waypointId + "!"), true);

            // Return 1 (completed without errors)
            return 1;
        }
        else {
            // If the waypoint coordinates' length is 0 (a.k.a. doesn't contain anything), inform the player
            context.getSource().sendFeedback(new LiteralText("That waypoint doesn't exist!"), true);

            // Return -1 (completed with errors)
            return -1;
        }
    }
}
