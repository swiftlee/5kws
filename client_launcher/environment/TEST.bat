@echo off
title 5kws launchwrapper

set MC_DIR=%APPDATA%/.minecraft

set GAME_DIR=%MC_DIR%

set LIBRARIES_DIR=%MC_DIR%/libraries

set ASSETS_DIR=%MC_DIR%/assets
set ASSET_INDEX=1.16

set GAME_VERSION=1.16.5-Baritone
set NATIVES_DIR=%MC_DIR%/bin/%GAME_VERSION%

set LIBRARIES=%LIBRARIES_DIR%/net/minecraft/launchwrapper/1.12/launchwrapper-1.12.jar;^
%LIBRARIES_DIR%/5kws/baritone-api/1.6.3/baritone-api-1.6.3.jar;^
%LIBRARIES_DIR%/com/github/ImpactDevelopment/SimpleTweaker/1.2/SimpleTweaker-1.2.jar;^
%LIBRARIES_DIR%/org/spongepowered/mixin/0.7.11-SNAPSHOT/mixin-0.7.11-SNAPSHOT.jar;^
%LIBRARIES_DIR%/org/ow2/asm/asm-all/5.0.3/asm-all-5.0.3.jar;^
%LIBRARIES_DIR%/com/mojang/patchy/1.1/patchy-1.1.jar;^
%LIBRARIES_DIR%/oshi-project/oshi-core/1.1/oshi-core-1.1.jar;^
%LIBRARIES_DIR%/net/java/dev/jna/jna/4.4.0/jna-4.4.0.jar;^
%LIBRARIES_DIR%/net/java/dev/jna/platform/3.4.0/platform-3.4.0.jar;^
%LIBRARIES_DIR%/com/ibm/icu/icu4j/66.1/icu4j-66.1.jar;^
%LIBRARIES_DIR%/com/mojang/javabridge/1.0.22/javabridge-1.0.22.jar;^
%LIBRARIES_DIR%/net/sf/jopt-simple/jopt-simple/5.0.3/jopt-simple-5.0.3.jar;^
%LIBRARIES_DIR%/io/netty/netty-all/4.1.25.Final/netty-all-4.1.25.Final.jar;^
%LIBRARIES_DIR%/com/google/guava/guava/21.0/guava-21.0.jar;^
%LIBRARIES_DIR%/org/apache/commons/commons-lang3/3.5/commons-lang3-3.5.jar;^
%LIBRARIES_DIR%/commons-io/commons-io/2.5/commons-io-2.5.jar;^
%LIBRARIES_DIR%/commons-codec/commons-codec/1.10/commons-codec-1.10.jar;^
%LIBRARIES_DIR%/net/java/jinput/jinput/2.0.5/jinput-2.0.5.jar;^
%LIBRARIES_DIR%/net/java/jutils/jutils/1.0.0/jutils-1.0.0.jar;^
%LIBRARIES_DIR%/com/mojang/brigadier/1.0.17/brigadier-1.0.17.jar;^
%LIBRARIES_DIR%/com/mojang/datafixerupper/4.0.26/datafixerupper-4.0.26.jar;^
%LIBRARIES_DIR%/com/google/code/gson/gson/2.8.0/gson-2.8.0.jar;^
%LIBRARIES_DIR%/com/mojang/authlib/2.1.28/authlib-2.1.28.jar;^
%LIBRARIES_DIR%/org/apache/commons/commons-compress/1.8.1/commons-compress-1.8.1.jar;^
%LIBRARIES_DIR%/org/apache/httpcomponents/httpclient/4.3.3/httpclient-4.3.3.jar;^
%LIBRARIES_DIR%/commons-logging/commons-logging/1.1.3/commons-logging-1.1.3.jar;^
%LIBRARIES_DIR%/org/apache/httpcomponents/httpcore/4.3.2/httpcore-4.3.2.jar;^
%LIBRARIES_DIR%/it/unimi/dsi/fastutil/8.2.1/fastutil-8.2.1.jar;^
%LIBRARIES_DIR%/org/apache/logging/log4j/log4j-api/2.8.1/log4j-api-2.8.1.jar;^
%LIBRARIES_DIR%/org/apache/logging/log4j/log4j-core/2.8.1/log4j-core-2.8.1.jar;^
%LIBRARIES_DIR%/org/lwjgl/lwjgl/3.2.2/lwjgl-3.2.2.jar;^
%LIBRARIES_DIR%/org/lwjgl/lwjgl-jemalloc/3.2.2/lwjgl-jemalloc-3.2.2.jar;^
%LIBRARIES_DIR%/org/lwjgl/lwjgl-openal/3.2.2/lwjgl-openal-3.2.2.jar;^
%LIBRARIES_DIR%/org/lwjgl/lwjgl-opengl/3.2.2/lwjgl-opengl-3.2.2.jar;^
%LIBRARIES_DIR%/org/lwjgl/lwjgl-glfw/3.2.2/lwjgl-glfw-3.2.2.jar;^
%LIBRARIES_DIR%/org/lwjgl/lwjgl-stb/3.2.2/lwjgl-stb-3.2.2.jar;^
%LIBRARIES_DIR%/org/lwjgl/lwjgl-tinyfd/3.2.2/lwjgl-tinyfd-3.2.2.jar;^
%LIBRARIES_DIR%/com/mojang/text2speech/1.11.3/text2speech-1.11.3.jar;^
%MC_DIR%/versions/1.16.5/1.16.5.jar

set JVM_ARGS=-XX:HeapDumpPath=MojangTricksIntelDriversForPerformance_javaw.exe_minecraft.exe.heapdump "-Dos.name=Windows 10" -Dos.version=10.0 -Xss1M -Djava.library.path=%NATIVES_DIR% -Dminecraft.launcher.brand=minecraft-launcher -Dminecraft.launcher.version=2.2.1431 

set "JVM_RAM=-Xmx2G"
set "LAUNCHER_ARGS=%JVM_RAM% -XX:+UnlockExperimentalVMOptions -XX:+UseG1GC -XX:G1NewSizePercent=20 -XX:G1ReservePercent=20 -XX:MaxGCPauseMillis=50 -XX:G1HeapRegionSize=32M -Dlog4j.configurationFile=%ASSETS_DIR%/log_configs/client-1.12.xml net.minecraft.launchwrapper.Launch"

%SystemDrive%
cd "%SystemDrive%\Program Files (x86)\Minecraft*\runtime\jre-x64\bin"

javaw.exe ^
%JVM_ARGS% ^
-cp %LIBRARIES% ^
%LAUNCHER_ARGS% ^
--username %1 ^
--version %GAME_VERSION% ^
--gameDir %GAME_DIR% ^
--assetsDir %ASSETS_DIR% ^
--assetIndex %ASSET_INDEX% ^
--uuid %2 ^
--accessToken %3 ^
--userType mojang ^
--versionType release ^
--tweakClass baritone.launch.BaritoneTweaker