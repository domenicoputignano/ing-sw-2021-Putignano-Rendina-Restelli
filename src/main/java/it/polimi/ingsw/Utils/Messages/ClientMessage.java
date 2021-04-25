package it.polimi.ingsw.Utils.Messages;


import it.polimi.ingsw.Controller.GameController;

/* Interface that provides an abstraction of messages expected from client, handleable by Controller classes */
public interface ClientMessage {

    public void handleMessage(GameController gameController);

}
