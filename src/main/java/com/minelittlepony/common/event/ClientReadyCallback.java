package com.minelittlepony.common.event;

import net.minecraft.client.Minecraft;

/**
 * Callback for the first tick on the client.
 */
@Deprecated
public interface ClientReadyCallback {

    Event<ClientReadyCallback> EVENT = EventFactory.createArrayBacked(ClientReadyCallback.class, listeners -> client -> {
        for (ClientReadyCallback event : listeners) {
            event.onClientPostInit(client);
        }
    });

    void onClientPostInit(Minecraft client);

    class Handler {

        private static boolean firstTick = true;

        private Handler() {
        }

        /**
         * No longer required: the event is fired by Kirin itself at the end of the first client tick.
         * Kept for compatibility.
         */
        @Deprecated
        public static void register() {
        }

        /**
         * Called at the end of every client tick. Fires {@link ClientReadyCallback#EVENT} on the first call only.
         */
        public static void onClientTick(Minecraft client) {
            if (firstTick) {
                firstTick = false;
                ClientReadyCallback.EVENT.invoker().onClientPostInit(client);
            }
        }
    }
}
