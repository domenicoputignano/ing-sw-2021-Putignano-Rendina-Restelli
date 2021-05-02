package it.polimi.ingsw.Model;

import java.util.*;

public class MultiPlayerMode extends Game {
    private int numOfPlayers;
    private int receivedInitialMessages;

    public MultiPlayerMode(Player inkwell, List<Player> playerList, Player currPlayer, int numOfPlayers) {
        this.inkwell = inkwell;
        this.playerList = playerList;
        this.currPlayer = currPlayer;
        this.numOfPlayers = numOfPlayers;
        this.gameState = GameState.SETUP;
    }

    public MultiPlayerMode(List<Player> playerList) {
        Collections.shuffle(playerList);
        this.playerList = playerList;
        this.inkwell = playerList.get(0);
        this.numOfPlayers = playerList.size();
        this.currPlayer = inkwell;
        gameSetup();
    }
    /*
    public Player getWinner() {
        //TODO
        return null;
    }

    public int calcVictoryPoints() {
        //TODO
        return 1;

    }*/

    private void gameSetup()
    {
        this.setup();
        this.nextState(GameState.LEADERCHOICE);
    }



}

