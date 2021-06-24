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

        this.client = GUIApp.client;

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

        if(client.getUI().hasDoneNormalAction()) {
            buyDevCard.setVisible(false);
            activateProd.setVisible(false);
            takeResources.setVisible(false);
            leaderAction.setTranslateY(-224);
        }
        if(client.getGame().getPlayer(client.getUser()).getNumOfNotActiveLeaderCards()==0)
            leaderAction.setVisible(false);

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
        showPopup("/gui/fxml/LeaderActionPage.fxml", 1180, 750);
    }
    public void handleActivateProduction()
    {
        Stage stageChoose = (Stage) closeChoose.getScene().getWindow();
        stageChoose.close();
        showPopup("/gui/fxml/ActivateProductionPage.fxml", 1180, 750);
    }
    public void handleBuyDevCard()
    {
        Stage stage = (Stage) closeChoose.getScene().getWindow();
        stage.close();
        showPopup("/gui/fxml/BuyDevCardPage.fxml", 1180, 750);
    }
    public void handleTakeResources()
    {
        Stage stage = (Stage) closeChoose.getScene().getWindow();
        stage.close();
        showPopup("/gui/fxml/TakeResourcesFromMarketPage.fxml", 1180, 750);
    }
}



