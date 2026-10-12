package com.minelittlepony.build

/**
 * Compares the targets of a Fabric access widener against a NeoForge access transformer.
 *
 * Targets are normalized to strings: a class is {@code a.b.C$D}, a field is {@code a.b.C$D#name}
 * and a method is {@code a.b.C$D#name(desc)ret}. Access modifiers are intentionally not compared.
 */
class AccessParity {
    static Set<String> parseAccessWidener(List<String> lines) {
        Set<String> targets = new LinkedHashSet<>()
        for (String line : lines) {
            List<String> tokens = tokenize(line)
            if (tokens.isEmpty() || tokens[0] == 'accessWidener') {
                continue
            }
            // <access> <kind> <owner> [<name> <desc>]
            if (tokens.size() < 3) {
                throw new IllegalArgumentException("Malformed access widener line: '${line}'")
            }
            String owner = dotted(tokens[2])
            switch (tokens[1]) {
                case 'class':
                    targets << owner
                    break
                case 'field':
                    targets << (owner + '#' + tokens[3])
                    break
                case 'method':
                    targets << (owner + '#' + tokens[3] + tokens[4])
                    break
                default:
                    throw new IllegalArgumentException("Unknown access widener target kind '${tokens[1]}': '${line}'")
            }
        }
        return targets
    }

    static Set<String> parseAccessTransformer(List<String> lines) {
        Set<String> targets = new LinkedHashSet<>()
        for (String line : lines) {
            List<String> tokens = tokenize(line)
            if (tokens.isEmpty()) {
                continue
            }
            // <access> <owner> [<name> | <name>(desc)ret]
            if (tokens.size() == 2) {
                targets << tokens[1]
            } else if (tokens.size() == 3) {
                targets << (tokens[1] + '#' + tokens[2])
            } else {
                throw new IllegalArgumentException("Malformed access transformer line: '${line}'")
            }
        }
        return targets
    }

    static List<String> diff(Set<String> aw, Set<String> at) {
        List<String> result = []
        result.addAll(aw.findAll { !at.contains(it) }.sort().collect { 'only in AW: ' + it })
        result.addAll(at.findAll { !aw.contains(it) }.sort().collect { 'only in AT: ' + it })
        return result
    }

    private static List<String> tokenize(String line) {
        int comment = line.indexOf('#')
        String code = comment >= 0 ? line.substring(0, comment) : line
        code = code.trim()
        return code.isEmpty() ? [] : code.split(/\s+/).toList()
    }

    private static String dotted(String internalName) {
        return internalName.replace('/', '.')
    }
}
