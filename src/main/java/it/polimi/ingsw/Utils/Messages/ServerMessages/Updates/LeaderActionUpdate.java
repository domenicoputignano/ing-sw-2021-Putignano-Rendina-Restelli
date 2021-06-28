package it.polimi.ingsw.Utils.Messages.ServerMessages.Updates;

import it.polimi.ingsw.Client.CliStatesController;
import it.polimi.ingsw.Client.reducedmodel.ReducedPersonalBoard;
import it.polimi.ingsw.Commons.LeaderCard;
import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Network.Client;

public class LeaderActionUpdate extends UpdateMessage {
    private final LeaderCard leaderCard;
    private int index;
    private final boolean toDiscard;

    public LeaderActionUpdate(User user, ReducedPersonalBoard reducedPersonalBoard, LeaderCard card, int index, boolean toDiscard)
    {
        this.user = user;
        this.userPersonalBoard = reducedPersonalBoard;
        this.leaderCard = card;
        this.index = index;
        this.toDiscard = toDiscard;
    }
    @Override
    public void handleMessage(Client handler) {
        handler.getGame().updatePersonalBoard(this);
        handler.getUI().render(this);
        CliStatesController.updateCliState(this, handler.getUI());
    }

    public boolean hasBeenDiscarded() {
        return toDiscard;
    }

    public LeaderCard getLeaderCard() {
        return leaderCard;
    }
}
