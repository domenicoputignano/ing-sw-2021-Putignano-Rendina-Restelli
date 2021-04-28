package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.MultiPlayerMode;
import it.polimi.ingsw.Model.GameState;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Utils.Messages.LeaderChoiceMessage;
import it.polimi.ingsw.Utils.Messages.ResourceChoiceMessage;

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

    public synchronized void handleResourceChoiceMessage(ResourceChoiceMessage message, Player sender) {
        while (model.getGameState() != GameState.RESOURCECHOICE) {
            try { wait(); }
            catch (InterruptedException e) {
                LOGGER.log(Level.SEVERE, "Thread accidentally interrupted");
            }
        }
        if(message.isValidMessage()) {
            if(checkPlayerResourceChoice(message, sender)){
                sender.performInitialResourcesChoice(message.getChosenResources());
                receivedChoiceMessage.getAndIncrement();
                checkAllResourceChoiceDone(receivedChoiceMessage);
            } else {
                // TODO HANDLEERROR("Numero di risorse scelte non compatibili")
            }
        }else {
            //TODO notificare il client dell'errore
        }
    }

    public synchronized void handleLeaderChoiceMessage(LeaderChoiceMessage message, Player sender) {
        if(model.getGameState() != GameState.LEADERCHOICE){
            if(message.isValidMessage()) {
                sender.performInitialLeaderChoice(message.getLeader1ToDiscard(), message.getLeader2ToDiscard());
                receivedChoiceMessage.getAndIncrement();
                checkAllLeaderChoicesDone(receivedChoiceMessage);
            } else {
                // TODO HANDLEERROR("Messaggio non valido")
            }
        } else {
            // TODO HANDLEERROR("Fase del gioco già effettuata")
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

