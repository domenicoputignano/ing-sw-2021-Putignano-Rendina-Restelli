package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Exceptions.InvalidActionException;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.TurnState;
import it.polimi.ingsw.Utils.ActivateProductionMessage;
import it.polimi.ingsw.Utils.BuyDevCardMessage;

import java.util.List;

public class TurnController {
    Player currPlayer;
    Game model;
    List<Player> playerList;

    public TurnController(Game model, List<Player> players, Player firstPlayer)
    {
        this.model = model;
        this.playerList = players;
        this.currPlayer = firstPlayer;
    }

    public void handleBuyDevCardMessage(BuyDevCardMessage message) {
        if(message.isValidMessage()) {
            if (!model.isEmptyDeck(message.getType())) {
                model.getTurn().setTurnState(TurnState.BUYDEVCARD);
                currPlayer.getPersonalBoard().isCompatibleSlot(message.getType().getLevel(), message.getDestinationSlot());
                try {
                    model.getTurn().getTurnState().getTurnPhase().buyDevCard(model.getTurn(), message);
                } catch (InvalidActionException e) {
                    //inviare messaggio "fase del turno non valida!"
                }
            }
            //TODO else HANDLEERROR((ENUM) ERROR.EmptyDeckMessage)
        }
        //TODO else HANDLEERROR((ENUM) ERROR.NotValidMessage)
    }

    public void handleActivateProductionMessage(ActivateProductionMessage message) {
        if(message.isValidMessage()) {

        }
        //TODO else HANDLEERROR((ENUM) ERROR.NotValidMessage)
    }


}
