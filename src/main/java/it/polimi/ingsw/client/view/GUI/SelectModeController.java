package it.polimi.ingsw.client.view.GUI;

import it.polimi.ingsw.client.clientstates.gui.GameModeChoiceGUI;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

/**
 * Class that represents the controller of the SelectMode page of the game
 * On this page the user must choose the game mode between SoloMode and MultiplayerMode
 */
public class SelectModeController extends Controller {

    /**
     * Main method that initializes the scene within the stage
     * It takes care of setting the background of the scene
     * and the font of the texts and buttons
     */
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
    /**
     * method used to manage the user's choice to MultiMode
     * It is performed when the MultiPlayerMode button is pressed
     */
    @FXML
    void handleMultiPlayButton()
    {
        clientState = new GameModeChoiceGUI(client,"multiplayer");
        clientState.manageUserInteraction();
    }
    /**
     * method used to manage the user's choice to SoloMode
     * It is performed when the SoloMode button is pressed
     */
    @FXML
    void handleSoloPlayButton()
    {
        client.getUI().setSoloMode(true);
        clientState = new GameModeChoiceGUI(client,"solo");
        clientState.manageUserInteraction();
    }
}