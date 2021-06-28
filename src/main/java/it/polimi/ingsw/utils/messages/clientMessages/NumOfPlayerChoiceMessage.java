package it.polimi.ingsw.utils.messages.clientMessages;


import it.polimi.ingsw.network.ClientStatus;



public class NumOfPlayerChoiceMessage implements ConfigurationMessage {


    private final int numOfPlayers;

    public NumOfPlayerChoiceMessage(int numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
    }


    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    @Override
    public void handleConfigurationMessage(ClientStatus clientStatus) {
        clientStatus.numOfPlayersChoice(this);
    }
}
