package it.polimi.ingsw.Client.view.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    @FXML
    public Button minusButton1,minusButton2,minusButton3,minusButton4,plusButton1,plusButton2,plusButton3,plusButton4;

    @FXML
    public ImageView resource1Img,resource2Img,resource3Img,resource4Img,resource1Imgpl4;

    @FXML
    public Button buttonDepot1,buttonDepot2,buttonDepot3;
    @FXML
    public Button buttonDepot1pl4,buttonDepot2pl4,buttonDepot3pl4;

    int playerNumber;
    int maxResources;
    int resources = 0;
    int coin = 0;
    int shield = 0;
    int servant = 0;
    int stone = 0;

    private boolean buttonDepot11 = false,buttonDepot21 = false,buttonDepot31 = false;
    private boolean buttonDepot12 = false,buttonDepot22 = false,buttonDepot32 = false;

    @FXML
    public void initialize() {
        super.initialize();

        BackgroundSize bSize = new BackgroundSize(80, 80, true, true, true, true);

        center.setBackground(new Background(new BackgroundImage(new Image("/gui/img/background.jpg"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bSize)));

        this.client = GUIApp.client;
        playerNumber = client.getUserPosition();

        if(playerNumber == 2 || playerNumber == 3)
            maxResources = 1;
        else if(playerNumber == 4)
            maxResources = 2;
        setFont(chooseResourceText,30);

        chooseResourceText.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        okButton.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        numCoin.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        numShield.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        buttonDepot1pl4.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        buttonDepot2pl4.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        buttonDepot3pl4.setStyle("-fx-text-fill: rgb(35, 25, 22);");
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
        buttonDepot1.setStyle("-fx-text-fill: #f2b535");
        buttonDepot2.setStyle("-fx-text-fill: #f2b535");
        buttonDepot3.setStyle("-fx-text-fill: #f2b535");
        chooseResourceText.setText("You are player "+playerNumber+" ! Select "+maxResources+" resources !");
        setFont(buttonDepot1pl4,24);
        setFont(buttonDepot2pl4,24);
        setFont(buttonDepot3pl4,24);
        setFont(clearButton,22);
        setFont(buttonDepot1,24);
        setFont(buttonDepot2,24);
        setFont(buttonDepot3,24);
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
    void handleOkChoice()
    {
        if(resources != maxResources)
        {
            errorMismatch.setText("You have to select "+maxResources+" resources");
        }
        else setInvisible();
    }

    void setInvisible()
    {
        chooseResourceText.setText("Choose where to put resource");
        okButton.setVisible(false);
        numCoin.setVisible(false);
        numShield.setVisible(false);
        numServant.setVisible(false);
        numStone.setVisible(false);
        coinText.setVisible(false);
        servantText.setVisible(false);
        shieldText.setVisible(false);
        stoneText.setVisible(false);
        clearButton.setVisible(false);
        okButton.setVisible(false);
        errorMismatch.setVisible(false);
        errorResources.setVisible(false);
        plusButton1.setVisible(false);
        plusButton2.setVisible(false);
        plusButton3.setVisible(false);
        plusButton4.setVisible(false);
        minusButton1.setVisible(false);
        minusButton2.setVisible(false);
        minusButton3.setVisible(false);
        minusButton4.setVisible(false);
        resource2Img.setVisible(false);
        resource3Img.setVisible(false);
        resource4Img.setVisible(false);
        buttonDepot1.setVisible(true);
        buttonDepot2.setVisible(true);
        buttonDepot3.setVisible(true);
        if(playerNumber != 4) {
            if (coin > 0)
                resource1Img.setImage(new Image("/gui/img/coin.png"));
            else if (servant > 0)
                resource1Img.setImage(new Image("/gui/img/servant.png"));
            else if (shield > 0)
                resource1Img.setImage(new Image("/gui/img/shield.png"));
            else if (stone > 0)
                resource1Img.setImage(new Image("/gui/img/stone.png"));
        }
        else {
            //TODO
        }
    }
    void clearSelect()
    {
        buttonDepot11=false;
        buttonDepot21=false;
        buttonDepot31=false;
        buttonDepot12=false;
        buttonDepot22=false;
        buttonDepot32=false;
        buttonDepot1.setStyle("-fx-background-size: 100%;");
        buttonDepot2.setStyle("-fx-background-size: 100%;");
        buttonDepot3.setStyle("-fx-background-size: 100%;");

    }
    void clearSelectpl4()
    {
        buttonDepot12=false;
        buttonDepot22=false;
        buttonDepot32=false;
        buttonDepot1pl4.setStyle("-fx-background-size: 100%;");
        buttonDepot2pl4.setStyle("-fx-background-size: 100%;");
        buttonDepot3pl4.setStyle("-fx-background-size: 100%;");

    }
    public void selectedButtonDepot11()
    {
        clearSelect();
        buttonDepot11=true;
        buttonDepot1.setStyle("-fx-background-size: 75% auto;");

    }
    public void selectedButtonDepot21()
    {
        clearSelect();
        buttonDepot21=true;
        buttonDepot2.setStyle("-fx-background-size: 75% auto;");

    }
    public void selectedButtonDepot31()
    {
        clearSelect();
        buttonDepot31=true;
        buttonDepot3.setStyle("-fx-background-size: 75% auto;");

    }
    public void selectedButtonDepot12()
    {
        clearSelectpl4();
        buttonDepot12=true;
        buttonDepot1pl4.setStyle("-fx-background-size: 75% auto;");
    }
    public void selectedButtonDepot22()
    {
        clearSelectpl4();
        buttonDepot22=true;
        buttonDepot2pl4.setStyle("-fx-background-size: 75% auto;");

    }
    public void selectedButtonDepot32()
    {
        clearSelectpl4();
        buttonDepot32=true;
        buttonDepot3pl4.setStyle("-fx-background-size: 75% auto;");
    }

}
