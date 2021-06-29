package it.polimi.ingsw.utils.messages.clientMessages;


import it.polimi.ingsw.network.ClientStatus;


/**
 * Class containing number of players selected to make up a multiplayer mode game.
 */
public class NumOfPlayerChoiceMessage implements ConfigurationMessage {

    /**
     * Number of players selected.
     */
    private final int numOfPlayers;

    /**
     * Constructor that initializes main attribute of the class.
     * @param numOfPlayers
     */
    public NumOfPlayerChoiceMessage(int numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
    }


    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    /**
     * Calls right method of {@link ClientStatus} in order to process message itself.
     * @param clientStatus {@link ClientStatus} instance that will process message itself.
     */
    @Override
    public void handleConfigurationMessage(ClientStatus clientStatus) {
        clientStatus.numOfPlayersChoice(this);
    }
}
