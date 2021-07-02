package it.polimi.ingsw.client.view.GUI;

import it.polimi.ingsw.client.Checker;
import it.polimi.ingsw.client.clientstates.gui.BuyDevCardGUI;
import it.polimi.ingsw.commons.ColorCard;
import it.polimi.ingsw.commons.DevelopmentCard;
import it.polimi.ingsw.commons.ResourceType;
import it.polimi.ingsw.utils.Pair;
import it.polimi.ingsw.utils.ResourceLocator;
import it.polimi.ingsw.utils.ResourceSource;
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
/**
     * Class that represents the controller of the Buy Development Card page of the game
 * on this page the user can perform the Buy Development Card action choosing the card
 * you want to buy from one of the possible decks if you have the resources to buy it
 */
public class BuyDevCardController extends Controller implements PaymentController {
    /**
     * Attribute that represents the central pane of the page
     */
    @FXML
    public AnchorPane anchorBuyDevCard;
    /**
     * Attribute that represents the text where the title of the page is shown
     */
    @FXML
    public Text buyDevCardText,selectHowPickResourcesText;
    /**
     * Attribute that represents the text where the type of deck is shown
     */
    @FXML
    public Text green,yellow,purple,blue,level1,level2,level3;
    /**
     * Attribute that represents the button to close the page
     */
    @FXML
    public Button closeBuyDevCard;
    /**
     * Attributes that represent the buttons to select the top Card of
     * each deck and eventually purchase it
     */
    @FXML
    public Button green3,green2,green1,blue3,blue2,blue1,yellow1,yellow2,yellow3,purple1,purple2,purple3;
    /**
     *Attributes that represent the text identifying the resources to be paid once
     * the card to be purchased has been selected
     */
    @FXML
    public Text errorDevText,resDepot1Text,resDepot2Text,resDepot3Text,resStronbox1Text,resStronbox2Text,resStronbox3Text,resExtra1Text,resExtra2Text,resExtra3Text;
    /**
     * Attribute representing the imageView of the card selected by the decks
     */
    @FXML
    public ImageView selectedCard;
    /**
     * Attributes representing the buttons to purchase the card
     * and confirm the payment after selecting the card
     */
    @FXML
    public Button  buyCard,toPayment, concludeAction;
    /**
     * Attributes that represent the imagViews of the cards below the top card of the specific deck
     */
    @FXML
    public ImageView deck1,deck2,deck3,deck4,deck5,deck6,deck7,deck8,deck9,deck10,deck11,deck12;
    /**
     * Attributes that represent the resource imagView that represent the cost of a card after selecting it
     */
    @FXML
    public ImageView resource1Cost,resource2Cost,resource3Cost;


    @FXML
    public Button plusDepotRes1,plusExtraRes1,plusStrRes1,minusDepotRes1,minusStrRes1,minusExtraRes1,
            plusDepotRes2,plusExtraRes2,plusStrRes2,minusDepotRes2,minusStrRes2,minusExtraRes2,
            plusDepotRes3,plusExtraRes3,plusStrRes3,minusDepotRes3,minusStrRes3,minusExtraRes3;
    /**
     * Attributes that represent the buttons used to increase or decrease
     * the number of resources to be paid for each resource
     */
    @FXML
    public TextField textField1,textField2,textField3,textField4,textField5,textField6,textField7,textField8,textField9;
    public ToggleGroup availableSlot;

    @FXML
    public VBox slotBox;

    DevelopmentCard developmentCard;
    boolean isCardBuyable;
    int slotIndex;
    List<Pair<ResourceType,Integer>> neededResources = new ArrayList<>();
    Map<ResourceType, Integer> startingCost = new EnumMap<>(ResourceType.class);
    Map<ResourceSource, EnumMap<ResourceType,Integer>> paymentInstruction = new HashMap<>();
    BuyDevCardGUI buyDevCardAction;

    /**
     * Main method that initializes the scene within the stage
     * It takes care of setting the background of the scene
     * ,the font of the texts and buttons,
     * and the images on the scene
     */
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

