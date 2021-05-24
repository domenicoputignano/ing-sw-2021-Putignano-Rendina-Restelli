package it.polimi.ingsw.Utils.Messages.ServerMessages.Errors;

import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Network.Client;

public class BuyDevCardError extends ErrorMessage {
    private final Trigger trigger;
    public enum Trigger {
        EMPTYDECK("Required deck is not available !"),
        NOTENOUGHRESOURCES("You don't have enough resources to buy the card !"),
        RESOURCESMISMATCH("Selected resources don't match required resources !"),
        PAYMENTERROR("Error while performing payment, you wrongly selected resources from some between Depot(s), Strongbox or ExtraDepot(s) !");

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

    public BuyDevCardError(User user, Trigger trigger) {
        super(user);
        this.trigger = trigger;
    }

    public Trigger getTrigger() {
        return trigger;
    }

    @Override
    public void handleMessage(Client handler) {
        if(handler.getUI().isReceiverAction(triggeringUser)) {
            handler.getUI().renderError(trigger.toString());
        }
    }
}
