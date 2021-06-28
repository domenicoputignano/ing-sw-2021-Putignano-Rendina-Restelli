package it.polimi.ingsw.Utils.Messages.ServerMessages.Updates;

import it.polimi.ingsw.Client.CliStatesController;
import it.polimi.ingsw.Client.reducedmodel.ReducedPersonalBoard;
import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Network.Client;

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
