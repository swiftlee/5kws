package baritone.command.defaults;

import baritone.api.IBaritone;
import baritone.api.command.Command;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.command.argument.ICommandArgument;
import baritone.api.command.datatypes.BlockById;
import baritone.api.command.exception.CommandException;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

import baritone.jedis.JedisAPI;
import baritone.jedis.JedisProvider;
import baritone.jedis.Party;

//progfiles86 minecraft launcher runtime jrex64 bin logs
public class DoubleMesh extends Command {
    protected DoubleMesh(IBaritone baritone) {
        super(baritone, "doublemesh");
    }

    @Override
    public void execute(String label, IArgConsumer args) throws CommandException {
        // arg can be one of two: MANUAL vs AUTOMATIC
        // if MANUAL - allow master client to queue with friends
        // if AUTOMATIC -- treat master client as bot party leader

        JedisProvider provider = JedisAPI.getProvider();
        if (args.hasExactly(3)) {
            String[] strArgs = args.getArgs().stream().map(ICommandArgument::getValue).toArray(String[]::new);
            int partySize = Integer.valueOf(strArgs[1]);
            if (strArgs[0].equalsIgnoreCase("auto")) {
                // COMMENCE ROLE ASSIGNMENT!!!!!!!!!
                // Message msg = new Message();
                // String partyName = strArgs[2];
                // logDirect(String.format("Starting starting role assignment, party size: %d,
                // party name: %s", partySize,
                // partyName));
                // Collection<String> uuids = new HashSet<>();
                // for (int i = 0; i < partySize; i++) {
                // uuids.add("User#" + i);
                // }
                // Party party = new Party(partyName, uuids.stream().toArray(String[]::new));
                // logDirect(String.format("Team members: %s",
                // Arrays.toString(party.getAllMembers().stream().toArray(String[]::new))));

            } /*
               * else if (argStr.equalsIgnoreCase("s")) {
               * logDirect("Subscribing to SEND_INVITATION channel...");
               * provider.subscribe(Channel.SEND_INVITATION); } else if
               * (argStr.equalsIgnoreCase("p")) { logDirect("Publishing to channel...");
               * provider.publish(Channel.ROLE_ASSIGNMENT, "Initial published message");
               */
            // }
        } else {
            logDirect("Closing jedis connection...");
            provider.getJedis().close();
        }
        // jedis.set("ping", "pong");
        // logDirect("ping: " + jedis.get("ping"));
        return;
    }

    @Override
    public Stream<String> tabComplete(String label, IArgConsumer args) throws CommandException {
        // since it's either a goal or a block, I don't think we can tab complete
        // properly?
        // so just tab complete for the block variant
        return args.tabCompleteDatatype(BlockById.INSTANCE);
    }

    @Override
    public String getShortDesc() {
        return "Go to a coordinate or block";
    }

    @Override
    public List<String> getLongDesc() {
        return Arrays.asList("The goto command tells Baritone to head towards a given goal or block.", "",
                "Wherever a coordinate is expected, you can use ~ just like in regular Minecraft commands. Or, you can just use regular numbers.",
                "", "Usage:", "> goto <block> - Go to a block, wherever it is in the world",
                "> goto <y> - Go to a Y level", "> goto <x> <z> - Go to an X,Z position",
                "> goto <x> <y> <z> - Go to an X,Y,Z position");
    }
}
