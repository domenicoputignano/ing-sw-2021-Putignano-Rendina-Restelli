package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Commons.ResourceType;
import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.GameState;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Network.RemoteView;
import it.polimi.ingsw.Utils.Messages.ClientMessages.*;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Errors.ActionError;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Errors.InvalidMessageError;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.NewTurnUpdate;
import it.polimi.ingsw.Utils.Pair;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GameController {
    private final Logger LOGGER = Logger.getLogger(GameController.class.getName());
    private final Game model;
    private final TurnController turnController;
    private final AtomicInteger receivedChoiceMessage = new AtomicInteger(0);


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

    public synchronized void handleResourceChoiceMessage(ResourceChoiceMessage message, RemoteView sender) {
        while (model.getGameState() != GameState.RESOURCECHOICE) {
            try { wait(); }
            catch (InterruptedException e) {
                LOGGER.log(Level.SEVERE, "Thread accidentally interrupted");
                Thread.currentThread().interrupt();
            }
        }
        if(message.isValidMessage()) {
            if(checkPlayerResourceChoice(message, sender.getPlayer())){
                if(sender.getPlayer().getPosition()==4) {
                    if (isValidFourthPlayerPositioning(message.getChosenResources())) {
                        sender.getPlayer().performInitialResourcesChoice(model, message.getChosenResources());
                        receivedChoiceMessage.getAndIncrement();
                        checkAllResourceChoiceDone(receivedChoiceMessage);
                    } else {
                        sender.sendError(new ActionError(sender.getPlayer().getUser(), ActionError.Trigger.INITIALRESOURCEPOSITIONINGERROR));
                    }
                } else {
                    sender.getPlayer().performInitialResourcesChoice(model, message.getChosenResources());
                    receivedChoiceMessage.getAndIncrement();
                    checkAllResourceChoiceDone(receivedChoiceMessage);
                }
            } else {
                sender.sendError(new ActionError(sender.getPlayer().getUser(),ActionError.Trigger.RESOURCECHOICEMISMATCH));
            }
        }else {
            sender.sendError(new InvalidMessageError(sender.getPlayer().getUser()));
        }
    }

    public synchronized void handleLeaderChoiceMessage(LeaderChoiceMessage message, RemoteView sender) {
        if(model.getGameState() == GameState.LEADERCHOICE){
            if(message.isValidMessage()) {
                sender.getPlayer().performInitialLeaderChoice(model,message.getLeader1ToDiscard(), message.getLeader2ToDiscard());
                receivedChoiceMessage.getAndIncrement();
                if(checkAllLeaderChoicesDone(receivedChoiceMessage)) {
                    notifyAll();
                }
            } else {
                sender.sendError(new InvalidMessageError(sender.getPlayer().getUser()));
            }
        } else {
            sender.sendError(new ActionError(sender.getPlayer().getUser(),ActionError.Trigger.WRONGGAMEPHASE));
        }
    }

    public synchronized void handlePlayerReconnection(User reconnectingUser){
        turnController.handlePlayerReconnection(reconnectingUser);
    }

    public synchronized void handlePlayerDisconnection(User disconnectingUser) {
        turnController.handlePlayerDisconnection(disconnectingUser);
    }

    private boolean checkAllLeaderChoicesDone(AtomicInteger leaderChoicesDone) {
        if(leaderChoicesDone.get() == model.getNumOfPlayers()){
            model.nextState(GameState.RESOURCECHOICE);
            receivedChoiceMessage.set(0);
            return true;
        }
        return false;
    }

    private void checkAllResourceChoiceDone(AtomicInteger resourceChoiceDone) {
        if(resourceChoiceDone.get() == model.getNumOfPlayers()-1){
            model.nextState(GameState.GAMEFLOW);
            receivedChoiceMessage.set(0);
            model.notifyTurn(new NewTurnUpdate(model.getCurrPlayer().getUser()));
        }
    }


    private boolean checkPlayerResourceChoice(ResourceChoiceMessage message, Player sender){
        if(sender.getPosition()==2 || sender.getPosition()==3) return message.getChosenResources().size() == 1;
        if(sender.getPosition()==4) {
            //return isValidFourthPlayerPositioning(message.getChosenResources());
            return message.getChosenResources().size()==2;
        }
        return true;
    }

    private boolean isValidFourthPlayerPositioning(List<Pair<ResourceType,Integer>> resourceDestination) {
        //if(resourceDestination.size() != 2) return false;
        if(resourceDestination.get(0).getKey() == resourceDestination.get(1).getKey()) {
            if(resourceDestination.stream().map(Pair::getValue).anyMatch(x -> x.equals(1))) return false;
            if(!resourceDestination.get(0).getValue().equals(resourceDestination.get(1).getValue())) return false;
        } else {
            if(resourceDestination.stream().map(Pair::getValue).allMatch(x -> x.equals(1)||x.equals(2)||x.equals(3))) {
                return false;
            }
        }
        return true;
    }

}

