package it.polimi.ingsw.utils.messages.serverMessages.Errors;

import it.polimi.ingsw.client.CliStatesController;
import it.polimi.ingsw.commons.User;
import it.polimi.ingsw.network.Client;

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
            CliStatesController.updateCliState(this, handler.getUI());
        }
    }
}
