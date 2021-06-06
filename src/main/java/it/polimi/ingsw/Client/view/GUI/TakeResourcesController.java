package it.polimi.ingsw.Client.view.GUI;

import it.polimi.ingsw.Client.clientstates.gui.TakeResourcesFromMarketGUI;
import it.polimi.ingsw.Client.reducedmodel.ReducedMarble;
import it.polimi.ingsw.Client.reducedmodel.ReducedMarketTray;
import it.polimi.ingsw.Utils.MarbleDestination;
import it.polimi.ingsw.Utils.MarketChoice;
import it.polimi.ingsw.Utils.Pair;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;

public class TakeResourcesController extends Controller{
    @FXML
    public AnchorPane anchorBuyDevCard;

    @FXML
    public TextField takeResourcesText;

    @FXML
    public Text resourcesText;

    @FXML
    public Button closeBuyDevCard;

    @FXML
    public ImageView light1rows,light2rows,light3rows;
    @FXML
    public ImageView light1columns,light2columns,light3columns,light4columns;

    @FXML
    public ImageView marble11,marble12,marble13,marble14,
                        marble21,marble22,marble23,marble24
                        ,marble31,marble32,marble33,marble34,slidingMarble;

    @FXML
    public ImageView selResources1,selResources2,selResources3,selResources4;

    @FXML
    public Button row1,row2,row3;
    @FXML
    public Button column1,column2,column3,column4;

    @FXML
    public Button yesResources,noResources,okButton;


    @FXML
    public Button sel1Depot1,sel1Depot2,sel1Depot3,
                sel2Depot1,sel2Depot2,sel2Depot3,
                sel3Depot1,sel3Depot2,sel3Depot3,
                sel4Depot1,sel4Depot2,sel4Depot3,
                sel1ExtraDepot,sel2ExtraDepot,sel3ExtraDepot,sel4ExtraDepot,
                discard1,discard2,discard3,discard4;

    private boolean depot1Marble1, depot2Marble1, depot3Marble1, extraMarble1, discardMarble1,
                depot1Marble2, depot2Marble2, depot3Marble2, extraMarble2, discardMarble2,
                depot1Marble3, depot2Marble3, depot3Marble3, extraMarble3, discardMarble3,
                depot1Marble4, depot2Marble4, depot3Marble4, extraMarble4, discardMarble4;

    private TakeResourcesFromMarketGUI state;

    private List<ReducedMarble> chosenMarbles;

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

    private void parseWarehouseChoices(){
        List<ReducedMarble> marbles = state.getSelectedMarbles();

        if(depot1Marble1){
            state.addMarbleChoice(new Pair<>(marbles.get(0), MarbleDestination.DEPOT1));
        } else if(depot2Marble1) {
            state.addMarbleChoice(new Pair<>(marbles.get(0), MarbleDestination.DEPOT2));
        } else if(depot3Marble1) {
            state.addMarbleChoice(new Pair<>(marbles.get(0), MarbleDestination.DEPOT3));
        } else if(extraMarble1) {
            state.addMarbleChoice(new Pair<>(marbles.get(0), MarbleDestination.EXTRA));
        } else if(discardMarble1) {
            state.addMarbleChoice(new Pair<>(marbles.get(0), MarbleDestination.DISCARD));
        }

        if(depot1Marble2){
            state.addMarbleChoice(new Pair<>(marbles.get(1), MarbleDestination.DEPOT1));
        } else if(depot2Marble2) {
            state.addMarbleChoice(new Pair<>(marbles.get(1), MarbleDestination.DEPOT2));
        } else if(depot3Marble2) {
            state.addMarbleChoice(new Pair<>(marbles.get(1), MarbleDestination.DEPOT3));
        } else if(extraMarble2) {
            state.addMarbleChoice(new Pair<>(marbles.get(1), MarbleDestination.EXTRA));
        } else if(discardMarble2) {
            state.addMarbleChoice(new Pair<>(marbles.get(1), MarbleDestination.DISCARD));
        }

        if(depot1Marble3){
            state.addMarbleChoice(new Pair<>(marbles.get(2), MarbleDestination.DEPOT1));
        } else if(depot2Marble3) {
            state.addMarbleChoice(new Pair<>(marbles.get(2), MarbleDestination.DEPOT2));
        } else if(depot3Marble3) {
            state.addMarbleChoice(new Pair<>(marbles.get(2), MarbleDestination.DEPOT3));
        } else if(extraMarble3) {
            state.addMarbleChoice(new Pair<>(marbles.get(2), MarbleDestination.EXTRA));
        } else if(discardMarble3) {
            state.addMarbleChoice(new Pair<>(marbles.get(2), MarbleDestination.DISCARD));
        }

        if(depot1Marble4){
            state.addMarbleChoice(new Pair<>(marbles.get(3), MarbleDestination.DEPOT1));
        } else if(depot2Marble4) {
            state.addMarbleChoice(new Pair<>(marbles.get(3), MarbleDestination.DEPOT2));
        } else if(depot3Marble4) {
            state.addMarbleChoice(new Pair<>(marbles.get(3), MarbleDestination.DEPOT3));
        } else if(extraMarble4) {
            state.addMarbleChoice(new Pair<>(marbles.get(3), MarbleDestination.EXTRA));
        } else if(discardMarble4) {
            state.addMarbleChoice(new Pair<>(marbles.get(3), MarbleDestination.DISCARD));
        }
    }

