package me.duncanruns.icarus.compat;

import me.voidxwalker.worldpreview.WPFakeServerPlayerEntity;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.network.ServerPlayerEntity;

public class WorldPreviewCompat {
    private static final boolean loaded = FabricLoader.getInstance().isModLoaded("worldpreview");

    public static boolean isFakePlayer(ServerPlayerEntity player) {
        return loaded && player instanceof WPFakeServerPlayerEntity;
    }
}
