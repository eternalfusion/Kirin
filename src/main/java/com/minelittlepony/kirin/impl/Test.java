package com.minelittlepony.kirin.impl;

import com.minelittlepony.common.client.gui.element.Button;
import com.minelittlepony.common.event.ClientReadyCallback;
import com.minelittlepony.common.event.ScreenInitCallback;
import com.minelittlepony.common.platform.Platform;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.TitleScreen;
import org.slf4j.Logger;

public class Test {
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final boolean DEBUG = Boolean.getBoolean("kirin.debug");

    /**
     * Common client initialization of Kirin, called by every loader's entrypoint.
     */
    public static void init() {
        if (DEBUG) {
            ScreenInitCallback.EVENT.register((screen, buttons) -> {
                if (screen instanceof TitleScreen) {
                    Button button = buttons.addButton(new Button(50, 20, 20, 20))
                            .onClick(_ -> Minecraft.getInstance().gui.setScreen(new KirinTestScreen(screen)));
                        button.getStyle()
                                .setTooltip("Kirin Test Screen", 0, 10);
                }
            });
            ClientReadyCallback.EVENT.register(client -> {
                LOGGER.info("[Kirin] client ready on {} (game dir {})", Platform.get().getName(), Platform.get().getGameDirectory());
                if (Boolean.getBoolean("kirin.smoketest")) {
                    client.stop();
                }
            });
        }
    }
}
