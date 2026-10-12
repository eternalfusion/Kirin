package com.minelittlepony.common.platform;

import java.util.ServiceLoader;

/**
 * Loads loader-specific implementations of common interfaces through {@link ServiceLoader}.
 */
public final class Services {
    private Services() {}

    /**
     * Loads the first available implementation of the given service type.
     *
     * @throws IllegalStateException if no implementation is present on the classpath.
     */
    public static <T> T load(Class<T> type) {
        return ServiceLoader.load(type, type.getClassLoader())
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No implementation of " + type.getName() + " found. Is the platform jar on the classpath?"));
    }
}
