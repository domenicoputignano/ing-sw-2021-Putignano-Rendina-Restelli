package it.polimi.ingsw.Client.view.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class GUI extends Application {

    Stage stage;

    Controller controller;

    private static final String CURSOR = "/gui/img/cursor.png";

    @Override
    public void start(Stage stage){
        this.stage = stage;
        this.stage.setTitle("Master of Renaissance");
        this.stage.initStyle(StageStyle.TRANSPARENT);
        this.stage.getIcons().add(new Image("/gui/img/Masters of Renaissance icon.png"));
        Scene scene = loadScene("/gui/fxml/UserName.fxml");
        this.stage.setScene(scene);
        this.stage.show();
    }

    public Scene loadScene(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            controller = loader.getController();
            controller.setGUI(this);
            Scene scene = new Scene(Objects.requireNonNull(root), 950, 800, Color.TRANSPARENT);
            scene.setCursor(new ImageCursor(new Image(CURSOR)));
            scene.setUserData(loader);
            controller.mainstage = stage;
            return scene;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static void main(String[] args) {
        launch(args);
    }

    public Stage getStage() {
        return stage;
    }
}
