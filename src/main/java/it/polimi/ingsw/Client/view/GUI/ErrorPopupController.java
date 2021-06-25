package it.polimi.ingsw.Client.view.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Class that represents the controller of the generic ErrorPopup page of the game
 * In this page, which is shown as a popup, the user can see the error message
 * following an unsuccessful action
 */
public class ErrorPopupController extends Controller{

    /**
     * Attribute that represents the central pane of the page
     */
    @FXML
    public AnchorPane errorPopupPane;

    /**
     * Attributes that represent the textFields where the message of popup is shown
     */
    @FXML
    public Text textField;
    /**
     * Attribute that represents the button to confirm the view of the popup and close it
     */
    @FXML
    public Button okButton;
    /**
     * Main method that initializes the scene within the stage
     * It takes care of setting the background of the scene
     * and the font of the texts and buttons
     */
    @FXML
    @Override
    public void initialize() {
        errorPopupPane.setBackground(new Background(new BackgroundImage(new Image("/gui/img/exit_tab.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false))));
        setFont(textField,22);
        textField.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(okButton,24);
        okButton.setStyle("-fx-text-fill: rgb(35, 25, 22);");
    }

    /**
     * method used to set the message text based on the type of error made
     * @param errorMessage message containing the type of update error that was made
     */
    public void setErrorText(String errorMessage){
        textField.setText(errorMessage);
    }
    /**
     * method used to confirm the view of the popup and close it
     * It is performed when okButton button is pressed
     */
    @FXML
    void handleOkButton()
    {
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }
}
