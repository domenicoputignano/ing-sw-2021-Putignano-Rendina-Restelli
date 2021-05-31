package it.polimi.ingsw.Client.view.GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class Controller {


    private static final String CURSOR = "/gui/img/cursor.png";

    @FXML
    public AnchorPane top;

    @FXML
    public AnchorPane bottom;

    @FXML
    public AnchorPane center;

    @FXML
    public Button exitButton;

    @FXML
    public Button infoButton;

    Stage exit;
    Stage infoStage;
    Stage mainstage;



    @FXML
    public void initialize() {

        mainstage = new Stage();
        mainstage.setTitle("Master of Renaissance");
        mainstage.initStyle(StageStyle.TRANSPARENT);

        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO,BackgroundSize.AUTO, false,false,true,true);

        top.setBackground(new Background(new BackgroundImage(new Image("/gui/img/top.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bSize)));
        bottom.setBackground(new Background(new BackgroundImage(new Image("/gui/img/bottom.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bSize)));
    }
    @FXML
    void exit()
    {
        exit = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/FXML/ExitPage.fxml"));
            Parent root = loader.load();
            GUIApp.controller = loader.getController();
            Scene scene = new Scene(Objects.requireNonNull(root), 570, 380, Color.TRANSPARENT);
            scene.setCursor(new ImageCursor(new Image(CURSOR), 36, 45));
            exit.initStyle(StageStyle.TRANSPARENT);
            exit.setAlwaysOnTop(true);
            scene.setUserData(loader);
            exit.initModality(Modality.WINDOW_MODAL);
            exit.initOwner(GUIApp.getStage());
            scene.setUserData(loader);
            exit.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
        exit.show();

    }
    @FXML
    void info()
    {
        infoStage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/FXML/InfoPage.fxml"));
            Parent root = loader.load();
            GUIApp.controller = loader.getController();
            Scene scene = new Scene(Objects.requireNonNull(root), 1180, 750, Color.TRANSPARENT);
            scene.setCursor(new ImageCursor(new Image(CURSOR), 36, 45));
            infoStage.initStyle(StageStyle.TRANSPARENT);
            infoStage.setAlwaysOnTop(true);
            scene.setUserData(loader);
            infoStage.initModality(Modality.WINDOW_MODAL);
            infoStage.initOwner(GUIApp.getStage());
            scene.setUserData(loader);
            infoStage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
        infoStage.show();

    }

    public void setFont(TextField textField, int dim){
        Font fontText = Font.loadFont(getClass().getResourceAsStream("/gui/font/Enchanted-Land.otf"),dim);
        textField.setFont(fontText);
    }
    public void setFont(Button button, int dim){
        Font fontButton = Font.loadFont(getClass().getResourceAsStream("/gui/font/Enchanted-Land.otf"),dim);
        button.setFont(fontButton);
        button.setStyle("-fx-text-fill: rgb(35, 25, 22);");
    }
    public void setFont(Text text, int dim){
        Font fontText = Font.loadFont(getClass().getResourceAsStream("/gui/font/Enchanted-Land.otf"),dim);
        text.setFont(fontText);
    }
}