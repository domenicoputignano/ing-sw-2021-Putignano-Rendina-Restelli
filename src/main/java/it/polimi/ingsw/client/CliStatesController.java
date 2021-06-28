package it.polimi.ingsw.client;


import it.polimi.ingsw.client.clientstates.AbstractClientState;
import it.polimi.ingsw.client.clientstates.cli.*;
import it.polimi.ingsw.client.view.UI;
import it.polimi.ingsw.utils.messages.serverMessages.*;
import it.polimi.ingsw.utils.messages.serverMessages.Errors.ErrorMessage;
import it.polimi.ingsw.utils.messages.serverMessages.Updates.*;

/**
 * Class that provides overloading methods to update status of the {@link it.polimi.ingsw.client.view.CLI} after
 * receiving a certain type of message, in the sense that this changes what a player graphically see in his interface and the interaction allowed at runtime.
 */
public class CliStatesController {

    public static void updateCliState(ServerAsksForNickname message, UI ui) {
        if(ui.isCLI()){
            ui.changeCliState(new UsernameChoiceCLI(ui.getClient()));
        }
        ui.manageUserInteraction();
    }


    public static void updateCliState(ServerAskForGameMode message, UI ui) {
        if(ui.isCLI())
            ui.changeCliState(new GameModeChoiceCLI(ui.getClient()));
        ui.manageUserInteraction();
    }


    public static void updateCliState(ServerAskForNumOfPlayer message, UI ui) {
        if(ui.isCLI()) {
            ui.changeCliState(new NumOfPlayersChoiceCLI(ui.getClient()));
        }
        ui.manageUserInteraction();
    }



    public static void updateCliState(GameSetupMessage message, UI ui) {
        if(ui.isCLI()) {
            if (!ui.isSoloMode()) {
                ((LobbyCLI) ui.getClientState()).shutDownWaiterThread();
            }
            ui.changeCliState(new InitialLeaderChoiceCLI(ui.getClient()));
            ui.manageUserInteraction();
        }
    }

    public static void updateCliState(InitialLeaderChoiceUpdate message, UI ui) {
        if(ui.isReceiverAction(message.getUser())) {
            if(ui.isCLI()) {
                if(ui.isSoloMode()) {
                    ui.changeCliState(new ActionChoiceCLI(ui.getClient()));
                    ui.manageUserInteraction();
                } else {
                    if(ui.getClient().getUserPosition() > 1) {
                        ui.changeCliState(new InitialResourceChoiceCLI(ui.getClient()));
                        ui.manageUserInteraction();
                    } else {
                        System.out.println("Waiting for players that are performing initial choices...");
                        ui.changeCliState(new WaitForTurnCLI(ui.getClient()));
                        ui.getClientState().manageUserInteraction();
                        //TODO settare il client in attesa del completamento della configurazione
                    }
                }
            }
        }

    }

    public static void updateCliState(ServerAsksForPositioning message, UI ui) {
        if(ui.isReceiverAction(message.getUser())) {
            if(ui.isCLI()) {
                ui.changeCliState(new PositioningResourcesCLI(ui.getClient(), message.getResourcesToSettle()));
                ui.manageUserInteraction();
            }
        }
    }


    public static void updateCliState(NewTurnUpdate message, UI ui) {
        if(ui.isReceiverAction(message.getCurrentUser())) {
            if(ui.isCLI()) {
                WaitForTurnCLI state = (WaitForTurnCLI) ui.getClientState();
                state.shutDownWaiterThread();
                ui.changeCliState(new ActionChoiceCLI(ui.getClient()));
                ui.manageUserInteraction();
            }
        }
    }

    public static void updateCliState(LorenzoPlayedUpdate message, UI ui) {
        if(ui.isCLI()) {
            if(ui.getClientState() instanceof WaitForTurnCLI) {
                WaitForTurnCLI state = (WaitForTurnCLI) ui.getClientState();
                state.shutDownWaiterThread();
            }
            ui.changeCliState(new ActionChoiceCLI(ui.getClient()));
            ui.manageUserInteraction();
        }
    }

    public static void updateCliState(UpdateMessage message, UI ui) {
        if(ui.isReceiverAction(message.getUser())) {
            if(ui.isCLI()){
                ui.changeCliState(new ActionChoiceCLI(ui.getClient()));
                ui.manageUserInteraction();
            }
        }
    }

    public static void updateCliState(GameResumedMessage message, UI ui) {
        if(ui.isCLI()) {
            if(message.getGame().isSoloMode()) {
                ui.changeCliState(new ActionChoiceCLI(ui.getClient()));
                ui.manageUserInteraction();
            } else {
                if(!ui.getClient().getUser().equals(message.getCurrentUser())) {
                    ui.changeCliState(new WaitForTurnCLI(ui.getClient()));
                    ui.getClientState().manageUserInteraction();
                    } else {
                    //ui.changeCliState(new ActionChoiceCLI(ui.getClient()));
                    //ui.manageUserInteraction();
                }
            }
        }
    }

    public static void updateCliState(ErrorMessage message, UI ui) {
        if(ui.isReceiverAction(message.getTriggeringUser())){
            if(ui.isCLI()){
                AbstractClientState previousState = ui.getClientState();
                //TODO fixare per bene, fixato con instance of (Es. errore durante la resource choice)
                if(previousState instanceof WaitForTurnCLI){
                    ((WaitForTurnCLI) previousState).shutDownWaiterThread();
                    ui.changeCliState(new InitialResourceChoiceCLI(ui.getClient()));
                }
                else {
                    ui.changeCliState(new ActionChoiceCLI(ui.getClient()));
                }
                ui.manageUserInteraction();
            }
        }
    }

}
