package it.polimi.ingsw.Utils.Messages;

public class MoveResourcesError implements ErrorMessage {
    private final Trigger trigger;
    public enum Trigger
    {
        MOVE("Error occurred while performing your move action !");
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

    public MoveResourcesError(Trigger trigger)
    {
        this.trigger = trigger;
    }

    public Trigger getTrigger() {
        return trigger;
    }
}
