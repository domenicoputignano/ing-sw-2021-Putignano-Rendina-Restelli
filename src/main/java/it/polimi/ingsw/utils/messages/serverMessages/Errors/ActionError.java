package it.polimi.ingsw.utils.messages.serverMessages.Errors;

import it.polimi.ingsw.client.CliStatesController;
import it.polimi.ingsw.commons.User;
import it.polimi.ingsw.network.Client;


/**
 * Class that contains details related to an error encountered before even starting a normal action.
 * These errors are concerned situation like for example: a player is trying to do a normal action twice in the same turn,
 * or he is ending his turn without make it.
 */
public class ActionError extends ErrorMessage {
    private final Trigger trigger;

    /**
     * Enum representing for each possible error, a statement that describes it.
     */
    public enum Trigger {
        NORMALACTIONALREADYDONE("Normal action has been already done for this turn!"),
        NORMALACTIONNOTDONEYET("You have to do a normal action before ending the turn!"),
        WRONGTURNPHASE("Required action cannot be accomplished in this context!"),
        RESOURCECHOICEMISMATCH("Number of selected resources is not compliant with Game rules!"),
        INITIALRESOURCEPOSITIONINGERROR("Selected resources cannot be settled in your depots according to game rules," +
                "please redo selection");

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

    public ActionError(User user, Trigger trigger) {
        super(user);
        this.trigger = trigger;
    }

    /**
     * Method called by {@link Client} in order to display it.
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
