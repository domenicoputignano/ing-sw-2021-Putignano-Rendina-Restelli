package it.polimi.ingsw.Client.view.GUI;

import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.LeaderActionUpdate;
import it.polimi.ingsw.Utils.ResourceLocator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LeaderActionPopupController extends Controller{

    @FXML
    public AnchorPane leaderActionpopup;

    @FXML
    public Text textField;

    @FXML
    public Button okButton;

    @FXML
    public ImageView leaderCard;

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

    public void setImage(LeaderActionUpdate message){
        if(message.hasBeenDiscarded()){
            textField.setText("You discarded the following leader card");
        } else {
            textField.setText("You correctly activate the following leader card");
        }

        leaderCard.setImage(new Image(message.getLeaderCard().toImage()));
    }

    @FXML
    void handleOkButton()
    {
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }
}
