package it.polimi.ingsw.client.reducedmodel;

import it.polimi.ingsw.commons.ColorCard;
import it.polimi.ingsw.commons.Deck;
import it.polimi.ingsw.commons.DevelopmentCard;
import it.polimi.ingsw.commons.User;
import it.polimi.ingsw.utils.messages.serverMessages.Updates.*;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public abstract class ReducedGame implements Serializable {

    protected List<ReducedPlayer> players;
    protected ReducedTurn turn;
    protected ReducedPlayer currPlayer;
    protected ReducedMarketTray marketTray;
    protected List<Deck> decks;
    protected final boolean isSoloMode;


    protected ReducedGame(List<ReducedPlayer> players,List<Deck> decks, ReducedMarketTray marketTray, boolean isSoloMode) {
        this.players = players;
        this.decks = decks;
        this.marketTray = marketTray;
        this.isSoloMode = isSoloMode;
        this.currPlayer = players.get(0);
    }


    //Overloading methods to perform updates with different messages from server
    public synchronized void performUpdate(ActivateProductionUpdate message) {
        updatePersonalBoard(message);
    }

    public synchronized void performUpdate(BuyDevCardPerformedUpdate message) {
        updatePersonalBoard(message);
        decks = message.getResultingDecks();
    }

    public synchronized void performUpdate(TakeResourcesFromMarketUpdate message) {
        updatePersonalBoard(message);
        marketTray = message.getResultingMarketTray();
    }

    public synchronized void performUpdate(ServerAsksForPositioning message) {
        updatePersonalBoard(message);
        marketTray = message.getResultingMarketTray();
    }

    public synchronized ReducedPlayer getPlayer(User user) {
        return players.stream().filter(x -> x.getNickname().equals(user)).
                findFirst().orElseGet(() -> new ReducedPlayer(null, null, 0));
    }



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

    public String getDeckTopCardAsASCII(int level, ColorCard color, int row){
        if(isAnyCardPresentInDeck(level,color))
            return decks.stream().filter(x -> x.getCardType().getLevel()==level && x.getCardType().getColor()==color)
                    .collect(Collectors.toList()).get(0).getTop().toASCII(row);
        else return "                           ";
    }

    public synchronized boolean isAnyCardPresentInDeck(int level, ColorCard color){
        return decks.stream().anyMatch(x -> x.getCardType().getLevel()==level && x.getCardType().getColor()==color && !x.isEmpty());
    }

    public DevelopmentCard getDeckTopCard(int level, ColorCard color) {
        if(isAnyCardPresentInDeck(level,color)) {
            return decks.stream().filter(x -> x.getCardType().getLevel() == level && x.getCardType().getColor()==color)
                    .collect(Collectors.toList()).get(0).getTop();
        } else
            return null;
    }

    public boolean isSoloMode() {
        return isSoloMode;
    }

    public List<ReducedPlayer> getPlayers() {
        return players;
    }

    public abstract void printPlayers();
}

