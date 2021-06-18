package it.polimi.ingsw.Utils.Messages.ClientMessages;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Controller.TurnController;
import it.polimi.ingsw.Network.RemoteView;

public class EndTurnMessage implements TurnControllerHandleable {

    @Override
    public void handleMessage(GameController gameController, RemoteView sender) {
        handleMessage(gameController.getTurnController(),sender);
    }

    @Override
    public boolean isValidMessage() {
        return true;
    }

    @Override
    public void handleMessage(TurnController turnController, RemoteView sender) {
        turnController.handleEndTurnMessage(this,sender);
    }

}
