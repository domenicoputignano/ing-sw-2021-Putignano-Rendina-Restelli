package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Network.RemoteView;
import it.polimi.ingsw.Utils.Messages.ClientMessages.*;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Errors.*;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.NewTurnUpdate;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.ServerAsksForPositioning;


public class TurnController {
    private Player currPlayer;
    private final Game model;

    public TurnController(Game model, Player firstPlayer) {
        this.model = model;
        this.currPlayer = firstPlayer;
    }



    public synchronized void handleBuyDevCardMessage(BuyDevCardMessage message, RemoteView sender) {
        if (isSenderTurn(sender.getPlayer())) {
            if (message.isValidMessage()) {
                if (!model.isEmptyDeck(message.getType())) {
                    model.getTurn().setTurnState(TurnState.ActionType.BUYDEVCARD);
                    currPlayer.getPersonalBoard().isCompatibleSlot(message.getType().getLevel(), message.getDestinationSlot());
                    try {
                        model.getTurn().getTurnPhase().buyDevCard(model.getTurn(), message);
                    } catch (InvalidActionException e) {
                        model.notifyError(new ActionError(sender.getPlayer().getUser(),ActionError.Trigger.NORMALACTIONALREADYDONE));
                    } catch (NotEnoughResourcesException e) {
                        model.notifyError(new BuyDevCardError(sender.getPlayer().getUser(),BuyDevCardError.Trigger.NOTENOUGHRESOURCES));
                    } catch (ResourceMismatchException e) {
                        model.notifyError(new BuyDevCardError(sender.getPlayer().getUser(),BuyDevCardError.Trigger.RESOURCESMISMATCH));
                    } catch (PaymentErrorException e) {
                        model.notifyError(new BuyDevCardError(sender.getPlayer().getUser(),BuyDevCardError.Trigger.PAYMENTERROR));
                    }
                } else model.notifyError(new BuyDevCardError(sender.getPlayer().getUser(),BuyDevCardError.Trigger.EMPTYDECK));
            } else sender.sendError(new InvalidMessageError(sender.getPlayer().getUser()));
        } else sender.sendError(new WrongTurnError(sender.getPlayer().getUser()));
    }

    public synchronized void handleActivateProductionMessage(ActivateProductionMessage message, RemoteView sender) {
        if(isSenderTurn(sender.getPlayer())) {
            if (message.isValidMessage()) {
                if (currPlayer.getPersonalBoard().isValidRequestedProduction(message.getProductions())) {
                    model.getTurn().setTurnState(TurnState.ActionType.ACTIVATEPRODUCTION);
                    try {
                        model.getTurn().getTurnPhase().activateProduction(model.getTurn(), message);
                    } catch (InvalidActionException e) {
                        model.notifyError(new ActionError(sender.getPlayer().getUser(),ActionError.Trigger.NORMALACTIONALREADYDONE));
                    } catch (PaymentErrorException e) {
                        model.notifyError(new ActivateProductionError(sender.getPlayer().getUser(),ActivateProductionError.Trigger.PAYMENTERROR));
                    } catch (NotEnoughResourcesException e) {
                        model.notifyError(new ActivateProductionError(sender.getPlayer().getUser(),ActivateProductionError.Trigger.NOTENOUGHRESOURCES));
                    } catch (ResourceMismatchException e) {
                        model.notifyError(new ActivateProductionError(sender.getPlayer().getUser(),ActivateProductionError.Trigger.RESOURCESMISMATCH));
                    }
                }
                else model.notifyError(new ActivateProductionError(sender.getPlayer().getUser(),ActivateProductionError.Trigger.INVALIDREQUEST));
            }
            else sender.sendError(new InvalidMessageError(sender.getPlayer().getUser()));
        } else sender.sendError(new WrongTurnError(sender.getPlayer().getUser()));
    }

