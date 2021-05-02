package it.polimi.ingsw.Utils.Messages.ClientMessages;


import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Model.Player;

import java.io.Serializable;

/* Interface that provides an abstraction of messages expected from client, handleable by Controller classes */
public interface ClientMessage extends Serializable {

    public void handleMessage(GameController gameController, Player sender);
    public boolean isValidMessage();

}
