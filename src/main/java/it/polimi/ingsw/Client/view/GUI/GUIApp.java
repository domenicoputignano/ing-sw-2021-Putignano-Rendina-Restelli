package it.polimi.ingsw.Client.view.GUI;

import it.polimi.ingsw.Network.Client;
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

/**
 * Main class to start GUI application.
 * Contains static methods for loading and showing scenes in GUI.
 */
public class GUIApp extends Application {

    /**
     * Locker object used to wait for the success launch of the JavaFX Application by the Listening Thread
     */
    private static final Object objectLock = new Object();

    /**
     *Attribute that represents the main stage of the page
     */
    public static Stage stage;

    /**
     *Attribute that represents the main controller class of the scene
     */
    public static Controller controller;

    private static boolean ok = false;

    /**
     *Attribute used to define the cursor in the scene
     */
    private static final String CURSOR = "/gui/img/cursor.png";
    /**
     *Attribute used to define the client associated with the scene
     */
    public static Client client;

    /**
     *Start method inherited from the Application class to run the GUI
     */
    @Override
    public void start(Stage stageD) throws InterruptedException {
        synchronized (objectLock)
        {
            stage = stageD;
            stage.setTitle("Masters of Renaissance");
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.getIcons().add(new Image("/gui/img/MastersOfRenaissanceIcon.png"));
            ok = true;
            stage.setScene(loadScene("/gui/fxml/HomePage.fxml"));
            stage.show();
            objectLock.notifyAll();
        }

    }

    /**
     * Static method used to launch the GUI from client
     */
    public static void launchGUI() {
        launch();
    }

    /**
     * Static method used to load a generic scene in GUI
     * @param fxmlFile fxml file to be loaded into the scene
     * @return Scene that needs to be loaded into the stage
     */
    public static Scene loadScene(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(GUIApp.class.getResource(fxmlFile));
            Parent root = loader.load();
            controller = loader.getController();
            controller.setClient(client);
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

    /**
     * Static method used to show a scene on the stage
     * @param FXMLfile fxml file to be loaded into the scene
     */
    public static void showScene(String FXMLfile) {
        stage.setScene(loadScene(FXMLfile));
        stage.show();
    }

    public static Stage getStage() {
        return stage;
    }

    /**
     * Static method used to wait for the success launch of the JavaFX Application by the Listening Thread
     * @param client associated with the scene
     * @throws InterruptedException
     */
    public static void waitForGameSetup(Client client) throws InterruptedException {
        synchronized (objectLock) {
            while(!ok) objectLock.wait();
        }
        GUIApp.setClient(client);
    }

    /**
     * Static method used to set the client associated with the scene
     * @param clientInput client associated with the scene
     */
    private static void setClient(Client clientInput)
    {
        client = clientInput;
    }
}
