package it.polimi.ingsw.Client.view.GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class PlayerBoardController extends Controller {
    Stage actions;

    @FXML
    public ImageView leaderCard1;

    @FXML
    public Button chooseAction;

    @FXML
    public ImageView leaderCard2;
    @FXML
    private static final String CURSOR = "/gui/img/cursor.png";

    public void initialize() {
        super.initialize();

        BackgroundSize bSize = new BackgroundSize(80, 80, true, true, true, true);

        center.setBackground(new Background(new BackgroundImage(new Image("/gui/img/background.jpg"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bSize)));
        setFont(chooseAction,24);
    }
    @FXML
    public void handleChooseActionButton()
    {
        actions = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/ChooseActionPage.fxml"));
            Parent root = loader.load();
            GUIApp.controller = loader.getController();
            Scene scene = new Scene(Objects.requireNonNull(root), 820, 520, Color.TRANSPARENT);
            scene.setCursor(new ImageCursor(new Image(CURSOR), 36, 45));
            actions.initStyle(StageStyle.TRANSPARENT);
            actions.setAlwaysOnTop(true);
            scene.setUserData(loader);
            actions.initModality(Modality.WINDOW_MODAL);
            actions.initOwner(GUIApp.getStage());
            scene.setUserData(loader);
            actions.setScene(scene);

        } catch (IOException e) {
                e.printStackTrace();
            }
        actions.show();
    }
}
