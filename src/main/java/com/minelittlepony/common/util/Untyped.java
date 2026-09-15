package com.minelittlepony.common.util;

import java.util.Optional;

public interface Untyped {
    /**
     * Performs an unchecked type cast.
     * <p>
     * Will throw a type cast exception if the types are not compatible.
     */
    @SuppressWarnings("unchecked")
    static <A, B> B cast(A a) {
        return (B)a;
    }

    static <A, B> Optional<B> cast(Optional<A> optional) {
        return optional.map(Untyped::cast);
    }
}
