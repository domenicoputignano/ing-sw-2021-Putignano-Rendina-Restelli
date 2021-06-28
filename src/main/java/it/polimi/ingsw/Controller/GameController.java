package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Commons.ResourceType;
import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.GameState;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Network.NetworkRemoteView;
import it.polimi.ingsw.Network.RemoteView;
import it.polimi.ingsw.Utils.Messages.ClientMessages.*;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Errors.ActionError;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Errors.InvalidMessageError;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.NewTurnUpdate;
import it.polimi.ingsw.Utils.Pair;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * This class represents the Controller in MVC design pattern. The whole
 * application, in fact, is based on MVC pattern, and in particular
 * Game Controller is called whenever a {@link GameControllerHandleable} message is received.
 * It handles initial choices done by players before starting the first turn.
 * Messages related to an action performed in a {@link it.polimi.ingsw.Model.Turn} are forwared to {@link TurnController}.
 * modify the model itself.
 */
public class GameController {
    private final Logger LOGGER = Logger.getLogger(GameController.class.getName());
    private final Game model;
    private final TurnController turnController;
    private final AtomicInteger receivedLeaderChoices = new AtomicInteger(0);
    private final AtomicInteger receivedResourceChoices = new AtomicInteger(0);
    private Timer gameCleaner;

    /**
     * Initializes a game controller with a game and a turn controller.
     */
    public GameController(Game game) {
        this.model = game;
        this.turnController = new TurnController(this.model, model.getCurrPlayer());
    }

    public TurnController getTurnController() {
        return turnController;
    }

    public Game getModel() {
        return model;
    }

    /**
     * Handles a resource choice message and updates number of processed messages.
     * Notifies remote view if any error has been detected before performing action.
     * @param message message to be handled.
     * @param sender remote view that has forwarded the message.
     */
    public synchronized void handleResourceChoiceMessage(ResourceChoiceMessage message, RemoteView sender) {
        if(message.isValidMessage()) {
            if(checkPlayerResourceChoice(message, sender.getPlayer())){
                if(sender.getPlayer().getPosition()==4) {
                    if (isValidFourthPlayerPositioning(message.getChosenResources())) {
                        sender.getPlayer().performInitialResourcesChoice(model, message.getChosenResources());
                        receivedResourceChoices.getAndIncrement();
                        startFirstTurn();
                    } else {
                        sender.sendError(new ActionError(sender.getPlayer().getUser(), ActionError.Trigger.INITIALRESOURCEPOSITIONINGERROR));
                    }
                } else {
                    sender.getPlayer().performInitialResourcesChoice(model, message.getChosenResources());
                    receivedResourceChoices.getAndIncrement();
                    startFirstTurn();
                }
            } else {
                sender.sendError(new ActionError(sender.getPlayer().getUser(),ActionError.Trigger.RESOURCECHOICEMISMATCH));
            }
        }else {
            sender.sendError(new InvalidMessageError(sender.getPlayer().getUser()));
        }
    }

    /**
     * Handles an initial leader cards choice. It takes into account of how many messages has been
     * correctly processed because messages are received asynchronously in the sense that a player can choose leaders while
     * another one is choosing his resources, in this way it's possible to detect if all choices have been made before starting the
     * first turn.
     * @param message message to be handled.
     * @param sender remote view that has forwarded the message.
     */
    public synchronized void handleLeaderChoiceMessage(LeaderChoiceMessage message, RemoteView sender) {
        if (message.isValidMessage()) {
            sender.getPlayer().performInitialLeaderChoice(model, message.getLeader1ToDiscard(), message.getLeader2ToDiscard());
            receivedLeaderChoices.getAndIncrement();
            startFirstTurn();
        } else {
            sender.sendError(new InvalidMessageError(sender.getPlayer().getUser()));
        }
    }

    /**
     * Handles an user reconnection.
     * @param reconnectingUser user that is reconnecting.
     */
    public synchronized void handlePlayerReconnection(User reconnectingUser){
        if(model.getGameState() == GameState.PAUSED) gameCleaner.cancel();
        turnController.handlePlayerReconnection(reconnectingUser);
    }