    private void setSelectedMarbles(){
        chosenMarbles = state.getSelectedMarbles();
        selResources1.setImage(new Image(chosenMarbles.get(0).toImage()));
        selResources2.setImage(new Image(chosenMarbles.get(1).toImage()));
        selResources3.setImage(new Image(chosenMarbles.get(2).toImage()));
        if(chosenMarbles.size()==4) selResources4.setImage(new Image(chosenMarbles.get(3).toImage()));
    }

    @FXML
    public void handleCloseChooseAction()
    {
        Stage stage = (Stage) closeBuyDevCard.getScene().getWindow();
        stage.close();
    }

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
    }
    @FXML
    public void handleNoResources()
    {
        setVisibleFalse();
    }

    @FXML public void handleYesResources()
    {
        yesResources.setVisible(false);
        noResources.setVisible(false);
        resourcesText.setText("Select where to place the resources: ");
        row1.setVisible(false);
        row2.setVisible(false);
        row3.setVisible(false);
        column1.setVisible(false);
        column2.setVisible(false);
        column3.setVisible(false);
        column4.setVisible(false);
        setPositionButton();
    }
    public void setPositionButton()
    {
        sel1Depot1.setVisible(true);
        sel1Depot2.setVisible(true);
        sel1Depot3.setVisible(true);
        sel2Depot1.setVisible(true);
        sel2Depot2.setVisible(true);
        sel2Depot3.setVisible(true);
        sel3Depot1.setVisible(true);
        sel3Depot2.setVisible(true);
        sel3Depot3.setVisible(true);
        sel4Depot1.setVisible(true);
        sel4Depot2.setVisible(true);
        sel4Depot3.setVisible(true);
        sel1ExtraDepot.setVisible(true);
        sel2ExtraDepot.setVisible(true);
        sel3ExtraDepot.setVisible(true);
        sel4ExtraDepot.setVisible(true);
        discard1.setVisible(true);
        discard2.setVisible(true);
        discard3.setVisible(true);
        discard4.setVisible(true);
        okButton.setVisible(true);
    }

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

    @FXML
    public void selectedDepot1Marble1(){
        pressedButton(sel1Depot1);
        clearButtonSelection(sel1Depot2);
        clearButtonSelection(sel1Depot3);
        clearButtonSelection(sel1ExtraDepot);
        clearButtonSelection(discard1);
        depot1Marble1 = true;
        depot2Marble1 = false;
        depot3Marble1 = false;
        extraMarble1 = false;
        discardMarble1 = false;
    }

    @FXML
    public void selectedDepot2Marble1(){
        pressedButton(sel1Depot2);
        clearButtonSelection(sel1Depot1);
        clearButtonSelection(sel1Depot3);
        clearButtonSelection(sel1ExtraDepot);
        clearButtonSelection(discard1);
        depot1Marble1 = false;
        depot2Marble1 = true;
        depot3Marble1 = false;
        extraMarble1 = false;
        discardMarble1 = false;
    }

    @FXML
    public void selectedDepot3Marble1(){
        pressedButton(sel1Depot3);
        clearButtonSelection(sel1Depot1);
        clearButtonSelection(sel1Depot2);
        clearButtonSelection(sel1ExtraDepot);
        clearButtonSelection(discard1);
        depot1Marble1 = false;
        depot2Marble1 = false;
        depot3Marble1 = true;
        extraMarble1 = false;
        discardMarble1 = false;
    }

    @FXML
    public void selectedExtraDepotMarble1(){
        pressedButton(sel1ExtraDepot);
        clearButtonSelection(sel1Depot1);
        clearButtonSelection(sel1Depot2);
        clearButtonSelection(sel1Depot3);
        clearButtonSelection(discard1);
        depot1Marble1 = false;
        depot2Marble1 = false;
        depot3Marble1 = false;
        extraMarble1 = true;
        discardMarble1 = false;
    }

    @FXML
    public void selectedDiscardMarble1(){
        pressedButton(discard1);
        clearButtonSelection(sel1Depot1);
        clearButtonSelection(sel1Depot2);
        clearButtonSelection(sel1Depot3);
        clearButtonSelection(sel1ExtraDepot);
        depot1Marble1 = false;
        depot2Marble1 = false;
        depot3Marble1 = false;
        extraMarble1 = false;
        discardMarble1 = true;
    }

    @FXML
    public void selectedDepot1Marble2(){
        pressedButton(sel2Depot1);
        clearButtonSelection(sel2Depot2);
        clearButtonSelection(sel2Depot3);
        clearButtonSelection(sel2ExtraDepot);
        clearButtonSelection(discard2);
        depot1Marble2 = true;
        depot2Marble2 = false;
        depot3Marble2 = false;
        extraMarble2 = false;
        discardMarble2 = false;
    }

    @FXML
    public void selectedDepot2Marble2(){
        pressedButton(sel2Depot2);
        clearButtonSelection(sel2Depot1);
        clearButtonSelection(sel2Depot3);
        clearButtonSelection(sel2ExtraDepot);
        clearButtonSelection(discard2);
        depot1Marble2 = false;
        depot2Marble2 = true;
        depot3Marble2 = false;
        extraMarble2 = false;
        discardMarble2 = false;
    }

    @FXML
    public void selectedDepot3Marble2(){
        pressedButton(sel2Depot3);
        clearButtonSelection(sel2Depot1);
        clearButtonSelection(sel2Depot2);
        clearButtonSelection(sel2ExtraDepot);
        clearButtonSelection(discard2);
        depot1Marble2 = false;
        depot2Marble2 = false;
        depot3Marble2 = true;
        extraMarble2 = false;
        discardMarble2 = false;
    }

    @FXML
    public void selectedExtraDepotMarble2(){
        pressedButton(sel2ExtraDepot);
        clearButtonSelection(sel2Depot1);
        clearButtonSelection(sel2Depot2);
        clearButtonSelection(sel2Depot3);
        clearButtonSelection(discard2);
        depot1Marble2 = false;
        depot2Marble2 = false;
        depot3Marble2 = false;
        extraMarble2 = true;
        discardMarble2 = false;
    }

    @FXML
    public void selectedDiscardMarble2(){
        pressedButton(discard2);
        clearButtonSelection(sel2Depot1);
        clearButtonSelection(sel2Depot2);
        clearButtonSelection(sel2Depot3);
        clearButtonSelection(sel2ExtraDepot);
        depot1Marble2 = false;
        depot2Marble2 = false;
        depot3Marble2 = false;
        extraMarble2 = false;
        discardMarble2 = true;
    }

    @FXML
    public void selectedDepot1Marble3(){
        pressedButton(sel3Depot1);
        clearButtonSelection(sel3Depot2);
        clearButtonSelection(sel3Depot3);
        clearButtonSelection(sel3ExtraDepot);
        clearButtonSelection(discard3);
        depot1Marble3 = true;
        depot2Marble3 = false;
        depot3Marble3 = false;
        extraMarble3 = false;
        discardMarble3 = false;
    }

    @FXML
    public void selectedDepot2Marble3(){
        pressedButton(sel3Depot2);
        clearButtonSelection(sel3Depot1);
        clearButtonSelection(sel3Depot3);
        clearButtonSelection(sel3ExtraDepot);
        clearButtonSelection(discard3);
        depot1Marble3 = false;
        depot2Marble3 = true;
        depot3Marble3 = false;
        extraMarble3 = false;
        discardMarble3 = false;
    }

    @FXML
    public void selectedDepot3Marble3(){
        pressedButton(sel3Depot3);
        clearButtonSelection(sel3Depot1);
        clearButtonSelection(sel3Depot2);
        clearButtonSelection(sel3ExtraDepot);
        clearButtonSelection(discard3);
        depot1Marble3 = false;
        depot2Marble3 = false;
        depot3Marble3 = true;
        extraMarble3 = false;
        discardMarble3 = false;
    }

    @FXML
    public void selectedExtraDepotMarble3(){
        pressedButton(sel3ExtraDepot);
        clearButtonSelection(sel3Depot1);
        clearButtonSelection(sel3Depot2);
        clearButtonSelection(sel3Depot3);
        clearButtonSelection(discard3);
        depot1Marble3 = false;
        depot2Marble3 = false;
        depot3Marble3 = false;
        extraMarble3 = true;
        discardMarble3 = false;
    }

    @FXML
    public void selectedDiscardMarble3(){
        pressedButton(discard3);
        clearButtonSelection(sel3Depot1);
        clearButtonSelection(sel3Depot2);
        clearButtonSelection(sel3Depot3);
        clearButtonSelection(sel3ExtraDepot);
        depot1Marble3 = false;
        depot2Marble3 = false;
        depot3Marble3 = false;
        extraMarble3 = false;
        discardMarble3 = true;
    }

    @FXML
    public void selectedDepot1Marble4(){
        pressedButton(sel4Depot1);
        clearButtonSelection(sel4Depot2);
        clearButtonSelection(sel4Depot3);
        clearButtonSelection(sel4ExtraDepot);
        clearButtonSelection(discard4);
        depot1Marble4 = true;
        depot2Marble4 = false;
        depot3Marble4 = false;
        extraMarble4 = false;
        discardMarble4 = false;
    }

    @FXML
    public void selectedDepot2Marble4(){
        pressedButton(sel4Depot2);
        clearButtonSelection(sel4Depot1);
        clearButtonSelection(sel4Depot3);
        clearButtonSelection(sel4ExtraDepot);
        clearButtonSelection(discard4);
        depot1Marble4 = false;
        depot2Marble4 = true;
        depot3Marble4 = false;
        extraMarble4 = false;
        discardMarble4 = false;
    }

    @FXML
    public void selectedDepot3Marble4(){
        pressedButton(sel4Depot3);
        clearButtonSelection(sel4Depot1);
        clearButtonSelection(sel4Depot2);
        clearButtonSelection(sel4ExtraDepot);
        clearButtonSelection(discard4);
        depot1Marble4 = false;
        depot2Marble4 = false;
        depot3Marble4 = true;
        extraMarble4 = false;
        discardMarble4 = false;
    }

    @FXML
    public void selectedExtraDepotMarble4(){
        pressedButton(sel4ExtraDepot);
        clearButtonSelection(sel4Depot1);
        clearButtonSelection(sel4Depot2);
        clearButtonSelection(sel4Depot3);
        clearButtonSelection(discard4);
        depot1Marble4 = false;
        depot2Marble4 = false;
        depot3Marble4 = false;
        extraMarble4 = true;
        discardMarble4 = false;
    }

    @FXML
    public void selectedDiscardMarble4(){
        pressedButton(discard4);
        clearButtonSelection(sel4Depot1);
        clearButtonSelection(sel4Depot2);
        clearButtonSelection(sel4Depot3);
        clearButtonSelection(sel4ExtraDepot);
        depot1Marble4 = false;
        depot2Marble4 = false;
        depot3Marble4 = false;
        extraMarble4 = false;
        discardMarble4 = true;
    }

    @FXML
    public void handleOkChoice()
    {

    }

    private void pressedButton(Button target){
        target.setStyle("-fx-background-size: 75% auto;");
    }

    private void clearButtonSelection(Button target){
        target.setStyle("-fx-background-size: 100% auto");
    }

}
