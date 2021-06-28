package it.polimi.ingsw.utils.messages.clientMessages;

import it.polimi.ingsw.controller.TurnController;
import it.polimi.ingsw.network.RemoteView;

public interface TurnControllerHandleable extends GameControllerHandleable {

    public void handleMessage(TurnController turnController, RemoteView sender);


}
