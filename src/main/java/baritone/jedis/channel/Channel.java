package baritone.jedis.channel;

import redis.clients.jedis.JedisPubSub;

public enum Channel {
  ROLE_ASSIGNMENT, SEND_INVITATION, ACK_INVITATION;

  public JedisPubSub getHandler() {
    switch (this) {
      case ROLE_ASSIGNMENT:
        return new RoleAssignmentHandler();
      case SEND_INVITATION:
        return new SendInvitationHandler();
      case ACK_INVITATION:
        return new SendInvitationHandler();
      default:
        return new SendInvitationHandler();
    }
  }

  @Override
  public String toString() {
    switch (this) {
      case ROLE_ASSIGNMENT:
        return "role_assignment";
      case SEND_INVITATION:
        return "send_invitation";
      case ACK_INVITATION:
        return "ack_invitation";
      default:
        return "";
    }
  }
}
