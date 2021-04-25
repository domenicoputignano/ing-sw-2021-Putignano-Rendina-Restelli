package it.polimi.ingsw.Utils.Messages;

import it.polimi.ingsw.Controller.TurnController;

public interface TurnControllerHandleable extends ClientMessage {

    public void handleMessage(TurnController turnController, TurnControllerHandleable message);


}
