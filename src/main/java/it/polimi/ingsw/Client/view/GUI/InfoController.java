package it.polimi.ingsw.Client.view.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * Class that represents the controller of the Info page of the game
 * In this page the user not in turn is able to view the rules of the game
 */
public class InfoController extends Controller{
    @FXML
    public ScrollPane scrollPane;

    /**
     * Attribute that represents the central pane of the page
     */
    @FXML
    public AnchorPane anchorHelper;
    /**
     * Attribute that represents the button to close the popup of the page
     */
    @FXML
    public Button closeHelper;

    /**
     * Main method that initializes the scene within the stage
     * It takes care of setting the background of the scene
     * and the font of the texts and buttons
     */
    @FXML
    @Override
    public void initialize() {

        anchorHelper.setBackground(new Background(new BackgroundImage(new Image("/gui/img/exit_tab.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false))));

    }
    /**
     * method used to close the popup page over the main stage
     * It is performed when closeHelper button is pressed
     */
    @FXML
    public void handleCloseHelper()
    {
        Stage stage = (Stage) closeHelper.getScene().getWindow();
        stage.close();
    }
}
