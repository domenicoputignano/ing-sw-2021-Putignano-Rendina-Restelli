package it.polimi.ingsw.Utils;

import it.polimi.ingsw.Model.Depot;
import it.polimi.ingsw.Model.MarketTray.Marble;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TakeResourcesFromMarketMessage {
    private List<Pair<Marble, MarbleDestination>> whereToPutMarbles = new ArrayList<>();
    private MarketChoice playerChoice;
    private int index;
    private List<Integer> whiteEffects = new ArrayList<>();


    public List<Pair<Marble, MarbleDestination>> getWhereToPutMarbles() {
        return whereToPutMarbles;
    }

    public MarketChoice getPlayerChoice() {
        return playerChoice;
    }

    public List<Integer> getWhiteEffects() {
        return whiteEffects;
    }

    public void setWhereToPutMarbles(List<Pair<Marble, MarbleDestination>> whereToPutMarbles) {
        this.whereToPutMarbles = whereToPutMarbles;
    }

    public void setPlayerChoice(MarketChoice playerChoice, int index) {
        this.playerChoice = playerChoice;
        this.index = index;
    }

    public void setWhiteEffects(List<Integer> whiteEffects) {
        this.whiteEffects = whiteEffects;
    }
}
