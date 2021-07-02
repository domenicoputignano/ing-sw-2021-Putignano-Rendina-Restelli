package it.polimi.ingsw.client.view.GUI;

import it.polimi.ingsw.client.clientstates.gui.TakeResourcesFromMarketGUI;
import it.polimi.ingsw.client.reducedmodel.ReducedMarble;
import it.polimi.ingsw.client.reducedmodel.ReducedMarketTray;
import it.polimi.ingsw.commons.ColorMarble;
import it.polimi.ingsw.commons.ResourceType;
import it.polimi.ingsw.utils.MarbleDestination;
import it.polimi.ingsw.utils.MarketChoice;
import it.polimi.ingsw.utils.Pair;
import it.polimi.ingsw.utils.ResourceLocator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;
/**
 * Class that represents the controller of the Take Resources from Market page of the game
 * On this page the user can perform the action of Take Resources From Market obtaining up
 * to a maximum of 4 resources by choosing to take 3 or 4 marbles based on the choice of column or row
 */
public class TakeResourcesController extends Controller{
    /**
     * Attribute that represents the central pane of the page
     */
    @FXML
    public AnchorPane anchorBuyDevCard;
    /**
     * Attributes that represent the texts where the title of the page is shown
     */
    @FXML
    public Text takeResourcesText,resourcesText;
    /**
     * Attribute that represents the button to close the page
     */
    @FXML
    public Button closeTakeRes;

    /**
     * Attributes representing the imageViews of the lights
     * that illuminate the selected row/column during the action
     */
    @FXML
    public ImageView light1rows,light2rows,light3rows,light1columns,light2columns,light3columns,light4columns;
    /**
     * Attributes that represent the imageViews of the marbles
     * actually contained within the Market Tray
     */
    @FXML
    public ImageView marble11,marble12,marble13,marble14,
                        marble21,marble22,marble23,marble24
                        ,marble31,marble32,marble33,marble34,slidingMarble;
    /**
     *Attributes that represent the imageViews that make up the MarketTray
     */
    @FXML
    public ImageView marketTrayImage,resourceSupply,selResources1,selResources2,selResources3,selResources4,
                    selMarble1,selMarble2,selMarble3,selMarble4,indRes1,indRes2,indRes3,indRes4;
    /**
     * Attributes representing the buttons to select the row or column
     */
    @FXML
    public Button row1,row2,row3,column1,column2,column3,column4;
    /**
     * Attributes that represent the buttons to accept or reject
     * the selected resources and to confirm the action
     */
    @FXML
    public Button yesResources,noResources,okButton;

    /**
     * Attributes representing the buttons to select where
     * to place each resource obtained from the choice of the row / column
     */
    @FXML
    public Button sel1Depot1,sel1Depot2,sel1Depot3,
                sel2Depot1,sel2Depot2,sel2Depot3,
                sel3Depot1,sel3Depot2,sel3Depot3,
                sel4Depot1,sel4Depot2,sel4Depot3,
                sel1ExtraDepot,sel2ExtraDepot,sel3ExtraDepot,sel4ExtraDepot,
                discard1,discard2,discard3,discard4;

    @FXML
    public HBox convertMarbleButtons1, convertMarbleButtons2, convertMarbleButtons3, convertMarbleButtons4;
    /**
     * Attribute that represents the the destination for each marble
     */
    private MarbleDestination marble1Choice, marble2Choice, marble3Choice, marble4Choice;

    private TakeResourcesFromMarketGUI state;
    /**
     * Attribute that represents the list of the selected marbles
     */
    private List<ReducedMarble> chosenMarbles;
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

