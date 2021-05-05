package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Network.RemoteView;
import it.polimi.ingsw.Utils.Messages.ClientMessages.*;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Errors.*;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.ServerAsksForPositioning;

import java.util.List;



public class TurnController {
    private Player currPlayer;
    private final Game model;
    private final List<Player> playerList;

    public TurnController(Game model, List<Player> players, Player firstPlayer) {
        this.model = model;
        this.playerList = players;
        this.currPlayer = firstPlayer;
    }



    public void handleBuyDevCardMessage(BuyDevCardMessage message, RemoteView sender) {
        if (isSenderTurn(sender.getPlayer())) {
            if (message.isValidMessage()) {
                if (!model.isEmptyDeck(message.getType())) {
                    model.getTurn().setTurnState(TurnState.ActionType.BUYDEVCARD);
                    currPlayer.getPersonalBoard().isCompatibleSlot(message.getType().getLevel(), message.getDestinationSlot());
                    try {
                        model.getTurn().getTurnPhase().buyDevCard(model.getTurn(), message);
                    } catch (InvalidActionException e) {
                        sender.sendError(new ActionError(ActionError.Trigger.NORMALACTIONALREADYDONE));
                    } catch (NotEnoughResourcesException e) {
                        sender.sendError(new BuyDevCardError(BuyDevCardError.Trigger.NOTENOUGHRESOURCES));
                    } catch (ResourceMismatchException e) {
                        sender.sendError(new BuyDevCardError(BuyDevCardError.Trigger.RESOURCESMISMATCH));
                    } catch (PaymentErrorException e) {
                        sender.sendError(new BuyDevCardError(BuyDevCardError.Trigger.PAYMENTERROR));
                    }
                } else sender.sendError(new BuyDevCardError(BuyDevCardError.Trigger.EMPTYDECK));
            } else sender.sendError(new InvalidMessageError());
        } else sender.sendError(new WrongTurnError());
    }

    public void handleActivateProductionMessage(ActivateProductionMessage message, RemoteView sender) {
        if(isSenderTurn(sender.getPlayer())) {
            if (message.isValidMessage()) {
                if (currPlayer.getPersonalBoard().isValidRequestedProduction(message.getProductions())) {
                    model.getTurn().setTurnState(TurnState.ActionType.ACTIVATEPRODUCTION);
                    try {
                        model.getTurn().getTurnPhase().activateProduction(model.getTurn(), message);
                    } catch (InvalidActionException e) {
                        sender.sendError(new ActionError(ActionError.Trigger.NORMALACTIONALREADYDONE));
                    } catch (PaymentErrorException e) {
                        sender.sendError(new ActivateProductionError(ActivateProductionError.Trigger.PAYMENTERROR));
                    } catch (NotEnoughResourcesException e) {
                        sender.sendError(new ActivateProductionError(ActivateProductionError.Trigger.NOTENOUGHRESOURCES));
                    } catch (ResourceMismatchException e) {
                        sender.sendError(new ActivateProductionError(ActivateProductionError.Trigger.RESOURCESMISMATCH));
                    }
                }
                else sender.sendError(new ActivateProductionError(ActivateProductionError.Trigger.INVALIDREQUEST));
            }
            else sender.sendError(new InvalidMessageError());
        } else sender.sendError(new WrongTurnError());
    }

    public void handleTakeResourcesFromMarketMessage(TakeResourcesFromMarketMessage message, RemoteView sender) {
        if(isSenderTurn(sender.getPlayer())) {
            if (message.isValidMessage()) {
                if (model.getMarketTray().checkRequestedMarbles(message.getRequestedMarbles(), message.getPlayerChoice(), message.getIndex())) {
                    model.getTurn().setTurnState(TurnState.ActionType.TAKERESOURCESFROMMARKET);
                    try {
                        try {
                            model.getTurn().getTurnPhase().takeResourcesFromMarket(model.getTurn(), message);
                        } catch (NeedPositioningException e) {
                            sender.update(new ServerAsksForPositioning());
                        }
                    } catch (InvalidActionException e) {
                        sender.sendError(new ActionError(ActionError.Trigger.NORMALACTIONALREADYDONE));
                    } catch (WhiteEffectMismatchException e) {
                        sender.sendError(new TakeResourcesFromMarketError(TakeResourcesFromMarketError.Trigger.WHITEEFFECTMISMATCH));
                    }
                } else sender.sendError(new TakeResourcesFromMarketError(TakeResourcesFromMarketError.Trigger.MARBLEMISMATCH));
            } else sender.sendError(new InvalidMessageError());
        } else sender.sendError(new WrongTurnError());
    }

    public void handleLeaderActionMessage(LeaderActionMessage message, RemoteView sender) {
        if(isSenderTurn(sender.getPlayer())) {
            if (message.isValidMessage()) {
                model.getTurn().setTurnState(TurnState.ActionType.LEADERACTION);
                try {
                    model.getTurn().getTurnPhase().leaderAction(model.getTurn(), message);
                } catch (LeaderStatusException e) {
                    sender.sendError(new LeaderActionError(LeaderActionError.Trigger.LEADERSTATUS));
                } catch (LeaderRequirementsException e) {
                    sender.sendError(new LeaderActionError(LeaderActionError.Trigger.REQUIREMENTS));
                }
            } else sender.sendError(new InvalidMessageError());
        } else sender.sendError(new WrongTurnError());
    }

    public void handleMoveMessage(MoveResourcesMessage message, RemoteView sender) {
        if(isSenderTurn(sender.getPlayer())) {
            if (message.isValidMessage()) {
                try {
                    model.getCurrPlayer().moveResources(message.getMoveAction());
                } catch (MoveResourcesException e) {
                    sender.sendError(new MoveResourcesError(MoveResourcesError.Trigger.MOVE));
                }
            }
            else sender.sendError(new InvalidMessageError());
        } else sender.sendError(new WrongTurnError());
    }

    public void handlePositioningMessage(PositioningMessage message, RemoteView sender) {
        if(isSenderTurn(sender.getPlayer())) {
            if (message.isValidMessage()) {
                if (model.getTurn().getActionType() == TurnState.ActionType.TAKERESOURCESFROMMARKET) {
                    TakeResourcesFromMarket turnInstance = (TakeResourcesFromMarket) model.getTurn().getTurnPhase();

                    //check if Resources contained in positioning Message match with left resources in TakeResourcesFromMarket
                    if (turnInstance.checkPendingResourcesPositioning(message.getResourcesToPut())) {
                        try {
                            turnInstance.handlePositioning(model.getTurn().getPlayer().getPersonalBoard().getWarehouse(), message.getWhereToPutResources());
                        } catch (PositioningException e) {
                            //send Error to the client
                            sender.sendError(new PositioningError(PositioningError.Trigger.DISCARDEDRESOURCES));
                        }
                        //TODO: AGGIUNGERE CONCLUDETURNPHASE IN TUTTE LE ALTRE AZIONI DEL TURNO
                        turnInstance.concludeTurnPhase(model.getTurn());
                    } else {
                        sender.sendError(new InvalidMessageError());
                    }
                } else {
                    sender.sendError(new ActionError(ActionError.Trigger.WRONGTURNPHASE));
                }
            } else {
                sender.sendError(new InvalidMessageError());
            }
        }
        else {
            sender.sendError(new WrongTurnError());
        }

    }

    private boolean isSenderTurn(Player sender) {
        return currPlayer.equals(sender);
    }

}
