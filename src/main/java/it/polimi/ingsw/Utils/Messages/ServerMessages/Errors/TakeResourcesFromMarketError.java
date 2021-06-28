package it.polimi.ingsw.Utils.Messages.ServerMessages.Errors;

import it.polimi.ingsw.Client.CliStatesController;
import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Network.Client;

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
