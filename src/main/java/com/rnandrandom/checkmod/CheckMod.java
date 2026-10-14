package com.rnandrandom.checkmod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckMod implements ModInitializer {
    public static final String MOD_ID = "checkmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing CheckMod");
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("check")
                .then(CommandManager.argument("player", EntityArgumentType.player())
                    .executes(context -> {
                        ServerPlayerEntity target = EntityArgumentType.getPlayer(context, "player");
                        ServerPlayerEntity checker = context.getSource().getPlayer();
                        // Implement check logic here
                        checker.sendMessage(Text.literal("Checking ").append(target.getDisplayName()).formatted(Formatting.RED), false);
                        return 1;
                    })));
            dispatcher.register(CommandManager.literal("uncheck")
                .executes(context -> {
                    ServerPlayerEntity checker = context.getSource().getPlayer();
                    // Implement uncheck logic here
                    checker.sendMessage(Text.literal("Unchecked").formatted(Formatting.GREEN), false);
                    return 1;
                }));
        });
    }
}