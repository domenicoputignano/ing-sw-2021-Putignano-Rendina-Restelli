package it.polimi.ingsw.utils.messages.serverMessages.Errors;

import it.polimi.ingsw.client.CliStatesController;
import it.polimi.ingsw.commons.User;
import it.polimi.ingsw.network.Client;

/**
 * Class containing details about
 * errors that can occur while performing a {@link it.polimi.ingsw.model.BuyDevCard} action.
 */
public class BuyDevCardError extends ErrorMessage {
    private final Trigger trigger;

    /**
     * Enum representing a specific statement for each possible error.
     */
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

    /**
     * Method used by {@link Client}  to display the error.
     * @param handler Client instance that has to handle the message.
     */
    @Override
    public void handleMessage(Client handler) {
        if(handler.getUI().isReceiverAction(triggeringUser)) {
            handler.getUI().renderError(trigger.toString());
            CliStatesController.updateCliState(this, handler.getUI());
        }
    }
}
