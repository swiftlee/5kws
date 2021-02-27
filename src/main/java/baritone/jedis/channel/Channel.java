package baritone.jedis.channel;

import redis.clients.jedis.JedisPubSub;

public enum Channel {
  ROLE_ASSIGNMENT, SEND_INVITATION, CREATE_PARTY;

  public JedisPubSub getHandler() {
    switch (this) {
      case ROLE_ASSIGNMENT:
        return new RoleAssignmentHandler();
      case SEND_INVITATION:
        return new SendInvitationHandler();
      case CREATE_PARTY:
        return new CreatePartyHandler();
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
      case CREATE_PARTY:
        return "create_party";
      default:
        return "";
    }
  }
}
