package it.polimi.ingsw.client.clientstates.gui;

import it.polimi.ingsw.client.clientstates.AbstractInitialResourceChoice;
import it.polimi.ingsw.commons.ResourceType;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.messages.clientMessages.ResourceChoiceMessage;
import it.polimi.ingsw.utils.Pair;

import java.util.List;

/**
 * This class represents the GUI-specific state that is reached when the client has to
 * choose the initial resources. This state is reached only in a Multiplayer Mode game
 * by second, third and fourth players.
 */
public class InitialResourceChoiceGUI extends AbstractInitialResourceChoice {

    /**
     * The list containing the resources chosen by the user during this phase.
     */
    private final List<Pair<ResourceType, Integer>> chosenResources;

    /**
     * Main method of the class used to send the message to the server after the user has chosen the resources
     * and where to place them using the JavaFX GUI.
     */
    @Override
    public void manageUserInteraction() {
        messageToSend = new ResourceChoiceMessage(chosenResources);
        client.sendMessage(messageToSend);
    }

    /**
     * Initializes references to CLI and client.
     */
    public InitialResourceChoiceGUI(Client client, List<Pair<ResourceType, Integer>> chosenResources) {
        super(client);
        this.chosenResources = chosenResources;
    }

}
