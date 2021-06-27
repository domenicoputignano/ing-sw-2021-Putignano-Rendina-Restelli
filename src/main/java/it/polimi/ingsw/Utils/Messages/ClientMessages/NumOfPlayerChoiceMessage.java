package it.polimi.ingsw.Utils.Messages.ClientMessages;


import it.polimi.ingsw.Network.ClientStatus;



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
