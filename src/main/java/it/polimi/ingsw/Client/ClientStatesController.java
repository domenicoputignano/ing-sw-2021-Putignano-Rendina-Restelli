package it.polimi.ingsw.Client;


import it.polimi.ingsw.Client.clientstates.cli.GameModeChoiceCLI;
import it.polimi.ingsw.Client.clientstates.cli.NumOfPlayersChoiceCLI;
import it.polimi.ingsw.Client.clientstates.cli.UsernameChoiceCLI;
import it.polimi.ingsw.Client.view.UI;
import it.polimi.ingsw.Utils.Messages.ServerMessages.ServerAskForGameMode;
import it.polimi.ingsw.Utils.Messages.ServerMessages.ServerAskForNumOfPlayer;
import it.polimi.ingsw.Utils.Messages.ServerMessages.ServerAsksForNickname;

public class ClientStatesController {

    public static void updateClientState(ServerAsksForNickname message, UI ui) {
        if(ui.isCLI()){
            ui.changeClientState(new UsernameChoiceCLI(ui.getClient()));

        }
        //todo con i metodi della GUI
        ui.showUpdate(message);
        ui.manageUserInteraction();
    }

    public static void updateClientState(ServerAskForGameMode message, UI ui) {
        if(ui.isCLI())
            ui.changeClientState(new GameModeChoiceCLI(ui.getClient()));
        ui.showUpdate(message);
        ui.manageUserInteraction();
    }

    public static void updateClientState(ServerAskForNumOfPlayer message, UI ui) {
        if(ui.isCLI()) {
            ui.changeClientState(new NumOfPlayersChoiceCLI(ui.getClient()));
        }
        ui.showUpdate(message);
        ui.manageUserInteraction();
    }

}
