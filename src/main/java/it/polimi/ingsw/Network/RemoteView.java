package it.polimi.ingsw.Network;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Observer;
import it.polimi.ingsw.Utils.Messages.ClientMessages.ClientMessage;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Errors.ErrorMessage;

public class RemoteView implements Observer<ClientMessage> {

    private final Player player;
    private final Game game;
    private final GameController gameController;
    private final ClientStatus clientStatus;

    public RemoteView(Player player, GameController gameController, ClientStatus clientStatus) {
        this.player = player;
        this.gameController = gameController;
        this.game = gameController.getModel();
        this.clientStatus = clientStatus;
    }

    @Override
    public void update(ClientMessage message) {
        message.handleMessage(gameController, this);
    }



    public void sendError(ErrorMessage errorMessage) {
        clientStatus.send(errorMessage);
     }

    public Player getPlayer() {
        return player;
    }
}
