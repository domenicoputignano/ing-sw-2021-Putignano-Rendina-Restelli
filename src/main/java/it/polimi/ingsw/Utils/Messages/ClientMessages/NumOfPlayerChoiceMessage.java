package it.polimi.ingsw.Utils.Messages.ClientMessages;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Network.ClientSetupConnection;
import it.polimi.ingsw.Network.RemoteView;

import java.io.IOException;

public class NumOfPlayerChoiceMessage implements ConfigurationMessage {


    private final int numOfPlayers;

    public NumOfPlayerChoiceMessage(int numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
    }


    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    @Override
    public void handleConfigurationMessage(ClientSetupConnection connection) throws IOException {
        connection.numOfPlayersChoice(this);
    }
}