    public synchronized void handleTakeResourcesFromMarketMessage(TakeResourcesFromMarketMessage message, RemoteView sender) {
        if(isSenderTurn(sender.getPlayer())) {
            if (message.isValidMessage()) {
                if (model.getMarketTray().checkRequestedMarbles(message.getRequestedMarbles(), message.getPlayerChoice(), message.getIndex())) {
                    model.getTurn().setTurnState(TurnState.ActionType.TAKERESOURCESFROMMARKET);
                    try {
                        try {
                            model.getTurn().getTurnPhase().takeResourcesFromMarket(model.getTurn(), message);
                        } catch (NeedPositioningException e) {
                            model.notifyUpdate(new ServerAsksForPositioning(currPlayer.getUser(),
                                    currPlayer.getPersonalBoard().getReducedVersion(),
                                    model.getMarketTray().getReducedVersion(), e.getResourcesToSettle()));
                        }
                    } catch (InvalidActionException e) {
                        model.notifyError(new ActionError(sender.getPlayer().getUser(),ActionError.Trigger.NORMALACTIONALREADYDONE));
                    } catch (WhiteEffectMismatchException e) {
                        model.notifyError(new TakeResourcesFromMarketError(sender.getPlayer().getUser(),TakeResourcesFromMarketError.Trigger.WHITEEFFECTMISMATCH));
                    }
                } else model.notifyError(new TakeResourcesFromMarketError(sender.getPlayer().getUser(),TakeResourcesFromMarketError.Trigger.MARBLEMISMATCH));
            } else sender.sendError(new InvalidMessageError(sender.getPlayer().getUser()));
        } else sender.sendError(new WrongTurnError(sender.getPlayer().getUser()));
    }

    public synchronized void handleLeaderActionMessage(LeaderActionMessage message, RemoteView sender) {
        if(isSenderTurn(sender.getPlayer())) {
            if (message.isValidMessage()) {
                model.getTurn().setTurnState(TurnState.ActionType.LEADERACTION);
                try {
                    model.getTurn().getTurnPhase().leaderAction(model.getTurn(), message);
                } catch (LeaderStatusException e) {
                    model.notifyError(new LeaderActionError(sender.getPlayer().getUser(),LeaderActionError.Trigger.LEADERSTATUS));
                } catch (LeaderRequirementsException e) {
                    model.notifyError(new LeaderActionError(sender.getPlayer().getUser(),LeaderActionError.Trigger.REQUIREMENTS));
                }
            } else sender.sendError(new InvalidMessageError(sender.getPlayer().getUser()));
        } else sender.sendError(new WrongTurnError(sender.getPlayer().getUser()));
    }

    public synchronized void handleMoveMessage(MoveResourcesMessage message, RemoteView sender) {
        if(isSenderTurn(sender.getPlayer())) {
            if (message.isValidMessage()) {
                try {
                    model.getCurrPlayer().moveResources(model, message.getMoveAction());
                } catch (MoveResourcesException e) {
                    model.notifyError(new MoveResourcesError(sender.getPlayer().getUser(),MoveResourcesError.Trigger.MOVE));
                }
            }
            else sender.sendError(new InvalidMessageError(sender.getPlayer().getUser()));
        } else sender.sendError(new WrongTurnError(sender.getPlayer().getUser()));
    }

    public synchronized void handlePositioningMessage(PositioningMessage message, RemoteView sender) {
        if(isSenderTurn(sender.getPlayer())) {
            if (message.isValidMessage()) {
                if (model.getTurn().getActionType() == TurnState.ActionType.TAKERESOURCESFROMMARKET) {
                    TakeResourcesFromMarket turnInstance = (TakeResourcesFromMarket) model.getTurn().getTurnPhase();

                    //check if Resources contained in positioning Message match with left resources in TakeResourcesFromMarket
                    if (turnInstance.checkPendingResourcesPositioning(message.getResourcesToPut())) {
                            turnInstance.handlePositioning(model.getTurn(), message.getWhereToPutResources());
                    } else {
                        sender.sendError(new InvalidMessageError(sender.getPlayer().getUser()));
                    }
                } else {
                    model.notifyError(new ActionError(sender.getPlayer().getUser(),ActionError.Trigger.WRONGTURNPHASE));
                }
            } else {
                sender.sendError(new InvalidMessageError(sender.getPlayer().getUser()));
            }
        }
        else {
            sender.sendError(new WrongTurnError(sender.getPlayer().getUser()));
        }

    }

    public synchronized void handleEndTurnMessage(EndTurnMessage message, RemoteView sender) {
        if(model.getTurn().isDoneNormalAction()){
            if(isSenderTurn(sender.getPlayer())) {
                model.nextTurn();
                this.currPlayer = model.getCurrPlayer();
            }
            else sender.sendError(new WrongTurnError(sender.getPlayer().getUser()));
        } else sender.sendError(new ActionError(sender.getPlayer().getUser(), ActionError.Trigger.NORMALACTIONNOTDONEYET));
    }

    private boolean isSenderTurn(Player sender) {
        return currPlayer.equals(sender);
    }

}
