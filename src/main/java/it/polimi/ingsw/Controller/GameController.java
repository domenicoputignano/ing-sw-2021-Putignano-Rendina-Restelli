package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.MultiPlayerMode;
import it.polimi.ingsw.Model.GameState;
import it.polimi.ingsw.Model.Player;

import java.util.Collections;
import java.util.List;

public class GameController {
    private final Game model;
    private final TurnController turnController;


    public GameController(Game game) {
        this.model = game;
        this.turnController = new TurnController(this.model, model.getPlayerList(), model.getCurrPlayer());
    }


    public TurnController getTurnController() {
        return turnController;
    }

    public Game getModel() {
        return model;
    }
}

