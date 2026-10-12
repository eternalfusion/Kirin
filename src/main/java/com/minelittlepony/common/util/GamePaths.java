package com.minelittlepony.common.util;

import com.minelittlepony.common.platform.Platform;

import net.minecraft.client.Minecraft;
import java.nio.file.Path;

/**
 * Provides access to all of the basic paths needed to interact with the game.
 * <p>
 * Some of these are not properly provided by the mod loader, or are provided, but use
 * discouraged methods of interacting with the filesystem.
 *
 * @author     Sollace
 */
public class GamePaths {

    private GamePaths() {}

    /**
     * Gets the current game (root) direction as a Path.
     */
    public static Path getGameDirectory() {
        return Platform.get().getGameDirectory();
    }

    /**
     * Gets the current game config direction as a Path.
     */
    public static Path getConfigDirectory() {
        return Platform.get().getConfigDirectory();
    }

    /**
     * Gets the current game's assets directory as a Path.
     */
    public static Path getAssetsDirectory() {
        return ((AssetsDirProvider) Minecraft.getInstance()).getAssetsDirectory();
    }

    public interface AssetsDirProvider {
        Path getAssetsDirectory();
    }
}
