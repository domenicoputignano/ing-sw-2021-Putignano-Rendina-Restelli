package it.polimi.ingsw.Client.view.GUI;

import it.polimi.ingsw.Commons.ResourceType;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.TakeResourcesFromMarketUpdate;
import it.polimi.ingsw.Utils.ResourceLocator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;

/**
 * Class that represents the controller of the TakeResourcesPopup page of the game
 * In this page, which is shown as a popup, the user can see the update popup following
 * a successful Take resources from Market action
 */
public class TakeResPopupController extends Controller{
    /**
     * Attribute that represents the central pane of the page
     */
    @FXML
    public AnchorPane takeRespopup;

    /**
     * Attributes that represent the text where the message of popup is shown
     */
    @FXML
    public Text takeResPopupText1,takeResPopupText2;
    /**
     * Attributes that represent the imageView of the resources obtained after user performed action
     */
    @FXML
    public ImageView res1,res2,res3,res4;
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
        takeRespopup.setBackground(new Background(new BackgroundImage(new Image("/gui/img/exit_tab.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false))));
        setFont(takeResPopupText1,23);
        takeResPopupText1.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(takeResPopupText2,23);
        takeResPopupText2.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(okButton,24);
        okButton.setStyle("-fx-text-fill: rgb(35, 25, 22);");
    }

    /**
     *method used to set images on the popup of the resources obtained after
     * successfully performing the action
     * @param message update message containing the information obtained
     *                after successfully performing the action
     * @see TakeResourcesFromMarketUpdate
     */
    public void setImages(TakeResourcesFromMarketUpdate message)
    {
        List<ResourceType> earnedResources = message.getEarnedResources();
        if(earnedResources.size()>0)
            res1.setImage(new Image(ResourceLocator.retrieveResourceTypeImage(earnedResources.get(0))));
        else {if(message.getFaithPoints()>0) res1.setImage(new Image("/gui/img/faith.png"));return;}
        if(earnedResources.size()>1)
            res2.setImage(new Image(ResourceLocator.retrieveResourceTypeImage(earnedResources.get(1))));
        else {if(message.getFaithPoints()>0) res2.setImage(new Image("/gui/img/faith.png"));return;}
        if(earnedResources.size()>2)
            res3.setImage(new Image(ResourceLocator.retrieveResourceTypeImage(earnedResources.get(2))));
        else {if(message.getFaithPoints()>0) res3.setImage(new Image("/gui/img/faith.png"));return;}
        if(earnedResources.size()>3)
            res4.setImage(new Image(ResourceLocator.retrieveResourceTypeImage(earnedResources.get(3))));
        else if(message.getFaithPoints()>0) res4.setImage(new Image("/gui/img/faith.png"));
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
