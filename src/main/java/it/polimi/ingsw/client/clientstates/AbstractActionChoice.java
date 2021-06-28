package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.messages.clientMessages.EndTurnMessage;

/**
 * This class represents the generic ui state that is reached when the player in turn
 * chooses the action he wants to perform during his turn.
 */
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
