package it.polimi.ingsw.client.view.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Class that represents the controller of the ChooseAction page of the game
 * On this page the user can choose which action to perform
 * if he has not yet performed an action during his turn
 */
public class ChooseActionController extends Controller{

    /**
     * Attribute that represents the central pane of the page
     */
    @FXML
    public AnchorPane anchorChoose;
    /**
     * Attribute that represents the title of the page
     */
    @FXML
    public Text ChooseActionText;
    /**
     * Attribute that represents the button to close the popup of the page
     */
    @FXML
    public Button closeChoose;
    /**
     * Attributes that represent the buttons to select the action that the user want to perform
     */
    @FXML
    public Button activateProd,takeResources,buyDevCard,leaderAction;

    /**
     * Main method that initializes the scene within the stage
     * It takes care of setting the background of the scene
     * and the font of the texts and buttons
     */
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
    /**
     * method used to close the popup page over the main stage
     * It is performed when closeChoose button is pressed
     */

    @FXML
    public void handleCloseChooseAction()
    {
        Stage stage = (Stage) closeChoose.getScene().getWindow();
        stage.close();
    }
    /**
     * method used to choose the leader action to perform
     * It is performed when leaderAction button is pressed
     */
    @FXML
    public void handleLeaderAction()
    {
        Stage stage = (Stage) closeChoose.getScene().getWindow();
        stage.close();
        showPopup("/gui/fxml/LeaderActionPage.fxml", 1180, 750);
    }
    /**
     * method used to choose the Activate Production action to perform
     * It is performed when activateProd button is pressed
     */
    public void handleActivateProduction()
    {
        Stage stageChoose = (Stage) closeChoose.getScene().getWindow();
        stageChoose.close();
        showPopup("/gui/fxml/ActivateProductionPage.fxml", 1180, 750);
    }
    /**
     * method used to choose the Buy Development Card action to perform
     * It is performed when buyDevCard button is pressed
     */
    public void handleBuyDevCard()
    {
        Stage stage = (Stage) closeChoose.getScene().getWindow();
        stage.close();
        showPopup("/gui/fxml/BuyDevCardPage.fxml", 1180, 750);
    }
    /**
     * method used to choose the Take Resources from market action to perform
     * It is performed when takeResources button is pressed
     */
    public void handleTakeResources()
    {
        Stage stage = (Stage) closeChoose.getScene().getWindow();
        stage.close();
        showPopup("/gui/fxml/TakeResourcesFromMarketPage.fxml", 1180, 750);
    }
}



