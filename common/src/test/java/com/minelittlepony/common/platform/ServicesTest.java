package com.minelittlepony.common.platform;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ServicesTest {
    interface Missing {}

    @Test
    void missingImplementationNamesTheInterface() {
        var e = assertThrows(IllegalStateException.class, () -> Services.load(Missing.class));
        assertTrue(e.getMessage().contains(Missing.class.getName()));
    }
}
