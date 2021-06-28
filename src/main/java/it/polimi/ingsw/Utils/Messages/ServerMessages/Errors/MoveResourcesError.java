package it.polimi.ingsw.Utils.Messages.ServerMessages.Errors;

import it.polimi.ingsw.Client.CliStatesController;
import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Network.Client;

public class MoveResourcesError extends ErrorMessage {
    private final Trigger trigger;
    public enum Trigger
    {
        MOVE("Error occurred while performing your move action !");
        private final String description;
        Trigger(String description)
        {
            this.description = description;
        }

        public String toString()
        {
            return this.description;
        }
    }

    public MoveResourcesError(User user, Trigger trigger)
    {
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

