package it.polimi.ingsw.Utils.Messages.ClientMessages;

import it.polimi.ingsw.Controller.TurnController;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Network.RemoteView;

public interface TurnControllerHandleable extends GameControllerHandleable {

    public void handleMessage(TurnController turnController, RemoteView sender);


}
