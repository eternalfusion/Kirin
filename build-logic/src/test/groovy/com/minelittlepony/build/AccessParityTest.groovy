package com.minelittlepony.build

import org.junit.jupiter.api.Test

class AccessParityTest {
    @Test void awClassFieldMethodAreNormalized() {
        def aw = AccessParity.parseAccessWidener([
            'accessWidener v2 official',
            'accessible class net/minecraft/a/B$C',
            'accessible field net/minecraft/a/B tooltip Lnet/minecraft/a/T;',
            'mutable    field net/minecraft/a/B tooltip Lnet/minecraft/a/T;',
            'accessible method net/minecraft/a/B make (I)Lnet/minecraft/a/T;',
            '# comment', ''])
        assert aw == ['net.minecraft.a.B$C', 'net.minecraft.a.B#tooltip', 'net.minecraft.a.B#make(I)Lnet/minecraft/a/T;'] as Set
    }

    @Test void atClassFieldMethodAreNormalized() {
        def at = AccessParity.parseAccessTransformer([
            'public net.minecraft.a.B$C',
            'public-f net.minecraft.a.B tooltip',
            'public net.minecraft.a.B make(I)Lnet/minecraft/a/T; # comment'])
        assert at == ['net.minecraft.a.B$C', 'net.minecraft.a.B#tooltip', 'net.minecraft.a.B#make(I)Lnet/minecraft/a/T;'] as Set
    }

    @Test void diffReportsBothDirections() {
        def d = AccessParity.diff(['x.A', 'x.B#f'] as Set, ['x.A', 'x.C'] as Set)
        assert d == ['only in AW: x.B#f', 'only in AT: x.C']
    }

    @Test void diffEmptyWhenEqual() {
        assert AccessParity.diff(['x.A'] as Set, ['x.A'] as Set).isEmpty()
    }
}
