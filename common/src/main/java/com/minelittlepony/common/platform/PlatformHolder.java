package com.minelittlepony.common.platform;

/**
 * Lazily resolves the {@link Platform} service on first use.
 */
final class PlatformHolder {
    static final Platform INSTANCE = Services.load(Platform.class);

    private PlatformHolder() {}
}
