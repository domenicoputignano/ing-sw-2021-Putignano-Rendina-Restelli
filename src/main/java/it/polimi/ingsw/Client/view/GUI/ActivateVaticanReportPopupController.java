package it.polimi.ingsw.Client.view.GUI;

import it.polimi.ingsw.Commons.ResourceType;
import it.polimi.ingsw.Commons.StateFavorTiles;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.ActivateVaticanReportUpdate;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.TakeResourcesFromMarketUpdate;
import it.polimi.ingsw.Utils.ResourceLocator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;

public class ActivateVaticanReportPopupController extends Controller{
    @FXML
    public AnchorPane activateVaticanPopup;

    @FXML
    public Text activateVaticanPopupText1,activateVaticanPopupText2;

    @FXML
    public ImageView favorTile;

    @FXML
    public Button okButton;

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

    @FXML
    void handleOkButton()
    {
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }
}
