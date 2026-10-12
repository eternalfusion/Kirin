package com.minelittlepony.common.event;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.function.Function;

/**
 * Minimal array-backed event, mirroring the shape of the Fabric API event.
 */
public final class Event<T> {
    private final Function<T[], T> invokerFactory;
    private T[] listeners;
    private volatile T invoker;

    @SuppressWarnings("unchecked")
    Event(Class<? super T> type, Function<T[], T> invokerFactory) {
        this.invokerFactory = invokerFactory;
        this.listeners = (T[]) Array.newInstance(type, 0);
        this.invoker = invokerFactory.apply(listeners);
    }

    public synchronized void register(T listener) {
        listeners = Arrays.copyOf(listeners, listeners.length + 1);
        listeners[listeners.length - 1] = listener;
        invoker = invokerFactory.apply(listeners);
    }

    public T invoker() {
        return invoker;
    }
}
