package it.polimi.ingsw.Client.view;

import it.polimi.ingsw.Client.clientstates.AbstractClientState;
import it.polimi.ingsw.Client.view.GUI.*;
import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Messages.ServerMessages.*;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.*;
import javafx.application.Platform;

import javax.print.DocFlavor;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Gui extends UI{

    public Gui(Client client) {
        super(client);
    }

    @Override
    public void manageUserInteraction() {

    }

    @Override
    public void changeClientState(AbstractClientState clientState) {

    }

    @Override
    public void showLeaderCards() {

    }

    @Override
    public void render(ServerAsksForNickname message) {
        try {
            GUIApp.waitForGameSetup(client);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, "Listening thread has been accidentally interrupted");
        }
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, "Listening thread has been accidentally interrupted");
        }
        Platform.runLater(() -> GUIApp.showScene("/gui/fxml/UserName.fxml")
        );
    }

    @Override
    public void render(ServerAskForGameMode message) {
        Platform.runLater(() -> GUIApp.showScene("/gui/fxml/SelectModePage.fxml")
        );
    }

    @Override
    public void render(ServerAskForNumOfPlayer message) {
        Platform.runLater(() -> GUIApp.showScene("/gui/fxml/SelectNumOfPlayersPage.fxml")
        );
    }

    @Override
    public void render(GameSetupMessage message) {
        Platform.runLater(() -> GUIApp.showScene("/gui/FXML/LeaderChoicePage.fxml"));
    }

    @Override
    public void render(GameResumedMessage message) {

    }

    @Override
    public void render(InitialLeaderChoiceUpdate message) {
        if(isReceiverAction(message.getUser())){
            if(isSoloMode()){
                Platform.runLater(() -> GUIApp.showScene("/gui/FXML/PlayerBoard.fxml"));
            } else {
                if(client.getUserPosition() > 1) {
                    Platform.runLater(() -> GUIApp.showScene("/gui/FXML/ResourceChoicePage.fxml"));
                } else {
                    // TODO MANDARE IN UNA SCHERMATA DI ATTESA
                }
            }
        }

    }

    @Override
    public void render(InitialResourceChoiceUpdate message) {

    }

    @Override
    public void render(ServerAsksForPositioning message) {

    }

    @Override
    public void render(NewTurnUpdate message) {

    }

    @Override
    public void render(TakeResourcesFromMarketUpdate message) {
        if(isReceiverAction(message.getUser())){
            Platform.runLater(() -> {
                GUIApp.showScene("/gui/FXML/PlayerBoard.fxml");
                GUIApp.controller.handleTakeResourcesFromMarketUpdate();
                ((TakeResPopupController)GUIApp.controller).setImages(message);
            });

        }
    }

    @Override
    public void render(FaithMarkerUpdate message) {

    }

    @Override
    public void render(MoveUpdate message) {

    }

    @Override
    public void render(LeaderActionUpdate message) {

    }

    @Override
    public void render(PositioningUpdate message) {

    }

    @Override
    public void render(LorenzoPlayedUpdate message) {

    }

    @Override
    public void render(BuyDevCardPerformedUpdate message) {

    }

    @Override
    public void render(ActivateProductionUpdate message) {

    }

    @Override
    public void render(NotAvailableNicknameMessage message) {

    }

    @Override
    public void render(ActivateVaticanReportUpdate message) {

    }

    @Override
    public void render(JoinLobbyMessage message) {
        List<String> nicknames = message.getAwaitingGuests();
        for(String nickname : nicknames) {
            if(nicknames.indexOf(nickname) == 0) {
                if(isReceiverAction(new User(nickname))) Platform.runLater(() -> ((LobbyController) GUIApp.controller).setPlayer1("You"));
                else Platform.runLater(() -> ((LobbyController) GUIApp.controller).setPlayer1(nickname));
            }
            if(nicknames.indexOf(nickname) == 1) {
                if(isReceiverAction(new User(nickname))) Platform.runLater(() -> ((LobbyController) GUIApp.controller).setPlayer2("You"));
                else Platform.runLater(() -> ((LobbyController) GUIApp.controller).setPlayer2(nickname));
            }
            if(nicknames.indexOf(nickname) == 2) {
                if(isReceiverAction(new User(nickname))) Platform.runLater(() -> ((LobbyController) GUIApp.controller).setPlayer3("You"));
                else Platform.runLater(() -> ((LobbyController) GUIApp.controller).setPlayer3(nickname));
            }
            if(nickname.indexOf(nickname) == 3) {
                if(isReceiverAction(new User(nickname))) Platform.runLater(() -> ((LobbyController) GUIApp.controller).setPlayer4("You"));
                else Platform.runLater(() -> ((LobbyController) GUIApp.controller).setPlayer4(nickname));
            }
        }
    }

    @Override
    public void renderError(String errorMessage) {

    }
}
