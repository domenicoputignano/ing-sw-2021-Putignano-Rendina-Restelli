package it.polimi.ingsw.Network;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Observer;
import it.polimi.ingsw.Utils.Messages.ClientMessage;

public class RemoteView implements Observer<ClientMessage> {

    private Player player;
    private Game game;
    private GameController gameController;


    public RemoteView(Player player, GameController gameController) {
        this.player = player;
        this.gameController = gameController;
        this.game = gameController.getModel();
    }

    @Override
    public void update(ClientMessage message) {

    }


}
