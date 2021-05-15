package it.polimi.ingsw.Utils.Messages.ServerMessages.Errors;

import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Network.Client;



public class ActionError extends ErrorMessage {
    private final Trigger trigger;

    @Override
    public void handleMessage(Client handler) {

    }

    public enum Trigger {
        NORMALACTIONALREADYDONE("Normal action has been already done for this turn!"),
        WRONGTURNPHASE("Required action cannot be accomplished in this context!"),
        WRONGGAMEPHASE("Required action cannot be accomplished in this game phase!"),
        RESOURCECHOICEMISMATCH("Number of selected resources is not compliant with Game rules!");

        private String description;
        private Trigger(String description)
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

}
