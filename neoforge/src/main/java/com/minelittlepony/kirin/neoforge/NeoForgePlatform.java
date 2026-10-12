package com.minelittlepony.kirin.neoforge;

import com.minelittlepony.common.platform.Platform;
import com.minelittlepony.common.platform.Side;

import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.loading.FMLPaths;

import java.nio.file.Path;
import java.util.Optional;

public final class NeoForgePlatform implements Platform {
    @Override
    public String getName() {
        return "neoforge";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public Optional<String> getModVersion(String modId) {
        return ModList.get().getModContainerById(modId).map(c -> c.getModInfo().getVersion().toString());
    }

    @Override
    public Path getGameDirectory() {
        return FMLPaths.GAMEDIR.get();
    }

    @Override
    public Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }

    @Override
    public Side getPhysicalSide() {
        return FMLEnvironment.getDist().isClient() ? Side.CLIENT : Side.SERVER;
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return !FMLEnvironment.isProduction();
    }
}
