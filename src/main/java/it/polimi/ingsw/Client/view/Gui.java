package it.polimi.ingsw.Client.view;

import it.polimi.ingsw.Client.clientstates.AbstractClientState;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Messages.ServerMessages.*;
import it.polimi.ingsw.Client.view.GUI.GUIApp;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.*;
import javafx.application.Application;
import javafx.application.Platform;

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
            GUIApp.waitForGameSetup();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Platform.runLater(() ->{

            GUIApp.showScene("/gui/fxml/UserName.fxml");
                }
        );
    }

    @Override
    public void render(ServerAskForGameMode message) {

    }

    @Override
    public void render(ServerAskForNumOfPlayer message) {

    }

    @Override
    public void render(GameSetupMessage message) {

    }

    @Override
    public void render(GameResumedMessage message) {

    }

    @Override
    public void render(InitialLeaderChoiceUpdate message) {

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
    public void renderError(String errorMessage) {

    }
}
