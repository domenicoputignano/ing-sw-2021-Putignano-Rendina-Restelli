package it.polimi.ingsw.Client.view.GUI;

import it.polimi.ingsw.Client.Checker;
import it.polimi.ingsw.Client.clientstates.gui.BuyDevCardGUI;
import it.polimi.ingsw.Commons.ColorCard;
import it.polimi.ingsw.Commons.DevelopmentCard;
import it.polimi.ingsw.Commons.Effect;
import it.polimi.ingsw.Commons.ResourceType;
import it.polimi.ingsw.Utils.Pair;
import it.polimi.ingsw.Utils.ResourceLocator;
import it.polimi.ingsw.Utils.ResourceSource;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.*;
import java.util.stream.Collectors;

public class BuyDevCardController extends Controller{
    @FXML
    public AnchorPane anchorBuyDevCard;

    @FXML
    public Text buyDevCardText;

    @FXML
    public Text green,yellow,purple,blue,level1,level2,level3;

    @FXML
    public Button closeBuyDevCard;

    @FXML
    public Button green3,green2,green1,blue3,blue2,blue1,yellow1,yellow2,yellow3,purple1,purple2,purple3;

    @FXML
    public Text errorDevText,resDepot1Text,resDepot2Text,resDepot3Text,resStronbox1Text,resStronbox2Text,resStronbox3Text,resExtra1Text,resExtra2Text,resExtra3Text;

    @FXML
    public ImageView selectedCard;

    @FXML
    public Button buyCard, toPayment, concludeAction;

    @FXML
    public ImageView deck1,deck2,deck3,deck4,deck5,deck6,deck7,deck8,deck9,deck10,deck11,deck12;

    @FXML
    public ImageView resource1Cost,resource2Cost,resource3Cost;

    @FXML
    public Text selectHowPickResourcesText;

    @FXML
    public Button plusDepotRes1,plusExtraRes1,plusStrRes1,minusDepotRes1,minusStrRes1,minusExtraRes1,
            plusDepotRes2,plusExtraRes2,plusStrRes2,minusDepotRes2,minusStrRes2,minusExtraRes2,
            plusDepotRes3,plusExtraRes3,plusStrRes3,minusDepotRes3,minusStrRes3,minusExtraRes3;

    @FXML
    public TextField textField1,textField2,textField3,textField4,textField5,textField6,textField7,textField8,textField9;
    public ToggleGroup availableSlot;

    @FXML
    public VBox slotBox;

    DevelopmentCard developmentCard;
    boolean isCardBuyable;
    int slotIndex;
    List<Pair<ResourceType,Integer>> neededResources = new ArrayList<>();
    List<Pair<ResourceType,Integer>> startingCost = new ArrayList<>();
    Map<ResourceSource, EnumMap<ResourceType,Integer>> paymentInstruction = new HashMap<>();
    BuyDevCardGUI buyDevCardAction;


