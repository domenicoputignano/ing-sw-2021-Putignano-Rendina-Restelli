package it.polimi.ingsw.Client;


import it.polimi.ingsw.Client.clientstates.AbstractActionChoice;
import it.polimi.ingsw.Client.clientstates.AbstractClientState;
import it.polimi.ingsw.Client.clientstates.cli.*;
import it.polimi.ingsw.Client.view.UI;
import it.polimi.ingsw.Utils.Messages.ServerMessages.*;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Errors.ErrorMessage;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.*;

public class ClientStatesController {

    public static void updateClientState(ServerAsksForNickname message, UI ui) {
        if(ui.isCLI()){
            ui.changeClientState(new UsernameChoiceCLI(ui.getClient()));

        }
        //todo con i metodi della GUIApp
        ui.manageUserInteraction();
    }

    public static void updateClientState(ServerAskForGameMode message, UI ui) {
        if(ui.isCLI())
            ui.changeClientState(new GameModeChoiceCLI(ui.getClient()));
        ui.manageUserInteraction();
    }

    public static void updateClientState(ServerAskForNumOfPlayer message, UI ui) {
        if(ui.isCLI()) {
            ui.changeClientState(new NumOfPlayersChoiceCLI(ui.getClient()));
        }
        ui.manageUserInteraction();
    }


    public static void updateClientState(GameSetupMessage message, UI ui) {
        if(ui.isCLI()) {
            if (!ui.isSoloMode()) {
                ((LobbyCLI) ui.getClientState()).shutDownWaiterThread();
            }
            ui.changeClientState(new InitialLeaderChoiceCLI(ui.getClient()));
            ui.manageUserInteraction();
        } else {
            //TODO metodo con GUIApp
        }
    }

    public static void updateClientState(InitialLeaderChoiceUpdate message, UI ui) {
        if(ui.isReceiverAction(message.getUser())) {
            if(ui.isCLI()) {
                if(ui.isSoloMode()) {
                    ui.changeClientState(new ActionChoiceCLI(ui.getClient()));
                    ui.manageUserInteraction();
                } else {
                    if(ui.getClient().getUserPosition() > 1) {
                        ui.changeClientState(new InitialResourceChoiceCLI(ui.getClient()));
                        ui.manageUserInteraction();
                    } else {
                        System.out.println("Waiting for players that are performing initial choices...");
                        ui.changeClientState(new WaitForTurnCLI(ui.getClient()));
                        ui.getClientState().manageUserInteraction();
                        //TODO settare il client in attesa del completamento della configurazione
                    }
                }
            } else {

            }
        }

    }

    public static void updateClientState(ServerAsksForPositioning message, UI ui) {
        if(ui.isReceiverAction(message.getUser())) {
            if(ui.isCLI()) {
                ui.changeClientState(new PositioningResourcesCLI(ui.getClient(), message.getResourcesToSettle()));
                ui.manageUserInteraction();
            } else {
                //TODO metodo con GUIApp
            }
        }
    }


    public static void updateClientState(NewTurnUpdate message, UI ui) {
        if(ui.isReceiverAction(message.getCurrentUser())) {
            if(ui.isCLI()) {
                WaitForTurnCLI state = (WaitForTurnCLI) ui.getClientState();
                state.shutDownWaiterThread();
                ui.changeClientState(new ActionChoiceCLI(ui.getClient()));
                ui.manageUserInteraction();
            }
        }
    }

    public static void updateClientState(LorenzoPlayedUpdate message, UI ui) {
        if(ui.isCLI()) {
            WaitForTurnCLI state = (WaitForTurnCLI) ui.getClientState();
            state.shutDownWaiterThread();
            ui.changeClientState(new ActionChoiceCLI(ui.getClient()));
            ui.manageUserInteraction();
        }
    }

    public static void updateClientState(UpdateMessage message, UI ui) {
        if(ui.isReceiverAction(message.getUser())) {
            if(ui.isCLI()){
                ui.changeClientState(new ActionChoiceCLI(ui.getClient()));
                ui.manageUserInteraction();
            }
        } else {

        }
    }

    public static void updateClientState(GameResumedMessage message, UI ui) {
        if(ui.isCLI()) {
            if(message.getGame().isSoloMode()) {
                ui.changeClientState(new ActionChoiceCLI(ui.getClient()));
                ui.manageUserInteraction();
            } else {
            ui.changeClientState(new WaitForTurnCLI(ui.getClient()));
            ui.manageUserInteraction();
            }
        }
    }

    public static void updateClientState(ErrorMessage message, UI ui) {
        if(ui.isReceiverAction(message.getTriggeringUser())){
            if(ui.isCLI()){
                AbstractClientState previousState = ui.getClientState();
                //TODO fixare per bene, fixato con instance of (Es. errore durante la resource choice)
                if(previousState instanceof WaitForTurnCLI){
                    ((WaitForTurnCLI) previousState).shutDownWaiterThread();
                    ui.changeClientState(new InitialResourceChoiceCLI(ui.getClient()));
                }
                else {
                    ui.changeClientState(new ActionChoiceCLI(ui.getClient()));
                }
                ui.manageUserInteraction();
            }
        }
    }

}
