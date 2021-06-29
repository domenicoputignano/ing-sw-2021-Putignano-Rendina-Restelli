package it.polimi.ingsw.utils.messages.serverMessages.Updates;

import it.polimi.ingsw.client.CliStatesController;
import it.polimi.ingsw.client.reducedmodel.ReducedPersonalBoard;
import it.polimi.ingsw.commons.Deck;
import it.polimi.ingsw.commons.DevelopmentCard;
import it.polimi.ingsw.commons.User;
import it.polimi.ingsw.network.Client;

import java.util.List;

/**
 * Class containing specific information about a development card purchase successfully performed.
 */
public class BuyDevCardPerformedUpdate extends UpdateMessage {

    /**
     * Grid containing development cards currently available.
     */
    private final List<Deck> resultingDecks;
    /**
     * Card bought by player in turn.
     */
    private final DevelopmentCard boughtCard;

    public BuyDevCardPerformedUpdate(User user, ReducedPersonalBoard reducedPersonalBoard, List<Deck> resultingDecks, DevelopmentCard card) {
        this.user = user;
        this.userPersonalBoard = reducedPersonalBoard;
        this.resultingDecks = resultingDecks;
        this.boughtCard = card;
    }

    /**
     * Method called by client in order to update its reduced model and show changes to player
     * @param client {@link Client} instance that manages the update itself.
     */
    public void handleMessage(Client client) {
        client.getGame().performUpdate(this);
        if(client.getUI().isReceiverAction(user)) client.getUI().setNormalActionDone(true);
        client.getUI().render(this);
        CliStatesController.updateCliState(this, client.getUI());
    }

    public List<Deck> getResultingDecks() {
        return resultingDecks;
    }

    public DevelopmentCard getBoughtCard() {
        return boughtCard;
    }
}
