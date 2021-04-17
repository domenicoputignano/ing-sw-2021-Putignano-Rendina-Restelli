package it.polimi.ingsw.Utils;

import it.polimi.ingsw.Model.Depot;
import it.polimi.ingsw.Model.MarketTray.Marble;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TakeResourcesFromMarketMessage {
    List<Pair<Marble, MarbleDestination>> whereToPutMarbles = new ArrayList<>();
    MarketChoice playerChoice;
    List<Integer> whiteEffects = new ArrayList<>();


    public List<Pair<Marble, MarbleDestination>> getWhereToPutMarbles() {
        return whereToPutMarbles;
    }

    public MarketChoice getPlayerChoice() {
        return playerChoice;
    }

    public List<Integer> getWhiteEffects() {
        return whiteEffects;
    }
}
