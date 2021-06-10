package it.polimi.ingsw.Client.view.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MoveResourcesUpdatePopupController extends Controller{

    @FXML
    public AnchorPane movePopup;

    @FXML
    public Button okButton;

    @FXML
    public Text title;

    @FXML
    @Override
    public void initialize() {
        movePopup.setBackground(new Background(new BackgroundImage(new Image("/gui/img/exit_tab.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false))));
        setFont(title,30);
        title.setStyle("-fx-text-fill: rgb(62,11,11);");
        setFont(okButton,24);
        okButton.setStyle("-fx-text-fill: rgb(35, 25, 22);");
    }

    @FXML
    void handleOkButton() {
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }
}
