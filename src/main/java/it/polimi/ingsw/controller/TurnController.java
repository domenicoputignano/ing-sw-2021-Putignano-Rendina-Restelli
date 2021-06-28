package it.polimi.ingsw.controller;

import it.polimi.ingsw.commons.User;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.RemoteView;
import it.polimi.ingsw.utils.messages.clientMessages.*;
import it.polimi.ingsw.utils.messages.serverMessages.Errors.*;
import it.polimi.ingsw.utils.messages.serverMessages.Updates.ServerAsksForPositioning;


/**
 * This class represents the link between player in turn and the game.
 * This, together with {@link GameController}, represents the Controller of the MVC Architectural pattern.
 */

public class TurnController {
    private Player currPlayer;
    private final Game model;


    /**
     * Constructor that initialize all the attributes.
     * @param model, instance of the game to which turn controller is related.
     * @param firstPlayer, player in turn at the beginning of the match.
     */
    public TurnController(Game model, Player firstPlayer) {
        this.model = model;
        this.currPlayer = firstPlayer;
    }


    /**
     * Dispatches an action of buying development card type to the model. It only accepts messages from player involved in the turn and
     * then makes an inspection of the message itself. After ensuring a sort of high level correctness,
     * It establishes how the model will notify remote views about any error occurred.
     * @param message message containing instruction on how to perform buy development card action.
     * @param sender remote view that forwards the message.
     */
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

    /**
     * Dispatches an activate production action to the model. It only accepts messages from player involved in the turn and
     * then makes an inspection of the message itself. After ensuring a sort of high level correctness,
     * It catches exceptions if any error occurred and, in this case, it establishes how the model will
     * notify remote views.
     * @param message message containing instructions on how to perform an activate production action.
     * @param sender  remote view that forwards the message.
     */
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

    /**
     * Dispatches a take resources from market action to the model.
     * If needed all the exceptional flows are caught and they are handled.
     * It catches exceptions if any error occurred and, in this case, it establishes how the model will
     * notify remote views.
     * {@link NeedPositioningException} is handled in a different way because if it is thrown basically means that the model requires
     * further action from the player.
     * @param message message containing information on how to perform the action.
     * @param sender remote view that forwards the message.
     */
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

    /**
     * Dispatches a leader action to the model after a message validity check.
     * If any exception is thrown, it establishes how the model will notify remote views.
     * @param message message containing instructions on how to perform the action.
     * @param sender remote view that forwards the message.
     */
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

    /**
     * Dispatches a move action to the model.
     * If any exception is thrown, it establishes how the model will notify remote views.
     * @param message message containing instructions on how to perform a move action
     * @param sender remote view that forwards the message
     */
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

    /**
     * Dispatches a positioning action to the model. This can happen after that a {@link NeedPositioningException} is thrown while performing
     * a take resources from market, so the player has to settle his pending resources, that's why it checks if the model is in a take resources from market phase.
     * If any exception is thrown, it establishes how the model will notify remote views.
     * @param message message containing instructions on how to settle each resource.
     * @param sender remote view that forwards the message.
     */
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

    /**
     * Dispatches an end turn action to the model after being assured that a normal action has been done.
     * @param message message triggered by player when he wants to close the turn.
     * @param sender remote view that forwards the message.
     */
    public synchronized void handleEndTurnMessage(EndTurnMessage message, RemoteView sender) {
        if(model.getTurn().isDoneNormalAction()){
            if(isSenderTurn(sender.getPlayer())) {
                model.nextTurn();
                this.currPlayer = model.getCurrPlayer();
            }
            else sender.sendError(new WrongTurnError(sender.getPlayer().getUser()));
        } else sender.sendError(new ActionError(sender.getPlayer().getUser(), ActionError.Trigger.NORMALACTIONNOTDONEYET));
    }

    /**
     * This method alerts the model that an user is attempting to reconnect.
     * @param reconnectingUser user that wants to join the same game.
     */
    public synchronized void handlePlayerReconnection(User reconnectingUser) {
        model.handlePlayerReconnection(reconnectingUser);
        this.currPlayer = model.getCurrPlayer();
    }

    /**
     * This method alerts the model that an user has just disconnected.
     * @param disconnectingUser user that lost the connection.
     */
    public synchronized void handlePlayerDisconnection(User disconnectingUser) {
        model.handlePlayerDisconnection(model.getPlayer(disconnectingUser));
        this.currPlayer = model.getCurrPlayer();
    }


    /**
     * Boolean method the tells if the player how has just sent a message is the player in turn.
     */
    private boolean isSenderTurn(Player sender) {
        return currPlayer.equals(sender);
    }

}
