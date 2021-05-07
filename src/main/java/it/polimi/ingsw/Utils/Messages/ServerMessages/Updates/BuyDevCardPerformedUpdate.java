package it.polimi.ingsw.Utils.Messages.ServerMessages.Updates;

import it.polimi.ingsw.Client.ReducedPersonalBoard;
import it.polimi.ingsw.Commons.Deck;
import it.polimi.ingsw.Commons.DevelopmentCard;
import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.UpdateMessage;

import java.util.ArrayList;
import java.util.List;

public class BuyDevCardPerformedUpdate extends UpdateMessage {

    private List<Deck> resultingDecks = new ArrayList<>();
    private DevelopmentCard boughtCard;

    public BuyDevCardPerformedUpdate(User user, ReducedPersonalBoard reducedPersonalBoard, List<Deck> resultingDecks, DevelopmentCard card)
    {
        this.user = user;
        this.userPersonalBoard = reducedPersonalBoard;
        this.resultingDecks = resultingDecks;
        this.boughtCard = card;
    }
    public void handleUpdateMessage(Client client) {
        //TODO implementare

    }
}
