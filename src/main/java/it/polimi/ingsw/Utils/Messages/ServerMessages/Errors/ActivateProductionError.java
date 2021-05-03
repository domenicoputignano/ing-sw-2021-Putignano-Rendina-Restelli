package it.polimi.ingsw.Utils.Messages.ServerMessages.Errors;

public class ActivateProductionError implements ErrorMessage {
    private final Trigger trigger;
    public enum Trigger
    {
        INVALIDREQUEST("Invalid requested production !"),
        PAYMENTERROR("Error found while performing payment! You wrongly selected resources from some between Depot(s), Strongbox or Extradepot(s)"),
        RESOURCESMISMATCH("Selected resources don't match required resources !"),
        NOTENOUGHRESOURCES("You don't have enough resources to activate selected productions !");
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

    public ActivateProductionError(Trigger trigger)
    {
        this.trigger = trigger;
    }

    public Trigger getTrigger() {
        return trigger;
    }
}
