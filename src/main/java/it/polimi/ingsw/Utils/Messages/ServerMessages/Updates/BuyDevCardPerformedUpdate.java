package it.polimi.ingsw.Utils.Messages.ServerMessages.Updates;

import it.polimi.ingsw.Client.reducedmodel.ReducedPersonalBoard;
import it.polimi.ingsw.Commons.Deck;
import it.polimi.ingsw.Commons.DevelopmentCard;
import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Network.Client;

import java.util.ArrayList;
import java.util.List;

public class BuyDevCardPerformedUpdate extends UpdateMessage {

    private List<Deck> resultingDecks = new ArrayList<>();
    private DevelopmentCard boughtCard;

    public BuyDevCardPerformedUpdate(User user, ReducedPersonalBoard reducedPersonalBoard, List<Deck> resultingDecks, DevelopmentCard card) {
        this.user = user;
        this.userPersonalBoard = reducedPersonalBoard;
        this.resultingDecks = resultingDecks;
        this.boughtCard = card;
    }

    public void handleMessage(Client client) {
        client.getGame().performUpdate(this);
        client.getUI().setNormalActionDone(true);
    }

    public List<Deck> getResultingDecks() {
        return resultingDecks;
    }
}
