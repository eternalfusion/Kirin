package com.minelittlepony.common.platform;

import java.nio.file.Path;
import java.util.Optional;

/**
 * Abstraction over the mod loader Kirin is running on.
 */
public interface Platform {
    /**
     * Gets the platform implementation for the current mod loader.
     */
    static Platform get() {
        return PlatformHolder.INSTANCE;
    }

    /**
     * The name of the current platform, either {@code "fabric"} or {@code "neoforge"}.
     */
    String getName();

    boolean isModLoaded(String modId);

    Optional<String> getModVersion(String modId);

    Path getGameDirectory();

    Path getConfigDirectory();

    Side getPhysicalSide();

    boolean isDevelopmentEnvironment();
}
