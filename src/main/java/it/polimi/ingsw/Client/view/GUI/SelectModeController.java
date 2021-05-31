package it.polimi.ingsw.Client.view.GUI;

import it.polimi.ingsw.Client.clientstates.gui.GameModeChoiceGUI;
import it.polimi.ingsw.Client.clientstates.gui.UsernameChoiceGUI;
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
        clientState = new GameModeChoiceGUI(client,"multiplayer");
        clientState.manageUserInteraction();
    }

    @FXML
    void handleSoloPlayButton()
    {
        clientState = new GameModeChoiceGUI(client,"solo");
        clientState.manageUserInteraction();
    }
    @FXML
    void handleBackButton()
    {
        GUIApp.getStage().setScene(GUIApp.loadScene("/gui/fxml/UserName.fxml"));
        GUIApp.getStage().show();
    }
}