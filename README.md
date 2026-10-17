# Kirin UI

[![Build Status](https://github.com/eternalfusion/Kirin/actions/workflows/gradle-build.yml/badge.svg)](https://github.com/eternalfusion/Kirin/actions/workflows/gradle-build.yml)
![License](https://img.shields.io/github/license/eternalfusion/Kirin)


Embedded common code used across several Mine Little Pony projects to create their GUIs.

https://minelittlepony-mod.com

This is a fork of [MineLittlePony/Kirin](https://github.com/MineLittlePony/Kirin) that builds Kirin for both Fabric and NeoForge from one source tree. All credit for the original work goes to its authors; the NeoForge port is by [eternalfusion](https://github.com/eternalfusion). Please report problems with this fork [here](https://github.com/eternalfusion/Kirin/issues), not to the original project.

## Building

Kirin targets Minecraft 26.3 and is built on the MultiLoader-Template layout. JDK 25 is required (Gradle will provision it through the toolchain resolver if it is missing).

| Project    | Purpose                                                                                                   |
|------------|-----------------------------------------------------------------------------------------------------------|
| `common`   | All shared code, mixins, assets, the access widener and access transformer. Compiled against vanilla (NeoForm). |
| `fabric`   | Fabric entrypoint and platform service (Fabric Loader only, no Fabric API).                               |
| `neoforge` | NeoForge entrypoint and platform service.                                                                 |

Build everything (this also runs the unit tests and the access parity check):

```
./gradlew build
```

The mod jars are written to `fabric/build/libs/kirin-fabric-<version>.jar` and `neoforge/build/libs/kirin-neoforge-<version>.jar`. Each one is self-contained and includes the code from `common`.

Run a development client or server:

```
./gradlew :kirin-fabric:runClient
./gradlew :kirin-neoforge:runClient
./gradlew :kirin-fabric:runServer
./gradlew :kirin-neoforge:runServer
```

Development runs are started with `-Dkirin.debug=true`, which adds the Kirin test screen button to the title screen and logs `[Kirin] client ready on <loader>` once the client has started. Pass `-Dkirin.smoketest=true` to the client run (for example `./gradlew :kirin-fabric:runClient -Dkirin.smoketest=true`) to make the client stop itself right after that line, which is useful for automated checks.

Publish all three artifacts (`com.minelittlepony:kirin-common`, `kirin-fabric` and `kirin-neoforge`) to your local Maven repository so other projects can build against them:

```
./gradlew publishToMavenLocal
```

### Access widener and access transformer

Fabric reads `common/src/main/resources/kirin.aw` and NeoForge reads `common/src/main/resources/META-INF/accesstransformer.cfg`. They must always open up the same set of targets, so change both files together. `./gradlew check` (and therefore `build`) runs `checkAccessParity`, which fails if the two files differ.

Interface injection is likewise declared twice: in `common/interfaces.json` for NeoForge and under `custom` -> `loom:injected_interfaces` in `fabric/src/main/resources/fabric.mod.json` for Fabric.

### Platform services

Code in `common` must not reference Fabric or NeoForge classes. Loader specific functionality is exposed through `com.minelittlepony.common.platform.Platform`, which each loader project implements and registers as a `ServiceLoader` service:

```java
Platform platform = Platform.get();
if (platform.isModLoaded("modmenu")) { /* ... */ }
Path config = platform.getConfigDirectory();
```

Mods that depend on Kirin and need their own loader specific code can use the same mechanism: declare an interface in their common code, implement it in each loader project, list the implementation in `META-INF/services/<interface name>`, and look it up with `Services.load(MyService.class)`.

Kirin on Fabric does not need Fabric API. Running it on Quilt has not been verified.

## Installation (Modders)

This fork is not published to a public Maven repository. Run `./gradlew publishToMavenLocal` in this repository and add `mavenLocal()` to your project's repositories.

Loader projects depend on the loader specific artifact and bundle it into their own jar:

```groovy
// Fabric (Loom)
dependencies {
    implementation 'com.minelittlepony:kirin-fabric:1.22.2'
    include 'com.minelittlepony:kirin-fabric:1.22.2'
}

// NeoForge (ModDevGradle)
dependencies {
    implementation 'com.minelittlepony:kirin-neoforge:1.22.2'
    jarJar('com.minelittlepony:kirin-neoforge:[1.22.2,)')
}
```

Projects that share code between loaders (a `common` project) may only use `com.minelittlepony:kirin-common:1.22.2` as `compileOnly`. Never put `kirin-common` on a runtime classpath: it carries `kirin.mixin.json`, `kirin.aw` and the access transformer, which must only be loaded through the loader specific jar.
