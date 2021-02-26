package baritone.jedis;

public final class JedisAPI {
  private static final JedisProvider provider;

  static {
    try {
      provider = (JedisProvider) Class.forName("baritone.jedis.JedisProvider").newInstance();
    } catch (ReflectiveOperationException ex) {
      throw new RuntimeException(ex);
    }
  }

  public static JedisProvider getProvider() {
    return JedisAPI.provider;
  }
}