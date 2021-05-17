package it.polimi.ingsw.Client.clientstates;

import it.polimi.ingsw.Client.clientstates.cli.InitialLeaderChoiceCLI;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Messages.ClientMessages.LeaderChoiceMessage;

public abstract class AbstractInitialLeaderChoice extends AbstractClientState {

    protected int leaderCard1Index;
    protected int leaderCard2Index;
    protected LeaderChoiceMessage messageToSend;


    public AbstractInitialLeaderChoice(Client client) {
        super(client);
    }

    @Override //TODO
    public AbstractClientState getGUIVersion() {
        return null;
    }

    @Override
    public InitialLeaderChoiceCLI getCLIVersion() {
        return new InitialLeaderChoiceCLI(client);
    }
}