        anchorBuyDevCard.setBackground(new Background(new BackgroundImage(new Image("/gui/img/exit_tab.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false))));
        resourcesText.setStyle("-fx-text-fill: rgb(35, 25, 22);");

        setMarbleImages();

        state = new TakeResourcesFromMarketGUI(client);

        takeResourcesText.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(takeResourcesText,39);
        setFont(resourcesText,27);
        setFont(okButton,27);
        setFont(yesResources,23);
        setFont(noResources,23);
        setFont(sel1Depot1,21);
        setFont(sel1Depot2,21);
        setFont(sel1Depot3,21);
        setFont(sel1ExtraDepot,21);
        setFont(sel2Depot1,21);
        setFont(sel2Depot2,21);
        setFont(sel2Depot3,21);
        setFont(sel2ExtraDepot,21);
        setFont(sel3Depot1,21);
        setFont(sel3Depot2,21);
        setFont(sel3Depot3,21);
        setFont(sel3ExtraDepot,21);
        setFont(sel4Depot1,21);
        setFont(sel4Depot2,21);
        setFont(sel4Depot3,21);
        setFont(sel4ExtraDepot,21);
        setFont(discard1,21);
        setFont(discard2,21);
        setFont(discard3,21);
        setFont(discard4,21);
    }

    /**
     * method to use to parse the choices made of the marbles
     */
    private void parseWarehouseChoices(){
        List<ReducedMarble> marbles = state.getSelectedMarbles();

        state.addMarbleChoice(new Pair<>(marbles.get(0), marble1Choice));

        state.addMarbleChoice(new Pair<>(marbles.get(1), marble2Choice));

        state.addMarbleChoice(new Pair<>(marbles.get(2), marble3Choice));

        if(marbles.size() == 4){
            state.addMarbleChoice(new Pair<>(marbles.get(3), marble4Choice));
        }

    }

    /**
     * method to use to set the imageView of the selected resources
     */
    private void setSelectedMarbles(){
        chosenMarbles = state.getSelectedMarbles();
        selMarble1.setImage(new Image(chosenMarbles.get(0).toImage()));
        selMarble2.setImage(new Image(chosenMarbles.get(1).toImage()));
        selMarble3.setImage(new Image(chosenMarbles.get(2).toImage()));
        indRes1.setVisible(true);
        indRes2.setVisible(true);
        indRes3.setVisible(true);
        selResources1.setImage(new Image(getConvertedResources(chosenMarbles.get(0).getColorMarble())));
        selResources2.setImage(new Image(getConvertedResources(chosenMarbles.get(1).getColorMarble())));
        selResources3.setImage(new Image(getConvertedResources(chosenMarbles.get(2).getColorMarble())));
        if(chosenMarbles.size()==4) {
            selMarble4.setImage(new Image(chosenMarbles.get(3).toImage()));
            indRes4.setVisible(true);
            selResources4.setImage(new Image(getConvertedResources(chosenMarbles.get(3).getColorMarble())));
        }
        else {
            selMarble4.setImage(null);
            selResources4.setImage(null);
            indRes4.setVisible(false);
        }
    }

    /**
     * method used to manage the cases of destination of the marbles for the red marble and for the white marble
     * @param marble type of marble
     * @return
     */
    private boolean haveToChooseMarbleDestination(ReducedMarble marble){
        if(marble.getColorMarble() == ColorMarble.RED) return false;
        if(marble.getColorMarble() == ColorMarble.WHITE && state.getConvertMarbleActiveEffects().size() == 0) return false;
        else return true;
    }

    /**
     * method used to check that the player has to choose between two resources in the case
     * of white marble due to two active Convert Marble effects
     * @return true if the player has two active convert marble effects, false otherwise
     */
    private boolean haveToChooseConvertMarbleEffect(){
        return state.getConvertMarbleActiveEffects().size() == 2;
    }

    /**
     * method used to set the resources associated with the white marbles based
     * on the type of convert marble owned
     * @param color color of the active convert marble
     * @return
     */
    private String getConvertedResources(ColorMarble color){
        switch (color){
            case RED: return "gui/img/faith.png";
            case BLUE: return ResourceLocator.retrieveResourceTypeImage(ResourceType.shield);
            case GREY: return ResourceLocator.retrieveResourceTypeImage(ResourceType.stone);
            case PURPLE: return ResourceLocator.retrieveResourceTypeImage(ResourceType.servant);
            case YELLOW: return ResourceLocator.retrieveResourceTypeImage(ResourceType.coin);
            case WHITE: {
                if(state.getConvertMarbleActiveEffects().size() == 1) return ResourceLocator.retrieveResourceTypeImage(state.getConvertMarbleActiveEffects().get(0));
                else return "gui/img/marbles/whiteMarble.png";
            }
            default: return "gui/img/marbles/whiteMarble.png"; // Resource not found
        }
    }


    /**
     * method used to close page of Take Resources from Market over the main stage
     * It is performed when closeTakeRes button is pressed
     */
    @FXML
    public void handleCloseChooseAction()
    {
        Stage stage = (Stage) closeTakeRes.getScene().getWindow();
        stage.close();
    }

    /**
     * method used to set images and resources in case row 1 is selected
     */
    @FXML
    public void selectedRow1()
    {
        setVisibleFalse();
        resourcesText.setText("Do you want to get the following resources?");
        yesResources.setVisible(true);
        noResources.setVisible(true);
        light1rows.setVisible(true);
        state.setMarketChoice(MarketChoice.ROW, 1);
        setSelectedMarbles();
    }
    /**
     * method used to set images and resources in case row 2 is selected
     */
    @FXML
    public void selectedRow2()
    {
        setVisibleFalse();
        resourcesText.setText("Do you want to get the following resources?");
        yesResources.setVisible(true);
        noResources.setVisible(true);
        light2rows.setVisible(true);
        state.setMarketChoice(MarketChoice.ROW, 2);
        setSelectedMarbles();
    }
    /**
     * method used to set images and resources in case row 3 is selected
     */
    @FXML
    public void selectedRow3()
    {
        setVisibleFalse();
        resourcesText.setText("Do you want to get the following resources?");
        yesResources.setVisible(true);
        noResources.setVisible(true);
        light3rows.setVisible(true);
        state.setMarketChoice(MarketChoice.ROW, 3);
        setSelectedMarbles();
    }
    /**
     * method used to set images and resources in case column 1 is selected
     */
    @FXML
    public void selectedColumn1()
    {
        setVisibleFalse();
        resourcesText.setText("Do you want to get the following resources?");
        yesResources.setVisible(true);
        noResources.setVisible(true);
        light1columns.setVisible(true);
        state.setMarketChoice(MarketChoice.COLUMN, 1);
        setSelectedMarbles();
    }
    /**
     * method used to set images and resources in case column 2 is selected
     */
    @FXML
    public void selectedColumn2()
    {
        setVisibleFalse();
        resourcesText.setText("Do you want to get the following resources?");
        yesResources.setVisible(true);
        noResources.setVisible(true);
        light2columns.setVisible(true);
        state.setMarketChoice(MarketChoice.COLUMN, 2);
        setSelectedMarbles();
    }
    /**
     * method used to set images and resources in case column 3 is selected
     */
    @FXML
    public void selectedColumn3()
    {
        setVisibleFalse();
        resourcesText.setText("Do you want to get the following resources?");
        yesResources.setVisible(true);
        noResources.setVisible(true);
        light3columns.setVisible(true);

        state.setMarketChoice(MarketChoice.COLUMN, 3);
        setSelectedMarbles();
    }
    /**
     * method used to set images and resources in case column 4 is selected
     */
    @FXML
    public void selectedColumn4()
    {
        setVisibleFalse();
        resourcesText.setText("Do you want to get the following marbles?");
        yesResources.setVisible(true);
        noResources.setVisible(true);
        light4columns.setVisible(true);

        state.setMarketChoice(MarketChoice.COLUMN, 4);
        setSelectedMarbles();
    }

    /**
     * method used to make some elements invisible after choosing the row / column
     */
    public void setVisibleFalse()
    {
        light1columns.setVisible(false);
        light2columns.setVisible(false);
        light3columns.setVisible(false);
        light4columns.setVisible(false);
        light1rows.setVisible(false);
        light2rows.setVisible(false);
        light3rows.setVisible(false);
        resourcesText.setText("");
        yesResources.setVisible(false);
        noResources.setVisible(false);
        selResources1.setImage(null);
        selResources2.setImage(null);
        selResources3.setImage(null);
        selResources4.setImage(null);
        selMarble1.setImage(null);
        selMarble2.setImage(null);
        selMarble3.setImage(null);
        selMarble4.setImage(null);
        indRes1.setVisible(false);
        indRes2.setVisible(false);
        indRes3.setVisible(false);
        indRes4.setVisible(false);
    }

    @FXML
    public void handleNoResources()
    {
        setVisibleFalse();
    }

    /**
     * method used to handle the case in which you confirm that you want
     * to get the resources of the selected row / column
     */
    @FXML public void handleYesResources()
    {
        yesResources.setVisible(false);
        noResources.setVisible(false);
        if(haveToChooseConvertMarbleEffect()){
            revealConvertMarbleChoice();
        }
        resourcesText.setText("Select where to place the resources: ");
        row1.setVisible(false);
        row2.setVisible(false);
        row3.setVisible(false);
        column1.setVisible(false);
        column2.setVisible(false);
        column3.setVisible(false);
        column4.setVisible(false);
        setPositionButton();
        if(chosenMarbles.stream().noneMatch(this::haveToChooseMarbleDestination)){
            okButton.setVisible(true);
        }
    }

    /**
     * method used to reveal the choice of the 2 convert marbles made
     */
    public void revealConvertMarbleChoice(){
        if(chosenMarbles.get(0).getColorMarble() == ColorMarble.WHITE){
            selResources1.setVisible(false);
            convertMarbleButtons1.setVisible(true);
            initializeConvertMarbleButtons(convertMarbleButtons1);
        }
        if(chosenMarbles.get(1).getColorMarble() == ColorMarble.WHITE){
            selResources2.setVisible(false);
            convertMarbleButtons2.setVisible(true);
            initializeConvertMarbleButtons(convertMarbleButtons2);
        }
        if(chosenMarbles.get(2).getColorMarble() == ColorMarble.WHITE){
            selResources3.setVisible(false);
            convertMarbleButtons3.setVisible(true);
            initializeConvertMarbleButtons(convertMarbleButtons3);
        }
        if(chosenMarbles.size()==4 && chosenMarbles.get(3).getColorMarble() == ColorMarble.WHITE){
            selResources4.setVisible(false);
            convertMarbleButtons4.setVisible(true);
            initializeConvertMarbleButtons(convertMarbleButtons4);
        }
    }

    /**
     * method used to set the choice buttons between the two convert marbles
     * based on the available convert marble resources
     * @param target Hbox containing the buttons of convert marble
     */
    public void initializeConvertMarbleButtons(HBox target){
        target.getChildren().get(0).setStyle("-fx-background-image: url(" +
                ResourceLocator.retrieveResourceTypeImage(state.getConvertMarbleActiveEffects().get(0)) + ") ; -fx-background-size: 90% auto");
        target.getChildren().get(1).setStyle("-fx-background-image: url(" +
                ResourceLocator.retrieveResourceTypeImage(state.getConvertMarbleActiveEffects().get(1)) + ") ; -fx-background-size: 90% auto");
    }

    /**
     * method used to set the buttons for choosing the destination
     * of the resources selected in the row / column
     */
    public void setPositionButton()
    {
        if(haveToChooseMarbleDestination(chosenMarbles.get(0))){
            sel1Depot1.setVisible(true);
            sel1Depot2.setVisible(true);
            sel1Depot3.setVisible(true);
            sel1ExtraDepot.setVisible(true);
            discard1.setVisible(true);
        } else marble1Choice = MarbleDestination.NOTNEEDED;

        if(haveToChooseMarbleDestination(chosenMarbles.get(1))){
            sel2Depot1.setVisible(true);
            sel2Depot2.setVisible(true);
            sel2Depot3.setVisible(true);
            sel2ExtraDepot.setVisible(true);
            discard2.setVisible(true);
        } else marble2Choice = MarbleDestination.NOTNEEDED;

        if(haveToChooseMarbleDestination(chosenMarbles.get(2))){
            sel3Depot1.setVisible(true);
            sel3Depot2.setVisible(true);
            sel3Depot3.setVisible(true);
            sel3ExtraDepot.setVisible(true);
            discard3.setVisible(true);
        } else marble3Choice = MarbleDestination.NOTNEEDED;

        if(chosenMarbles.size() == 4 && haveToChooseMarbleDestination(chosenMarbles.get(3))){
            sel4Depot1.setVisible(true);
            sel4Depot2.setVisible(true);
            sel4Depot3.setVisible(true);
            sel4ExtraDepot.setVisible(true);
            discard4.setVisible(true);
        } else marble4Choice = MarbleDestination.NOTNEEDED;

    }

    /**
     * method used to set the imageView of the marbles actually present in the MarketTray
     */
    private void setMarbleImages() {
        ReducedMarketTray marketTray = client.getGame().getMarketTray();
        marble11.setImage(new Image(marketTray.getMarble(1, 1).toImage()));
        marble12.setImage(new Image(marketTray.getMarble(1, 2).toImage()));
        marble13.setImage(new Image(marketTray.getMarble(1, 3).toImage()));
        marble14.setImage(new Image(marketTray.getMarble(1, 4).toImage()));

        marble21.setImage(new Image(marketTray.getMarble(2, 1).toImage()));
        marble22.setImage(new Image(marketTray.getMarble(2, 2).toImage()));
        marble23.setImage(new Image(marketTray.getMarble(2, 3).toImage()));
        marble24.setImage(new Image(marketTray.getMarble(2, 4).toImage()));

        marble31.setImage(new Image(marketTray.getMarble(3, 1).toImage()));
        marble32.setImage(new Image(marketTray.getMarble(3, 2).toImage()));
        marble33.setImage(new Image(marketTray.getMarble(3, 3).toImage()));
        marble34.setImage(new Image(marketTray.getMarble(3, 4).toImage()));

        slidingMarble.setImage(new Image(marketTray.getSlidingMarble().toImage()));
    }

    /**
     * method used to make the positioning confirmation button visible for the resources obtained
     * only after selecting all the destinations for each resource
     */
    private void showOkButton(){
        if(marble1Choice != null && marble2Choice != null && marble3Choice != null && marble4Choice != null){
            okButton.setVisible(true);
        }
        if(haveToChooseConvertMarbleEffect() &&
                state.getAlreadySelectedWhiteEffects().size() != chosenMarbles.stream().filter(x -> x.getColorMarble() == ColorMarble.WHITE).count()){
            okButton.setVisible(false);
        }
    }

    /**
     * method used to manage the choice of convert marble for the first resource
     * @param actionEvent marble to set
     */
    @FXML
    public void handleFirstConvertMarbleChoice(ActionEvent actionEvent){
        HBox pressedHBox = ((HBox)((Button)actionEvent.getSource()).getParent());
        state.addWhiteEffect(1);
        if(pressedHBox.equals(convertMarbleButtons1)){
            convertMarbleButtons1.setVisible(false);
            setupImageViewConvertedResource(selResources1, 0);
        }
        if(pressedHBox.equals(convertMarbleButtons2)){
            convertMarbleButtons2.setVisible(false);
            setupImageViewConvertedResource(selResources2, 0);
        }
        if(pressedHBox.equals(convertMarbleButtons3)){
            convertMarbleButtons3.setVisible(false);
            setupImageViewConvertedResource(selResources3, 0);
        }
        if(pressedHBox.equals(convertMarbleButtons4)){
            convertMarbleButtons4.setVisible(false);
            setupImageViewConvertedResource(selResources4, 0);
        }
        showOkButton();
    }
    /**
     * method used to manage the choice of convert marble for the second resource
     * @param actionEvent marble to set
     */
    @FXML
    public void handleSecondConvertMarbleChoice(ActionEvent actionEvent){
        HBox pressedHBox = ((HBox)((Button)actionEvent.getSource()).getParent());
        state.addWhiteEffect(2);
        if(pressedHBox.equals(convertMarbleButtons1)){
            convertMarbleButtons1.setVisible(false);
            setupImageViewConvertedResource(selResources1, 1);
        }
        if(pressedHBox.equals(convertMarbleButtons2)){
            convertMarbleButtons2.setVisible(false);
            setupImageViewConvertedResource(selResources2, 1);
        }
        if(pressedHBox.equals(convertMarbleButtons3)){
            convertMarbleButtons3.setVisible(false);
            setupImageViewConvertedResource(selResources3, 1);
        }
        if(pressedHBox.equals(convertMarbleButtons4)){
            convertMarbleButtons4.setVisible(false);
            setupImageViewConvertedResource(selResources4, 1);
        }
        showOkButton();
    }

    /**
     * method used to set the imageView of the specific convert marble
     * @param target imageView to set
     * @param index index of convert marble effect
     */
    private void setupImageViewConvertedResource(ImageView target, int index){
        target.setVisible(true);
        target.setImage(new Image(Objects.requireNonNull(ResourceLocator.retrieveResourceTypeImage(state.getConvertMarbleActiveEffects().get(index)))));
    }

    /**
     * method used to manage the choice of depot 1 as the destination of the first resource
     */
    @FXML
    public void selectedDepot1Marble1(){
        pressedButton(sel1Depot1);
        clearButtonSelection(sel1Depot2);
        clearButtonSelection(sel1Depot3);
        clearButtonSelection(sel1ExtraDepot);
        clearButtonSelection(discard1);
        marble1Choice = MarbleDestination.DEPOT1;
        showOkButton();
    }
    /**
     * method used to manage the choice of depot 2 as the destination of the first resource
     */
    @FXML
    public void selectedDepot2Marble1(){
        pressedButton(sel1Depot2);
        clearButtonSelection(sel1Depot1);
        clearButtonSelection(sel1Depot3);
        clearButtonSelection(sel1ExtraDepot);
        clearButtonSelection(discard1);
        marble1Choice = MarbleDestination.DEPOT2;
        showOkButton();
    }
    /**
     * method used to manage the choice of depot 3 as the destination of the first resource
     */
    @FXML
    public void selectedDepot3Marble1(){
        pressedButton(sel1Depot3);
        clearButtonSelection(sel1Depot1);
        clearButtonSelection(sel1Depot2);
        clearButtonSelection(sel1ExtraDepot);
        clearButtonSelection(discard1);
        marble1Choice = MarbleDestination.DEPOT3;
        showOkButton();
    }
    /**
     * method used to manage the choice of the extra depot as the destination of the first resource
     */
    @FXML
    public void selectedExtraDepotMarble1(){
        pressedButton(sel1ExtraDepot);
        clearButtonSelection(sel1Depot1);
        clearButtonSelection(sel1Depot2);
        clearButtonSelection(sel1Depot3);
        clearButtonSelection(discard1);
        marble1Choice = MarbleDestination.EXTRA;
        showOkButton();
    }
    /**
     * method used to manage the choice to discard the first resource
     */
    @FXML
    public void selectedDiscardMarble1(){
        pressedButton(discard1);
        clearButtonSelection(sel1Depot1);
        clearButtonSelection(sel1Depot2);
        clearButtonSelection(sel1Depot3);
        clearButtonSelection(sel1ExtraDepot);
        marble1Choice = MarbleDestination.DISCARD;
        showOkButton();
    }
    /**
     * method used to manage the choice of depot 1 as the destination of the second resource
     */
    @FXML
    public void selectedDepot1Marble2(){
        pressedButton(sel2Depot1);
        clearButtonSelection(sel2Depot2);
        clearButtonSelection(sel2Depot3);
        clearButtonSelection(sel2ExtraDepot);
        clearButtonSelection(discard2);
        marble2Choice = MarbleDestination.DEPOT1;
        showOkButton();
    }
    /**
     * method used to manage the choice of depot 2 as the destination of the second resource
     */
    @FXML
    public void selectedDepot2Marble2(){
        pressedButton(sel2Depot2);
        clearButtonSelection(sel2Depot1);
        clearButtonSelection(sel2Depot3);
        clearButtonSelection(sel2ExtraDepot);
        clearButtonSelection(discard2);
        marble2Choice = MarbleDestination.DEPOT2;
        showOkButton();
    }
    /**
     * method used to manage the choice of depot 3 as the destination of the second resource
     */
    @FXML
    public void selectedDepot3Marble2(){
        pressedButton(sel2Depot3);
        clearButtonSelection(sel2Depot1);
        clearButtonSelection(sel2Depot2);
        clearButtonSelection(sel2ExtraDepot);
        clearButtonSelection(discard2);
        marble2Choice = MarbleDestination.DEPOT3;
        showOkButton();
    }
    /**
     * method used to manage the choice of the extra depot as the destination of the second resource
     */
    @FXML
    public void selectedExtraDepotMarble2(){
        pressedButton(sel2ExtraDepot);
        clearButtonSelection(sel2Depot1);
        clearButtonSelection(sel2Depot2);
        clearButtonSelection(sel2Depot3);
        clearButtonSelection(discard2);
        marble2Choice = MarbleDestination.EXTRA;
        showOkButton();
    }
    /**
     * method used to manage the choice to discard the second resource
     */
    @FXML
    public void selectedDiscardMarble2(){
        pressedButton(discard2);
        clearButtonSelection(sel2Depot1);
        clearButtonSelection(sel2Depot2);
        clearButtonSelection(sel2Depot3);
        clearButtonSelection(sel2ExtraDepot);
        marble2Choice = MarbleDestination.DISCARD;
        showOkButton();
    }
    /**
     * method used to manage the choice of depot 1 as the destination of the third resource
     */
    @FXML
    public void selectedDepot1Marble3(){
        pressedButton(sel3Depot1);
        clearButtonSelection(sel3Depot2);
        clearButtonSelection(sel3Depot3);
        clearButtonSelection(sel3ExtraDepot);
        clearButtonSelection(discard3);
        marble3Choice = MarbleDestination.DEPOT1;
        showOkButton();
    }
    /**
     * method used to manage the choice of depot 2 as the destination of the third resource
     */
    @FXML
    public void selectedDepot2Marble3(){
        pressedButton(sel3Depot2);
        clearButtonSelection(sel3Depot1);
        clearButtonSelection(sel3Depot3);
        clearButtonSelection(sel3ExtraDepot);
        clearButtonSelection(discard3);
        marble3Choice = MarbleDestination.DEPOT2;
        showOkButton();
    }
    /**
     * method used to manage the choice of depot 3 as the destination of the third resource
     */
    @FXML
    public void selectedDepot3Marble3(){
        pressedButton(sel3Depot3);
        clearButtonSelection(sel3Depot1);
        clearButtonSelection(sel3Depot2);
        clearButtonSelection(sel3ExtraDepot);
        clearButtonSelection(discard3);
        marble3Choice = MarbleDestination.DEPOT3;
        showOkButton();
    }
    /**
     * method used to manage the choice of the extra depot as the destination of the third resource
     */
    @FXML
    public void selectedExtraDepotMarble3(){
        pressedButton(sel3ExtraDepot);
        clearButtonSelection(sel3Depot1);
        clearButtonSelection(sel3Depot2);
        clearButtonSelection(sel3Depot3);
        clearButtonSelection(discard3);
        marble3Choice = MarbleDestination.EXTRA;
        showOkButton();
    }
    /**
     * method used to manage the choice to discard the third resource
     */
    @FXML
    public void selectedDiscardMarble3(){
        pressedButton(discard3);
        clearButtonSelection(sel3Depot1);
        clearButtonSelection(sel3Depot2);
        clearButtonSelection(sel3Depot3);
        clearButtonSelection(sel3ExtraDepot);
        marble3Choice = MarbleDestination.DISCARD;
        showOkButton();
    }
    /**
     * method used to manage the choice of depot 1 as the destination of the fourt resource
     */
    @FXML
    public void selectedDepot1Marble4(){
        pressedButton(sel4Depot1);
        clearButtonSelection(sel4Depot2);
        clearButtonSelection(sel4Depot3);
        clearButtonSelection(sel4ExtraDepot);
        clearButtonSelection(discard4);
        marble4Choice = MarbleDestination.DEPOT1;
        showOkButton();
    }
    /**
     * method used to manage the choice of depot 2 as the destination of the fourt resource
     */
    @FXML
    public void selectedDepot2Marble4(){
        pressedButton(sel4Depot2);
        clearButtonSelection(sel4Depot1);
        clearButtonSelection(sel4Depot3);
        clearButtonSelection(sel4ExtraDepot);
        clearButtonSelection(discard4);
        marble4Choice = MarbleDestination.DEPOT2;
        showOkButton();
    }
    /**
     * method used to manage the choice of depot 3 as the destination of the fourt resource
     */
    @FXML
    public void selectedDepot3Marble4(){
        pressedButton(sel4Depot3);
        clearButtonSelection(sel4Depot1);
        clearButtonSelection(sel4Depot2);
        clearButtonSelection(sel4ExtraDepot);
        clearButtonSelection(discard4);
        marble4Choice = MarbleDestination.DEPOT3;
        showOkButton();
    }
    /**
     * method used to manage the choice of the extra depot as the destination of the fourth resource
     */
    @FXML
    public void selectedExtraDepotMarble4(){
        pressedButton(sel4ExtraDepot);
        clearButtonSelection(sel4Depot1);
        clearButtonSelection(sel4Depot2);
        clearButtonSelection(sel4Depot3);
        clearButtonSelection(discard4);
        marble4Choice = MarbleDestination.EXTRA;
        showOkButton();
    }
    /**
     * method used to manage the choice to discard the fourth resource
     */
    @FXML
    public void selectedDiscardMarble4(){
        pressedButton(discard4);
        clearButtonSelection(sel4Depot1);
        clearButtonSelection(sel4Depot2);
        clearButtonSelection(sel4Depot3);
        clearButtonSelection(sel4ExtraDepot);
        marble4Choice = MarbleDestination.DISCARD;
        showOkButton();
    }

    /**
     * method used to manage the confirmation of the action carried out
     * It is performed when okButton is pressed
     */
    @FXML
    public void handleOkChoice()
    {
        parseWarehouseChoices();
        state.manageUserInteraction();
        handleCloseChooseAction();
    }

    /**
     * method used to set the CSS of the generic button pressed
     * @param target button to set
     */
    private void pressedButton(Button target){
        target.setStyle("-fx-background-size: 75% auto;");
    }
    /**
     * method used to clear the CSS of the generic selected button
     * @param target button to set
     */
    private void clearButtonSelection(Button target){
        target.setStyle("-fx-background-size: 100% auto");
    }

}