    /**
     * Handles an user disconnection. If this event happens before that leaders choice is done by disconnected user,
     * game controller makes an autonomous decision about which leader cards have to be discarded.
     * In case of disconnection during a game turn further decisions are handled by turn controller.
     * If who has just disconnected was the last playing user, it starts a timer that after its deadline will delete
     * the game.
     * @param disconnectingUser user that lost connection.
     * @param remoteView remote view of user that lost connection.
     */
    public synchronized void handlePlayerDisconnection(User disconnectingUser, NetworkRemoteView remoteView) {
        if(model.getGameState() == GameState.INITIALCHOICES) {
            if(model.getPlayer(disconnectingUser).getLeaderCards().size() > 2) {
                model.getPlayer(disconnectingUser).performInitialLeaderChoice(model,4,3);
                receivedLeaderChoices.getAndIncrement();
            }
            if(model.getPlayer(disconnectingUser).getPosition() > 1 && hasNotDoneResourceChoice(disconnectingUser)) {
                receivedResourceChoices.getAndIncrement();
            }
            turnController.handlePlayerDisconnection(disconnectingUser);
            startFirstTurn();
        } else if(model.getGameState() == GameState.GAMEFLOW)
            turnController.handlePlayerDisconnection(disconnectingUser);
        if(model.getGameState() == GameState.PAUSED) {
            List<User> usersToBeRemoved = model.getPlayerList().stream().map(Player::getUser).collect(Collectors.toList());
            gameCleaner = new Timer();
            gameCleaner.schedule(new TimerTask() {
                @Override
                public void run() {
                    LOGGER.log(Level.INFO, "Match is being deleted");
                    remoteView.deleteMatch(usersToBeRemoved);
                }
            }, 10000);
        }
    }

    private boolean checkAllLeaderChoicesDone(AtomicInteger leaderChoicesDone) {
        return leaderChoicesDone.get() == model.getNumOfPlayers();
    }

    private boolean checkAllChoicesDone(AtomicInteger resourceChoiceDone) {
        return (resourceChoiceDone.get() == model.getNumOfPlayers()-1)&&checkAllLeaderChoicesDone(receivedLeaderChoices);
    }

    /**
     * Method that handles decisions about starting the first turn, that can be done
     * only if all the players have chosen leader cards and for those who are able to, resources.
     */
    private void startFirstTurn() {
        if (checkAllChoicesDone(receivedResourceChoices)) {
            model.nextState(GameState.GAMEFLOW);
            if(model.getNumOfPlayers()>1)
                model.notifyTurn(new NewTurnUpdate(model.getCurrPlayer().getUser()));
        }
    }

    /**
     * Boolean method that checks if a resource choice is legal given player position.
     * @param message message containing chosen resources.
     * @param sender player that made resource choice.
     */
    private boolean checkPlayerResourceChoice(ResourceChoiceMessage message, Player sender){
        if(sender.getPosition()==2 || sender.getPosition()==3) return message.getChosenResources().size() == 1;
        if(sender.getPosition()==4) {
            return message.getChosenResources().size()==2;
        }
        return true;
    }

    /**
     * Method to check if the fourth player in a game has correctly chosen destination for his resources.
     * Since only the fourth player has the possibility to choose two resources, he is also the only one that can make errors
     * choosing them, so this control is necessary to avoid any errors.
     */
    public boolean isValidFourthPlayerPositioning(List<Pair<ResourceType,Integer>> resourceDestination) {
        if(resourceDestination.get(0).getKey() == resourceDestination.get(1).getKey()) {
            if(resourceDestination.stream().map(Pair::getValue).anyMatch(x -> x.equals(1))) return false;
            return resourceDestination.get(0).getValue().equals(resourceDestination.get(1).getValue());
        } else {
            if(resourceDestination.get(0).getValue().equals(resourceDestination.get(1).getValue())) return false;
            if(resourceDestination.stream().map(Pair::getValue).anyMatch(x -> x > 3||x < 1)) return false;
        }
        return true;
    }

    /**
     * Boolean that performs a player warehouse inspection and checks if a player has done his resources choice
     * before losing the connection.
     * @param disconnectingUser user that lost connection during initial choices phase of the game.
     */
    private boolean hasNotDoneResourceChoice(User disconnectingUser) {
        return model.getPlayer(disconnectingUser).getPersonalBoard().getWarehouse().getAvailableResources().entrySet().stream().allMatch(x -> x.getValue() == 0);
    }

}

