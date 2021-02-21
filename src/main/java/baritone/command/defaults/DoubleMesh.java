package baritone.command.defaults;

import baritone.api.IBaritone;
import baritone.api.command.Command;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.command.datatypes.BlockById;
import baritone.api.command.exception.CommandException;
import redis.clients.jedis.Jedis;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class DoubleMesh extends Command {
    protected DoubleMesh(IBaritone baritone) {
        super(baritone, "doublemesh");
    }

    @Override
    public void execute(String label, IArgConsumer args) throws CommandException {
        Jedis jedis = new Jedis();
        jedis.set("ping", "pong");
        logDirect("ping: " + jedis.get("ping"));
        jedis.close();
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
