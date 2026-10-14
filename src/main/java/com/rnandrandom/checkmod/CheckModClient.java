package com.rnandrandom.checkmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckModClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("CheckModClient");
    private static EndCrystalEntity guardianEntity = null;
    private static boolean isChecking = false;

    @Override
    public void onInitializeClient() {
        LOGGER.info("Initializing CheckModClient");
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (isChecking && client.player != null) {
                if (guardianEntity == null) {
                    guardianEntity = new EndCrystalEntity(EntityType.END_CRYSTAL, client.world);
                    guardianEntity.setPos(client.player.getX(), client.player.getY() + 2, client.player.getZ());
                    client.world.addEntity(guardianEntity);
                    client.player.playSound(SoundEvents.ENTITY_ELDER_GUARDIAN_AMBIENT, 1.0F, 1.0F);
                    client.player.sendMessage(Text.literal("Checking...").formatted(Formatting.RED), true);
                }
            } else if (guardianEntity != null) {
                guardianEntity.remove(Entity.RemovalReason.DISCARDED);
                guardianEntity = null;
                client.player.sendMessage(Text.literal("Unchecked").formatted(Formatting.GREEN), true);
            }
        });
    }

    public static void setChecking(boolean checking) {
        isChecking = checking;
    }
}