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

public class UserNameController extends Controller {

    @FXML
    public TextField username;

    @FXML
    public TextField errorText;

    @FXML
    public Button submitButton;

    @FXML
    public Button cancelButton;



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

    @FXML
    void cancelUsername()
    {
        errorText.clear();
        username.clear();
    }

    @FXML
    void submitUsername() {
        clientState = new UsernameChoiceGUI(client,username.getText());
        clientState.manageUserInteraction();
    }

    public void setErrorText(String error){
        errorText.setText(error);
    }

}
