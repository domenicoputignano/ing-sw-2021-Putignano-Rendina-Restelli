package it.polimi.ingsw.Utils.Messages.ServerMessages.Errors;

import javax.swing.*;

public class ActionError implements ErrorMessage {
    private final Trigger trigger;
    public enum Trigger {
        NORMALACTIONALREADYDONE("Normal action has been already done for this turn!"),
        WRONGTURNPHASE("Required action cannot be accomplished in this context!");

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

    public ActionError(Trigger trigger) {
        this.trigger = trigger;
    }

}
