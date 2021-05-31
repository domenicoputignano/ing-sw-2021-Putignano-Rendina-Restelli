package it.polimi.ingsw.Client.view.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class SelectNumOfPlayersController extends Controller{
    @FXML
    public TextField numberPlayers;

    @FXML
    public Button twoPlayers;

    @FXML
    public Button threePlayers;

    @FXML
    public Button fourPlayers;


    @FXML
    public void initialize() {
        super.initialize();

        BackgroundSize bSize = new BackgroundSize(80, 80, true, true, true, true);

        center.setBackground(new Background(new BackgroundImage(new Image("/gui/img/background.jpg"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bSize)));
        numberPlayers.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        twoPlayers.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        threePlayers.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        fourPlayers.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(numberPlayers,50);
        setFont(twoPlayers,26);
        setFont(threePlayers,26);
        setFont(fourPlayers,26);
    }

    @FXML
    void handleBackButton()
    {
        GUIApp.getStage().setScene(GUIApp.loadScene("/gui/fxml/SelectModePage.fxml"));
        GUIApp.getStage().show();
    }

    @FXML
    void goLeaderChoice()
    {
        GUIApp.getStage().setScene(GUIApp.loadScene("/gui/fxml/LeaderChoicePage.fxml"));
        GUIApp.getStage().show();
    }
    @FXML
    void pb()
    {
        GUIApp.getStage().setScene(GUIApp.loadScene("/gui/fxml/PlayerBoard.fxml"));
        GUIApp.getStage().show();
    }
}