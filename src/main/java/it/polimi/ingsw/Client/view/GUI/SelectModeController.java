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
        this.gui.getStage().setScene(this.gui.loadScene("/GUI/FXML/SelectNumOfPlayersPage.fxml"));
        this.gui.getStage().show();
    }

    @FXML
    void handleSoloPlayButton()
    {
        this.gui.getStage().setScene(this.gui.loadScene("/GUI/FXML/LeaderChoicePage.fxml"));
        this.gui.getStage().show();
    }
    @FXML
    void handleBackButton()
    {
        this.gui.getStage().setScene(this.gui.loadScene("/GUI/FXML/UserName.fxml"));
        this.gui.getStage().show();
    }
}