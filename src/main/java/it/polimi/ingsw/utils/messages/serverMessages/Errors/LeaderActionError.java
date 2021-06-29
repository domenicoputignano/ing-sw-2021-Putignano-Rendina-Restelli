package it.polimi.ingsw.utils.messages.serverMessages.Errors;

import it.polimi.ingsw.client.CliStatesController;
import it.polimi.ingsw.commons.User;
import it.polimi.ingsw.network.Client;

/**
 * Class containing information about errors
 * found while performing a {@link it.polimi.ingsw.model.LeaderAction}.
 */
public class LeaderActionError extends ErrorMessage {
    private final Trigger trigger;

    /**
     * Enum describing errors.
     */
    public enum Trigger {
        REQUIREMENTS("Requirements not satisfied !"),
        LEADERSTATUS("Not available action !");
        private final String description;
        Trigger(String description)
        {
            this.description = description;
        }
        public String toString() {
            return this.description;
        }
    }

    public LeaderActionError(User user, Trigger trigger) {
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
