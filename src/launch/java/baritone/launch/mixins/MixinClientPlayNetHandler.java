/*
 * This file is part of Baritone.
 *
 * Baritone is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Baritone is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Baritone.  If not, see <https://www.gnu.org/licenses/>.
 */

package baritone.launch.mixins;

import baritone.Baritone;
import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import baritone.api.event.events.ChunkEvent;
import baritone.api.event.events.type.EventState;
import baritone.api.utils.Helper;
import baritone.api.utils.IPlayerContext;
import baritone.api.utils.IPlayerController;
import baritone.cache.CachedChunk;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.*;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

import com.mojang.realmsclient.gui.RealmsServerSlotButton.Action;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author Brady
 * @since 8/3/2018
 */
@Mixin(ClientPlayNetHandler.class)
public class MixinClientPlayNetHandler implements Helper {

    // unused lol
    /*
     * @Inject( method = "handleChunkData", at = @At( value = "INVOKE", target =
     * "net/minecraft/client/multiplayer/ChunkProviderClient.func_212474_a(IILnet/minecraft/network/PacketBuffer;IZ)Lnet/minecraft/world/chunk/Chunk;"
     * ) ) private void preRead(SPacketChunkData packetIn, CallbackInfo ci) { for
     * (IBaritone ibaritone : BaritoneAPI.getProvider().getAllBaritones()) {
     * ClientPlayerEntity player = ibaritone.getPlayerContext().player(); if (player
     * != null && player.connection == (ClientPlayNetHandler) (Object) this) {
     * ibaritone.getGameEventHandler().onChunkEvent( new ChunkEvent( EventState.PRE,
     * packetIn.isFullChunk() ? ChunkEvent.Type.POPULATE_FULL :
     * ChunkEvent.Type.POPULATE_PARTIAL, packetIn.getChunkX(), packetIn.getChunkZ()
     * ) ); } } }
     */

    @Inject(method = "handleJoinGame", at = @At("RETURN"))
    private void doPathOnClientJoinGame(SJoinGamePacket packetIn, CallbackInfo ci) {
        String prefix = "\u00A77\u00A7l[\u00A7e\u00A7l!\u00A77\u00A7l]\u00A7r ";
        logDirect("Bri'ish are ya? Ow'd ya know?");
        IBaritone baritone = BaritoneAPI.getProvider().getPrimaryBaritone();
        IPlayerContext primaryPlayerContext = baritone.getPlayerContext();
        ClientPlayerEntity player = primaryPlayerContext.player();
        if (player != null) {
            logDirect("Player is not null");
            World world = player.world;
            Entity entity = world.getEntityByID(packetIn.getPlayerId());
            if (entity != null && entity.getUniqueID().equals(player.getUniqueID())) {
                logDirect("Entity is not null");
                logDirect(prefix + "\u00A7a\u00A7l\u00A7uIDs matched!");
                IPlayerController playerController = primaryPlayerContext.playerController();
                if (playerController != null) {
                    ActionResultType result = playerController.processRightClick(player, world, Hand.MAIN_HAND);

                    if (result == ActionResultType.SUCCESS) {
                        logDirect("success");
                    } else {
                        logDirect("fail");
                    }

                    Thread t = new Thread(() -> {
                        // TODO: why tho
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        Container gui = player.openContainer;
                        /*
                         * while (gui == player.container) { gui =
                         * BaritoneAPI.getProvider().getPrimaryBaritone().getPlayerContext()
                         * .player().openContainer; }
                         */
                        if (gui != null) {
                            int slotId = 13;
                            ItemStack s = playerController.windowClick(player.openContainer.windowId, slotId, 1,
                                    ClickType.PICKUP, player);
                            logDirect("Item stack: " + s.getDisplayName());
                        } else {
                            logDirect("I plead Oopsie-Daisy Your Honor");
                        }
                    });

                    t.start();

                }

                // baritone.getInputOverrideHandler().clearAllKeys();
                // baritone.getInputOverrideHandler().setInputForceState(Input.CLICK_RIGHT,
                // true);
                logDirect("After trying to click!");
            }
            // FOR REFERENCE LATER WHEN CLICKING INVENTORY
            // baritone.getPlayerContext().playerController().windowClick(windowId,
            // slotId,
            // mouseButton, type, player)
        }

    }

