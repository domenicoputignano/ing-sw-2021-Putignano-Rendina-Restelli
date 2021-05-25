package it.polimi.ingsw.Client.clientstates;


import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Messages.ClientMessages.EndTurnMessage;

public abstract class AbstractActionChoice extends AbstractClientState {


    protected AbstractActionChoice(Client client) {
        super(client);
    }

    protected boolean normalActionAlreadyDone(){return client.getUI().hasDoneNormalAction();}

    protected void endTurn() {
        client.sendMessage(new EndTurnMessage());
    }

}
