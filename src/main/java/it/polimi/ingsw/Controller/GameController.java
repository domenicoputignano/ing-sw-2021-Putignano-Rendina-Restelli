package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.MultiPlayerMode;
import it.polimi.ingsw.Model.GameState;
import it.polimi.ingsw.Model.Player;

import java.util.Collections;
import java.util.List;

public class GameController {
    MultiPlayerMode model;
    TurnController turnController;
    List<Player> playerList;


    public GameController(List<Player> players) {
        Collections.shuffle(players);
        this.playerList = players;
        this.model = new MultiPlayerMode(players.get(0),players,players.get(0),players.size());
        this.turnController = new TurnController(this.model, players,players.get(0));
        gameSetup();
    }

    private void gameSetup()
    {
        model.setup();
        model.nextState(GameState.RESOURCECHOICE);
    }
}

