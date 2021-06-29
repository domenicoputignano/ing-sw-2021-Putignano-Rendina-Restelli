package it.polimi.ingsw.utils.messages.clientMessages;


import it.polimi.ingsw.network.ClientStatus;

/**
 * This class represent a specific {@link ConfigurationMessage} sent by client in order to choose
 * between a multiplayer mode and solo mode game.
 */
public class GameModeChoiceMessage implements ConfigurationMessage {

    private final String gameModeChoice;

    public GameModeChoiceMessage(String gameModeChoice) {
        this.gameModeChoice = gameModeChoice;
    }

    public String getGameModeChoice() {
        return gameModeChoice;
    }

    /**
     *
     * @param clientStatus
     */
    @Override
    public void handleConfigurationMessage(ClientStatus clientStatus) {
        clientStatus.gameChoice(this);
    }
}
