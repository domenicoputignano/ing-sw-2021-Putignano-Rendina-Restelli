package it.polimi.ingsw.Client.view.GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class ChooseActionController extends Controller{

    @FXML
    public AnchorPane anchorChoose;

    @FXML
    public Text ChooseActionText;

    @FXML
    public Button closeChoose;

    @FXML
    public Button activateProd;

    @FXML
    public Button takeResources;

    @FXML
    public Button buyDevCard;

    @FXML
    public Button leaderAction;

    private static final String CURSOR = "/gui/img/cursor.png";

    Stage leaderStage;
    Stage activateProdStage;
    Stage buyDevCardStage;
    Stage takeResourcesStage;
    @FXML
    @Override
    public void initialize() {


        anchorChoose.setBackground(new Background(new BackgroundImage(new Image("/gui/img/exit_tab.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false))));
        setFont(ChooseActionText,39);
        ChooseActionText.setStyle("-fx-text-fill: rgb(35, 25, 22);");

        setFont(buyDevCard,35);
        buyDevCard.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(leaderAction,35);
        leaderAction.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(activateProd,35);
        activateProd.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(takeResources,35);
        takeResources.setStyle("-fx-text-fill: rgb(35, 25, 22);");
    }

    @FXML
    public void handleCloseChooseAction()
    {
        Stage stage = (Stage) closeChoose.getScene().getWindow();
        stage.close();
    }
    @FXML
    public void handleLeaderAction()
    {
        Stage stage = (Stage) closeChoose.getScene().getWindow();
        stage.close();
        leaderStage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/FXML/LeaderActionPage.fxml"));
            Parent root = loader.load();
            this.gui.controller = loader.getController();
            this.gui.controller.setGUI(this.gui);
            Scene scene = new Scene(Objects.requireNonNull(root), 1180, 750, Color.TRANSPARENT);
            scene.setCursor(new ImageCursor(new Image(CURSOR), 36, 45));
            leaderStage.initStyle(StageStyle.TRANSPARENT);
            leaderStage.setAlwaysOnTop(true);
            scene.setUserData(loader);
            leaderStage.initModality(Modality.WINDOW_MODAL);
            leaderStage.initOwner(this.gui.getStage());
            scene.setUserData(loader);
            leaderStage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
        leaderStage.show();
    }
    public void handleActivateProduction()
    {
        Stage stageChoose = (Stage) closeChoose.getScene().getWindow();
        stageChoose.close();
        activateProdStage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/FXML/ActivateProductionPage.fxml"));
            Parent root = loader.load();
            this.gui.controller = loader.getController();
            this.gui.controller.setGUI(this.gui);
            Scene scene = new Scene(Objects.requireNonNull(root), 1180, 750, Color.TRANSPARENT);
            scene.setCursor(new ImageCursor(new Image(CURSOR), 36, 45));
            activateProdStage.initStyle(StageStyle.TRANSPARENT);
            activateProdStage.setAlwaysOnTop(true);
            scene.setUserData(loader);
            activateProdStage.initModality(Modality.WINDOW_MODAL);
            activateProdStage.initOwner(this.gui.getStage());
            scene.setUserData(loader);
            activateProdStage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
        activateProdStage.show();
    }
    public void handleBuyDevCard()
    {
        Stage stage = (Stage) closeChoose.getScene().getWindow();
        stage.close();
        buyDevCardStage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/FXML/BuyDevCardPage.fxml"));
            Parent root = loader.load();
            this.gui.controller = loader.getController();
            this.gui.controller.setGUI(this.gui);
            Scene scene = new Scene(Objects.requireNonNull(root), 1180, 750, Color.TRANSPARENT);
            scene.setCursor(new ImageCursor(new Image(CURSOR), 36, 45));
            buyDevCardStage.initStyle(StageStyle.TRANSPARENT);
            buyDevCardStage.setAlwaysOnTop(true);
            scene.setUserData(loader);
            buyDevCardStage.initModality(Modality.WINDOW_MODAL);
            buyDevCardStage.initOwner(this.gui.getStage());
            scene.setUserData(loader);
            buyDevCardStage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
        buyDevCardStage.show();
    }
    public void handleTakeResources()
    {
        Stage stage = (Stage) closeChoose.getScene().getWindow();
        stage.close();
        takeResourcesStage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/FXML/TakeResourcesFromMarketPage.fxml"));
            Parent root = loader.load();
            this.gui.controller = loader.getController();
            this.gui.controller.setGUI(this.gui);
            Scene scene = new Scene(Objects.requireNonNull(root), 1180, 750, Color.TRANSPARENT);
            scene.setCursor(new ImageCursor(new Image(CURSOR), 36, 45));
            takeResourcesStage.initStyle(StageStyle.TRANSPARENT);
            takeResourcesStage.setAlwaysOnTop(true);
            scene.setUserData(loader);
            takeResourcesStage.initModality(Modality.WINDOW_MODAL);
            takeResourcesStage.initOwner(this.gui.getStage());
            scene.setUserData(loader);
            takeResourcesStage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
        takeResourcesStage.show();
    }
}



