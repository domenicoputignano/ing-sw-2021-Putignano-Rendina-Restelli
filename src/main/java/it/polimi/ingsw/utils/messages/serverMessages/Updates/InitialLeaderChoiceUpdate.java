package it.polimi.ingsw.utils.messages.serverMessages.Updates;

import it.polimi.ingsw.client.CliStatesController;
import it.polimi.ingsw.client.reducedmodel.ReducedPersonalBoard;
import it.polimi.ingsw.commons.User;
import it.polimi.ingsw.network.Client;

/**
 * Class representing an update sent when a player correctly performs initial leaders choice by discarding two
 * of his own cards.
 */
public class InitialLeaderChoiceUpdate extends UpdateMessage {

    private final int firstLeaderDiscarded;
    private final int secondLeaderDiscarded;

    public InitialLeaderChoiceUpdate(User user, ReducedPersonalBoard reducedPersonalBoard, int firstLeaderDiscarded, int secondLeaderDiscarded) {
        this.user = user;
        this.userPersonalBoard = reducedPersonalBoard;
        this.firstLeaderDiscarded = firstLeaderDiscarded;
        this.secondLeaderDiscarded = secondLeaderDiscarded;
    }

    /**
     * Method called by client in order to update its reduced model and show changes to player
     * @param client {@link Client} instance that manages the update itself.
     */
    @Override
    public void handleMessage(Client client) {
        client.getGame().updatePersonalBoard(this);
        client.getUI().render(this);
        CliStatesController.updateCliState(this, client.getUI());
    }

}
