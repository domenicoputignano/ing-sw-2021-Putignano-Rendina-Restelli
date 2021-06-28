package it.polimi.ingsw.utils.messages.serverMessages.Errors;

import it.polimi.ingsw.client.CliStatesController;
import it.polimi.ingsw.commons.User;
import it.polimi.ingsw.network.Client;

public class TakeResourcesFromMarketError extends ErrorMessage {
    private final Trigger trigger;
    public enum Trigger
    {
        MARBLEMISMATCH("Selected marbles don't match marbles in Market Tray !"),
        WHITEEFFECTMISMATCH("Mismatch in selected white marbles effects !");
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

    public TakeResourcesFromMarketError(User user, Trigger trigger) {
        super(user);
        this.trigger = trigger;
    }

    @Override
    public void handleMessage(Client handler) {
        if(handler.getUI().isReceiverAction(triggeringUser)){
            handler.getUI().renderError(trigger.toString());
            CliStatesController.updateCliState(this, handler.getUI());
        }
    }
}
