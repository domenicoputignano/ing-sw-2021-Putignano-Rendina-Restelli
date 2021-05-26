package it.polimi.ingsw.Utils.Messages.ServerMessages.Updates;

import it.polimi.ingsw.Client.ClientStatesController;
import it.polimi.ingsw.Client.reducedmodel.ReducedPersonalBoard;
import it.polimi.ingsw.Client.reducedmodel.ReducedSoloMode;
import it.polimi.ingsw.Commons.Deck;
import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Model.SoloMode.Token;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Messages.ServerMessages.ServerMessage;

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
        ClientStatesController.updateClientState(this, handler.getUI());
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
