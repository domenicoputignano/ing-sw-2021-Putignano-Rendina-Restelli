package it.polimi.ingsw.Client.view.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class InfoController extends Controller{
    @FXML
    public ScrollPane scrollPane;

    @FXML
    public AnchorPane anchorHelper;
    @FXML
    public Button closeHelper;

    @FXML
    @Override
    public void initialize() {

        anchorHelper.setBackground(new Background(new BackgroundImage(new Image("/gui/img/exit_tab.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false))));

    }
    @FXML
    public void handleCloseHelper()
    {
        Stage stage = (Stage) closeHelper.getScene().getWindow();
        stage.close();
    }
}
