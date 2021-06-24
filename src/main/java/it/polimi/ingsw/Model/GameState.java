package it.polimi.ingsw.Model;

/**
 * Represents the current state of the game.
 * SETUP is the initial state, when all the card are distributed, market tray is initialized and decks are prepared.
 * INITIALCHOICES is the state in which each player discards leader cards and chooses resources.
 * GAMEFLOW represents the normal sequence of turns involving one player at a time.
 */
public enum GameState {
    SETUP, INITIALCHOICES,GAMEFLOW,ENDGAME,PAUSED

}
