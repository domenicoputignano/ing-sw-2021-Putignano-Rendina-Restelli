package it.polimi.ingsw.Utils.Messages.ServerMessages.Errors;

public class BuyDevCardError implements ErrorMessage {
    private final Trigger trigger;
    public enum Trigger {
        EMPTYDECK("Required deck is not available !"),
        NOTENOUGHRESOURCES("You don't have enough resources to buy the card !"),
        RESOURCESMISMATCH("Selected resources don't match required resources !");

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

    public BuyDevCardError(Trigger trigger)
    {
        this.trigger = trigger;
    }

    public Trigger getTrigger() {
        return trigger;
    }

}