    /**
     * method used to set the images of the top cards of the decks actually present
     */
    private void initializeDecksImages(){

        if(client.getGame().isAnyCardPresentInDeck(3, ColorCard.green)) {
            green3.setStyle("-fx-background-image: url(" +
                    client.getGame().getDeckTopCard(3, ColorCard.green).toImage() + ")");
        } else green3.setVisible(false);
        if(client.getGame().isAnyCardPresentInDeck(2, ColorCard.green)) {
            green2.setStyle("-fx-background-image: url(" +
                    client.getGame().getDeckTopCard(2, ColorCard.green).toImage() + ")");
        } else green2.setVisible(false);
        if(client.getGame().isAnyCardPresentInDeck(1, ColorCard.green)) {
            green1.setStyle("-fx-background-image: url(" +
                    client.getGame().getDeckTopCard(1, ColorCard.green).toImage() + ")");
        } else green1.setVisible(false);

        if(client.getGame().isAnyCardPresentInDeck(3, ColorCard.blue)) {
            blue3.setStyle("-fx-background-image: url(" +
                    client.getGame().getDeckTopCard(3, ColorCard.blue).toImage() + ")");
        } else blue3.setVisible(false);
        if(client.getGame().isAnyCardPresentInDeck(2, ColorCard.blue)) {
            blue2.setStyle("-fx-background-image: url(" +
                    client.getGame().getDeckTopCard(2, ColorCard.blue).toImage() + ")");
        } else blue2.setVisible(false);
        if(client.getGame().isAnyCardPresentInDeck(1, ColorCard.blue)) {
            blue1.setStyle("-fx-background-image: url(" +
                    client.getGame().getDeckTopCard(1, ColorCard.blue).toImage() + ")");
        } else blue1.setVisible(false);

        if(client.getGame().isAnyCardPresentInDeck(3, ColorCard.yellow)) {
            yellow3.setStyle("-fx-background-image: url(" +
                    client.getGame().getDeckTopCard(3, ColorCard.yellow).toImage() + ")");
        } else yellow3.setVisible(false);
        if(client.getGame().isAnyCardPresentInDeck(2, ColorCard.yellow)) {
            yellow2.setStyle("-fx-background-image: url(" +
                    client.getGame().getDeckTopCard(2, ColorCard.yellow).toImage() + ")");
        } else yellow2.setVisible(false);
        if(client.getGame().isAnyCardPresentInDeck(1, ColorCard.yellow)) {
            yellow1.setStyle("-fx-background-image: url(" +
                    client.getGame().getDeckTopCard(1, ColorCard.yellow).toImage() + ")");
        } else yellow1.setVisible(false);

        if(client.getGame().isAnyCardPresentInDeck(3, ColorCard.purple)) {
            purple3.setStyle("-fx-background-image: url(" +
                    client.getGame().getDeckTopCard(3, ColorCard.purple).toImage() + ")");
        } else purple3.setVisible(false);
        if(client.getGame().isAnyCardPresentInDeck(2, ColorCard.purple)) {
            purple2.setStyle("-fx-background-image: url(" +
                    client.getGame().getDeckTopCard(2, ColorCard.purple).toImage() + ")");
        } else purple2.setVisible(false);
        if(client.getGame().isAnyCardPresentInDeck(1, ColorCard.purple)) {
            purple1.setStyle("-fx-background-image: url(" +
                    client.getGame().getDeckTopCard(1, ColorCard.purple).toImage() + ")");
        } else purple1.setVisible(false);

    }

    /**
     * method used to set the image of the selected card of the decks
     * @param developmentCard selected card image to show
     */
    private void setSelectedCardImage(DevelopmentCard developmentCard){
        selectedCard.setImage(new Image(developmentCard.toImage()));
    }

    /**
     * method used to set the visibility of the button to be able to buy
     * the card if you have the resources and the text that indicates whether you have them or not
     * @param toDisplay string to show
     */
    private void setDevelopmentCardTextOK(String toDisplay){
        errorDevText.setText(toDisplay);
        errorDevText.setVisible(true);
        if(client.getUser().equals(client.getGame().getCurrPlayer().getNickname()))
            buyCard.setVisible(true);
    }

