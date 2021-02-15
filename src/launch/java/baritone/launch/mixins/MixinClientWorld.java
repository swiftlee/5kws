package baritone.launch.mixins;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import baritone.api.pathing.goals.GoalBlock;
import baritone.api.pathing.goals.GoalXZ;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.Helper;
import baritone.api.utils.IPlayerContext;
import baritone.api.utils.input.Input;
import baritone.utils.player.PrimaryPlayerContext;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TextFormatting;

@Mixin(ClientWorld.class)
public class MixinClientWorld implements Helper {
    // int playerId, AbstractClientPlayerEntity playerEntityIn
   /* @Inject(method = "addPlayer", at = @At("RETURN"))
    public void doPathOnPlayerSpawn(int playerID, AbstractClientPlayerEntity playerEntityIn, CallbackInfo ci) {
        // TODO:
        // Master receives information from other players on join
        // Master sends invitation to party for each player
        // Once party is full (depending on game type for specified master), start
        // process:
        //
        // Process:
        // Put compass in hand, select first item slot
        // Right click to open gui
        // Click item slot with index 13 (bed)
        // Path to specified game type (1v1,2v2,3v3,4v4)
        // Right click on entity to join lobby

        // ISSUE: inconsistent opening of game menu, setting item to 0 is working though
        // Still need to figure out how to click bed

        *//*
         * ExecutorService executor = Executors.newSingleThreadExecutor(); class
         * MoveTask implements Runnable, Helper {
         *
         * @Override public void run() { try {
         *//*
        // if (world != null) {
        IBaritone baritone = BaritoneAPI.getProvider().getPrimaryBaritone();
        // baritone.getPlayerContext();
        ClientPlayerEntity player = baritone.getPlayerContext().player();
        int entityId = player.getEntityId();
        logDirect(String.format("Entity id: %d", entityId));

        Entity entity = player.world.getEntityByID(entityId);
        PlayerEntity playerEntity = (PlayerEntity) entity;
        ActionResultType type = playerEntity.interactOn(playerEntity, playerEntity.getActiveHand());
        if (type.SUCCESS)
            if (player != null && playerEntityIn.getUniqueID().equals(player.getUniqueID())) {
                logDirect(String.format("IDs match? -> \u00A7a%s ==? %s", playerEntityIn.getUniqueID().toString(),
                        player.getUniqueID().toString()));
                // baritone.getPlayerContext().playerController().
                // open minigame lobby selection menu
                if (player.inventory != null) {
                    int item = player.inventory.currentItem;
                    player.inventory.changeCurrentItem(item > 0 ? -item : 0);
                    logDirect(String.format("Item slot: %d", player.inventory.currentItem));
                    logDirect("\u00A7eIDs \u00A7u\u00A7amatched\u00A7r\u00A7e, in first if statement");
                    if (player.inventory.currentItem == 0) {
                        Hand hand = playerEntityIn.getActiveHand();
                        // KeyBinding.onTick(mc.gameSettings.keyBindUseItem.getDefault());

                        // FOR REFERENCE LATER WHEN CLICKING INVENTORY
                        // baritone.getPlayerContext().playerController().windowClick(windowId, slotId,
                        // mouseButton, type, player)
                        baritone.getInputOverrideHandler().clearAllKeys();
                        baritone.getInputOverrideHandler().setInputForceState(Input.CLICK_RIGHT, true);
                        logDirect("After input state forced to click");

                        // logDirect("\u00A7aSuccessfully right clicked!");
                        // logDirect("Current item was \u00A7b\u00A7l0, \u00A7r\u00A77in nested if
                        // statement");

                        // logDirect(String.format("The container %s null.",
                        // player.openContainer != null ? "\u00A7a\u00A7uwas not" :
                        // "\u00A7c\u00A7uwas"));

                        // logDirect(
                        // String.format("\u00A77The container was of type: \u00A7e\u00A7l%s",
                        // player.openContainer));

                        // if (player.openContainer instanceof ChestContainer) {
                        // ChestContainer gui = (ChestContainer) player.openContainer;
                        // logDirect("Trying to click on bed");
                        // logDirect("\u00A77Container type:\u00A7e" + gui.getType().toString());
                        // gui.slotClick(13, 0, ClickType.PICKUP, player);
                        // }

                        // }
                    }
                    *//*
                     * } else { Thread.sleep(500); run(); }
                     *//*
                    *//*
                     * } catch (Exception e) { }
                     *//*
                    // }
                }
            }
    }*/
}
// executor.execute(new MoveTask());