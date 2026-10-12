package com.minelittlepony.common.util.registry;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentMap;

/**
 * Utilities for creating holders that stand in for another holder.
 * <p>
 * {@link Holder} is sealed in vanilla Minecraft, so this is no longer a proxying holder type.
 */
public final class ForwardingHolder {

    private ForwardingHolder() {}

    /**
     * Returns a new holder that holds the same value as the provided one but uses the given components.
     *
     * @param <T> The wrapped object type
     * @param holder The holder whose value to use
     * @param components The components to use
     * @return A new direct holder of the provided holder's value with the given components.
     */
    public static <T> Holder<T> withComponents(Holder<T> holder, DataComponentMap components) {
        return new Holder.Direct<>(holder.value(), components);
    }
}
