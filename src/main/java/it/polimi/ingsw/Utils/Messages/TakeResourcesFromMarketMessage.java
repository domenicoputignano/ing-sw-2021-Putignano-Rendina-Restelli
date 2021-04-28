package it.polimi.ingsw.Utils.Messages;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Controller.TurnController;
import it.polimi.ingsw.Model.MarketTray.Marble;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Utils.MarbleDestination;
import it.polimi.ingsw.Utils.MarketChoice;
import it.polimi.ingsw.Utils.Messages.ClientMessage;
import it.polimi.ingsw.Utils.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TakeResourcesFromMarketMessage implements TurnControllerHandleable {
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

    public void setPlayerChoice(MarketChoice playerChoice) {
        this.playerChoice = playerChoice;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex()
    {
        return index;
    }

    public void setPlayerChoice(MarketChoice playerChoice, int index) {
        this.playerChoice = playerChoice;
        this.index = index;
    }

    public void setWhiteEffects(List<Integer> whiteEffects) {
        this.whiteEffects = whiteEffects;
    }

    public boolean isValidMessage()
    {
        if(playerChoice == null) return false;
        if(playerChoice == MarketChoice.ROW && !(whereToPutMarbles.size() == 4)) return false;
        if(playerChoice == MarketChoice.COLUMN && !(whereToPutMarbles.size() == 3)) return false;
        if(whereToPutMarbles.stream().anyMatch(x -> x.getKey() == null || x.getValue() == null)) return false;
        if(playerChoice == MarketChoice.ROW && (index<=0 || index>3)) return false;
        if(playerChoice == MarketChoice.COLUMN && (index<=0 || index>4)) return false;
        if(whiteEffects.size()!=0 && whiteEffects.stream().anyMatch(x -> x<1 || x>2)) return false;
        return true;
    }

    public List<Marble> getRequestedMarbles() {
        return whereToPutMarbles.stream().map(x -> x.getKey()).collect(Collectors.toList());
    }

    public void handleMessage(TurnController turnController, Player sender) {
        turnController.handleTakeResourcesFromMarketMessage(this);
    }

    public void handleMessage(GameController gameController, Player sender) {
        handleMessage(gameController.getTurnController(), sender);
    }

}
