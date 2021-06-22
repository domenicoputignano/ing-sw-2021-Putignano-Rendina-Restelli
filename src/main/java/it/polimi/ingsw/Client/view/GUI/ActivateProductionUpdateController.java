package it.polimi.ingsw.Client.view.GUI;

import it.polimi.ingsw.Commons.ResourceType;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.ActivateProductionUpdate;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Map;

public class ActivateProductionUpdateController extends Controller{
    @FXML
    public AnchorPane popupPane;

    @FXML
    public HBox obtainedResources;

    @FXML
    public Text textUpdate;

    @FXML
    public Text numCoinObtained;

    @FXML
    public Text numShieldObtained;

    @FXML
    public Text numServantObtained;

    @FXML
    public Text numStoneObtained;

    @FXML
    public Text numFaithObtained;

    @FXML
    public Button okButton;

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

    public void setObtainedResources(ActivateProductionUpdate message){
        textUpdate.setText("You correctly activated productions and obtained\n the following resources:");
        Map<ResourceType, Integer> obtainedResources = message.getReceivedResources();

        numCoinObtained.setText(obtainedResources.get(ResourceType.coin).toString());
        numServantObtained.setText(obtainedResources.get(ResourceType.servant).toString());
        numShieldObtained.setText(obtainedResources.get(ResourceType.shield).toString());
        numStoneObtained.setText(obtainedResources.get(ResourceType.stone).toString());
        numFaithObtained.setText("" + message.getFaithPoints());
    }

    @FXML
    public void handleOkButton(){
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }
}
