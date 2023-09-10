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
import net.minecraft.util.math.BlockPos;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;

public class SetWaypointCommand {
    // Register the command:
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
        // Register under the /waypoint command
        dispatcher.register(CommandManager.literal("waypoint")

                // Register as "set" (/waypoint set)
                .then(CommandManager.literal("set")

                        // Take a custom string (the waypoint's id) as argument (/waypoint tp <waypoint_id>)
                        .then(CommandManager.argument("waypoint_id", StringArgumentType.string())

                                // When the command is sent, execute the run() method
                                .executes(SetWaypointCommand::run))));
    }

    public static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        IEntityDataSaver player = (IEntityDataSaver)context.getSource().getPlayer();
        BlockPos playerPos = context.getSource().getPlayer().getBlockPos();
        String pos = "(" + playerPos.getX() + ", " + playerPos.getY() + ", " + playerPos.getZ() + ")";

        player.getPersistentData().putIntArray(AdvancedTp.MOD_ID + "waypoint_" + getString(context, "waypoint_id"),
                new int[] {playerPos.getX(), playerPos.getY(), playerPos.getZ() });

        context.getSource().sendFeedback(new LiteralText("Set new waypoint (" + getString(context, "waypoint_id") + ") at " + pos), true);
        return 1;
    }
}
