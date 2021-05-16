package it.polimi.ingsw.Utils.Messages.ClientMessages;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Network.RemoteView;

public class NumOfPlayerChoiceMessage implements ClientMessage {


    private final int numOfPlayers;

    public NumOfPlayerChoiceMessage(int numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
    }

    @Override
    public void handleMessage(GameController gameController, RemoteView sender) { }

    @Override
    public boolean isValidMessage() {
        return true;
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }
}
