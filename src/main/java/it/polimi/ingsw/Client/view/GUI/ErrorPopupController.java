package it.polimi.ingsw.Client.view.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ErrorPopupController extends Controller{

    @FXML
    public AnchorPane errorPopupPane;

    @FXML
    public Text textField;

    @FXML
    public Button okButton;

    @FXML
    @Override
    public void initialize() {
        errorPopupPane.setBackground(new Background(new BackgroundImage(new Image("/gui/img/exit_tab.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false))));
        setFont(textField,22);
        textField.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(okButton,24);
        okButton.setStyle("-fx-text-fill: rgb(35, 25, 22);");
    }

    public void setErrorText(String errorMessage){
        textField.setText(errorMessage);
    }

    @FXML
    void handleOkButton()
    {
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }
}
