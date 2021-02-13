package baritone.launch.mixins;

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
import baritone.api.utils.input.Input;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.inventory.container.ClickType;

@Mixin(ClientWorld.class)
public class MixinClientWorld implements Helper {
    // int playerId, AbstractClientPlayerEntity playerEntityIn
    @Inject(method = "addPlayer", at = @At("RETURN"))
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
        
        //ISSUE: inconsistent opening of game menu, setting item to 0 is working though
        //Still need to figure out how to click bed

        /*
         * ExecutorService executor = Executors.newSingleThreadExecutor(); class
         * MoveTask implements Runnable, Helper {
         * 
         * @Override public void run() { try {
         */
        // if (world != null) {
        IBaritone baritone = BaritoneAPI.getProvider().getPrimaryBaritone();
        ClientPlayerEntity player = baritone.getPlayerContext().player();
        // String color = playerEntityIn.getUniqueID().equals(player.getUniqueID()) ?
        // "§a" : "§c";
        // logDirect(String.format("IDs match? -> %s%s ==? %s", color,
        // playerEntityIn.getUniqueID().toString(),
        // player.getUniqueID().toString()));
        if (player == null) {
            // try {
            // Thread.sleep(500);
            // run();
            // } catch (InterruptedException e) {
            // e.printStackTrace();
            // }
            logDirect("Player was null!");
            // return;
        } else if (playerEntityIn.getUniqueID().equals(player.getUniqueID())) {
            // open minigame lobby selection menu
            int item = player.inventory.currentItem;
            player.inventory.changeCurrentItem(item > 0 ? -item : 0);
            logDirect(String.format("Item slot: %d", player.inventory.currentItem));
            // player.inventory.currentItem = 0; // set item to first hotbar slot
            baritone.getInputOverrideHandler().setInputForceState(Input.CLICK_RIGHT, true); // right click to open gui
            if (player.openContainer != null) {
                logDirect("Trying to click on bed");
                player.openContainer.slotClick(13, 0, ClickType.PICKUP, player);
            }
            /*
             * GoalBlock goal = new GoalBlock(1, 2, 3);
             * logDirect(String.format("Going to: %s", goal.toString()));
             * baritone.getCustomGoalProcess().setGoalAndPath(goal);
             */
        }
        /*
         * } else { Thread.sleep(500); run(); }
         */
        /*
         * } catch (Exception e) { }
         */
        // }

    }
    // executor.execute(new MoveTask());
}