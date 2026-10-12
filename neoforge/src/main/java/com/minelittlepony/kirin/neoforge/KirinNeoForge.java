package com.minelittlepony.kirin.neoforge;

import com.minelittlepony.kirin.impl.Test;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;

@Mod(value = "kirin", dist = Dist.CLIENT)
public class KirinNeoForge {
    public KirinNeoForge() {
        Test.init();
    }
}
