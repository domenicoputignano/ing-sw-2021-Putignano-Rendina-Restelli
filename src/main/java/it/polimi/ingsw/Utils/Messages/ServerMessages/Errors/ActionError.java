package it.polimi.ingsw.Utils.Messages.ServerMessages.Errors;

import it.polimi.ingsw.Client.ClientStatesController;
import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Network.Client;



public class ActionError extends ErrorMessage {
    private final Trigger trigger;

    public enum Trigger {
        NORMALACTIONALREADYDONE("Normal action has been already done for this turn!"),
        NORMALACTIONNOTDONEYET("You have to do a normal action before ending the turn!"),
        WRONGTURNPHASE("Required action cannot be accomplished in this context!"),
        WRONGGAMEPHASE("Required action cannot be accomplished in this game phase!"),
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

    @Override
    public void handleMessage(Client handler) {
        if(handler.getUI().isReceiverAction(triggeringUser)) {
            handler.getUI().renderError(trigger.toString());
            ClientStatesController.updateClientState(this, handler.getUI());
        }
    }

}
