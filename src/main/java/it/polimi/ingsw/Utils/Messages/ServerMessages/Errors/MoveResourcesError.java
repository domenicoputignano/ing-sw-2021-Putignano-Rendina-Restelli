package it.polimi.ingsw.Utils.Messages.ServerMessages.Errors;

import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Network.Client;

public class MoveResourcesError extends ErrorMessage {
    private final Trigger trigger;
    public enum Trigger
    {
        MOVE("Error occurred while performing your move action !");
        private String description;
        private Trigger(String description)
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

    public Trigger getTrigger() {
        return trigger;
    }

    @Override
    public void handleMessage(Client handler) {

    }
}

