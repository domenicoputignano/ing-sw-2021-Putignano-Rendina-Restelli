package it.polimi.ingsw.Client.view.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class ExitController extends Controller {
    @FXML
    public Button exitYes;

    @FXML
    public Button exitNo;

    @FXML
    public AnchorPane anchorExit;

    @FXML
    public TextField exitText;

    @FXML
    @Override
    public void initialize() {


        anchorExit.setBackground(new Background(new BackgroundImage(new Image("/gui/img/exit_tab.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false))));
        exitText.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        exitNo.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        exitYes.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(exitText,39);
        setFont(exitNo,24);
        setFont(exitYes,24);
    }

    @FXML
    void handleExitNoButton()
    {
        Stage stage = (Stage) exitNo.getScene().getWindow();
        stage.close();
    }

    @FXML
    void handleExitYesButton()
    {
        System.exit(0);
    }
}

