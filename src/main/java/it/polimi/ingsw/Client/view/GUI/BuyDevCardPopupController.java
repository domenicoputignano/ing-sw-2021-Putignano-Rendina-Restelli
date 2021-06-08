package it.polimi.ingsw.Client.view.GUI;

import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.BuyDevCardPerformedUpdate;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BuyDevCardPopupController extends Controller{
    @FXML
    public AnchorPane buyDevCardPopupPane;

    @FXML
    public Text textField;

    @FXML
    public Button okButton;

    @FXML
    public ImageView cardBought;

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

    public void setImage(BuyDevCardPerformedUpdate message){
        textField.setText("You successfully bought this card!");

        cardBought.setImage(new Image(message.getBoughtCard().toImage()));
    }

    @FXML
    void handleOkButton()
    {
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }
}
