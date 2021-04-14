package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.TurnState;

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

    /*
    public void handleBuyDevCardMessage(BuyDevCardMessage message) {
       -- model.getTurn().setTurnState(TurnState.BUYDEVCARD);
       -- isCompatibleSlot(message.getType().getLevel(), message.getDestinationSlot())


       TODO
    }*/


}
