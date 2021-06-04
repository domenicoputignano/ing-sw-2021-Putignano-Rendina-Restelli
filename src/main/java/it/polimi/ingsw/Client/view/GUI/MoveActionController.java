package it.polimi.ingsw.Client.view.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MoveActionController extends Controller{
    @FXML
    public AnchorPane anchorMoveAction;

    @FXML
    public Text moveActionText;

    @FXML
    public Button closeMoveAction;

    @FXML
    public Button res1Depot1, res1Depot2, res2Depot2, res1Depot3, res2Depot3, res3Depot3;

    @FXML
    public Text extraDepotsText;

    @FXML
    @Override
    public void initialize() {


        anchorMoveAction.setBackground(new Background(new BackgroundImage(new Image("/gui/img/exit_tab.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false))));
        moveActionText.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(moveActionText,39);
        extraDepotsText.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(extraDepotsText,30);
    }

    private void initializeDepots(){

    }

    @FXML
    public void handleCloseMoveAction()
    {
        Stage stage = (Stage) closeMoveAction.getScene().getWindow();
        stage.close();
    }
}
