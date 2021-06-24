package it.polimi.ingsw.Client.reducedmodel;

import it.polimi.ingsw.Commons.Deck;
import it.polimi.ingsw.Model.SoloMode.Token;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.BlackCrossMoveUpdate;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.LorenzoPlayedUpdate;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.NewTurnUpdate;

import java.util.List;
import java.util.Stack;

public class ReducedSoloMode extends ReducedGame {
    private int blackCross;
    private Stack<Token> tokens;

    public void performUpdate(BlackCrossMoveUpdate update){
        this.blackCross = update.getBlackCross();
    }


    public ReducedSoloMode(List<ReducedPlayer> players, List<Deck> decks, ReducedMarketTray marketTray, Stack<Token> tokens, int blackCross) {
        super(players,decks,marketTray, true);
        this.tokens = tokens;
        this.blackCross = blackCross;
    }

    public void nextTurn(LorenzoPlayedUpdate message) {
        this.currPlayer.updatePersonalBoard(message.getUserPersonalBoard());
        this.tokens = message.getTokens();
        this.decks = message.getDecks();
        this.blackCross = message.getBlackCross();
    }

    public int getBlackCross() {
        return blackCross;
    }

    public void printPlayers() {
        System.out.println("You are competing versus Lorenzo Il Magnifico whose black cross is in "+blackCross+"° position!");
    }


}
