package it.polimi.ingsw.utils.messages.serverMessages.Errors;

import it.polimi.ingsw.client.CliStatesController;
import it.polimi.ingsw.commons.User;
import it.polimi.ingsw.network.Client;

/**
 * Class containing information about errors triggered
 * while performing a {@link it.polimi.ingsw.model.TakeResourcesFromMarket} action.
 * */
public class TakeResourcesFromMarketError extends ErrorMessage {
    private final Trigger trigger;

    /**
     * Enum that links each error with a simple description.
     */
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

    /**
     * Method called by {@link Client} to display the error.
     * @param handler Client instance that has to handle the message.
     */
    @Override
    public void handleMessage(Client handler) {
        if(handler.getUI().isReceiverAction(triggeringUser)){
            handler.getUI().renderError(trigger.toString());
            CliStatesController.updateCliState(this, handler.getUI());
        }
    }
}
