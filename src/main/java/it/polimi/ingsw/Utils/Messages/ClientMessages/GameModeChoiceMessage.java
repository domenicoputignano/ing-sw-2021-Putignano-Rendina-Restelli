package it.polimi.ingsw.Utils.Messages.ClientMessages;


import it.polimi.ingsw.Network.ClientSetupConnection;

import java.io.IOException;

public class GameModeChoiceMessage implements ConfigurationMessage {

    private final String gameModeChoice;

    public GameModeChoiceMessage(String gameModeChoice) {
        this.gameModeChoice = gameModeChoice;
    }

    public String getGameModeChoice() {
        return gameModeChoice;
    }

    @Override
    public void handleConfigurationMessage(ClientSetupConnection connection) throws IOException {
        connection.gameChoice(this);
    }
}
