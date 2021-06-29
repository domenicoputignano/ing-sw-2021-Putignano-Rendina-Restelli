package it.polimi.ingsw.client.reducedmodel;

import it.polimi.ingsw.commons.ColorCard;
import it.polimi.ingsw.commons.Deck;
import it.polimi.ingsw.commons.DevelopmentCard;
import it.polimi.ingsw.commons.User;
import it.polimi.ingsw.utils.messages.serverMessages.Updates.*;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
/**
 * This class represents a simplified version of the Game with respect to the server's class.
 * It contains only the information required client side.
 */
public abstract class ReducedGame implements Serializable {
    /**
     * Attributes that represent the contents of the Game
     */
    protected List<ReducedPlayer> players;
    protected ReducedTurn turn;
    protected ReducedPlayer currPlayer;
    protected ReducedMarketTray marketTray;
    protected List<Deck> decks;
    protected final boolean isSoloMode;

    /**
     * Initialize an instance by setting players,decks,marketTray of the Game.
     */
    protected ReducedGame(List<ReducedPlayer> players,List<Deck> decks, ReducedMarketTray marketTray, boolean isSoloMode) {
        this.players = players;
        this.decks = decks;
        this.marketTray = marketTray;
        this.isSoloMode = isSoloMode;
        this.currPlayer = players.get(0);
    }
    /**
     * Overloading methods to perform updates with different messages from server
     * @param message Activate production update message
     * @see ActivateProductionUpdate
     */
    public synchronized void performUpdate(ActivateProductionUpdate message) {
        updatePersonalBoard(message);
    }
    /**
     * Overloading methods to perform updates with different messages from server
     * @param message BuyDevCard update message
     * @see BuyDevCardPerformedUpdate
     */
    public synchronized void performUpdate(BuyDevCardPerformedUpdate message) {
        updatePersonalBoard(message);
        decks = message.getResultingDecks();
    }
    /**
     * Overloading methods to perform updates with different messages from server
     * @param message TakeResourcesFromMarket update message
     * @see TakeResourcesFromMarketUpdate
     */
    public synchronized void performUpdate(TakeResourcesFromMarketUpdate message) {
        updatePersonalBoard(message);
        marketTray = message.getResultingMarketTray();
    }
    /**
     * Overloading methods to perform updates with different messages from server
     * @param message ServerAsksForPositioning update message
     * @see ServerAsksForPositioning
     */
    public synchronized void performUpdate(ServerAsksForPositioning message) {
        updatePersonalBoard(message);
        marketTray = message.getResultingMarketTray();
    }

    public synchronized ReducedPlayer getPlayer(User user) {
        return players.stream().filter(x -> x.getNickname().equals(user)).
                findFirst().orElseGet(() -> new ReducedPlayer(null, null, 0));
    }


    /**
     * method used to update the PersonalBoard after an action has been performed
     * @param message UpdateMessage
     * @see UpdateMessage
     */
    public synchronized void updatePersonalBoard(UpdateMessage message) {
        getPlayer(message.getUser()).updatePersonalBoard(message.getUserPersonalBoard());
    }


    public synchronized ReducedPlayer getCurrPlayer() {
        return currPlayer;
    }

    public synchronized List<Deck> getDecks() {
        return decks;
    }


    public synchronized ReducedMarketTray getMarketTray() {
        return marketTray;
    }
    /**
     * Return representation of the top Development card used by command line interface.
     */
    public String getDeckTopCardAsASCII(int level, ColorCard color, int row){
        if(isAnyCardPresentInDeck(level,color))
            return decks.stream().filter(x -> x.getCardType().getLevel()==level && x.getCardType().getColor()==color)
                    .collect(Collectors.toList()).get(0).getTop().toASCII(row);
        else return "                           ";
    }

    /**
     * method used to check that there are cards inside the indicated deck
     * @param level deck level
     * @param color deck color
     * @return true if there are cards inside the indicated deck, false otherwise
     */
    public synchronized boolean isAnyCardPresentInDeck(int level, ColorCard color){
        return decks.stream().anyMatch(x -> x.getCardType().getLevel()==level && x.getCardType().getColor()==color && !x.isEmpty());
    }

    /**
     * method used to return the top card of the indicated deck
     * @param level deck level
     * @param color deck color
     * @return development card at the top of the deck
     */
    public DevelopmentCard getDeckTopCard(int level, ColorCard color) {
        if(isAnyCardPresentInDeck(level,color)) {
            return decks.stream().filter(x -> x.getCardType().getLevel() == level && x.getCardType().getColor()==color)
                    .collect(Collectors.toList()).get(0).getTop();
        } else
            return null;
    }

    /**
     * method that indicates whether the match is of type SoloMode or not
     * @return true if the game is of type SoloMode, false if it is of type MultiPlayerMode
     */
    public boolean isSoloMode() {
        return isSoloMode;
    }

    /**
     * method that returns the list of players of the game
     * @return list of players
     */
    public List<ReducedPlayer> getPlayers() {
        return players;
    }

    public abstract void printPlayers();
}

