package baritone.jedis;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import baritone.api.utils.Helper;

import com.google.common.collect.Sets;

public class Party implements Helper {
  private String name;
  private JedisProvider provider;

  public Party(String name) {
    this.name = name;
    this.provider = JedisAPI.getProvider();
  }

  public Party(String name, String... members) {
    this(name);
    storeMembers(Sets.newHashSet(members));
  }

  public void storeMember(String uuid) {
    logDirect(String.format("Adding %s to party %s", uuid, name));
    provider.getJedis().sadd(name, uuid);
  }

  public void storeMember(UUID uuid) {
    storeMember(uuid.toString());
  }

  public void storeMembers(Collection<String> uuids) {
    uuids.forEach(this::storeMember);
  }

  public Set<String> getAllMembers() {
    return provider.getJedis().smembers(name);
  }

  public byte getPartySize() {
    return provider.getJedis().scard(name).byteValue();
  }

  public boolean isInParty(UUID uuid) {
    return provider.getJedis().sismember(name, uuid.toString());
  }

}
// https://redis.io/commands/linsert