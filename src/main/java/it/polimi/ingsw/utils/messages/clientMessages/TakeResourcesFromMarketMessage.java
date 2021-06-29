package it.polimi.ingsw.utils.messages.clientMessages;

import it.polimi.ingsw.client.reducedmodel.ReducedMarble;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.TurnController;
import it.polimi.ingsw.network.RemoteView;
import it.polimi.ingsw.utils.MarbleDestination;
import it.polimi.ingsw.utils.MarketChoice;
import it.polimi.ingsw.utils.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class containing information on how to perform a {@link it.polimi.ingsw.model.TakeResourcesFromMarket} action.
 */
public class TakeResourcesFromMarketMessage implements TurnControllerHandleable {
    /**
     * List of {@link Pair}, each one links a marble instance to index of destination slot.
     */
    private List<Pair<ReducedMarble, MarbleDestination>> whereToPutMarbles = new ArrayList<>();
    /**
     * Selects between a column or a row of market board.
     */
    private MarketChoice playerChoice;
    /**
     * Row/Column index.
     */
    private int index;
    /**
     * List containing possible effects linked to {@link it.polimi.ingsw.model.marketTray.WhiteMarble}.
     */
    private List<Integer> whiteEffects = new ArrayList<>();


    public List<Pair<ReducedMarble, MarbleDestination>> getWhereToPutMarbles() {
        return whereToPutMarbles;
    }

    public MarketChoice getPlayerChoice() {
        return playerChoice;
    }

    public List<Integer> getWhiteEffects() {
        return whiteEffects;
    }


    /**
     * Adds a marble with index of depot in which settle resource .
     * @param whereToPutMarble instance of {@link Pair} with
     */
    public void addWhereToPutMarbles(Pair<ReducedMarble, MarbleDestination> whereToPutMarble) {
        this.whereToPutMarbles.add(whereToPutMarble);
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

    /**
     * Method used to bind a convert marble effect to a white marble.
     */
    public void addWhiteEffect(Integer choice) {
        this.whiteEffects.add(choice);
    }

    public void setWhiteEffects(List<Integer> whiteEffects) {
        this.whiteEffects = whiteEffects;
    }

    /**
     *
     */
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

    public List<ReducedMarble> getRequestedMarbles() {
        return whereToPutMarbles.stream().map(Pair::getKey).collect(Collectors.toList());
    }

    public void handleMessage(TurnController turnController, RemoteView sender) {
        turnController.handleTakeResourcesFromMarketMessage(this,sender);
    }

    public void handleMessage(GameController gameController, RemoteView sender) {
        handleMessage(gameController.getTurnController(), sender);
    }

}
