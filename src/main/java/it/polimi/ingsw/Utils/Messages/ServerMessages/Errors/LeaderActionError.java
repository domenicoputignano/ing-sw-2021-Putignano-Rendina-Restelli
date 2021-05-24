package it.polimi.ingsw.Utils.Messages.ServerMessages.Errors;

import it.polimi.ingsw.Client.ClientStatesController;
import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Network.Client;

public class LeaderActionError extends ErrorMessage {
    private final Trigger trigger;
    public enum Trigger
    {
        REQUIREMENTS("Requirements not satisfied !"),
        LEADERSTATUS("Not available action !");
        private final String description;
        Trigger(String description)
        {
            this.description = description;
        }
        public String toString() {
            return this.description;
        }
    }

    public LeaderActionError(User user, Trigger trigger) {
        super(user);
        this.trigger = trigger;
    }

    @Override
    public void handleMessage(Client handler) {
        if(handler.getUI().isReceiverAction(triggeringUser)) {
            handler.getUI().renderError(trigger.toString());
            ClientStatesController.updateClientState(this, handler.getUI());
        }
    }
}
