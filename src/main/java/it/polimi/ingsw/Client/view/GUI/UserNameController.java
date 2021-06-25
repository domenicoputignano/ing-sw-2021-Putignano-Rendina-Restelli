package it.polimi.ingsw.Client.view.GUI;

import it.polimi.ingsw.Client.clientstates.gui.UsernameChoiceGUI;
import it.polimi.ingsw.Utils.Messages.ClientMessages.UsernameChoiceMessage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that represents the controller of the UserName page of the game
 * On this page the user must enter the username to be used during the game
 * and which will uniquely identify him to the server
 */
public class UserNameController extends Controller {

    /**
     * Attribute that represents the textField where the user must enter the username
     */
    @FXML
    public TextField username;

    /**
     * Attribute that represents the textField where an error is shown in case the username is invalid
     */
    @FXML
    public TextField errorText;
    /**
     * Attribute that represents the button to press to submit the username to the server
     */
    @FXML
    public Button submitButton;
    /**
     * Attribute that represents the button to clear the username textField
     */
    @FXML
    public Button cancelButton;


    /**
    * Main method that initializes the scene within the stage
     * It takes care of setting the background of the scene
     * and the font of the texts and buttons
     */
    @FXML
    public void initialize() {
        super.initialize();
        BackgroundSize bSize = new BackgroundSize(80, 80, true, true, true, true);

        center.setBackground(new Background(new BackgroundImage(new Image("/gui/img/background.jpg"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bSize)));
        username.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        errorText.setStyle("-fx-text-fill: #f2b535");
        submitButton.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        cancelButton.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(username,29);

        setFont(errorText,30);
        setFont(submitButton,29);
        setFont(cancelButton,29);
    }
    /**
     * Method used to cancel the username entered in the textField
     * It is performed when the cancel button is pressed
     */
    @FXML
    void cancelUsername()
    {
        errorText.clear();
        username.clear();
    }

    /**
     * Method used to submit the username entered in the textField
     * It is performed when the submit button is pressed
     */
    @FXML
    void submitUsername() {
        clientState = new UsernameChoiceGUI(client,username.getText());
        clientState.manageUserInteraction();
    }

    /**
     * method used to set the text of the error message
     * in case the username entered in the textField is not valid
     * @param error String that represents the error to show
     */
    public void setErrorText(String error){
        errorText.setText(error);
    }

}
