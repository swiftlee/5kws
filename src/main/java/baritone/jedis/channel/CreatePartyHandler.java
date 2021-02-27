package baritone.jedis.channel;

import baritone.api.utils.Helper;
import redis.clients.jedis.JedisPubSub;

public class CreatePartyHandler extends JedisPubSub implements Helper {

  @Override
  public void onMessage(String channel, String message) {
    logDirect(String.format("Message in channel %s received: %s", channel, message));
  }

  @Override
  public void onSubscribe(String channel, int subscribed) {
    logDirect(String.format("Subscribed to channel %s", channel));
  }
}