    @Inject(method = "handleChunkData", at = @At("RETURN"))
    private void postHandleChunkData(SChunkDataPacket packetIn, CallbackInfo ci) {
        for (IBaritone ibaritone : BaritoneAPI.getProvider().getAllBaritones()) {
            ClientPlayerEntity player = ibaritone.getPlayerContext().player();
            if (player != null && player.connection == (ClientPlayNetHandler) (Object) this) {
                ibaritone.getGameEventHandler()
                        .onChunkEvent(new ChunkEvent(EventState.POST,
                                packetIn.isFullChunk() ? ChunkEvent.Type.POPULATE_FULL
                                        : ChunkEvent.Type.POPULATE_PARTIAL,
                                packetIn.getChunkX(), packetIn.getChunkZ()));
            }
        }
    }

    @Inject(method = "processChunkUnload", at = @At("HEAD"))
    private void preChunkUnload(SUnloadChunkPacket packet, CallbackInfo ci) {
        for (IBaritone ibaritone : BaritoneAPI.getProvider().getAllBaritones()) {
            ClientPlayerEntity player = ibaritone.getPlayerContext().player();
            if (player != null && player.connection == (ClientPlayNetHandler) (Object) this) {
                ibaritone.getGameEventHandler().onChunkEvent(
                        new ChunkEvent(EventState.PRE, ChunkEvent.Type.UNLOAD, packet.getX(), packet.getZ()));
            }
        }
    }

    @Inject(method = "processChunkUnload", at = @At("RETURN"))
    private void postChunkUnload(SUnloadChunkPacket packet, CallbackInfo ci) {
        for (IBaritone ibaritone : BaritoneAPI.getProvider().getAllBaritones()) {
            ClientPlayerEntity player = ibaritone.getPlayerContext().player();
            if (player != null && player.connection == (ClientPlayNetHandler) (Object) this) {
                ibaritone.getGameEventHandler().onChunkEvent(
                        new ChunkEvent(EventState.POST, ChunkEvent.Type.UNLOAD, packet.getX(), packet.getZ()));
            }
        }
    }

    @Inject(method = "handleBlockChange", at = @At("RETURN"))
    private void postHandleBlockChange(SChangeBlockPacket packetIn, CallbackInfo ci) {
        if (!Baritone.settings().repackOnAnyBlockChange.value) {
            return;
        }
        if (!CachedChunk.BLOCKS_TO_KEEP_TRACK_OF.contains(packetIn.getState().getBlock())) {
            return;
        }
        for (IBaritone ibaritone : BaritoneAPI.getProvider().getAllBaritones()) {
            ClientPlayerEntity player = ibaritone.getPlayerContext().player();
            if (player != null && player.connection == (ClientPlayNetHandler) (Object) this) {
                ibaritone.getGameEventHandler().onChunkEvent(new ChunkEvent(EventState.POST,
                        ChunkEvent.Type.POPULATE_FULL, packetIn.getPos().getX() >> 4, packetIn.getPos().getZ() >> 4));
            }
        }
    }

    @Inject(method = "handleMultiBlockChange", at = @At("RETURN"))
    private void postHandleMultiBlockChange(SMultiBlockChangePacket packetIn, CallbackInfo ci) {
        if (!Baritone.settings().repackOnAnyBlockChange.value) {
            return;
        }
        ChunkPos[] chunkPos = new ChunkPos[1];
        packetIn.func_244310_a((pos, state) -> {
            if (CachedChunk.BLOCKS_TO_KEEP_TRACK_OF.contains(state.getBlock())) {
                chunkPos[0] = new ChunkPos(pos);
            }
        });
        if (chunkPos[0] == null) {
            return;
        }
        for (IBaritone ibaritone : BaritoneAPI.getProvider().getAllBaritones()) {
            ClientPlayerEntity player = ibaritone.getPlayerContext().player();
            if (player != null && player.connection == (ClientPlayNetHandler) (Object) this) {
                ibaritone.getGameEventHandler().onChunkEvent(
                        new ChunkEvent(EventState.POST, ChunkEvent.Type.POPULATE_FULL, chunkPos[0].x, chunkPos[0].z));
            }
        }
    }

    @Inject(method = "handleCombatEvent", at = @At(value = "INVOKE", target = "net/minecraft/client/Minecraft.displayGuiScreen(Lnet/minecraft/client/gui/screen/Screen;)V"))
    private void onPlayerDeath(SCombatPacket packetIn, CallbackInfo ci) {
        for (IBaritone ibaritone : BaritoneAPI.getProvider().getAllBaritones()) {
            ClientPlayerEntity player = ibaritone.getPlayerContext().player();
            if (player != null && player.connection == (ClientPlayNetHandler) (Object) this) {
                ibaritone.getGameEventHandler().onPlayerDeath();
            }
        }
    }
}
