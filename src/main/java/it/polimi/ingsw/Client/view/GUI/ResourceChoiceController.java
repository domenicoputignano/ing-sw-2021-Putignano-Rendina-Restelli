package it.polimi.ingsw.Client.view.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;


public class ResourceChoiceController extends Controller{
    @FXML
    public TextField chooseResourceText;

    @FXML
    public Button clearButton;

    @FXML
    public Button okButton;

    @FXML
    public TextField coinText;

    @FXML
    public TextField stoneText;

    @FXML
    public TextField shieldText;

    @FXML
    public TextField servantText;

    @FXML
    public TextField numCoin;

    @FXML
    public TextField numShield;

    @FXML
    public TextField numStone;

    @FXML
    public TextField numServant;

    @FXML
    public TextField errorResources;

    @FXML
    public TextField errorMismatch;

    int playerNumber;
    int maxResources;
    int resources = 0;
    int coin = 0;
    int shield = 0;
    int servant = 0;
    int stone = 0;
    @FXML
    public void initialize() {
        super.initialize();

        BackgroundSize bSize = new BackgroundSize(80, 80, true, true, true, true);

        center.setBackground(new Background(new BackgroundImage(new Image("/gui/img/background.jpg"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bSize)));
        playerNumber = 4;
        if(playerNumber == 2 || playerNumber == 3)
            maxResources = 1;
        else if(playerNumber == 4)
            maxResources = 2;
        setFont(chooseResourceText,30);
        chooseResourceText.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        okButton.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        numCoin.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        numShield.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        numServant.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        numStone.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        coinText.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        servantText.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        shieldText.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        stoneText.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        clearButton.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        okButton.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        errorMismatch.setStyle("-fx-text-fill: #f2b535");
        errorResources.setStyle("-fx-text-fill: #f2b535");
        chooseResourceText.setText("You are player "+playerNumber+" ! Select "+maxResources+" resources !");
        setFont(clearButton,22);
        setFont(errorMismatch,30);
        setFont(errorResources,30);
        setFont(okButton,20);
        setFont(coinText,26);
        setFont(shieldText,26);
        setFont(servantText,26);
        setFont(stoneText,26);
        setFont(numCoin,26);
        setFont(numServant,26);
        setFont(numShield,26);
        setFont(numStone,26);
        numCoin.setText("" + coin);
        numServant.setText("" + servant);
        numShield.setText("" + shield);
        numStone.setText("" + stone);
    }

    @FXML
    void clearResources()
    {
        resources=0;
        coin=0;
        shield=0;
        stone=0;
        servant=0;
        numCoin.setText("" + coin);
        numServant.setText("" + servant);
        numShield.setText("" + shield);
        numStone.setText("" + stone);
        errorResources.setText("");
    }

    @FXML
    void plusCoin()
    {
        if(resources+1 <= maxResources)
        {
            resources++;
            coin++;
            numCoin.setText("" + coin);
        }
        else errorResources.setText("You can select maximum "+maxResources+" resources !");
    }
    @FXML
    void plusServant()
    {
        if(resources+1 <= maxResources)
        {
            resources++;
            servant++;
            numServant.setText("" + servant);
        }
        else errorResources.setText("You can select maximum "+maxResources+" resources !");
    }
    @FXML
    void plusShield() {
        if (resources + 1 <= maxResources) {
            resources++;
            shield++;
            numShield.setText("" + shield);
        }
        else errorResources.setText("You can select maximum "+maxResources+" resources !");
    }
    @FXML
    void plusStone()
    {
        if(resources+1 <= maxResources) {
            resources++;
            stone++;
            numStone.setText("" + stone);
        }
        else errorResources.setText("You can select maximum "+maxResources+" resources !");
    }
    @FXML
    void minusStone()
    {
        if (stone > 0) {
            stone--;
            resources--;
            errorResources.setText("");
            numStone.setText("" + stone);
        }
    }
    @FXML
    void minusServant()
    {
        if (servant > 0) {
            servant--;
            resources--;
            errorResources.setText("");
            numServant.setText("" + servant);
        }
    }
    @FXML
    void minusShield()
    {
        if (shield > 0) {
            shield--;
            resources--;
            errorResources.setText("");
            numShield.setText("" + shield);
        }
    }
    @FXML
    void minusCoin()
    {
        if (coin > 0) {
            coin--;
            resources--;
            errorResources.setText("");
            numCoin.setText("" + coin);
        }
    }
    @FXML
    void handleBackButton()
    {
        this.gui.getStage().setScene(this.gui.loadScene("/GUI/FXML/LeaderChoicePage.fxml"));
        this.gui.getStage().show();
    }
    @FXML
    void handleOkChoice()
    {
        if(resources != maxResources)
        {
            errorMismatch.setText("You have to select "+maxResources+" resources");
        }
    }
}
