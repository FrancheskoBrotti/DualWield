# DualWield

A Minecraft plugin that enables dual-wielding mechanics for players.

## Fork Information

This repository is a **fork of a fork**:

- **Original plugin:** [ranull/dualwield](https://gitlab.com/ranull/minecraft/dualwield) on GitLab
- **Intermediate fork:** [JaySmethers/dualwield](https://gitlab.com/JaySmethers/dualwield) on GitLab — this repo is a direct fork of this one
- **This fork:** Adds Minecraft 1.21.11 Paper support and ongoing maintenance

## Requirements

- Java 21+
- Maven 3.6+
- Spigot BuildTools (for building NMS modules — see below)

## How to Clone & Build

### 1. Clone the repository

```bash
git clone https://github.com/FrancheskoBrotti/DualWield.git
cd DualWield
git checkout dev
```

### 2. Install BuildTools dependencies

The plugin uses NMS (net.minecraft.server) internals and requires Spigot artifacts installed locally via [BuildTools](https://www.spigotmc.org/wiki/buildtools/).

Download BuildTools:
```bash
mkdir -p ~/buildtools && cd ~/buildtools
curl -o BuildTools.jar https://hub.spigotmc.org/jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar
```

For **Minecraft 1.21.11** (the currently supported version, requires Java 21):
```bash
java -jar BuildTools.jar --rev 1.21.11 --compile CRAFTBUKKIT --remapped
```

> **Note:** If you want to build support for older Minecraft versions (1.9–1.20), you will need to run BuildTools for each version. Older versions (1.9–1.16) require Java 8. See the [BuildTools wiki](https://www.spigotmc.org/wiki/buildtools/) for details.

### 3. Build the plugin

```bash
cd /path/to/DualWield
mvn clean package
```

The built jar will be at:
```
target/DualWield-<version>.jar
```

