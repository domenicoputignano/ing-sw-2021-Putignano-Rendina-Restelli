package it.polimi.ingsw.Utils.Messages.ServerMessages.Errors;

import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Network.Client;

public class ActivateProductionError extends ErrorMessage {
    private final Trigger trigger;
    public enum Trigger
    {
        INVALIDREQUEST("Invalid requested production !"),
        PAYMENTERROR("Error found while performing payment! You wrongly selected resources from some between Depot(s), Strongbox or Extradepot(s)"),
        RESOURCESMISMATCH("Selected resources don't match required resources !"),
        NOTENOUGHRESOURCES("You don't have enough resources to activate selected productions !");
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

    public ActivateProductionError(User user, Trigger trigger)
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
