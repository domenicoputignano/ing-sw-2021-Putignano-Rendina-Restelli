package it.polimi.ingsw.client.clientstates;


import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.messages.clientMessages.EndTurnMessage;

public abstract class AbstractActionChoice extends AbstractClientState {


    protected AbstractActionChoice(Client client) {
        super(client);
    }

    /**
     * Boolean method that returns if a normal action has been already done for the current turn.
     */
    protected boolean normalActionAlreadyDone(){return client.getUI().hasDoneNormalAction();}

    /**
     * Sends a message of {@link EndTurnMessage} type.
     */
    protected void endTurn() {
        client.sendMessage(new EndTurnMessage());
    }

}
