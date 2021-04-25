package it.polimi.ingsw.Network;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Controller.TurnController;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Observable;
import it.polimi.ingsw.Utils.Messages.ClientMessage;

public class ClientStatus extends Observable<ClientMessage> {
    private final Player player;
    private final Game model;
    private final GameController gameController;
    private final TurnController turnController;

    public ClientStatus(Player player, Game model, GameController gameController, TurnController turnController) {
        this.player = player;
        this.model = model;
        this.gameController = gameController;
        this.turnController = turnController;
    }




}
