package it.polimi.ingsw.client.view.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * Class that represents the controller of the Exit page of the game
 * In this page, which is shown as a popup, the user can log out of the game
 * via the popup that is shown after pressing the exit button
 */
public class ExitController extends Controller {
    /**
     * Attributes that represent the buttons to log out or not log out the game
     */
    @FXML
    public Button exitYes,exitNo;

    /**
     * Attribute that represents the central pane of the page
     */
    @FXML
    public AnchorPane anchorExit;
    /**
     * Attributes that represent the textFields where the message of popup is shown
     */
    @FXML
    public TextField exitText;

    /**
     * Main method that initializes the scene within the stage
     * It takes care of setting the background of the scene
     * and the font of the texts and buttons
     */
    @FXML
    @Override
    public void initialize() {


        anchorExit.setBackground(new Background(new BackgroundImage(new Image("/gui/img/exit_tab.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false))));
        exitText.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        exitNo.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        exitYes.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(exitText,39);
        setFont(exitNo,24);
        setFont(exitYes,24);
    }

    /**
     * method used to confirm the user does not want to log out of the gamee
     * It is performed when exitNo button is pressed
     */
    @FXML
    void handleExitNoButton()
    {
        Stage stage = (Stage) exitNo.getScene().getWindow();
        stage.close();
    }
    /**
     * method used to confirm disconnection from the game
     * It is performed when exitYes button is pressed
     */
    @FXML
    void handleExitYesButton()
    {
        System.exit(0);
    }
}

