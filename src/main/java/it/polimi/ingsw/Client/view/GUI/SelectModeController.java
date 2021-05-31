package it.polimi.ingsw.Client.view.GUI;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class SelectModeController extends Controller {

    @FXML
    public void initialize() {
        super.initialize();

        BackgroundSize bSize = new BackgroundSize(80,80, true, true, true, true);

        center.setBackground(new Background(new BackgroundImage(new Image("/gui/img/background.jpg"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bSize)));
    }
    @FXML
    void handleMultiPlayButton()
    {
        GUIApp.getStage().setScene(GUIApp.loadScene("/gui/fxml/SelectNumOfPlayersPage.fxml"));
        GUIApp.getStage().show();
    }

    @FXML
    void handleSoloPlayButton()
    {
        GUIApp.getStage().setScene(GUIApp.loadScene("/gui/fxml/LeaderChoicePage.fxml"));
        GUIApp.getStage().show();
    }
    @FXML
    void handleBackButton()
    {
        GUIApp.getStage().setScene(GUIApp.loadScene("/gui/fxml/UserName.fxml"));
        GUIApp.getStage().show();
    }
}