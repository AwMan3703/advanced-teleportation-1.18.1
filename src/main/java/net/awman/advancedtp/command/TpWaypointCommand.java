package net.awman.advancedtp.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.awman.advancedtp.AdvancedTp;
import net.awman.advancedtp.util.IEntityDataSaver;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;

// This is the [/waypoint tp <name>] command, used to teleport the player to a specific waypoint
public class TpWaypointCommand {
    // Register the command:
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
        // Register under the  /waypoint command
        dispatcher.register(CommandManager.literal("waypoint")

                // Register as "tp" (/waypoint tp)
                .then(CommandManager.literal("tp")

                        // Take a custom string (the waypoint's id) as argument (/waypoint tp <waypoint_id>)
                        .then(CommandManager.argument("waypoint_id", StringArgumentType.string()))

                            // When the command is sent, execute the run() method
                            .executes(context -> run(context))));//, getString(context, "waypoint_id")))));
    }

    // When the command is executed:
    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        // Get the player's dataSaver interface, to read their persistent data
        IEntityDataSaver player = (IEntityDataSaver) context.getSource().getPlayer();

        // Get the waypoint id from the command
        final String waypointId = AdvancedTp.MOD_ID + "_waypoint_" + getString(context, "waypoint_id");

        // Get the waypoint position from the player's persistentData.
        // This is achieved by requesting an IntArray, with the waypoint_id as key
        int[] waypointCoords = player.getPersistentData().getIntArray(waypointId);

        // If the waypoint coordinates' length is > 0 means it contains SOMETHING
        if(waypointCoords.length > 0) {

            // Get the waypoint's coordinates
            //int[] playerPos = player.getPersistentData().getIntArray(waypointId);

            // Teleport the player there
            context.getSource().getPlayer().requestTeleport(waypointCoords[0], waypointCoords[1], waypointCoords[2]);

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
