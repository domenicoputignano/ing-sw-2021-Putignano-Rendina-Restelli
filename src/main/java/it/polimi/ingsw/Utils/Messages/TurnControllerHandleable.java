package it.polimi.ingsw.Utils.Messages;

import it.polimi.ingsw.Controller.TurnController;
import it.polimi.ingsw.Model.Player;

public interface TurnControllerHandleable extends ClientMessage {

    public void handleMessage(TurnController turnController, Player sender);


}
