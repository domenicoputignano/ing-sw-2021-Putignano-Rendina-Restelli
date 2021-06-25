package it.polimi.ingsw.Client.view.GUI;

import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.LeaderActionUpdate;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.LorenzoPlayedUpdate;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Class that represents the controller of the LorenzoPlayedPopup page of the game
 * In this page, which is shown as a popup, the user can see the update popup following
 * a successful action performed by Lorenzo il Magnifico
 */
public class LorenzoPlayedPopupController extends Controller{
    /**
     * Attribute that represents the central pane of the page
     */
    @FXML
    public AnchorPane lorenzoPopup;

    /**
     * Attributes that represent the textFields where the message of popup is shown
     * It show what action is performed by Lorenzo il Magnifico
     */
    @FXML
    public Text textField;

    /**
     * Attribute that represents the button to confirm the view of the popup and close it
     */
    @FXML
    public Button okButton;

    /**
     * Attribute that represents the imageView associated
     * at the action performed by Lorenzo il Magnifico
     */
    @FXML
    public ImageView tokenImage;

    /**
     * Attribute that represents the title of the popup
     */
    @FXML
    public Text title;

    /**
     * Main method that initializes the scene within the stage
     * It takes care of setting the background of the scene
     * and the font of the texts and buttons
     */
    @FXML
    @Override
    public void initialize() {
        lorenzoPopup.setBackground(new Background(new BackgroundImage(new Image("/gui/img/exit_tab.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false))));
        setFont(title,30);
        title.setStyle("-fx-text-fill: rgb(62,11,11);");
        setFont(textField,22);
        textField.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(okButton,24);
        okButton.setStyle("-fx-text-fill: rgb(35, 25, 22);");
    }

    /**
     * method usedd to set text and image based on the type of action performed by lorenzo
     * @param message message that identifies the type of action performed by lorenzo
     * @see LorenzoPlayedUpdate
     */
    public void setTokenRender(LorenzoPlayedUpdate message){
        textField.setText(message.getPlayedToken().getTokenEffect().renderTokenEffect());
        tokenImage.setImage(new Image(message.getPlayedToken().getTokenEffect().renderTokenImage()));
    }
    /**
     * method used to confirm the view of the popup and close it
     * It is performed when okButton button is pressed
     */
    @FXML
    void handleOkButton() {
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }
}
