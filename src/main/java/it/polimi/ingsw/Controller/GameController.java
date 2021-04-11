package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.GameState;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.Turn;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;

public class GameController {
    Game model;
    TurnController turnController;
    List<Player> playerList;


    public GameController(List<Player> players) {
        Collections.shuffle(players);
        this.playerList = players;
        this.model = new Game(players.get(0),players,players.get(0),players.size());
        this.turnController = new TurnController();
        gameSetup();
    }

    private void gameSetup()
    {
        model.setup();
        model.nextState(GameState.RESOURCECHOICE);
    }
}

