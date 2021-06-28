package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.commons.Effect;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.messages.clientMessages.MoveResourcesMessage;

public abstract class AbstractMoveResources extends AbstractClientState {

    protected MoveResourcesMessage messageToSend = new MoveResourcesMessage();

    public AbstractMoveResources(Client client) {
        super(client);
    }

    public int getNumOfExtraDepots() {
        return client.getGame().getPlayer(client.getUser()).getCompatibleLeaderEffect(Effect.EXTRADEPOT).size();
    }

}
