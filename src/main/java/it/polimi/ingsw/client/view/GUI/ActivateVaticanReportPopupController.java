package it.polimi.ingsw.client.view.GUI;

import it.polimi.ingsw.commons.StateFavorTiles;
import it.polimi.ingsw.utils.messages.serverMessages.Updates.ActivateVaticanReportUpdate;
import it.polimi.ingsw.utils.messages.serverMessages.Updates.LorenzoActivatedVaticanReportUpdate;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Class that represents the controller of the ActivateVaticanReportPopup page of the game
 * In this page, which is shown as a popup, the user can see the update popup following
 * a successful Activate Vatican Report action
 */
public class ActivateVaticanReportPopupController extends Controller{
    /**
     * Attribute that represents the central pane of the page
     */
    @FXML
    public AnchorPane activateVaticanPopup;
    /**
     * Attributes that represent the text where the message of popup is shown
     */
    @FXML
    public Text activateVaticanPopupText1,activateVaticanPopupText2;
    /**
     * Attribute that represents the imageView of the favor tile turned or discarded after user performed action
     */
    @FXML
    public ImageView favorTile;
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
        activateVaticanPopup.setBackground(new Background(new BackgroundImage(new Image("/gui/img/exit_tab.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false))));
        setFont(activateVaticanPopupText1,23);
        activateVaticanPopupText1.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(activateVaticanPopupText2,23);
        activateVaticanPopupText2.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(okButton,24);
        okButton.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        this.client = GUIApp.client;
    }

    /**
     * Method used to set the text messages and the image of the favor tile
     * turned over or discarded in the case of activation of the
     * Report in the Vatican performed by a player
     * @param message update message containing the information obtained
     *                after successfully performing the action
     * @see ActivateVaticanReportUpdate
     */
    public void setPopup(ActivateVaticanReportUpdate message)
    {
        String state,triggeringUser;
        if(client.getUser().equals(message.getTriggeringUser()))
            triggeringUser = "You have";
        else triggeringUser = "Player "+message.getTriggeringUser().getNickname()+" has";
        int section = message.getSection()+1;
        if(message.getState()== StateFavorTiles.DISCARDED) {
            state = "discarded";
            if(section==1)
              favorTile.setImage(new Image("/gui/img/favorTile1D.png"));
            else if(section==2)
                favorTile.setImage(new Image("/gui/img/favorTile2D.png"));
            else if(section==3)
                favorTile.setImage(new Image("/gui/img/favorTile3D.png"));
        }else {
            state = "turned over";
            if(section==1)
                favorTile.setImage(new Image("/gui/img/favorTile1.png"));
            else if(section==2)
                favorTile.setImage(new Image("/gui/img/favorTile2.png"));
            else if(section==3)
                favorTile.setImage(new Image("/gui/img/favorTile3.png"));
        }
        activateVaticanPopupText1.setText(""+triggeringUser+" activated the Vatican report of section "+section+"!");
        activateVaticanPopupText2.setText("Your favorTile has been "+ state +" !");

    }

    /**
     * Method used to set the text messages and the image of the favor tile
     * turned over or discarded in the case of activation of the
     * Report in the Vatican performed by Lorenzo il Magnifico
     * @param message update message containing the information obtained
     *                after Lorenzo il Magnifico successfully performing the action
     * @see LorenzoActivatedVaticanReportUpdate
     */
    public void setLorenzoActivationEffect(LorenzoActivatedVaticanReportUpdate message){
        String state;
        int section = message.getVatican_index();
        if(message.getResultingStateFavorTile() == StateFavorTiles.DISCARDED) {
            state = "discarded";
            if(section==1)
                favorTile.setImage(new Image("/gui/img/favorTile1D.png"));
            else if(section==2)
                favorTile.setImage(new Image("/gui/img/favorTile2D.png"));
            else if(section==3)
                favorTile.setImage(new Image("/gui/img/favorTile3D.png"));
        } else {
            state = "turned over";
            if(section==1)
                favorTile.setImage(new Image("/gui/img/favorTile1.png"));
            else if(section==2)
                favorTile.setImage(new Image("/gui/img/favorTile2.png"));
            else if(section==3)
                favorTile.setImage(new Image("/gui/img/favorTile3.png"));
        }
        activateVaticanPopupText1.setText("Lorenzo activated the Vatican report on section "+section+"!");
        activateVaticanPopupText2.setText("Your favorTile has been "+ state +" !");
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
