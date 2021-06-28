package it.polimi.ingsw.utils.messages.clientMessages;


import it.polimi.ingsw.network.ClientStatus;

public class GameModeChoiceMessage implements ConfigurationMessage {

    private final String gameModeChoice;

    public GameModeChoiceMessage(String gameModeChoice) {
        this.gameModeChoice = gameModeChoice;
    }

    public String getGameModeChoice() {
        return gameModeChoice;
    }

    @Override
    public void handleConfigurationMessage(ClientStatus clientStatus) {
        clientStatus.gameChoice(this);
    }
}
