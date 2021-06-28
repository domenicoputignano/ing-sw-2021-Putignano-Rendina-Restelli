package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.messages.clientMessages.LeaderChoiceMessage;

public abstract class AbstractInitialLeaderChoice extends AbstractClientState {

    protected int leaderCard1Index;
    protected int leaderCard2Index;
    protected LeaderChoiceMessage messageToSend;


    public AbstractInitialLeaderChoice(Client client) {
        super(client);
    }

}
