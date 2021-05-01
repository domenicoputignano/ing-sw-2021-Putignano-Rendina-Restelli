package it.polimi.ingsw.Utils.Messages.ClientMessages;


import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Model.Player;

/* Interface that provides an abstraction of messages expected from client, handleable by Controller classes */
public interface ClientMessage {

    public void handleMessage(GameController gameController, Player sender);
    public boolean isValidMessage();

}