    /**
     * method used to set the visibility of the button to be able to buy
     *  the card if you have the resources and the text indicating that
     *  you have the necessary resources to purchase it
     */
    private void setFeasiblePurchase(){
        if(isCardBuyable) {
        setDevelopmentCardTextOK("You have \nenough resources \nto purchase \nselected card");
        if(client.getUser().equals(client.getGame().getCurrPlayer().getNickname()))
                buyCard.setVisible(true);
        } else buyCard.setVisible(false);
    }

    /**
     * method used to set an error message
     * @param error type of error
     */
    public void maxOccurrencesSet(String error){
        errorDevText.setText(error);
        errorDevText.setVisible(true);
    }

    /**
     * method used to check if you have the necessary resources to purchase the level 3 green card
     */
    @FXML
    public void checkIfCanBuyCard3Green() {
        developmentCard = client.getGame().getDeckTopCard(3,ColorCard.green);
        setSelectedCardImage(developmentCard);
        if(Checker.checkResources(buyDevCardAction.computeActualCost(developmentCard),client.getGame().getPlayer(client.getUser()).getPersonalBoard())){
        //if(Checker.checkResources(developmentCard.getCost(),client.getGame().getPlayer(client.getUser()).getPersonalBoard())) {
            isCardBuyable = true;

        } else {
            isCardBuyable = false;
            maxOccurrencesSet("You don't have \nenough resources \nto purchase \nselected card");
        }
        setFeasiblePurchase();
    }
    /**
     * method used to check if you have the necessary resources to purchase the level 2 green card
     */
    @FXML
    public void checkIfCanBuyCard2Green() {
        developmentCard = client.getGame().getDeckTopCard(2,ColorCard.green);
        setSelectedCardImage(developmentCard);
        if(Checker.checkResources(buyDevCardAction.computeActualCost(developmentCard),client.getGame().getPlayer(client.getUser()).getPersonalBoard())){
        //if(Checker.checkResources(developmentCard.getCost(),client.getGame().getPlayer(client.getUser()).getPersonalBoard())) {
            isCardBuyable = true;
        } else {
            isCardBuyable = false;
            maxOccurrencesSet("You don't have \nenough resources \nto purchase \nselected card");
        }
        setFeasiblePurchase();
    }
    /**
     * method used to check if you have the necessary resources to purchase the level 1 green card
     */
    @FXML
    public void checkIfCanBuyCard1Green() {
        developmentCard = client.getGame().getDeckTopCard(1,ColorCard.green);
        setSelectedCardImage(developmentCard);

        if(Checker.checkResources(buyDevCardAction.computeActualCost(developmentCard),client.getGame().getPlayer(client.getUser()).getPersonalBoard())){
            setFeasiblePurchase();
            isCardBuyable = true;
        } else {
            isCardBuyable = false;
            maxOccurrencesSet("You don't have \nenough resources \nto purchase \nselected card");
        }
        setFeasiblePurchase();
    }
    /**
     * method used to check if you have the necessary resources to purchase the level 3 blue card
     */
    @FXML
    public void checkIfCanBuyCard3Blue() {
        developmentCard = client.getGame().getDeckTopCard(3,ColorCard.blue);
        setSelectedCardImage(developmentCard);
        //if(Checker.checkResources(developmentCard.getCost(),client.getGame().getPlayer(client.getUser()).getPersonalBoard())) {
        if(Checker.checkResources(buyDevCardAction.computeActualCost(developmentCard),client.getGame().getPlayer(client.getUser()).getPersonalBoard())){
            isCardBuyable = true;
        } else {
            isCardBuyable = false;
            maxOccurrencesSet("You don't have \nenough resources \nto purchase \nselected card");
        }
        setFeasiblePurchase();
    }
    /**
     * method used to check if you have the necessary resources to purchase the level 2 blue card
     */
    @FXML
    public void checkIfCanBuyCard2Blue() {
        developmentCard = client.getGame().getDeckTopCard(2,ColorCard.blue);
        setSelectedCardImage(developmentCard);
        //if(Checker.checkResources(developmentCard.getCost(),client.getGame().getPlayer(client.getUser()).getPersonalBoard())) {
        if(Checker.checkResources(buyDevCardAction.computeActualCost(developmentCard),client.getGame().getPlayer(client.getUser()).getPersonalBoard())){
            isCardBuyable = true;
        } else {
            isCardBuyable = false;
            maxOccurrencesSet("You don't have \nenough resources \nto purchase \nselected card");
        }
        setFeasiblePurchase();
    }
    /**
     * method used to check if you have the necessary resources to purchase the level 1 blue card
     */
    @FXML
    public void checkIfCanBuyCard1Blue() {
        developmentCard = client.getGame().getDeckTopCard(1,ColorCard.blue);
        setSelectedCardImage(developmentCard);
        //if(Checker.checkResources(developmentCard.getCost(),client.getGame().getPlayer(client.getUser()).getPersonalBoard())) {
        if(Checker.checkResources(buyDevCardAction.computeActualCost(developmentCard),client.getGame().getPlayer(client.getUser()).getPersonalBoard())){
            isCardBuyable = true;
        } else {
            isCardBuyable = false;
            maxOccurrencesSet("You don't have \nenough resources \nto purchase \nselected card");
        }
        setFeasiblePurchase();
    }
    /**
     * method used to check if you have the necessary resources to purchase the level 3 yellow card
     */
    @FXML
    public void checkIfCanBuyCard3Yellow() {
        developmentCard = client.getGame().getDeckTopCard(3,ColorCard.yellow);
        setSelectedCardImage(developmentCard);
        //if(Checker.checkResources(developmentCard.getCost(),client.getGame().getPlayer(client.getUser()).getPersonalBoard())) {

        if(Checker.checkResources(buyDevCardAction.computeActualCost(developmentCard),client.getGame().getPlayer(client.getUser()).getPersonalBoard())){
            isCardBuyable = true;

        } else {
            isCardBuyable = false;
            maxOccurrencesSet("You don't have \nenough resources \nto purchase \nselected card");

        }
        setFeasiblePurchase();
    }
    /**
     * method used to check if you have the necessary resources to purchase the level 2 yellow card
     */
    @FXML
    public void checkIfCanBuyCard2Yellow() {
        developmentCard = client.getGame().getDeckTopCard(2,ColorCard.yellow);
        setSelectedCardImage(developmentCard);
        //if(Checker.checkResources(developmentCard.getCost(),client.getGame().getPlayer(client.getUser()).getPersonalBoard())) {
        if(Checker.checkResources(buyDevCardAction.computeActualCost(developmentCard),client.getGame().getPlayer(client.getUser()).getPersonalBoard())){
            isCardBuyable = true;

        } else {
            isCardBuyable = false;
            maxOccurrencesSet("You don't have \nenough resources \nto purchase \nselected card");
        }
        setFeasiblePurchase();
    }
    /**
     * method used to check if you have the necessary resources to purchase the level 1 yellow card
     */
    @FXML
    public void checkIfCanBuyCard1Yellow() {
        developmentCard = client.getGame().getDeckTopCard(1,ColorCard.yellow);
        setSelectedCardImage(developmentCard);
        //if(Checker.checkResources(developmentCard.getCost(),client.getGame().getPlayer(client.getUser()).getPersonalBoard())) {
        if(Checker.checkResources(buyDevCardAction.computeActualCost(developmentCard),client.getGame().getPlayer(client.getUser()).getPersonalBoard())){
            isCardBuyable = true;

        } else {
            isCardBuyable = false;
            maxOccurrencesSet("You don't have \nenough resources \nto purchase \nselected card");
        }
        setFeasiblePurchase();
    }
    /**
     * method used to check if you have the necessary resources to purchase the level 3 purple card
     */
    @FXML
    public void checkIfCanBuyCard3Purple() {
        developmentCard = client.getGame().getDeckTopCard(3,ColorCard.purple);
        setSelectedCardImage(developmentCard);
        //if(Checker.checkResources(developmentCard.getCost(),client.getGame().getPlayer(client.getUser()).getPersonalBoard())) {
        if(Checker.checkResources(buyDevCardAction.computeActualCost(developmentCard),client.getGame().getPlayer(client.getUser()).getPersonalBoard())){
            isCardBuyable = true;

        } else {
            isCardBuyable = false;
            maxOccurrencesSet("You don't have \nenough resources \nto purchase \nselected card");
        }
        setFeasiblePurchase();
    }
    /**
     * method used to check if you have the necessary resources to purchase the level 2 purple card
     */
    @FXML
    public void checkIfCanBuyCard2Purple() {
        developmentCard = client.getGame().getDeckTopCard(2,ColorCard.purple);
        setSelectedCardImage(developmentCard);
        //if(Checker.checkResources(developmentCard.getCost(),client.getGame().getPlayer(client.getUser()).getPersonalBoard())) {

        if(Checker.checkResources(buyDevCardAction.computeActualCost(developmentCard),client.getGame().getPlayer(client.getUser()).getPersonalBoard())){
            isCardBuyable = true;

        } else {

            isCardBuyable = false;
            maxOccurrencesSet("You don't have \nenough resources \nto purchase \nselected card");
        }
        setFeasiblePurchase();
    }
    /**
     * method used to check if you have the necessary resources to purchase the level 1 purple card
     */
    @FXML
    public void checkIfCanBuyCard1Purple() {
        developmentCard = client.getGame().getDeckTopCard(1,ColorCard.purple);
        setSelectedCardImage(developmentCard);
        //if(Checker.checkResources(developmentCard.getCost(),client.getGame().getPlayer(client.getUser()).getPersonalBoard())) {
        if(Checker.checkResources(buyDevCardAction.computeActualCost(developmentCard),client.getGame().getPlayer(client.getUser()).getPersonalBoard())){
            isCardBuyable = true;

        } else {
            isCardBuyable = false;
            maxOccurrencesSet("You don't have \nenough resources \nto purchase \nselected card");
        }
        setFeasiblePurchase();
    }

