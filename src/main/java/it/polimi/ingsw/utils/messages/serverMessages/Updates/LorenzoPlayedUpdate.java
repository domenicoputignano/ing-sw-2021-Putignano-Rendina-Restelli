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

/**
 * Class representing an update sent at the end of a turn while playing a solo mode game.
 * It contains all attributes that could be edited by a {@link it.polimi.ingsw.model.soloMode.LorenzoIlMagnifico} move.
 */
public class LorenzoPlayedUpdate implements ServerMessage {
    private final ReducedPersonalBoard userPersonalBoard;
    /**
     * Token played at the end of the turn.
     */
    private final Token playedToken;
    /**
     * Updated stack of playable tokens.
     */
    private final Stack<Token> tokens;
    /**
     * Updated grid of development cards.
     */
    private final List<Deck> decks;
    /**
     * Updated position of Lorenzo's cross.
     */
    private final int blackCross;

    public LorenzoPlayedUpdate(ReducedPersonalBoard reducedPersonalBoard, Token playedToken, Stack<Token> tokens, List<Deck> decks, int blackCross) {
        this.userPersonalBoard = reducedPersonalBoard;
        this.playedToken = playedToken;
        this.tokens = tokens;
        this.decks = decks;
        this.blackCross = blackCross;
    }

    /**
     * Method called by client in order to update its reduced model and show changes to player
     * @param handler {@link Client} instance that manages the update itself.
     */
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
