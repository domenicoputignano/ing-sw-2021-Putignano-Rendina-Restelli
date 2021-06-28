package it.polimi.ingsw.client.view.GUI;

import it.polimi.ingsw.commons.ResourceType;
import it.polimi.ingsw.utils.messages.serverMessages.Updates.ActivateProductionUpdate;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Map;
/**
 * Class that represents the controller of the ActivateVaticanReportPopup page of the game
 * In this page, which is shown as a popup, the user can see the update popup following
 * a successful Activate Production action
 */
public class ActivateProductionUpdateController extends Controller{
    /**
     * Attribute that represents the central pane of the page
     */
    @FXML
    public AnchorPane popupPane;
    /**
     * attribute that represents the HBox containing the imageView
     * relating to the resources obtained
     */
    @FXML
    public HBox obtainedResources;
    /**
     * Attribute that represents the text where the message of popup is shown
     */
    @FXML
    public Text textUpdate;
    /**
     * attributes representing the text relating
     * to the number of resources obtained for each resource
     */
    @FXML
    public Text numCoinObtained,numShieldObtained,numServantObtained,numStoneObtained,numFaithObtained;
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
        popupPane.setBackground(new Background(new BackgroundImage(new Image("/gui/img/exit_tab.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false))));
        this.client = GUIApp.client;
        setFont(textUpdate,28);
        textUpdate.setStyle("-fx-text-fill: rgb(62,11,11);");
        setFont(okButton,24);
        okButton.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(numCoinObtained, 25);
        setFont(numServantObtained, 25);
        setFont(numShieldObtained, 25);
        setFont(numStoneObtained, 25);
        setFont(numFaithObtained, 25);
    }

    /**
     *method used to set the update message and to set the number
     * of resources obtained for each resource
     * @param message update message containing the information obtained
     *      *                after successfully performing the action
     * @see ActivateProductionUpdate
     */
    public void setObtainedResources(ActivateProductionUpdate message){
        textUpdate.setText("You correctly activated productions and obtained\n the following resources:");
        Map<ResourceType, Integer> obtainedResources = message.getReceivedResources();

        numCoinObtained.setText(obtainedResources.get(ResourceType.coin).toString());
        numServantObtained.setText(obtainedResources.get(ResourceType.servant).toString());
        numShieldObtained.setText(obtainedResources.get(ResourceType.shield).toString());
        numStoneObtained.setText(obtainedResources.get(ResourceType.stone).toString());
        numFaithObtained.setText("" + message.getFaithPoints());
    }
    /**
     * method used to confirm the view of the popup and close it
     * It is performed when okButton button is pressed
     */
    @FXML
    public void handleOkButton(){
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }
}
