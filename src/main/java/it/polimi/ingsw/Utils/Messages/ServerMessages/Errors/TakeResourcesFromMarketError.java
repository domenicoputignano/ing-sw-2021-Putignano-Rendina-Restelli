package it.polimi.ingsw.Utils.Messages.ServerMessages.Errors;

import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Errors.ErrorMessage;

public class TakeResourcesFromMarketError extends ErrorMessage {
    private final Trigger trigger;
    public enum Trigger
    {
        MARBLEMISMATCH("Selected marbles don't match marbles in Market Tray !"),
        WHITEEFFECTMISMATCH("Mismatch in selected white marbles effects !");
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

    public TakeResourcesFromMarketError(User user, Trigger trigger)
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
