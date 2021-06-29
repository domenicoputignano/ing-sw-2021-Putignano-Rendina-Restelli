package it.polimi.ingsw.utils.messages.serverMessages.Updates;

import it.polimi.ingsw.client.CliStatesController;
import it.polimi.ingsw.client.reducedmodel.ReducedPersonalBoard;
import it.polimi.ingsw.commons.LeaderCard;
import it.polimi.ingsw.commons.User;
import it.polimi.ingsw.network.Client;

/**
 * Class representing an update sent when a {@link it.polimi.ingsw.model.LeaderAction} is successfully done.
 */
public class LeaderActionUpdate extends UpdateMessage {
    /**
     * Leader card on which the action has been performed.
     */
    private final LeaderCard leaderCard;
    /**
     * Leader card index inside list owned by player in turn.
     */
    private int index;
    /**
     * Boolean variable establishes if the card was to discard.
     */
    private final boolean toDiscard;

    public LeaderActionUpdate(User user, ReducedPersonalBoard reducedPersonalBoard, LeaderCard card, int index, boolean toDiscard)
    {
        this.user = user;
        this.userPersonalBoard = reducedPersonalBoard;
        this.leaderCard = card;
        this.index = index;
        this.toDiscard = toDiscard;
    }

    /**
     * Method called by client in order to update its reduced model and show changes to player
     * @param handler {@link Client} instance that manages the update itself.
     */
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
