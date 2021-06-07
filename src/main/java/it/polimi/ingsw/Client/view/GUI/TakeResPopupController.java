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

public class TakeResPopupController extends Controller{
    @FXML
    public AnchorPane takeRespopup;

    @FXML
    public Text takeResPopupText1,takeResPopupText2;

    @FXML
    public ImageView res1,res2,res3,res4;

    @FXML
    public Button okButton;

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

    @FXML
    void handleOkButton()
    {
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }
}