    /**
     * method used to make page elements invisible in order to load other elements on the same page
     */
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
    /**
     * method used to close page of Buy Development Card over the main stage
     * It is performed when closeBuyDevCard button is pressed
     */
    @FXML
    public void handleCloseChooseAction()
    {
        Stage stage = (Stage) closeBuyDevCard.getScene().getWindow();
        stage.close();
    }

    /**
     * method used to manage the purchase of the selected card after
     * clicking on the button to purchase it
     * It is performed when buyCard button is pressed
     */
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

        }
    }

    /**
     * method used to manage which slot to insert the card into after purchasing it
     */
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
            maxOccurrencesSet("Selected slot is not available");
            toPayment.setVisible(false);
        }
    }

    /**
     * method used to go to the card payment page
     */
    @FXML
    public void goToPayment() {
        switchToHowToTakeResourcesPhase();
    }


    /**
     * method used to make card payments visible and where to get the resources from
     */
    public void switchToHowToTakeResourcesPhase()
    {
        errorDevText.setVisible(false);
        slotBox.setVisible(false);
        selectHowPickResourcesText.setVisible(true);
        startingCost = buyDevCardAction.computeActualCost(developmentCard);
        neededResources = startingCost.entrySet().stream().filter(x -> x.getValue() > 0).map( x -> new Pair<>(x.getKey(),x.getValue())).collect(Collectors.toList());
        showRequiredResources(neededResources);
        toPayment.setVisible(false);
    }


    /**
     * method used to make visible where to get the resources for the first cost resource
     */
    public void setVisibleFirstResource() {
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
        if(isAvailableExtraDepotOfType(neededResources.get(0).getKey(), client)) {
            resExtra1Text.setVisible(true);
            plusExtraRes1.setVisible(true);
            minusExtraRes1.setVisible(true);
            textField3.setVisible(true);
            textField3.setText("0");
        }
        textField1.setText("0");
        textField2.setText("0");
    }
    /**
     * method used to make visible where to get the resources for the second cost resource
     */
    public void setVisibleSecondResource() {
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
        if(isAvailableExtraDepotOfType(neededResources.get(1).getKey(),client)) {
            resExtra2Text.setVisible(true);
            plusExtraRes2.setVisible(true);
            minusExtraRes2.setVisible(true);
            textField6.setText("0");
            textField6.setVisible(true);
        }
        textField4.setText("0");
        textField5.setText("0");
    }
    /**
     * method used to make visible where to get the resources for the third cost resource
     */
    public void setVisibleThirdResource() {
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
        if(isAvailableExtraDepotOfType(neededResources.get(2).getKey(), client)) {
            resExtra3Text.setVisible(true);
            plusExtraRes3.setVisible(true);
            minusExtraRes3.setVisible(true);
            textField9.setText("0");
            textField9.setVisible(true);
        }
        textField7.setText("0");
        textField8.setText("0");
    }

    /**
     * method used to decrement the resource pick counter from depots for the first resource
     */
    @FXML
    public void minusFirstResourceFromDepot() {
        makeTextInvisible(errorDevText);
        firstCurrentValueDepot--;
        if(!addToCostList(neededResources,1, 1, firstCurrentValueDepot, textField1)) firstCurrentValueDepot++;
    }
    /**
     * method used to decrement the resource pick counter from depots for the second resource
     */
    @FXML
    public void minusSecondResourceFromDepot() {
        makeTextInvisible(errorDevText);
        secondCurrentValueDepot--;
        if(!addToCostList(neededResources,2, 1, secondCurrentValueDepot, textField4)) secondCurrentValueDepot++;
    }
    /**
     * method used to decrement the resource pick counter from depots for the third resource
     */
    @FXML
    public void minusThirdResourceFromDepot() {
        makeTextInvisible(errorDevText);
        thirdCurrentValueDepot--;
        if(!addToCostList(neededResources,3,1, thirdCurrentValueDepot, textField7)) thirdCurrentValueDepot++;
    }
    /**
     * method used to decrement the resource pick counter from Strongbox for the first resource
     */
    @FXML
    public void minusFirstResourceFromStrongbox() {
        makeTextInvisible(errorDevText);
        firstCurrentValueStrongbox--;
        if(!addToCostList(neededResources,1,1, firstCurrentValueStrongbox, textField2)) firstCurrentValueStrongbox++;
    }
    /**
     * method used to decrement the resource pick counter from Strongbox for the second resource
     */
    @FXML
    public void minusSecondResourceFromStrongbox() {
        makeTextInvisible(errorDevText);
        secondCurrentValueStrongbox--;
        if(!addToCostList(neededResources,2,1, secondCurrentValueStrongbox, textField5)) secondCurrentValueStrongbox++;
    }
    /**
     * method used to decrement the resource pick counter from Strongbox for the third resource
     */
    @FXML
    public void minusThirdResourceFromStrongbox() {
        makeTextInvisible(errorDevText);
        thirdCurrentValueStrongbox--;
        if(!addToCostList(neededResources, 3,1,thirdCurrentValueStrongbox, textField8)) thirdCurrentValueStrongbox++;
    }


    /**
     * method used to increment the resource selection counter from depots for the first resource
     */
    @FXML
    public void plusFirstResourceFromDepot() {
        firstCurrentValueDepot++;
        if(!editedCostList(neededResources,1, 1, firstCurrentValueDepot, textField1)) firstCurrentValueDepot--;
    }

    /**
     * method used to increment the resource selection counter from depots for the second resource
     */
    @FXML
    public void plusSecondResourceFromDepot(){
        secondCurrentValueDepot++;
        if(!editedCostList(neededResources,2, 1, secondCurrentValueDepot, textField4)) secondCurrentValueDepot--;
    }
    /**
     * method used to increment the resource selection counter from depots for the third resource
     */
    @FXML
    public void plusThirdResourceFromDepot(){
        thirdCurrentValueDepot++;
        if(!editedCostList(neededResources,3, 1, thirdCurrentValueDepot, textField7)) thirdCurrentValueDepot--;
    }
    /**
     * method used to increment the resource selection counter from Strongbox for the first resource
     */
    @FXML
    public void plusFirstResourceFromStrongbox() {
        firstCurrentValueStrongbox++;
        if(!editedCostList(neededResources,1, 1, firstCurrentValueStrongbox, textField2)) firstCurrentValueStrongbox--;
    }
    /**
     * method used to increment the resource selection counter from Strongbox for the second resource
     */
    @FXML
    public void plusSecondResourceFromStrongbox(){
        secondCurrentValueStrongbox++;
        if(!editedCostList(neededResources,2, 1,secondCurrentValueStrongbox , textField5)) secondCurrentValueStrongbox--;
    }
    /**
     * method used to increment the resource selection counter from Strongbox for the third resource
     */
    @FXML
    public void plusThirdResourceFromStrongbox(){
        thirdCurrentValueStrongbox++;
        if(!editedCostList(neededResources,3, 1, thirdCurrentValueStrongbox, textField8)) thirdCurrentValueStrongbox--;
    }
    /**
     * method used to increment the resource selection counter from Extra depot for the first resource
     */
    @FXML
    public void plusFirstResourceFromExtra() {
        firstCurrentValueExtra++;
        if(!editedCostList(neededResources,1, 1, firstCurrentValueExtra, textField3)) firstCurrentValueExtra--;
    }
    /**
     * method used to increment the resource selection counter from Extra depot for the second resource
     */
    @FXML
    public void plusSecondResourceFromExtra() {
        secondCurrentValueExtra++;
        if(!editedCostList(neededResources,2, 1, secondCurrentValueExtra, textField6)) secondCurrentValueExtra--;
    }
    /**
     * method used to increment the resource selection counter from Extra depot for the third resource
     */
    @FXML
    public void plusThirdResourceFromExtra() {
        thirdCurrentValueExtra++;
        if(!editedCostList(neededResources,3, 1, thirdCurrentValueExtra, textField9)) thirdCurrentValueExtra--;
    }
    /**
     * method used to decrement the resource pick counter from Extra depot for the first resource
     */
    @FXML
    public void minusFirstResourceFromExtra() {
        makeTextInvisible(errorDevText);
        firstCurrentValueExtra--;
        if(!addToCostList(neededResources,1, 1, firstCurrentValueExtra, textField3)) firstCurrentValueExtra++;
    }
    /**
     * method used to decrement the resource pick counter from Extra depot for the second resource
     */
    @FXML
    public void minusSecondResourceFromExtra() {
        makeTextInvisible(errorDevText);
        secondCurrentValueExtra--;
        if(!addToCostList(neededResources,2,1,secondCurrentValueExtra, textField6)) secondCurrentValueExtra++;
    }
    /**
     * method used to decrement the resource pick counter from Extra depot for the third resource
     */
    @FXML
    public void minusThirdResourceFromExtra() {
        makeTextInvisible(errorDevText);
        thirdCurrentValueExtra--;
        if (!addToCostList(neededResources,3,1,thirdCurrentValueExtra, textField9)) thirdCurrentValueExtra++;
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


    /**
     * method used to manage the conclusion of the action
     */
    @FXML
    public void handleActionConclusion() {
        paymentInstruction = initializeMap();
        for(int i = 0; i < neededResources.size(); i++) {
            if (i == 0)
                createInstructionEntry(paymentInstruction, neededResources, i, firstCurrentValueDepot, firstCurrentValueStrongbox, firstCurrentValueExtra);
            if (i == 1)
                createInstructionEntry(paymentInstruction, neededResources, i, secondCurrentValueDepot, secondCurrentValueStrongbox, secondCurrentValueExtra);
            if (i == 2)
                createInstructionEntry(paymentInstruction, neededResources, i, thirdCurrentValueDepot, thirdCurrentValueStrongbox, thirdCurrentValueExtra);
        }
        buyDevCardAction.compileMessage(developmentCard.getType(),paymentInstruction, slotIndex);
        buyDevCardAction.manageUserInteraction();
        closeAction(concludeAction);
    }

    /**
     * method used to make the payment button visible after choosing where
     * to place all the resources
     */
    public void showFinishButton() {
        concludeAction.setVisible(isPaymentDone(neededResources));
    }

    /**
     * method used to hide the error message
     */
    @Override
    public void hideError() {
        errorDevText.setVisible(false);
    }


}