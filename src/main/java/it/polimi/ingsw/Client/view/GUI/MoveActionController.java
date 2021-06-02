package it.polimi.ingsw.Client.view.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
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
    @Override
    public void initialize() {


        anchorMoveAction.setBackground(new Background(new BackgroundImage(new Image("/gui/img/exit_tab.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false))));
        moveActionText.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(moveActionText,39);
    }

    @FXML
    public void handleCloseMoveAction()
    {
        Stage stage = (Stage) closeMoveAction.getScene().getWindow();
        stage.close();
    }
}
