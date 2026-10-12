package com.minelittlepony.kirin.fabric;

import com.minelittlepony.kirin.impl.Test;

import net.fabricmc.api.ClientModInitializer;

public class KirinFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Test.init();
    }
}
