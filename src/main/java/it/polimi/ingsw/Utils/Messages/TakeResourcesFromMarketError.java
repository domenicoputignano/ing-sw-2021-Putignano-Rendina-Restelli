package it.polimi.ingsw.Utils.Messages;

public class TakeResourcesFromMarketError implements ErrorMessage {
    private final Trigger trigger;
    public enum Trigger
    {
        MARBLEMISMATCH("Selected marbles don't match marbles in Market Tray !"),
        WHITEEFFECTMISMATCH("Mismatch in selected white marbles effects !");
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

    public TakeResourcesFromMarketError(Trigger trigger)
    {
        this.trigger = trigger;
    }

    public Trigger getTrigger() {
        return trigger;
    }
}
