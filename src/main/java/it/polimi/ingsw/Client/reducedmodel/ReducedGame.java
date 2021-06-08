package it.polimi.ingsw.Client.reducedmodel;

import it.polimi.ingsw.Commons.ColorCard;
import it.polimi.ingsw.Commons.Deck;
import it.polimi.ingsw.Commons.DevelopmentCard;
import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Utils.Messages.ServerMessages.ServerMessage;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.NewTurnUpdate;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.*;

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
    public void performUpdate(ActivateProductionUpdate message) {
        updatePersonalBoard(message);
    }

    public void performUpdate(BuyDevCardPerformedUpdate message) {
        updatePersonalBoard(message);
        decks = message.getResultingDecks();
    }

    public void performUpdate(TakeResourcesFromMarketUpdate message) {
        updatePersonalBoard(message);
        marketTray = message.getResultingMarketTray();
    }

    public void performUpdate(ServerAsksForPositioning message) {
        updatePersonalBoard(message);
        marketTray = message.getResultingMarketTray();
    }

    public ReducedPlayer getPlayer(User user) {
        return players.stream().filter(x -> x.getNickname().equals(user)).
                findFirst().orElseGet(() -> new ReducedPlayer(null, null, 0));
    }


    public void updatePersonalBoard(UpdateMessage message) {
        getPlayer(message.getUser()).updatePersonalBoard(message.getUserPersonalBoard());
    }


    public ReducedPlayer getCurrPlayer() {
        return currPlayer;
    }

    public List<Deck> getDecks() {
        return decks;
    }


    public ReducedMarketTray getMarketTray() {
        return marketTray;
    }

    public String getDeckTopCardAsASCII(int level, ColorCard color, int row){
        if(isAnyCardPresentInDeck(level,color))
            return decks.stream().filter(x -> x.getCardType().getLevel()==level && x.getCardType().getColor()==color)
                    .collect(Collectors.toList()).get(0).getTop().toASCII(row);
        else return "|                            |";
    }

    public boolean isAnyCardPresentInDeck(int level, ColorCard color){
        return decks.stream().anyMatch(x -> x.getCardType().getLevel()==level && x.getCardType().getColor()==color && !x.isEmpty());
    }

    public DevelopmentCard getDeckTopCard(int level, ColorCard color) {
        if(isAnyCardPresentInDeck(level,color)) {
            return decks.stream().filter(x -> x.getCardType().getLevel() == level && x.getCardType().getColor()==color)
                    .collect(Collectors.toList()).get(0).getTop();
        } else
            return null;
    }

}

