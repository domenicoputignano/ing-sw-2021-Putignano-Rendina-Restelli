package it.polimi.ingsw.Client.clientstates;

import it.polimi.ingsw.Commons.Effect;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Messages.ClientMessages.MoveResourcesMessage;

public abstract class AbstractMoveResources extends AbstractClientState {

    protected MoveResourcesMessage messageToSend = new MoveResourcesMessage();

    public AbstractMoveResources(Client client) {
        super(client);
    }

    protected int getNumOfExtraDepots() {
        return client.getGame().getPlayer(client.getUser()).getCompatibleLeaderEffect(Effect.EXTRADEPOT).size();
    }

}
