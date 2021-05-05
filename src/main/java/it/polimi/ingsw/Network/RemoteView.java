package it.polimi.ingsw.Network;

import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Observer;
import it.polimi.ingsw.Utils.Messages.ClientMessages.ClientMessage;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Errors.ErrorMessage;

public class RemoteView implements Observer<ClientMessage> {

    private final User user;
    private final Game game;
    private final GameController gameController;
    private final ClientStatus clientStatus;

    public RemoteView(User user, GameController gameController, ClientStatus clientStatus) {
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public Player getPlayer() {
        return game.getPlayer(user);
    }
}
