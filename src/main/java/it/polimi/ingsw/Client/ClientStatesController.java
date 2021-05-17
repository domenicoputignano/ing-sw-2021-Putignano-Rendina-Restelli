package it.polimi.ingsw.Client;


import it.polimi.ingsw.Client.clientstates.cli.*;
import it.polimi.ingsw.Client.view.UI;
import it.polimi.ingsw.Utils.Messages.ServerMessages.GameSetupMessage;
import it.polimi.ingsw.Utils.Messages.ServerMessages.ServerAskForGameMode;
import it.polimi.ingsw.Utils.Messages.ServerMessages.ServerAskForNumOfPlayer;
import it.polimi.ingsw.Utils.Messages.ServerMessages.ServerAsksForNickname;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.InitialLeaderChoiceUpdate;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.NewTurnUpdate;

public class ClientStatesController {

    public static void updateClientState(ServerAsksForNickname message, UI ui) {
        if(ui.isCLI()){
            ui.changeClientState(new UsernameChoiceCLI(ui.getClient()));

        }
        //todo con i metodi della GUI
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
            ui.changeClientState(new InitialLeaderChoiceCLI(ui.getClient()));
            ui.manageUserInteraction();
        } else {
            //TODO metodo con GUI
        }
    }

    public static void updateClientState(InitialLeaderChoiceUpdate message, UI ui) {
        if(ui.isCLI()) {
            if(ui.getClient().getUserPosition() > 1 && ui.isReceiverAction(message.getUser())) {
                ui.changeClientState(new InitialResourceChoiceCLI(ui.getClient()));
                ui.manageUserInteraction();
            } else {
                //TODO settare il client in attesa del completamento della configurazione
            }
        } else {

        }
    }

    public static void updateClientState(NewTurnUpdate message, UI ui) {

    }

}
