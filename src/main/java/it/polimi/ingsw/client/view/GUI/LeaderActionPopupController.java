package it.polimi.ingsw.client.view.GUI;

import it.polimi.ingsw.utils.messages.serverMessages.Updates.LeaderActionUpdate;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Class that represents the controller of the LeaderActionPopup page of the game
 * In this page, which is shown as a popup, the user can see the update popup following
 * a successful Leader action
 */
public class LeaderActionPopupController extends Controller{

    /**
     * Attribute that represents the central pane of the page
     */
    @FXML
    public AnchorPane leaderActionpopup;

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
     * Attribute that represents the imageView of the leader card on which the action was performed
     */
    @FXML
    public ImageView leaderCard;

    /**
     * Main method that initializes the scene within the stage
     * It takes care of setting the background of the scene
     * and the font of the texts and buttons
     */
    @FXML
    @Override
    public void initialize() {
        leaderActionpopup.setBackground(new Background(new BackgroundImage(new Image("/gui/img/vertical_tab.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false))));
        setFont(textField,20);
        textField.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(okButton,24);
        okButton.setStyle("-fx-text-fill: rgb(35, 25, 22);");
    }

    /**
     * method used to set image of the leader card on which the action was performed
     * @param message message that contains the result of the update of the leader action
     * @see LeaderActionUpdate
     */
    public void setImage(LeaderActionUpdate message){
        if(message.hasBeenDiscarded()){
            textField.setText("You discarded the following leader card");
        } else {
            textField.setText("You correctly activate the following leader card");
        }

        leaderCard.setImage(new Image(message.getLeaderCard().toImage()));
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
