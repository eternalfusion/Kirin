package com.minelittlepony.common.event;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClientReadyCallbackTest {
    @Test
    void firesExactlyOnce() {
        var calls = new AtomicInteger();
        ClientReadyCallback.EVENT.register(_ -> calls.incrementAndGet());
        ClientReadyCallback.Handler.onClientTick(null);
        ClientReadyCallback.Handler.onClientTick(null);
        assertEquals(1, calls.get());
    }
}
