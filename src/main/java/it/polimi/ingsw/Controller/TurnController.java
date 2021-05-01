package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Exceptions.InvalidActionException;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Utils.Messages.ServerMessages.*;
import it.polimi.ingsw.Utils.Messages.ClientMessages.*;

import java.util.List;

public class TurnController {
    private Player currPlayer;
    private final Game model;
    private final List<Player> playerList;

    public TurnController(Game model, List<Player> players, Player firstPlayer)
    {
        this.model = model;
        this.playerList = players;
        this.currPlayer = firstPlayer;
    }



    public void handleBuyDevCardMessage(BuyDevCardMessage message, Player sender) {
        if (isSenderTurn(sender)) {
            if (message.isValidMessage()) {
                if (!model.isEmptyDeck(message.getType())) {
                    model.getTurn().setTurnState(TurnState.ActionType.BUYDEVCARD);
                    currPlayer.getPersonalBoard().isCompatibleSlot(message.getType().getLevel(), message.getDestinationSlot());
                    try {
                        model.getTurn().getTurnPhase().buyDevCard(model.getTurn(), message);
                    } catch (InvalidActionException e) {
                        //inviare messaggio "fase del turno non valida!"
                    }
                }
                //TODO else HANDLEERROR((ENUM) ERROR.EmptyDeckMessage)
            }
        }
        //TODO else HANDLEERROR((ENUM) ERROR.NotValidMessage)
    }

    public void handleActivateProductionMessage(ActivateProductionMessage message, Player sender) {
        if(isSenderTurn(sender)) {
            if (message.isValidMessage()) {
                if (currPlayer.getPersonalBoard().isValidRequestedProduction(message.getProductions())) {
                    model.getTurn().setTurnState(TurnState.ActionType.ACTIVATEPRODUCTION);
                    try {
                        model.getTurn().getTurnPhase().activateProduction(model.getTurn(), message);
                    } catch (InvalidActionException e) {
                        // inviare messaggio "fase del turno non valida!"
                    }
                }
                // TODO else HANDLEERROR((ENUM) ERROR.ProductionNotPossibleMessage)
                ActivateProductionError error = new ActivateProductionError(ActivateProductionError.Trigger.INVALIDREQUEST);

            }
        }
            //TODO else HANDLEERROR((ENUM) ERROR.NotValidMessage)
    }

    public void handleTakeResourcesFromMarketMessage(TakeResourcesFromMarketMessage message, Player sender) {
        if(isSenderTurn(sender)) {
            if (message.isValidMessage()) {
                if (model.getMarketTray().checkRequestedMarbles(message.getRequestedMarbles(), message.getPlayerChoice(), message.getIndex())) {
                    model.getTurn().setTurnState(TurnState.ActionType.TAKERESOURCESFROMMARKET);
                    try {
                        model.getTurn().getTurnPhase().takeResourcesFromMarket(model.getTurn(), message);
                    } catch (InvalidActionException e) {
                        // inviare messaggio "fase del turno non valida!"
                    }
                }
                // TODO else HANDLEERROR((ENUM) ERROR.TakeResourcesNotPossibleMessage)
            }
        }
        //TODO else HANDLEERROR((ENUM) ERROR.NotValidMessage)
    }

    public void handleLeaderActionMessage(LeaderActionMessage message, Player sender) {
        if(isSenderTurn(sender)) {
            if (message.isValidMessage()) {
                model.getTurn().setTurnState(TurnState.ActionType.LEADERACTION);
                try {
                    model.getTurn().getTurnPhase().leaderAction(model.getTurn(), message);
                } catch (InvalidActionException e) {
                    // inviare messaggio "fase del turno non valida!"
                }
            }
        }
        //TODO else HANDLEERROR((ENUM) ERROR.NotValidMessage)
    }
    public void handleMoveMessage(MoveResourcesMessage message, Player sender)
    {
        if(isSenderTurn(sender)) {
            if (message.isValidMessage())
                model.getCurrPlayer().moveResources(message.getMoveAction());
        }
    }
    public void handlePositioningMessage(PositioningMessage message, Player sender) {
        if(isSenderTurn(sender)) {
            if (message.isValidMessage()) {
                if (model.getTurn().getActionType() == TurnState.ActionType.TAKERESOURCESFROMMARKET) {
                    TakeResourcesFromMarket turnInstance = (TakeResourcesFromMarket) model.getTurn().getTurnPhase();
                    if (turnInstance.checkPendingResourcesPositioning(message.getResourcesToPut())) {
                        turnInstance.handlePositioning(model.getTurn().getPlayer().getPersonalBoard().getWarehouse(), message.getWhereToPutResources());
                        //TODO: AGGIUNGERE CONCLUDETURNPHASE IN TUTTE LE ALTRE AZIONI DEL TURNO
                        turnInstance.concludeTurnPhase(model.getTurn());
                    }
                    //TODO: else HANDLEERROR(MESSAGGIO NON COMPATIBILE)
                }
                //TODO : else HANDLEERROR(FASE DEL TURNO NON VALIDA)
            }
        }
        //TODO else HANDLEERROR((ENUM) ERROR.NotValidMessage)
    }

    private boolean isSenderTurn(Player sender) {
        return currPlayer.equals(sender);
    }

}
