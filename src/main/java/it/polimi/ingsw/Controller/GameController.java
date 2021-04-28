package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.MultiPlayerMode;
import it.polimi.ingsw.Model.GameState;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Utils.Messages.LeaderChoiceMessage;
import it.polimi.ingsw.Utils.Messages.ResourceChoiceMessage;

import java.util.logging.Level;
import java.util.logging.Logger;


public class GameController {
    private Logger LOGGER = Logger.getLogger(GameController.class.getName());
    private final Game model;
    private final TurnController turnController;


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
            sender.performInitialResourcesChoice(message.getChosenResources());
        } else {
            //TODO notificare il client dell'errore
        }
    }

    public synchronized void handleLeaderChoiceMessage(LeaderChoiceMessage message, Player sender) {
        while (model.getGameState() != GameState.LEADERCHOICE) {
            try { wait(); }
            catch (InterruptedException e) {
                LOGGER.log(Level.SEVERE, "Thread accidentally interrupted");
            }
        }
        if(message.isValidMessage()) {
            sender.performInitialLeaderChoice(message.getLeader1ToDiscard(), message.getLeader2ToDiscard());
        } else {

        }
    }

}

