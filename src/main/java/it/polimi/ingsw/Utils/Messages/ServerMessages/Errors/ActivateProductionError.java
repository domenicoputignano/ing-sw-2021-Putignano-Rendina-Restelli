package it.polimi.ingsw.Utils.Messages.ServerMessages.Errors;

public class ActivateProductionError implements ErrorMessage {
    private final Trigger trigger;
    public enum Trigger
    {
        INVALIDREQUEST("Invalid requested production !"),
        DEPOTEMPTY("Not enough resources in depots !"),
        DEPOTNOTFOUND("Selected resources not found in depot !"),
        STRONGBOXEMPTY("Not enough resources in strongbox"),
        RESOURCEMISMATCH("Selected resources don't match required resources !");
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
