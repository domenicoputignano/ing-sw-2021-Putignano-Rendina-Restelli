package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.network.Client;

/**
 * This is the highest-level class of the Client States hierarchy.
 * It represents a generic ui state where the client can be.
 * There are two layers of subclasses starting from this class: first one is represented by all the
 * abstract states of the ui which contain all the methods and attributes independent from the specific
 * ui implementation (CLI or GUI); the second layer is represented by all the ui-implementation-dependent
 * states, which contain methods specifically thought to lead the interaction with the user.
 */
public abstract class AbstractClientState {

    /**
     * The client main class bounded to this ui state.
     */
    protected Client client;

    /**
     * Constructs an instance of this class from the given client.
     * @param client the client bounded to this state.
     */
    protected AbstractClientState(Client client) {
        this.client = client;
    }

    /**
     * This method manages all the interaction with the user. It is implemented in
     * different ways depending on the ui-specific implementation.
     */
    public abstract void manageUserInteraction();

}
