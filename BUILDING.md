# Building OptiLeaves

Each port is a separate Gradle project. The wrapper downloads the required Gradle version on first use.

| Directory | Build JDK | Gradle |
| --- | --- | --- |
| `Forge/1.20.1` | 17 | 8.8 |
| `NeoForge/1.21.1` | 21 | 8.14.3 |
| `Fabric/1.20.1` | 21 | 8.12 |
| `Fabric/1.21.1` | 21 | 8.12 |

Install the listed JDK, set `JAVA_HOME`, and run from the port's directory:

```sh
./gradlew build --no-daemon
```

On Windows:

```powershell
.\gradlew.bat build --no-daemon
```

The mod jar is written to `build/libs/`. Fabric 1.20.1 uses JDK 21 for its build tools and targets Java 17 at runtime. Do not install jars ending in `-sources.jar` or `-dev.jar`.

An internet connection is required to download MC, mappings and build dependencies. CI checks compilation and packaging; it does not launch the game or measure performance.

The source code is subject to [LICENSE](LICENSE).
