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

package baritone.command;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import baritone.Baritone;
import baritone.api.event.events.WorldEvent;
import baritone.api.event.events.type.EventState;
import baritone.api.event.listener.AbstractGameEventListener;
import baritone.api.pathing.goals.Goal;
import baritone.api.pathing.goals.GoalXZ;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.Helper;
import baritone.cache.WorldProvider;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import baritone.api.command.argument.IArgConsumer;

public class DoubleMeshControl implements Helper, AbstractGameEventListener {

    Baritone baritone;

    public DoubleMeshControl(Baritone baritone) {
        baritone.getGameEventHandler().registerEventListener(this);
        this.baritone = baritone;
    }

    @Override
    public void onWorldEvent(WorldEvent event) {
        // If we have a numeric first argument, then parse arguments as coordinates.
        // Note: There is no reason to want to go where you're already at so there
        // is no need to handle the case of empty arguments.
        WorldProvider cache = this.baritone.getWorldProvider();
        // TODO: crashing here?? why tho
        if (event.getWorld() != null) {
            cache.initWorld(event.getWorld().getDimensionKey());
            ExecutorService executor = Executors.newSingleThreadExecutor();
            class MoveTask implements Runnable {
                @Override
                public void run() {
                    ClientPlayerEntity player = baritone.getPlayerContext().player();
                    if (player == null) {
                        try {
                            Thread.sleep(500);
                            run();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    logDirect("I'm here!!");
                    BetterBlockPos origin = baritone.getPlayerContext().playerFeet();
                    Goal goal = new GoalXZ(origin.getX() + 20, origin.getZ());
                    logDirect(String.format("Going to: %s", goal.toString()));
                    baritone.getCustomGoalProcess().setGoalAndPath(goal);

                }

            }
            executor.execute(new MoveTask());
        }
        // IArgConsumer args = new IArgConsumer()

        // baritone.getCustomGoalProcess().setGoalAndPath(goal);
        /*
         * args.requireMax(1); BlockOptionalMeta destination =
         * args.getDatatypeFor(ForBlockOptionalMeta.INSTANCE);
         * baritone.getGetToBlockProcess().getToBlock(destination);
         */
    }
}