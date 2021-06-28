package it.polimi.ingsw.utils.messages.clientMessages;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.TurnController;
import it.polimi.ingsw.network.RemoteView;

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
