package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Player;

import java.util.List;

public class TurnController {
    Player currPlayer;
    Game model;
    List<Player> playerList;

    public TurnController(Game model, List<Player> players, Player firstPlayer)
    {
        this.model = model;
        this.playerList = players;
        this.currPlayer = firstPlayer;

    }


}
