package it.polimi.ingsw.Utils.Messages.ServerMessages.Errors;

public class PositioningError implements ErrorMessage {
    private final Trigger trigger;
    public enum Trigger
    {
        DISCARDEDRESOURCES("Resources discarded because wrongly indicated positioning !");
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

    public PositioningError(Trigger trigger)
    {
        this.trigger = trigger;
    }

    public Trigger getTrigger() {
        return trigger;
    }
}
