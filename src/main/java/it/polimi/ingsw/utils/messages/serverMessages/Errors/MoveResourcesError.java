package it.polimi.ingsw.utils.messages.serverMessages.Errors;

import it.polimi.ingsw.client.CliStatesController;
import it.polimi.ingsw.commons.User;
import it.polimi.ingsw.network.Client;

/**
 * Class containing information about errors made by player who is attempting to settle pending resources coming
 * from market tray in his depots.
 */
public class MoveResourcesError extends ErrorMessage {
    private final Trigger trigger;

    /**
     * Enum that describes error encountered.
     */
    public enum Trigger
    {
        MOVE("Error occurred while performing your move action !");
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

    public MoveResourcesError(User user, Trigger trigger)
    {
        super(user);
        this.trigger = trigger;
    }

    /**
     * Method called by {@link Client} to display error itself.
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

