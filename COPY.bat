rem @echo off
title 5kws launchwrapper

set MC_DIR=%APPDATA%\.minecraft

set GAME_DIR=%MC_DIR%

set LIBRARIES_DIR=%MC_DIR%\libraries

set ASSETS_DIR=%MC_DIR%\assets
set ASSETS_INDEX=1.16

set GAME_VERSION=1.16.5-Baritone
set NATIVES_DIR=%MC_DIR%\bin\%GAME_VERSION%

set LIBRARIES_LIST=%LIBRARIES_DIR%\com\github\ImpactDevelopment\SimpleTweaker\1.2\SimpleTweaker-1.2.jar;^
%LIBRARIES_DIR%\org\spongepowered\mixin\0.7.11-SNAPSHOT\mixin-0.7.11-SNAPSHOT.jar;^
%LIBRARIES_DIR%\org\ow2\asm\asm-all\5.0.3\asm-all-5.0.3.jar;^
%LIBRARIES_DIR%\5kws\baritone-api\1.6.3\baritone-api-1.6.3.jar;^
%LIBRARIES_DIR%\net\minecraft\launchwrapper\1.12\launchwrapper-1.12.jar;^
%LIBRARIES_DIR%\org\ow2\asm\asm-all\5.0.3\asm-all-5.0.3.jar;^
%LIBRARIES_DIR%\org\jline\jline\3.12.1\jline-3.12.1.jar;^
%LIBRARIES_DIR%\net\java\dev\jna\jna\4.4.0\jna-4.4.0.jar;^
%LIBRARIES_DIR%\net\sf\jopt-simple\jopt-simple\5.0.3\jopt-simple-5.0.3.jar;^
%LIBRARIES_DIR%\java3d\vecmath\1.5.2\vecmath-1.5.2.jar;^
%LIBRARIES_DIR%\org\apache\maven\maven-artifact\3.6.0\maven-artifact-3.6.0.jar;^
%LIBRARIES_DIR%\com\mojang\patchy\1.1\patchy-1.1.jar;^
%LIBRARIES_DIR%\oshi-project\oshi-core\1.1\oshi-core-1.1.jar;^
%LIBRARIES_DIR%\net\java\dev\jna\jna\4.4.0\jna-4.4.0.jar;^
%LIBRARIES_DIR%\net\java\dev\jna\platform\3.4.0\platform-3.4.0.jar;^
%LIBRARIES_DIR%\com\ibm\icu\icu4j-core-mojang\51.2\icu4j-core-mojang-51.2.jar;^
%LIBRARIES_DIR%\net\sf\jopt-simple\jopt-simple\5.0.4\jopt-simple-5.0.4.jar;^
%LIBRARIES_DIR%\com\paulscode\codecjorbis\20101023\codecjorbis-20101023.jar;^
%LIBRARIES_DIR%\com\paulscode\codecwav\20101023\codecwav-20101023.jar;^
%LIBRARIES_DIR%\com\paulscode\libraryjavasound\20101123\libraryjavasound-20101123.jar;^
%LIBRARIES_DIR%\com\paulscode\librarylwjglopenal\20100824\librarylwjglopenal-20100824.jar;^
%LIBRARIES_DIR%\com\paulscode\soundsystem\20120107\soundsystem-20120107.jar;^
%LIBRARIES_DIR%\io\netty\netty-all\4.1.25.Final\netty-all-4.1.25.Final.jar;^
%LIBRARIES_DIR%\com\google\guava\guava\21.0\guava-21.0.jar;^
%LIBRARIES_DIR%\org\apache\commons\commons-lang3\3.5\commons-lang3-3.5.jar;^
%LIBRARIES_DIR%\commons-io\commons-io\2.5\commons-io-2.5.jar;^
%LIBRARIES_DIR%\commons-codec\commons-codec\1.10\commons-codec-1.10.jar;^
%LIBRARIES_DIR%\net\java\jinput\jinput\2.0.5\jinput-2.0.5.jar;^
%LIBRARIES_DIR%\net\java\jutils\jutils\1.0.0\jutils-1.0.0.jar;^
%LIBRARIES_DIR%\com\google\code\gson\gson\2.8.0\gson-2.8.0.jar;^
%LIBRARIES_DIR%\com\mojang\datafixerupper\4.0.26\datafixerupper-4.0.26.jar;^
%LIBRARIES_DIR%\com\mojang\brigadier\1.0.17\brigadier-1.0.17.jar;^
%LIBRARIES_DIR%\com\mojang\authlib\2.1.28\authlib-2.1.28.jar;^
%LIBRARIES_DIR%\com\mojang\realms\1.13.9\realms-1.13.9.jar;^
%LIBRARIES_DIR%\org\apache\commons\commons-compress\1.8.1\commons-compress-1.8.1.jar;^
%LIBRARIES_DIR%\org\apache\httpcomponents\httpclient\4.3.3\httpclient-4.3.3.jar;^
%LIBRARIES_DIR%\commons-logging\commons-logging\1.1.3\commons-logging-1.1.3.jar;^
%LIBRARIES_DIR%\org\apache\httpcomponets\httpcore\4.3.2\httpcore-4.3.2.jar;^
%LIBRARIES_DIR%\it\unimi\dsi\fastutil\8.2.1\fastutil-8.2.1.jar;^
%LIBRARIES_DIR%\org\apache\logging\log4j\log4j-api\2.11.2\log4j-api-2.11.2.jar;^
%LIBRARIES_DIR%\org\apache\logging\log4j\log4j-core\2.11.2\log4j-core-2.11.2.jar;^
%LIBRARIES_DIR%\org\lwjgl\lwjgl\lwjgl\2.9.4-nightly-20150209\lwjgl-2.9.4-nightly-20150209.jar;^
%LIBRARIES_DIR%\org\lwjgl\lwjgl\lwjgl_util\2.9.4-nightly-20150209\lwjgl_util-2.9.4-nightly-20150209.jar;^
%LIBRARIES_DIR%\org\lwjgl\lwjgl\lwjgl-platform\2.9.4-nightly-20150209\lwjgl-platform-2.9.4-nightly-20150209.jar;^
%LIBRARIES_DIR%\com\mojang\text2speech\1.11.3\text2speech-1.11.3.jar;^
%LIBRARIES_DIR%\com\mojang\text2speech\1.11.3\text2speech-1.11.3.jar;^
C:\Users\jmc18\AppData\Roaming\.minecraft\versions\1.16.5\1.16.5.jar

set PLAYER_NAME=ledreth@gmail.com

set WINDOW_W=800
set WINDOW_H=640

set "JVM_RAM=-Xmx2G"
set "JVM_ARGS=-XX:+UnlockExperimentalVMOptions -XX:+UseG1GC -XX:G1NewSizePercent=20 -XX:G1ReservePercent=20 -XX:MaxGCPauseMillis=50 -XX:G1HeapRegionSize=32M"

set TWEAK_CLASS=baritone.launch.BaritoneTweaker


%SystemDrive%
cd %MC_DIR%

java %JVM_RAM% %JVM_ARGS% ^
-Djava.library.path=%NATIVES_DIR% ^
-cp %LIBRARIES_LIST% ^
net.minecraft.launchwrapper.Launch ^
--username %PLAYER_NAME% ^
--version %GAME_VERSION% ^
--accessToken 0 --userProperties {} ^
--gameDir %GAME_DIR% ^
--assetsDir %ASSETS_DIR% ^
--assetIndex %ASSETS_INDEX% ^
--tweakClass %TWEAK_CLASS% ^
--width %WINDOW_W% ^
--height %WINDOW_H% ^
