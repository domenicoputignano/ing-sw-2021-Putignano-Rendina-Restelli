package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.GameState;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Network.RemoteView;
import it.polimi.ingsw.Utils.Messages.ClientMessages.*;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Errors.ActionError;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Errors.InvalidMessageError;

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
        this.turnController = new TurnController(this.model, model.getPlayerList(), model.getCurrPlayer());
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
            }
        }
        if(message.isValidMessage()) {
            if(checkPlayerResourceChoice(message, sender.getPlayer())){
                sender.getPlayer().performInitialResourcesChoice(model, message.getChosenResources());
                receivedChoiceMessage.getAndIncrement();
                checkAllResourceChoiceDone(receivedChoiceMessage);
            } else {
                sender.sendError(new ActionError(ActionError.Trigger.RESOURCECHOICEMISMATCH));
            }
        }else {
            sender.sendError(new InvalidMessageError());
        }
    }

    public synchronized void handleLeaderChoiceMessage(LeaderChoiceMessage message, RemoteView sender) {
        if(model.getGameState() != GameState.LEADERCHOICE){
            if(message.isValidMessage()) {
                sender.getPlayer().performInitialLeaderChoice(model,message.getLeader1ToDiscard(), message.getLeader2ToDiscard());
                receivedChoiceMessage.getAndIncrement();
                checkAllLeaderChoicesDone(receivedChoiceMessage);
            } else {
                sender.sendError(new InvalidMessageError());
            }
        } else {
            sender.sendError(new ActionError(ActionError.Trigger.WRONGGAMEPHASE));
        }
    }

    private void checkAllLeaderChoicesDone(AtomicInteger leaderChoicesDone) {
        if(leaderChoicesDone.get() == model.getNumOfPlayers()){
            model.nextState(GameState.RESOURCECHOICE);
            receivedChoiceMessage.set(0);
            notifyAll();
        }
    }

    private void checkAllResourceChoiceDone(AtomicInteger resourceChoiceDone) {
        if(resourceChoiceDone.get() == model.getNumOfPlayers()-1){
            model.nextState(GameState.GAMEFLOW);
            receivedChoiceMessage.set(0);
        }
    }


    private boolean checkPlayerResourceChoice(ResourceChoiceMessage message, Player sender){
        if(sender.getPosition()==2 || sender.getPosition()==3) return message.getChosenResources().size() == 1;
        if(sender.getPosition()==4) return message.getChosenResources().size() == 2;
        return true;
    }
}

