package it.polimi.ingsw.Client.view.GUI;

import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.BuyDevCardPerformedUpdate;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Class that represents the controller of the BuyDevelopmentCardPopup page of the game
 * In this page, which is shown as a popup, the user can see the update popup following
 * a successful TBuy Development Card action
 */
public class BuyDevCardPopupController extends Controller{
    /**
     * Attribute that represents the central pane of the page
     */
    @FXML
    public AnchorPane buyDevCardPopupPane;

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
     * Attribute that represents the imageView of the resource obtained after user performed action
     */
    @FXML
    public ImageView cardBought;
    /**
     * Main method that initializes the scene within the stage
     * It takes care of setting the background of the scene
     * and the font of the texts and buttons
     */
    @FXML
    @Override
    public void initialize() {
        buyDevCardPopupPane.setBackground(new Background(new BackgroundImage(new Image("/gui/img/exit_tab.png"),
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
     * method used to set the text of the update message and set the image of the card
     * that was purchased after performing the action
     * @param message update message containing the information obtained
     *                after successfully performing the action
     * @see BuyDevCardPerformedUpdate
     */
    public void setImage(BuyDevCardPerformedUpdate message){
        textField.setText("You successfully bought this card!");

        cardBought.setImage(new Image(message.getBoughtCard().toImage()));
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
