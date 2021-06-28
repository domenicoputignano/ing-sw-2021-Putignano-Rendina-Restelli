package it.polimi.ingsw.utils.messages.serverMessages.Updates;

import it.polimi.ingsw.client.CliStatesController;
import it.polimi.ingsw.client.reducedmodel.ReducedPersonalBoard;
import it.polimi.ingsw.client.reducedmodel.ReducedSoloMode;
import it.polimi.ingsw.commons.Deck;
import it.polimi.ingsw.model.soloMode.Token;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.messages.serverMessages.ServerMessage;

import java.util.List;
import java.util.Stack;

public class LorenzoPlayedUpdate implements ServerMessage {
    private final ReducedPersonalBoard userPersonalBoard;
    private final Token playedToken;
    private final Stack<Token> tokens;
    private final List<Deck> decks;
    private final int blackCross;

    public LorenzoPlayedUpdate(ReducedPersonalBoard reducedPersonalBoard, Token playedToken, Stack<Token> tokens, List<Deck> decks, int blackCross) {
        this.userPersonalBoard = reducedPersonalBoard;
        this.playedToken = playedToken;
        this.tokens = tokens;
        this.decks = decks;
        this.blackCross = blackCross;
    }

    @Override
    public void handleMessage(Client handler) {
        ((ReducedSoloMode) handler.getGame()).nextTurn(this);
        handler.getUI().setNormalActionDone(false);
        handler.getUI().render(this);
        CliStatesController.updateCliState(this, handler.getUI());
    }

    public ReducedPersonalBoard getUserPersonalBoard() {
        return userPersonalBoard;
    }

    public Token getPlayedToken() {
        return playedToken;
    }

    public Stack<Token> getTokens() {
        return tokens;
    }

    public List<Deck> getDecks() {
        return decks;
    }

    public int getBlackCross() {
        return blackCross;
    }
}
