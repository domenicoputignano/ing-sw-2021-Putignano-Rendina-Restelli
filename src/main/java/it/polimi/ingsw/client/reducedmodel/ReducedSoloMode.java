package it.polimi.ingsw.client.reducedmodel;

import it.polimi.ingsw.commons.Deck;
import it.polimi.ingsw.model.soloMode.Token;
import it.polimi.ingsw.utils.messages.serverMessages.Updates.BlackCrossMoveUpdate;
import it.polimi.ingsw.utils.messages.serverMessages.Updates.LorenzoPlayedUpdate;
import it.polimi.ingsw.utils.messages.serverMessages.Updates.ServerAsksForPositioning;

import java.util.List;
import java.util.Stack;
/**
 * This class represents a simplified version of a SoloMode with respect to the server's class.
 * It contains only the information required client side.
 */
public class ReducedSoloMode extends ReducedGame {
    /**
     * Attributes that represent the contents of the SoloMode game
     */
    private int blackCross;
    private Stack<Token> tokens;
    /**
     * Overloading methods to perform updates with different messages from server
     * @param update BlackCrossMoveUpdate update message
     * @see BlackCrossMoveUpdate
     */
    public void performUpdate(BlackCrossMoveUpdate update){
        this.blackCross = update.getBlackCross();
    }

    /**
     * Initialize an instance by setting players,decks,marketTray,tokens and black cross of the SoloMode Game.
     */
    public ReducedSoloMode(List<ReducedPlayer> players, List<Deck> decks, ReducedMarketTray marketTray, Stack<Token> tokens, int blackCross) {
        super(players,decks,marketTray, true);
        this.tokens = tokens;
        this.blackCross = blackCross;
    }
    /**
     * Overloading methods to perform updates with different messages from server
     * @param message LorenzoPlayedUpdate update message
     * @see LorenzoPlayedUpdate
     */
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
