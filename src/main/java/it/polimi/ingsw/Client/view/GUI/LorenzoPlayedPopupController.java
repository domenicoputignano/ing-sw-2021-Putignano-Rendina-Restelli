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

public class LorenzoPlayedPopupController extends Controller{

    @FXML
    public AnchorPane lorenzoPopup;

    @FXML
    public Text textField;

    @FXML
    public Button okButton;

    @FXML
    public ImageView tokenImage;

    @FXML
    public Text title;

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

    public void setTokenRender(LorenzoPlayedUpdate message){
        textField.setText(message.getPlayedToken().getTokenEffect().renderTokenEffect());
        tokenImage.setImage(new Image(message.getPlayedToken().getTokenEffect().renderTokenImage()));
    }

    @FXML
    void handleOkButton() {
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }
}
