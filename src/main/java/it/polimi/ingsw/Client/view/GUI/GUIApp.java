package it.polimi.ingsw.Client.view.GUI;

import it.polimi.ingsw.Utils.Messages.ServerMessages.ServerAsksForNickname;
import javafx.application.Application;
import javafx.application.Platform;
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
import java.util.concurrent.locks.Lock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GUIApp extends Application {
    private static final Object objectLock = new Object();

    public static Stage stage;

    public static Controller controller;
    private static boolean ok = false;
    private static final String CURSOR = "/gui/img/cursor.png";

    @Override
    public void start(Stage stageD) throws InterruptedException {
        synchronized (objectLock)
        {
        stage = stageD;
        stage.setTitle("Masters of Renaissance");
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.getIcons().add(new Image("/gui/img/Masters of Renaissance icon.png"));
        ok = true;
        stage.setScene(loadScene("/gui/fxml/HomePage.fxml"));
        stage.show();
        objectLock.notifyAll();
        }

    }

    public static void launchGUI(String[] args) {
        launch(args);
    }

    public static Scene loadScene(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(GUIApp.class.getResource(fxmlFile));
            Parent root = loader.load();
            controller = loader.getController();
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

    public static void showScene(String FXMLfile) {
            stage.setScene(loadScene(FXMLfile));
            stage.show();
    }

    public static Stage getStage() {
        return stage;
    }

    public static void waitForGameSetup() throws InterruptedException {
        synchronized (objectLock) {
            while(!ok) objectLock.wait();
        }
    }

}
