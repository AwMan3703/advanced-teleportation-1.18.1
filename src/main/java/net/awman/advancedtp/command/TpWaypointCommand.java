package net.awman.advancedtp.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.awman.advancedtp.AdvancedTp;
import net.awman.advancedtp.AdvancedTpClient;
import net.awman.advancedtp.util.IEntityDataSaver;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.MessageType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;

import java.util.UUID;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;

public class TpWaypointCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
        dispatcher.register(CommandManager.literal("waypoint")
                .then(CommandManager.literal("tp")
                        .then(CommandManager.argument("waypoint_id", StringArgumentType.string()))
                            .executes(TpWaypointCommand::run)));
    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        IEntityDataSaver player = (IEntityDataSaver)context.getSource().getPlayer();

        MinecraftClient mc = MinecraftClient.getInstance();
        final String waypointId = getString(context, "waypoint_id");
        mc.inGameHud.addChatMessage(MessageType.SYSTEM, net.minecraft.text.Text.of(waypointId), UUID.fromString(""));
        // not 0 means it contains SOMETHING
        int[] waypointCoords = player.getPersistentData().getIntArray(waypointId);
        mc.inGameHud.addChatMessage(MessageType.SYSTEM, net.minecraft.text.Text.of(waypointCoords.toString()), UUID.fromString(""));
        if(waypointCoords.length != 0) {
            int[] playerPos = player.getPersistentData().getIntArray(waypointId);
            context.getSource().getPlayer().requestTeleport(playerPos[0], playerPos[1], playerPos[2]);

            context.getSource().sendFeedback(new LiteralText("Teleported to " + waypointId + "!"), true);
            return 1;
        }
        else {
            context.getSource().sendFeedback(new LiteralText("That waypoint doesn't exist!"), true);
            return -1;
        }
    }
}
