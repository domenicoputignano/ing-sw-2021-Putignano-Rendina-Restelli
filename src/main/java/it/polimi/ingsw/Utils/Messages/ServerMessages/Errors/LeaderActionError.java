package it.polimi.ingsw.Utils.Messages.ServerMessages.Errors;

import it.polimi.ingsw.Network.Client;

public class LeaderActionError extends ErrorMessage {
    private final Trigger trigger;
    public enum Trigger
    {
        REQUIREMENTS("Requirements not satisfied !"),
        LEADERSTATUS("Not available action !"),
        ;
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

    public LeaderActionError(Trigger trigger)
    {
        this.trigger = trigger;
    }

    public Trigger getTrigger() {
        return trigger;
    }


    @Override
    public void handleMessage(Client handler) {

    }
}
