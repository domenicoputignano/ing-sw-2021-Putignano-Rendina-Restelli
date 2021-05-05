package it.polimi.ingsw.Utils.Messages.ServerMessages.Updates;

import it.polimi.ingsw.Commons.Deck;
import it.polimi.ingsw.Commons.DevelopmentCard;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.UpdateMessage;

import java.util.ArrayList;
import java.util.List;

public class BuyDevCardPerformedUpdate extends UpdateMessage {

    private List<Deck> resultingDecks = new ArrayList<>();
    private DevelopmentCard boughtCard;

    public void handleUpdateMessage(Client client) {
        //TODO implementare

    }
}
