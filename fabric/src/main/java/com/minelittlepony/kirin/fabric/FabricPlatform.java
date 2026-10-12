package com.minelittlepony.kirin.fabric;

import com.minelittlepony.common.platform.Platform;
import com.minelittlepony.common.platform.Side;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;
import java.util.Optional;

public final class FabricPlatform implements Platform {
    @Override
    public String getName() {
        return "fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public Optional<String> getModVersion(String modId) {
        return FabricLoader.getInstance().getModContainer(modId).map(c -> c.getMetadata().getVersion().getFriendlyString());
    }

    @Override
    public Path getGameDirectory() {
        return FabricLoader.getInstance().getGameDir();
    }

    @Override
    public Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }

    @Override
    public Side getPhysicalSide() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT ? Side.CLIENT : Side.SERVER;
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }
}
