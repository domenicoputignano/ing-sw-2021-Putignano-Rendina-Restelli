package it.polimi.ingsw.Client;

import it.polimi.ingsw.Commons.Deck;
import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.ActivateProductionUpdate;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.BuyDevCardPerformedUpdate;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.UpdateMessage;

import java.util.List;

public abstract class ReducedGame {

    protected List<ReducedPlayer> players;
    protected ReducedTurn turn;
    protected ReducedPlayer currPlayer;
    protected ReducedMarketTray marketTray;
    protected List<Deck> decks;


    //Overloading methods to perform updates with different messages from server
    public void performUpdate(ActivateProductionUpdate message) {
        updatePersonalBoard(message);
        //TODO chiamare un render delle risorse
    }

    public void performUpdate(BuyDevCardPerformedUpdate message) {
        updatePersonalBoard(message);
        decks = message.getResultingDecks();
    }


    public abstract void nextTurn();


    private ReducedPlayer getPlayer(User user) {
        return players.stream().filter(x -> x.getNickname().equals(user)).
                findFirst().orElseGet(() -> new ReducedPlayer(null, null, 0));
    }


    private void updatePersonalBoard(UpdateMessage message) {
        getPlayer(message.getUser()).updatePersonalBoard(message.getUserPersonalBoard());
    }


    public ReducedPlayer getCurrPlayer() {
        return currPlayer;
    }
}
