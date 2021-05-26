package it.polimi.ingsw.Client.reducedmodel;

import it.polimi.ingsw.Commons.Deck;
import it.polimi.ingsw.Model.SoloMode.Token;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.LorenzoPlayedUpdate;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.NewTurnUpdate;

import java.util.List;
import java.util.Stack;

public class ReducedSoloMode extends ReducedGame {
    private int blackCross;
    private Stack<Token> tokens;


    public ReducedSoloMode(List<ReducedPlayer> players, List<Deck> decks, ReducedMarketTray marketTray, Stack<Token> tokens) {
        super(players,decks,marketTray, true);
        this.tokens = tokens;
    }

    public void nextTurn(LorenzoPlayedUpdate message) {
        this.currPlayer.updatePersonalBoard(message.getUserPersonalBoard());
        this.tokens = message.getTokens();
        this.decks = message.getDecks();
        this.blackCross = message.getBlackCross();
    }


}
