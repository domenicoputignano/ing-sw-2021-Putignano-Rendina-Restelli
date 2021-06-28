package it.polimi.ingsw.utils.messages.serverMessages.Errors;

import it.polimi.ingsw.client.CliStatesController;
import it.polimi.ingsw.commons.User;
import it.polimi.ingsw.network.Client;

public class ActivateProductionError extends ErrorMessage {
    private final Trigger trigger;
    public enum Trigger
    {
        INVALIDREQUEST("Invalid requested production !"),
        PAYMENTERROR("Error found while performing payment! You wrongly selected resources from some between Depot(s), Strongbox or Extradepot(s)"),
        RESOURCESMISMATCH("Selected resources don't match required resources !"),
        NOTENOUGHRESOURCES("You don't have enough resources to activate selected productions !");
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

    public ActivateProductionError(User user, Trigger trigger) {
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