    @FXML
    @Override
    public void initialize() {

        this.client = GUIApp.client;
        buyDevCardAction = new BuyDevCardGUI(client);
        anchorBuyDevCard.setBackground(new Background(new BackgroundImage(new Image("/gui/img/exit_tab.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false))));
        buyDevCardText.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(buyDevCardText,39);
        green.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(green,24);
        blue.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(blue,24);
        yellow.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(yellow,24);
        purple.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(purple,24);
        level1.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(level1,24);
        level2.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(level2,24);
        level3.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(level3,24);
        errorDevText.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(errorDevText,30);
        buyCard.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(buyCard,24);
        concludeAction.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(concludeAction,24);
        selectHowPickResourcesText.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(selectHowPickResourcesText,35);
        resDepot1Text.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(resDepot1Text,30);
        resDepot2Text.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(resDepot2Text,30);
        resDepot3Text.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(resDepot3Text,30);
        resStronbox1Text.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(resStronbox1Text,30);
        resStronbox2Text.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(resStronbox2Text,30);
        resStronbox3Text.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(resStronbox3Text,30);
        resExtra1Text.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(resExtra1Text,30);
        resExtra2Text.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(resExtra2Text,30);
        resExtra3Text.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(resExtra3Text,30);
        textField1.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(textField1,18);
        textField2.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(textField2,18);
        textField3.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(textField3,18);
        textField4.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(textField4,18);
        textField5.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(textField5,18);
        textField6.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(textField6,18);
        textField7.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(textField7,18);
        textField8.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(textField8,18);
        textField9.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(textField9,18);
        initializeDecksImages();
    }

    private void initializeDecksImages(){

        if(client.getGame().isAnyCardPresentInDeck(3, ColorCard.green)) {
            green3.setStyle("-fx-background-image: url(" +
                    client.getGame().getDeckTopCard(3, ColorCard.green).toImage() + ")");
        }
        if(client.getGame().isAnyCardPresentInDeck(2, ColorCard.green)) {
            green2.setStyle("-fx-background-image: url(" +
                    client.getGame().getDeckTopCard(2, ColorCard.green).toImage() + ")");
        }
        if(client.getGame().isAnyCardPresentInDeck(1, ColorCard.green)) {
            green1.setStyle("-fx-background-image: url(" +
                    client.getGame().getDeckTopCard(1, ColorCard.green).toImage() + ")");
        }

        if(client.getGame().isAnyCardPresentInDeck(3, ColorCard.blue)) {
            blue3.setStyle("-fx-background-image: url(" +
                    client.getGame().getDeckTopCard(3, ColorCard.blue).toImage() + ")");
        }
        if(client.getGame().isAnyCardPresentInDeck(2, ColorCard.blue)) {
            blue2.setStyle("-fx-background-image: url(" +
                    client.getGame().getDeckTopCard(2, ColorCard.blue).toImage() + ")");
        }
        if(client.getGame().isAnyCardPresentInDeck(1, ColorCard.blue)) {
            blue1.setStyle("-fx-background-image: url(" +
                    client.getGame().getDeckTopCard(1, ColorCard.blue).toImage() + ")");
        }

        if(client.getGame().isAnyCardPresentInDeck(3, ColorCard.yellow)) {
            yellow3.setStyle("-fx-background-image: url(" +
                    client.getGame().getDeckTopCard(3, ColorCard.yellow).toImage() + ")");
        }
        if(client.getGame().isAnyCardPresentInDeck(2, ColorCard.yellow)) {
            yellow2.setStyle("-fx-background-image: url(" +
                    client.getGame().getDeckTopCard(2, ColorCard.yellow).toImage() + ")");
        }
        if(client.getGame().isAnyCardPresentInDeck(1, ColorCard.yellow)) {
            yellow1.setStyle("-fx-background-image: url(" +
                    client.getGame().getDeckTopCard(1, ColorCard.yellow).toImage() + ")");
        }

        if(client.getGame().isAnyCardPresentInDeck(3, ColorCard.purple)) {
            purple3.setStyle("-fx-background-image: url(" +
                    client.getGame().getDeckTopCard(3, ColorCard.purple).toImage() + ")");
        }
        if(client.getGame().isAnyCardPresentInDeck(2, ColorCard.purple)) {
            purple2.setStyle("-fx-background-image: url(" +
                    client.getGame().getDeckTopCard(2, ColorCard.purple).toImage() + ")");
        }
        if(client.getGame().isAnyCardPresentInDeck(1, ColorCard.purple)) {
            purple1.setStyle("-fx-background-image: url(" +
                    client.getGame().getDeckTopCard(1, ColorCard.purple).toImage() + ")");
        }

    }

    private void setSelectedCardImage(DevelopmentCard developmentCard){
        selectedCard.setImage(new Image(developmentCard.toImage()));
    }

    private void setDevelopmentCardTextOK(String toDisplay){
        errorDevText.setText(toDisplay);
        errorDevText.setVisible(true);
        buyCard.setVisible(true);
    }

    private void setFeasiblePurchaseOK(){
        setDevelopmentCardTextOK("You have have \nenough resources \nto purchase \nselected card");
        buyCard.setVisible(true);
    }

    private void setErrorDevTextKO(String error){
        errorDevText.setText(error);
        errorDevText.setVisible(true);
        //buyCard.setVisible(true);
    }

    @FXML
    public void checkIfCanBuyCard3Green() {
        developmentCard = client.getGame().getDeckTopCard(3,ColorCard.green);
        setSelectedCardImage(developmentCard);
        if(Checker.checkResources(developmentCard.getCost(),client.getGame().getPlayer(client.getUser()).getPersonalBoard())) {
            isCardBuyable = true;
            setFeasiblePurchaseOK();
        } else {
            isCardBuyable = false;
            setErrorDevTextKO("You don't have \nenough resources \nto purchase \nselected card");
            //TODO da togliere
            buyCard.setVisible(true);
        }
    }

    @FXML
    public void checkIfCanBuyCard2Green() {
        developmentCard = client.getGame().getDeckTopCard(2,ColorCard.green);
        setSelectedCardImage(developmentCard);
        if(Checker.checkResources(developmentCard.getCost(),client.getGame().getPlayer(client.getUser()).getPersonalBoard())) {
            isCardBuyable = true;
            setFeasiblePurchaseOK();
        } else {
            isCardBuyable = false;
            setErrorDevTextKO("You don't have \nenough resources \nto purchase \nselected card");
            //TODO da togliere
            buyCard.setVisible(true);
        }
    }

    @FXML
    public void checkIfCanBuyCard1Green() {
        developmentCard = client.getGame().getDeckTopCard(1,ColorCard.green);
        setSelectedCardImage(developmentCard);
        if(Checker.checkResources(developmentCard.getCost(),client.getGame().getPlayer(client.getUser()).getPersonalBoard())) {
            setFeasiblePurchaseOK();
            isCardBuyable = true;
        } else {
            isCardBuyable = false;
            setErrorDevTextKO("You don't have \nenough resources \nto purchase \nselected card");
            //TODO da togliere
            buyCard.setVisible(true);
        }
    }

    @FXML
    public void checkIfCanBuyCard3Blue() {
        developmentCard = client.getGame().getDeckTopCard(3,ColorCard.blue);
        setSelectedCardImage(developmentCard);
        if(Checker.checkResources(developmentCard.getCost(),client.getGame().getPlayer(client.getUser()).getPersonalBoard())) {
            isCardBuyable = true;
            setFeasiblePurchaseOK();
        } else {
            isCardBuyable = false;
            setErrorDevTextKO("You don't have \nenough resources \nto purchase \nselected card");
            //TODO da togliere
            buyCard.setVisible(true);
        }
    }

    @FXML
    public void checkIfCanBuyCard2Blue() {
        developmentCard = client.getGame().getDeckTopCard(2,ColorCard.blue);
        setSelectedCardImage(developmentCard);
        if(Checker.checkResources(developmentCard.getCost(),client.getGame().getPlayer(client.getUser()).getPersonalBoard())) {
            isCardBuyable = true;
            setFeasiblePurchaseOK();
        } else {
            isCardBuyable = false;
            setErrorDevTextKO("You don't have \nenough resources \nto purchase \nselected card");
            //TODO da togliere
            buyCard.setVisible(true);
        }
    }

    @FXML
    public void checkIfCanBuyCard1Blue() {
        developmentCard = client.getGame().getDeckTopCard(1,ColorCard.blue);
        setSelectedCardImage(developmentCard);
        if(Checker.checkResources(developmentCard.getCost(),client.getGame().getPlayer(client.getUser()).getPersonalBoard())) {
            isCardBuyable = true;
            setFeasiblePurchaseOK();
        } else {
            isCardBuyable = false;
            setErrorDevTextKO("You don't have \nenough resources \nto purchase \nselected card");
            //TODO da togliere
            buyCard.setVisible(true);
        }
    }

    @FXML
    public void checkIfCanBuyCard3Yellow() {
        developmentCard = client.getGame().getDeckTopCard(3,ColorCard.yellow);
        setSelectedCardImage(developmentCard);
        if(Checker.checkResources(developmentCard.getCost(),client.getGame().getPlayer(client.getUser()).getPersonalBoard())) {
            isCardBuyable = true;
            setFeasiblePurchaseOK();
        } else {
            isCardBuyable = false;
            setErrorDevTextKO("You don't have \nenough resources \nto purchase \nselected card");
            //TODO da togliere
            buyCard.setVisible(true);
        }
    }

    @FXML
    public void checkIfCanBuyCard2Yellow() {
        developmentCard = client.getGame().getDeckTopCard(2,ColorCard.yellow);
        setSelectedCardImage(developmentCard);
        if(Checker.checkResources(developmentCard.getCost(),client.getGame().getPlayer(client.getUser()).getPersonalBoard())) {
            isCardBuyable = true;
            setFeasiblePurchaseOK();
        } else {
            isCardBuyable = false;
            setErrorDevTextKO("You don't have \nenough resources \nto purchase \nselected card");
            //TODO da togliere
            buyCard.setVisible(true);
        }
    }

    @FXML
    public void checkIfCanBuyCard1Yellow() {
        developmentCard = client.getGame().getDeckTopCard(1,ColorCard.yellow);
        setSelectedCardImage(developmentCard);
        if(Checker.checkResources(developmentCard.getCost(),client.getGame().getPlayer(client.getUser()).getPersonalBoard())) {
            isCardBuyable = true;
            setFeasiblePurchaseOK();
        } else {
            isCardBuyable = false;
            setErrorDevTextKO("You don't have \nenough resources \nto purchase \nselected card");
            //TODO da togliere
            buyCard.setVisible(true);
        }
    }

    @FXML
    public void checkIfCanBuyCard3Purple() {
        developmentCard = client.getGame().getDeckTopCard(3,ColorCard.purple);
        setSelectedCardImage(developmentCard);
        if(Checker.checkResources(developmentCard.getCost(),client.getGame().getPlayer(client.getUser()).getPersonalBoard())) {
            isCardBuyable = true;
            setFeasiblePurchaseOK();
        } else {
            isCardBuyable = false;
            setErrorDevTextKO("You don't have \nenough resources \nto purchase \nselected card");
            //TODO da togliere
            buyCard.setVisible(true);
        }
    }

    @FXML
    public void checkIfCanBuyCard2Purple() {
        developmentCard = client.getGame().getDeckTopCard(2,ColorCard.purple);
        setSelectedCardImage(developmentCard);
        if(Checker.checkResources(developmentCard.getCost(),client.getGame().getPlayer(client.getUser()).getPersonalBoard())) {
            isCardBuyable = true;
            setFeasiblePurchaseOK();
        } else {

            isCardBuyable = false;
            setErrorDevTextKO("You don't have \nenough resources \nto purchase \nselected card");
            //TODO da togliere
            buyCard.setVisible(true);
        }
    }

    @FXML
    public void checkIfCanBuyCard1Purple() {
        developmentCard = client.getGame().getDeckTopCard(1,ColorCard.purple);
        setSelectedCardImage(developmentCard);
        if(Checker.checkResources(developmentCard.getCost(),client.getGame().getPlayer(client.getUser()).getPersonalBoard())) {
            isCardBuyable = true;
            setFeasiblePurchaseOK();
        } else {
            isCardBuyable = false;
            setErrorDevTextKO("You don't have \nenough resources \nto purchase \nselected card");
            //TODO da togliere
            buyCard.setVisible(true);
        }
    }


    @FXML
    public void setInvisible() {
        green3.setVisible(false);
        green2.setVisible(false);
        green1.setVisible(false);
        yellow3.setVisible(false);
        yellow2.setVisible(false);
        yellow1.setVisible(false);
        blue3.setVisible(false);
        blue2.setVisible(false);
        blue1.setVisible(false);
        purple3.setVisible(false);
        purple2.setVisible(false);
        purple1.setVisible(false);
        level1.setVisible(false);
        level2.setVisible(false);
        level3.setVisible(false);
        buyCard.setVisible(false);
        errorDevText.setVisible(false);
        deck1.setVisible(false);
        deck2.setVisible(false);
        deck3.setVisible(false);
        deck4.setVisible(false);
        deck5.setVisible(false);
        deck6.setVisible(false);
        deck7.setVisible(false);
        deck8.setVisible(false);
        deck9.setVisible(false);
        deck10.setVisible(false);
        deck11.setVisible(false);
        deck12.setVisible(false);
        green.setVisible(false);
        purple.setVisible(false);
        blue.setVisible(false);
        yellow.setVisible(false);
        slotBox.setVisible(false);
    }

    @FXML
    public void handleCloseChooseAction()
    {
        Stage stage = (Stage) closeBuyDevCard.getScene().getWindow();
        stage.close();
    }
    @FXML
    public void handleOkBuyDevCard()
    {
        if(isCardBuyable) {
            setInvisible();
            Label label = (Label) slotBox.getChildren().get(0);
            label.setFont(Font.loadFont(getClass().getResourceAsStream("/gui/font/Enchanted-Land.otf"),40));
            setFont((RadioButton)availableSlot.getToggles().get(0), 35);
            setFont((RadioButton)availableSlot.getToggles().get(1), 35);
            setFont((RadioButton)availableSlot.getToggles().get(2), 35);
            slotBox.setVisible(true);
        }
        else {
            //TODO da spostare opportunamente
        }
    }

    @FXML
    public void handleSlotSelection() {
        RadioButton slot = (RadioButton) availableSlot.getSelectedToggle();
        slotIndex = Integer.parseInt(String.valueOf(slot.getText().charAt(slot.getText().length()-1)));
        //if(buyDevCardAction.canBuyCardOfLevel(developmentCard.getType().getLevel())) {
        if (client.getGame().getPlayer(client.getUser()).getPersonalBoard().canPutCardInSlot(slotIndex, developmentCard.getType().getLevel())) {
            setDevelopmentCardTextOK("Now we need instruction on\nhow you want to pay the card");
            buyCard.setVisible(false);
            setFont(toPayment, 24);
            toPayment.setVisible(true);
        } else {
            setErrorDevTextKO("Selected slot is not available");
        }
    }


    @FXML
    public void goToPayment() {
        switchToHowToTakeResourcesPhase();
    }

    public boolean isAvailableExtraDepotOfType(ResourceType resource) {
        return client.getGame().getPlayer(client.getUser()).getPersonalBoard().isAvailableEffectOfType(Effect.EXTRADEPOT,resource);
    }


    public void switchToHowToTakeResourcesPhase()
    {
        errorDevText.setVisible(false);
        slotBox.setVisible(false);
        selectHowPickResourcesText.setVisible(true);
        showRequiredResources();
    }

    public void showRequiredResources() {
        neededResources = developmentCard.getCost().entrySet().stream().filter(x -> x.getValue() > 0).map( x -> new Pair<>(x.getKey(),x.getValue())).collect(Collectors.toList());
        for(int i = 0; i < neededResources.size(); i++) {
            if(i == 0) {
                resource1Cost.setImage(new Image(ResourceLocator.retrieveResourceTypeImage(neededResources.get(0).getKey())));
                resource1Cost.setVisible(true);
                resDepot1Text.setVisible(true);
                resStronbox1Text.setVisible(true);
                plusDepotRes1.setVisible(true);
                plusStrRes1.setVisible(true);
                minusDepotRes1.setVisible(true);
                minusStrRes1.setVisible(true);
                textField1.setVisible(true);
                textField2.setVisible(true);
                if(isAvailableExtraDepotOfType(neededResources.get(i).getKey())) {
                    resExtra1Text.setVisible(true);
                    plusExtraRes1.setVisible(true);
                    minusExtraRes1.setVisible(true);
                    textField3.setVisible(true);
                    textField3.setText("0");
                }
                textField1.setText("0");
                textField2.setText("0");
            }
            if(i == 1) {
                resource2Cost.setImage(new Image(ResourceLocator.retrieveResourceTypeImage(neededResources.get(1).getKey())));
                resource2Cost.setVisible(true);
                resDepot2Text.setVisible(true);
                resStronbox2Text.setVisible(true);
                plusDepotRes2.setVisible(true);
                plusStrRes2.setVisible(true);
                minusDepotRes2.setVisible(true);
                minusStrRes2.setVisible(true);
                textField4.setVisible(true);
                textField5.setVisible(true);
                if(isAvailableExtraDepotOfType(neededResources.get(i).getKey())) {
                    resExtra2Text.setVisible(true);
                    plusExtraRes2.setVisible(true);
                    minusExtraRes2.setVisible(true);
                    textField6.setText("0");
                    textField6.setVisible(true);
                }
                textField4.setText("0");
                textField5.setText("0");

            }
            if(i == 2) {
                resource3Cost.setImage(new Image(ResourceLocator.retrieveResourceTypeImage(neededResources.get(2).getKey())));
                resource3Cost.setVisible(true);
                resDepot3Text.setVisible(true);
                resStronbox3Text.setVisible(true);
                plusDepotRes3.setVisible(true);
                plusStrRes3.setVisible(true);
                minusDepotRes3.setVisible(true);
                minusStrRes3.setVisible(true);
                textField7.setVisible(true);
                textField8.setVisible(true);
                if(isAvailableExtraDepotOfType(neededResources.get(i).getKey())) {
                    resExtra3Text.setVisible(true);
                    plusExtraRes3.setVisible(true);
                    minusExtraRes3.setVisible(true);
                    textField9.setText("0");
                    textField9.setVisible(true);
                }
                textField7.setText("0");
                textField8.setText("0");
            }
        }
        toPayment.setVisible(false);
    }

    @FXML
    public void minusFirstResourceFromDepot() {
        makeTextInvisible(errorDevText);
        firstCurrentValueDepot--;
        if(!addToCostList(1, 1, firstCurrentValueDepot, textField1)) firstCurrentValueDepot++;
    }

    @FXML
    public void minusSecondResourceFromDepot() {
        makeTextInvisible(errorDevText);
        secondCurrentValueDepot--;
        if(!addToCostList(2, 1, secondCurrentValueDepot, textField4)) secondCurrentValueDepot++;
    }

    @FXML
    public void minusThirdResourceFromDepot() {
        makeTextInvisible(errorDevText);
        thirdCurrentValueDepot--;
        if(!addToCostList(3,1, thirdCurrentValueDepot, textField7)) thirdCurrentValueDepot++;
    }

    @FXML
    public void minusFirstResourceFromStrongbox() {
        makeTextInvisible(errorDevText);
        firstCurrentValueStrongbox--;
        if(!addToCostList(1,1, firstCurrentValueStrongbox, textField2)) firstCurrentValueStrongbox++;
    }
    @FXML
    public void minusSecondResourceFromStrongbox() {
        makeTextInvisible(errorDevText);
        secondCurrentValueStrongbox--;
        if(!addToCostList(2,1, secondCurrentValueStrongbox, textField5)) secondCurrentValueStrongbox++;
    }
    @FXML
    public void minusThirdResourceFromStrongbox() {
        makeTextInvisible(errorDevText);
        thirdCurrentValueStrongbox--;
        if(!addToCostList(3,1,thirdCurrentValueStrongbox, textField8)) thirdCurrentValueStrongbox++;
    }



    @FXML
    public void plusFirstResourceFromDepot() {
        firstCurrentValueDepot++;
        if(!editedCostList(1, 1, firstCurrentValueDepot, textField1)) firstCurrentValueDepot--;
    }


    @FXML
    public void plusSecondResourceFromDepot(){
        secondCurrentValueDepot++;
        if(!editedCostList(2, 1, secondCurrentValueDepot, textField4)) secondCurrentValueDepot--;
    }

    @FXML
    public void plusThirdResourceFromDepot(){
        thirdCurrentValueDepot++;
        if(!editedCostList(3, 1, thirdCurrentValueDepot, textField7)) thirdCurrentValueDepot--;
    }

    @FXML
    public void plusFirstResourceFromStrongbox() {
        firstCurrentValueStrongbox++;
        if(!editedCostList(1, 1, firstCurrentValueStrongbox, textField2)) firstCurrentValueStrongbox--;
    }

    @FXML
    public void plusSecondResourceFromStrongbox(){
        secondCurrentValueStrongbox++;
        if(!editedCostList(2, 1,secondCurrentValueStrongbox , textField5)) secondCurrentValueStrongbox--;
    }

    @FXML
    public void plusThirdResourceFromStrongbox(){
        thirdCurrentValueStrongbox++;
        if(!editedCostList(3, 1, thirdCurrentValueStrongbox, textField8)) thirdCurrentValueStrongbox--;
    }


    int firstCurrentValueDepot = 0;
    int secondCurrentValueDepot = 0;
    int thirdCurrentValueDepot = 0;


    int firstCurrentValueStrongbox = 0;
    int secondCurrentValueStrongbox = 0;
    int thirdCurrentValueStrongbox = 0;

    int firstCurrentValueExtra = 0;
    int secondCurrentValueExtra = 0;
    int thirdCurrentValueExtra = 0;


    private boolean editedCostList(int resourceIndex, int taken, int currentValue, TextField resource) {
        int required = neededResources.get(resourceIndex-1).getValue();
        if(required>=taken) {
            errorDevText.setVisible(false);
            neededResources.get(resourceIndex-1).setValue(required-taken);
            resource.setText(""+currentValue);
            showFinishButton();
            return true;
        } else {
            setErrorDevTextKO("Maximum number selected!");
            showFinishButton();
            return false;
        }
    }

    private boolean addToCostList(int resourceIndex, int taken, int currentValue, TextField resource) {
        if(currentValue >= 0) {
            neededResources.get(resourceIndex-1).setValue(neededResources.get(resourceIndex-1).getValue()+taken);
            resource.setText(""+currentValue);
            showFinishButton();
            return true;
        } else {
            showFinishButton();
            return false;
        }
    }

    public void handleActionConclusion() {
        initializeMap();
        for(int i = 0; i < neededResources.size(); i++) {
            if (i == 0)
                createInstructionEntry(i, firstCurrentValueDepot, firstCurrentValueStrongbox, firstCurrentValueExtra);
            if (i == 1)
                createInstructionEntry(i, secondCurrentValueDepot, secondCurrentValueStrongbox, secondCurrentValueExtra);
            if (i == 2)
                createInstructionEntry(i, thirdCurrentValueDepot, thirdCurrentValueStrongbox, thirdCurrentValueExtra);
        }
        buyDevCardAction.compileMessage(developmentCard.getType(),paymentInstruction, slotIndex);
        buyDevCardAction.manageUserInteraction();
    }


    public void showFinishButton() {
        concludeAction.setVisible(isPaymentDone());
    }

    public void makeTextInvisible(Text text) {
        text.setVisible(false);
    }

    private boolean isPaymentDone() {
        return neededResources.stream().allMatch(x -> x.getValue() == 0);
    }

    private void initializeMap() {
        paymentInstruction.put(ResourceSource.DEPOT, new EnumMap<>(ResourceType.class));
        paymentInstruction.put(ResourceSource.STRONGBOX, new EnumMap<>(ResourceType.class));
        paymentInstruction.put(ResourceSource.EXTRA, new EnumMap<>(ResourceType.class));

    }

    private void createInstructionEntry(int resourceIndex,int sourceDepot, int sourceStrongbox, int sourceExtra) {
        if(sourceDepot > 0) {
            paymentInstruction.get(ResourceSource.DEPOT).put(neededResources.get(resourceIndex).getKey(),sourceDepot);
        }
        if(sourceStrongbox > 0) {
            paymentInstruction.get(ResourceSource.STRONGBOX).put(neededResources.get(resourceIndex).getKey(),sourceStrongbox);
        }
        if(sourceExtra > 0) {
            paymentInstruction.get(ResourceSource.EXTRA).put(neededResources.get(resourceIndex).getKey(),sourceExtra);
        }
    }

}
