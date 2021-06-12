package it.polimi.ingsw.Client.view.GUI;

import it.polimi.ingsw.Client.clientstates.gui.InitialResourceChoiceGUI;
import it.polimi.ingsw.Commons.ResourceType;
import it.polimi.ingsw.Utils.Pair;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.Client.view.UI.fromStringToResourceType;


public class ResourceChoiceController extends Controller{
    @FXML
    public TextField chooseResourceText;

    @FXML
    public Button clearButton;

    @FXML
    public Button okButton,okButton1;

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
        okButton1.setVisible(true);

        if(getResourcesSelectedTimes(1).size()==1){
            resource1Img.setImage(new Image(getURLImageResource(getResourcesSelectedTimes(1).get(0))));
        }
        else {
            if(getResourcesSelectedTimes(1).size()==2) {
                resource1Img.setImage(new Image(getURLImageResource(getResourcesSelectedTimes(1).get(0))));
                resource1Imgpl4.setVisible(true);
                resource1Imgpl4.setImage(new Image(getURLImageResource(getResourcesSelectedTimes(1).get(1))));
                buttonDepot1pl4.setVisible(true);
                buttonDepot2pl4.setVisible(true);
                buttonDepot3pl4.setVisible(true);

            }
            if(getResourcesSelectedTimes(2).size()==1) {
                resource1Img.setImage(new Image(getURLImageResource(getResourcesSelectedTimes(2).get(0))));
                buttonDepot1.setVisible(false);
            }
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
        buttonDepot12 = false;
        buttonDepot1.setStyle("-fx-background-size: 75% auto;");
        buttonDepot1pl4.setStyle("-fx-background-size: 100% auto;");
    }
    public void selectedButtonDepot21()
    {
        clearSelect();
        buttonDepot21=true;
        buttonDepot22=false;
        buttonDepot2.setStyle("-fx-background-size: 75% auto;");
        buttonDepot2pl4.setStyle("-fx-background-size: 100% auto;");
    }
    public void selectedButtonDepot31()
    {
        clearSelect();
        buttonDepot31=true;
        buttonDepot32=false;
        buttonDepot3pl4.setStyle("-fx-background-size: 100% auto;");
        buttonDepot3.setStyle("-fx-background-size: 75% auto;");

    }
    public void selectedButtonDepot12()
    {
        clearSelectpl4();
        buttonDepot11 = false;
        buttonDepot1.setStyle("-fx-background-size: 100% auto;");
        buttonDepot12 = true;
        buttonDepot1pl4.setStyle("-fx-background-size: 75% auto;");
    }
    public void selectedButtonDepot22()
    {
        clearSelectpl4();
        buttonDepot21 = false;
        buttonDepot2.setStyle("-fx-background-size: 100% auto;");
        buttonDepot22=true;
        buttonDepot2pl4.setStyle("-fx-background-size: 75% auto;");

    }
    public void selectedButtonDepot32()
    {
        clearSelectpl4();
        buttonDepot31 = false;
        buttonDepot3.setStyle("-fx-background-size: 100% auto;");
        buttonDepot32=true;
        buttonDepot3pl4.setStyle("-fx-background-size: 75% auto;");
    }

    private List<String> getResourcesSelectedTimes(int times) {
        List<String> result = new ArrayList<>();
        if(coin == times) result.add("coin");
        if(servant == times) result.add("servant");
        if(shield == times) result.add("shield");
        if(stone == times) result.add("stone");
        return result;
    }



    private String getURLImageResource(String resource) {
        if(resource.equals("coin")) return "/gui/img/resources/coin.png";
        if(resource.equals("servant")) return "/gui/img/resources/servant.png";
        if(resource.equals("shield")) return "/gui/img/resources/shield.png";
        if(resource.equals("stone")) return "/gui/img/resources/stone.png";
        else return "";
    }

    private int getDepotIndex(boolean buttonDepot1, boolean buttonDepot2, boolean buttonDepot3) {
        if(buttonDepot1) return 1;
        if(buttonDepot2) return 2;
        if(buttonDepot3) return 3;
        else return 0;
    }




    @FXML
    void submitInitialResourceChoice() {
        List<Pair<ResourceType,Integer>> resourcesWithDepotDestination = new ArrayList<>();
        if(getResourcesSelectedTimes(1).size()==1) {
            resourcesWithDepotDestination.
                    add(new Pair<>(fromStringToResourceType(getResourcesSelectedTimes(1).get(0)),
                            getDepotIndex(buttonDepot11, buttonDepot21, buttonDepot31)));
        } else {
            if(getResourcesSelectedTimes(1).size()==2) {
                resourcesWithDepotDestination.add(new Pair<>(fromStringToResourceType(getResourcesSelectedTimes(1).get(0)),
                        getDepotIndex(buttonDepot11, buttonDepot21, buttonDepot31)));
                resourcesWithDepotDestination.add(new Pair<>(fromStringToResourceType(getResourcesSelectedTimes(1).get(1)),
                        getDepotIndex(buttonDepot12, buttonDepot22, buttonDepot32)));
            }
            if(getResourcesSelectedTimes(2).size()==1) {
                resourcesWithDepotDestination.add(new Pair<>(fromStringToResourceType(getResourcesSelectedTimes(2).get(0)),
                        getDepotIndex(buttonDepot11, buttonDepot21, buttonDepot31)));
                resourcesWithDepotDestination.add(new Pair<>(fromStringToResourceType(getResourcesSelectedTimes(2).get(0)),
                        getDepotIndex(buttonDepot11, buttonDepot21, buttonDepot31)));
            }
        }
        (new InitialResourceChoiceGUI(client, resourcesWithDepotDestination)).manageUserInteraction();
        cleanPane();
        chooseResourceText.setText("Setup completed\nYou are the "+client.getGame().getPlayer(client.getUser()).getPosition()+"° player");
    }

    private void cleanPane() {
        clearButton.setVisible(false);
        okButton.setVisible(false);
        okButton1.setVisible(false);
        coinText.setVisible(false);
        stoneText.setVisible(false);
        shieldText.setVisible(false);
        servantText.setVisible(false);
        numCoin.setVisible(false);
        numShield.setVisible(false);
        numStone.setVisible(false);
        numServant.setVisible(false);
        errorResources.setVisible(false);
        errorMismatch.setVisible(false);
        minusButton1.setVisible(false);
        minusButton2.setVisible(false);
        minusButton3.setVisible(false);
        minusButton4.setVisible(false);
        plusButton1.setVisible(false);
        plusButton2.setVisible(false);
        plusButton3.setVisible(false);
        plusButton4.setVisible(false);
        resource1Img.setVisible(false);
        resource2Img.setVisible(false);
        resource3Img.setVisible(false);
        resource4Img.setVisible(false);
        resource1Imgpl4.setVisible(false);
        buttonDepot1.setVisible(false);
        buttonDepot2.setVisible(false);
        buttonDepot3.setVisible(false);
        buttonDepot1pl4.setVisible(false);
        buttonDepot2pl4.setVisible(false);
        buttonDepot3pl4.setVisible(false);
    }
}
