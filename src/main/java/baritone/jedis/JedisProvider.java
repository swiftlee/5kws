package baritone.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import baritone.api.utils.Helper;
import baritone.jedis.channel.Channel;

public final class JedisProvider implements Helper {
  private final JedisPool jedisPool;
  // a handler has many subscribed channels
  private JedisPubSub handler;
  private final Set<String> subscribedChannels;

  {
    JedisPoolConfig config = new JedisPoolConfig();
    config.setMaxTotal(128);
    config.setMaxIdle(128);
    this.jedisPool = new JedisPool(config);
    this.subscribedChannels = new HashSet<>();
  }

  public Jedis getJedis() {
    return this.jedisPool.getResource();
  }

  public void publish(Channel channel, String message) {
    logDirect(String.format("Sending message: %s to channel: %s", message, channel.toString()));
    try {
      getJedis().publish(channel.toString(), message);
    } catch (Exception e) {
      logDirect("Error with class name: " + e.getClass().getName());
      logDirect(e.getMessage());
    }
  }

  public void subscribe(Channel channel) {
    this.handler = channel.getHandler();
    Thread subscriberThread = new Thread(() -> {
      try {
        subscribedChannels.add(channel.toString());
        getJedis().subscribe(handler, channel.toString());
      } catch (Exception e) {
        e.printStackTrace();
      }
    });

    ExecutorService exec = Executors.newSingleThreadExecutor();
    exec.execute(subscriberThread);
  }

  public void initRoleAssignment() {
    subscribe(Channel.ROLE_ASSIGNMENT);
  }

  public void unsubscribe(Channel channel, String message) {
    this.handler.unsubscribe(channel.toString());
  }

  public void unsubscribeAll() {
    this.handler.unsubscribe(subscribedChannels.stream().toArray(String[]::new));
  }
}
