package it.polimi.ingsw.utils.messages.serverMessages.Updates;

import it.polimi.ingsw.client.CliStatesController;
import it.polimi.ingsw.client.reducedmodel.ReducedPersonalBoard;
import it.polimi.ingsw.commons.User;
import it.polimi.ingsw.network.Client;

public class InitialLeaderChoiceUpdate extends UpdateMessage {

    private final int leader1ToDiscard;
    private final int leader2ToDiscard;

    public InitialLeaderChoiceUpdate(User user, ReducedPersonalBoard reducedPersonalBoard, int leader1ToDiscard, int leader2ToDiscard) {
        this.user = user;
        this.userPersonalBoard = reducedPersonalBoard;
        this.leader1ToDiscard = leader1ToDiscard;
        this.leader2ToDiscard = leader2ToDiscard;
    }

    @Override
    public void handleMessage(Client client) {
        client.getGame().updatePersonalBoard(this);
        client.getUI().render(this);
        CliStatesController.updateCliState(this, client.getUI());
    }

}
