package it.polimi.ingsw.Client.view.GUI;

import it.polimi.ingsw.Client.clientstates.gui.ActivateProductionGUI;
import it.polimi.ingsw.Commons.*;
import it.polimi.ingsw.Network.Server;
import it.polimi.ingsw.Utils.Pair;
import it.polimi.ingsw.Utils.ResourceLocator;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ActivateProductionController extends Controller implements PaymentController {

    @FXML
    public AnchorPane anchorActivateProd;

    @FXML
    public Text activateProdText;

    @FXML
    public Button closeActivateProd;

    @FXML
    public Button slot1,slot2,slot3,basicProduction, firstExtraSlot, secondExtraSlot;

    @FXML
    public Button plusFirstResourceDepot, plusSecondResourceDepot, plusThirdResourceDepot, plusFourthResourceDepot;
    public Button plusFirstResourceStrongbox, plusSecondResourceStrongbox, plusThirdResourceStrongbox, plusFourthResourceStrongbox;
    public Button plusFirstResourceExtra, plusSecondResourceExtra, plusThirdResourceExtra, plusFourthResourceExtra;


    public Button minusFirstResourceDepot, minusSecondResourceDepot, minusThirdResourceDepot, minusFourthResourceDepot;
    public Button minusFirstResourceStrongbox, minusSecondResourceStrongbox, minusThirdResourceStrongbox, minusFourthResourceStrongbox;
    public Button minusFirstResourceExtra, minusSecondResourceExtra, minusThirdResourceExtra, minusFourthResourceExtra;

    public Button nextStep;

    public ImageView slotsBackground;

    @FXML
    public TextField firstDepotOcc, secondDepotOcc, thirdDepotOcc, fourthDepotOcc;
    public TextField firstStrongboxOcc, secondStrongboxOcc, thirdStrongboxOcc, fourthStrongboxOcc;
    public TextField firstExtraOcc, secondExtraOcc,thirdExtraOcc,fourthExtraOcc;



    public Button concludeAction;

    private ActivateProductionGUI activateProductionAction;
    private List<Pair<ResourceType,Integer>> requiredResources;


    public VBox input, inputSecond, output;
    public Button coinInput, servantInput, shieldInput, stoneInput, coinOutput, servantOutput, shieldOutput, stoneOutput;
    public Button coinInputSecond, servantInputSecond, shieldInputSecond, stoneInputSecond;
    public Button clear;
    public Button toPayment, extraProduction;
    public Label firstInputLabel, secondInputLabel, outputLabel;


    int firstCurrentValueDepot = 0;
    int secondCurrentValueDepot = 0;
    int thirdCurrentValueDepot = 0;


    int firstCurrentValueStrongbox = 0;
    int secondCurrentValueStrongbox = 0;
    int thirdCurrentValueStrongbox = 0;

    int firstCurrentValueExtra = 0;
    int secondCurrentValueExtra = 0;
    int thirdCurrentValueExtra = 0;

    int fourthCurrentValueDepot = 0;
    int fourthCurrentValueStrongbox = 0;
    int fourthCurrentValueExtra = 0;


    public HBox extraProductions;
    public Text firstResourceDepot;
    public Text secondResourceDepot;
    public Text thirdResourceDepot;
    public Text fourthResourceDepot;
    public Text firstResourceStrongbox;
    public Text secondResourceStrongbox;
    public Text thirdResourceStrongbox;
    public Text fourthResourceStrongbox;
    public Text firstResourceExtra;
    public Text secondResourceExtra;
    public Text thirdResourceExtra;
    public Text fourthResourceExtra;


    @FXML
    @Override
    public void initialize() {
        this.client = GUIApp.client;
        activateProductionAction = new ActivateProductionGUI(client);
        anchorActivateProd.setBackground(new Background(new BackgroundImage(new Image("/gui/img/exit_tab.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false))));
        setFont(activateProdText,30);
        activateProdText.setStyle("-fx-text-fill: rgb(230,230,180);");
        setFont(nextStep,25);
        nextStep.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(toPayment,25);
        clear.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setText();
        initializeSlotsImages();
        initializeInputOutput();
    }


    private void setText() {
        setFont(firstDepotOcc, 20);
        setFont(secondDepotOcc, 20);
        setFont(thirdDepotOcc, 20);
        setFont(fourthDepotOcc, 20);
        setFont(firstStrongboxOcc, 20);
        setFont(secondStrongboxOcc, 20);
        setFont(thirdStrongboxOcc, 20);
        setFont(fourthStrongboxOcc, 20);
        setFont(firstExtraOcc, 20);
        setFont(secondExtraOcc, 20);
        setFont(thirdExtraOcc, 20);
        setFont(fourthExtraOcc, 20);
        setFont(activateProdText, 30);
        setFont(clear, 25);
        setFont(firstInputLabel, 30);
        setFont(secondInputLabel, 30);
        setFont(outputLabel, 30);
    }

    private void initializeSlotsImages()
    {
        if(!client.getGame().getPlayer(client.getUser()).getPersonalBoard().isEmptySlot(0)) {
            slot1.setStyle("-fx-background-image: url(" +
                    client.getGame().getPlayer(client.getUser()).getPersonalBoard().peekTopCardInSlot(0).toImage() + ")");
        }
        if(!client.getGame().getPlayer(client.getUser()).getPersonalBoard().isEmptySlot(1)) {
            slot2.setStyle("-fx-background-image: url(" +
                    client.getGame().getPlayer(client.getUser()).getPersonalBoard().peekTopCardInSlot(1).toImage() + ")");
        }
        if(!client.getGame().getPlayer(client.getUser()).getPersonalBoard().isEmptySlot(2)) {
            slot3.setStyle("-fx-background-image: url(" +
                    client.getGame().getPlayer(client.getUser()).getPersonalBoard().peekTopCardInSlot(2).toImage() + ")");
        }
        List<LeaderEffect> extraProduction = activateProductionAction.listExtraProductionEffect();
        for(int i = 0; i < extraProduction.size(); i++) {
            if(i == 0) {
                firstExtraSlot.setStyle("-fx-background-image: url("+extraProduction.get(i).toImage()+")");
                firstExtraSlot.setVisible(true);
            }
            if(i == 1) {
                secondExtraSlot.setStyle("-fx-background-image: url("+extraProduction.get(i).toImage()+")");
                secondExtraSlot.setVisible(true);
            }
        }
    }

    private void initializeInputOutput() {
        coinInput.setStyle("-fx-background-image: url("+ResourceLocator.retrieveResourceTypeImage(ResourceType.coin)+")");
        servantInput.setStyle("-fx-background-image: url("+ResourceLocator.retrieveResourceTypeImage(ResourceType.servant)+")");
        shieldInput.setStyle("-fx-background-image: url("+ResourceLocator.retrieveResourceTypeImage(ResourceType.shield)+")");
        stoneInput.setStyle("-fx-background-image: url("+ResourceLocator.retrieveResourceTypeImage(ResourceType.stone)+")");
        coinOutput.setStyle("-fx-background-image: url("+ResourceLocator.retrieveResourceTypeImage(ResourceType.coin)+")");
        servantOutput.setStyle("-fx-background-image: url("+ResourceLocator.retrieveResourceTypeImage(ResourceType.servant)+")");
        shieldOutput.setStyle("-fx-background-image: url("+ResourceLocator.retrieveResourceTypeImage(ResourceType.shield)+")");
        stoneOutput.setStyle("-fx-background-image: url("+ResourceLocator.retrieveResourceTypeImage(ResourceType.stone)+")");

    }

    private void nextButton() {
        if(activateProductionAction.isBasicProduction()){
            if(activateProductionAction.isBasicSettingDone()) {
                //TODO da cambiare con l'aggiunta delle extra production
                toPayment.setVisible(true);
            }
            else toPayment.setVisible(false);
        }
    }

    @FXML
    public void handleBasicSelection() {
        activateProductionAction.setBasicSlot();
        buttonSelection(basicProduction);
        nextStep.setVisible(true);
    }


    @FXML
    public void clearSelection() {
        deselect(input);
        deselect(inputSecond);
        deselect(output);
        activateProductionAction.clearBasicChoices();
    }

    @FXML
    public void coinInputChosen() {
        deselect(input);
        buttonSelection(coinInput);
        activateProductionAction.setInput(1,ResourceType.coin);
        nextButton();
    }

    @FXML
    public void coinInputSecondChosen() {
        deselect(inputSecond);
        buttonSelection(coinInputSecond);
        activateProductionAction.setInput(2, ResourceType.coin);
        nextButton();
    }

    @FXML
    public void servantInputChosen() {
        deselect(input);
        buttonSelection(servantInput);
        activateProductionAction.setInput(1, ResourceType.servant);
        nextButton();
    }

    @FXML
    public void servantInputSecondChosen() {
        deselect(inputSecond);
        buttonSelection(servantInputSecond);
        activateProductionAction.setInput(2, ResourceType.servant);
        nextButton();
    }

    @FXML
    public void shieldInputChosen() {
        deselect(input);
        buttonSelection(shieldInput);
        activateProductionAction.setInput(1, ResourceType.shield);
        nextButton();
    }

    @FXML
    public void shieldInputSecondChosen() {
        deselect(inputSecond);
        buttonSelection(shieldInputSecond);
        activateProductionAction.setInput(2, ResourceType.shield);
        nextButton();
    }

    @FXML
    public void stoneInputChosen() {
        deselect(input);
        buttonSelection(stoneInput);
        activateProductionAction.setInput(1, ResourceType.stone);
        nextButton();
    }

    @FXML
    public void stoneInputSecondChosen(){
        deselect(inputSecond);
        buttonSelection(stoneInputSecond);
        activateProductionAction.setInput(2, ResourceType.stone);
        nextButton();
    }

    @FXML
    public void coinOutputChosen() {
        deselect(output);
        buttonSelection(coinOutput);
        activateProductionAction.setOutput("normal",1,ResourceType.coin);
        nextButton();
    }

    @FXML
    public void servantOutputChosen(){
        deselect(output);
        buttonSelection(servantOutput);
        activateProductionAction.setOutput("normal", 1,ResourceType.servant);
        nextButton();
    }
    @FXML
    public void shieldOutputChosen() {
        deselect(output);
        buttonSelection(shieldOutput);
        activateProductionAction.setOutput("normal", 1,ResourceType.shield);
        nextButton();
    }
    @FXML
    public void stoneOutputChosen() {
        deselect(output);
        buttonSelection(stoneOutput);
        activateProductionAction.setOutput("normal", 1, ResourceType.stone);
        nextButton();
    }





    @FXML
    public void handleFirstSlotSelection() {
        if(activateProductionAction.canActivateSlot(0)) {
            buttonSelection(slot1);
            activateProductionAction.setSlot(0,true);
            nextStep.setVisible(true);
        } else {
            showErrorInSlotsSelection();
        }
    }

    @FXML
    public void handleSecondSlotSelection() {
        if(activateProductionAction.canActivateSlot(1)) {
            buttonSelection(slot2);
            activateProductionAction.setSlot(1,true);
            nextStep.setVisible(true);
        } else {
            showErrorInSlotsSelection();
        }
    }

    @FXML
    public void handleThirdSlotSelection() {
        if(activateProductionAction.canActivateSlot(2)) {
            buttonSelection(slot3);
            activateProductionAction.setSlot(2, true);
            nextStep.setVisible(true);
        } else {
            showErrorInSlotsSelection();
        }
    }

    private void showErrorInSlotsSelection() {
        setActivateProdText("You can't activate this production because the slot is empty");
    }

    private void setActivateProdText(String text) {
        activateProdText.setText(text);
        activateProdText.setVisible(true);
    }


    @FXML
    public void concludeProductionsSelection() {
        cleanPane();
        Map<ResourceType, Integer> inputResources = activateProductionAction.calculateInputResources();
        requiredResources = inputResources.entrySet().stream().
                map(x -> new Pair<>(x.getKey(),x.getValue())).collect(Collectors.toList());
        setActivateProdText("It's time to choose from where pick resources needed for production");
        showRequiredResources(requiredResources);
        /*if(activateProductionAction.checkRequiredResources()) {
            nextStep.setVisible(false);
            cleanPane();
            showRequiredResources(requiredResources);
        } else {

        }*/
    }


    public void cleanPane() {
        plusFirstResourceDepot.setVisible(false);
        plusFirstResourceStrongbox.setVisible(false);
        minusFirstResourceDepot.setVisible(false);
        minusFirstResourceStrongbox.setVisible(false);
        firstDepotOcc.setVisible(false);
        firstStrongboxOcc.setVisible(false);
        plusFirstResourceExtra.setVisible(false);
        minusFirstResourceExtra.setVisible(false);
        firstExtraOcc.setVisible(false);


        plusSecondResourceDepot.setVisible(false);
        plusSecondResourceStrongbox.setVisible(false);
        minusSecondResourceDepot.setVisible(false);
        minusSecondResourceStrongbox.setVisible(false);
        secondDepotOcc.setVisible(false);
        secondStrongboxOcc.setVisible(false);
        plusSecondResourceExtra.setVisible(false);
        minusSecondResourceExtra.setVisible(false);
        secondExtraOcc.setVisible(false);


        plusThirdResourceDepot.setVisible(false);
        plusThirdResourceStrongbox.setVisible(false);
        minusThirdResourceDepot.setVisible(false);
        minusThirdResourceStrongbox.setVisible(false);
        thirdDepotOcc.setVisible(false);
        thirdStrongboxOcc.setVisible(false);
        plusThirdResourceExtra.setVisible(false);
        minusThirdResourceExtra.setVisible(false);
        thirdExtraOcc.setVisible(false);


        plusFourthResourceDepot.setVisible(false);
        plusFourthResourceStrongbox.setVisible(false);
        minusFourthResourceDepot.setVisible(false);
        minusFourthResourceStrongbox.setVisible(false);
        fourthDepotOcc.setVisible(false);
        fourthStrongboxOcc.setVisible(false);
        plusFourthResourceExtra.setVisible(false);
        minusFourthResourceExtra.setVisible(false);
        fourthExtraOcc.setVisible(false);

        slotsBackground.setVisible(false);
        basicProduction.setVisible(false);
        slot1.setVisible(false);
        slot2.setVisible(false);
        slot3.setVisible(false);

        nextStep.setVisible(false);
        activateProdText.setVisible(false);
        firstInputLabel.setVisible(false);
        secondInputLabel.setVisible(false);
        outputLabel.setVisible(false);
        input.setVisible(false);
        inputSecond.setVisible(false);
        output.setVisible(false);
    }



    @Override
    public void setVisibleFirstResource() {
        plusFirstResourceDepot.setVisible(true);
        plusFirstResourceStrongbox.setVisible(true);
        minusFirstResourceDepot.setVisible(true);
        minusFirstResourceStrongbox.setVisible(true);
        firstDepotOcc.setVisible(true);
        firstStrongboxOcc.setVisible(true);
        firstResourceDepot.setVisible(true);
        firstResourceStrongbox.setVisible(true);
        if(isAvailableExtraDepotOfType(requiredResources.get(0).getKey(), client)) {
            plusFirstResourceExtra.setVisible(true);
            minusFirstResourceExtra.setVisible(true);
            firstExtraOcc.setVisible(true);
            firstResourceExtra.setVisible(true);
        }
    }

    @Override
    public void setVisibleSecondResource() {
        plusSecondResourceDepot.setVisible(true);
        plusSecondResourceStrongbox.setVisible(true);
        minusSecondResourceDepot.setVisible(true);
        minusSecondResourceStrongbox.setVisible(true);
        secondDepotOcc.setVisible(true);
        secondStrongboxOcc.setVisible(true);
        secondResourceDepot.setVisible(true);
        secondResourceStrongbox.setVisible(true);
        if(isAvailableExtraDepotOfType(requiredResources.get(1).getKey(), client)) {
            plusSecondResourceExtra.setVisible(true);
            minusSecondResourceExtra.setVisible(true);
            secondExtraOcc.setVisible(true);
            secondResourceExtra.setVisible(true);
        }
    }

    @Override
    public void setVisibleThirdResource() {
        plusThirdResourceDepot.setVisible(true);
        plusThirdResourceStrongbox.setVisible(true);
        minusThirdResourceDepot.setVisible(true);
        minusThirdResourceStrongbox.setVisible(true);
        thirdDepotOcc.setVisible(true);
        thirdStrongboxOcc.setVisible(true);
        thirdResourceDepot.setVisible(true);
        thirdResourceStrongbox.setVisible(true);
        if(isAvailableExtraDepotOfType(requiredResources.get(2).getKey(), client)) {
            plusThirdResourceExtra.setVisible(true);
            minusThirdResourceExtra.setVisible(true);
            thirdExtraOcc.setVisible(true);
            thirdResourceExtra.setVisible(true);
        }
        if(requiredResources.size()>3) {
            setVisibleFourthResource();
        }
    }

    public void setVisibleFourthResource() {
        plusFourthResourceDepot.setVisible(true);
        plusFourthResourceStrongbox.setVisible(true);
        minusFourthResourceDepot.setVisible(true);
        minusFourthResourceStrongbox.setVisible(true);
        fourthDepotOcc.setVisible(true);
        fourthStrongboxOcc.setVisible(true);
        fourthResourceDepot.setVisible(true);
        fourthResourceStrongbox.setVisible(true);
        if(isAvailableExtraDepotOfType(requiredResources.get(3).getKey(),client)) {
            plusFourthResourceExtra.setVisible(true);
            minusFourthResourceExtra.setVisible(true);
            fourthExtraOcc.setVisible(true);
            fourthResourceExtra.setVisible(true);
        }
    }


    @FXML
    public void minusFirstResourceFromDepot() {
        makeTextInvisible(activateProdText);
        firstCurrentValueDepot--;
        if(!addToCostList(requiredResources,1, 1, firstCurrentValueDepot, firstDepotOcc)) firstCurrentValueDepot++;
    }

    @FXML
    public void minusSecondResourceFromDepot() {
        makeTextInvisible(activateProdText);
        secondCurrentValueDepot--;
        if(!addToCostList(requiredResources,2, 1, secondCurrentValueDepot, secondDepotOcc)) secondCurrentValueDepot++;
    }

    @FXML
    public void minusThirdResourceFromDepot() {
        makeTextInvisible(activateProdText);
        thirdCurrentValueDepot--;
        if(!addToCostList(requiredResources,3,1, thirdCurrentValueDepot, thirdDepotOcc)) thirdCurrentValueDepot++;
    }

    @FXML
    public void minusFirstResourceFromStrongbox() {
        makeTextInvisible(activateProdText);
        firstCurrentValueStrongbox--;
        if(!addToCostList(requiredResources,1,1, firstCurrentValueStrongbox, firstStrongboxOcc)) firstCurrentValueStrongbox++;
    }
    @FXML
    public void minusSecondResourceFromStrongbox() {
        makeTextInvisible(activateProdText);
        secondCurrentValueStrongbox--;
        if(!addToCostList(requiredResources,2,1, secondCurrentValueStrongbox, secondStrongboxOcc)) secondCurrentValueStrongbox++;
    }
    @FXML
    public void minusThirdResourceFromStrongbox() {
        makeTextInvisible(activateProdText);
        thirdCurrentValueStrongbox--;
        if(!addToCostList(requiredResources, 3,1,thirdCurrentValueStrongbox, thirdStrongboxOcc)) thirdCurrentValueStrongbox++;
    }



    @FXML
    public void plusFirstResourceFromDepot() {
        firstCurrentValueDepot++;
        if(!editedCostList(requiredResources,1, 1, firstCurrentValueDepot, firstDepotOcc)) firstCurrentValueDepot--;
    }


    @FXML
    public void plusSecondResourceFromDepot(){
        secondCurrentValueDepot++;
        if(!editedCostList(requiredResources,2, 1, secondCurrentValueDepot, secondDepotOcc)) secondCurrentValueDepot--;
    }

    @FXML
    public void plusThirdResourceFromDepot(){
        thirdCurrentValueDepot++;
        if(!editedCostList(requiredResources,3, 1, thirdCurrentValueDepot, thirdDepotOcc)) thirdCurrentValueDepot--;
    }

    @FXML
    public void plusFirstResourceFromStrongbox() {
        firstCurrentValueStrongbox++;
        if(!editedCostList(requiredResources,1, 1, firstCurrentValueStrongbox, firstStrongboxOcc)) firstCurrentValueStrongbox--;
    }

    @FXML
    public void plusSecondResourceFromStrongbox(){
        secondCurrentValueStrongbox++;
        if(!editedCostList(requiredResources,2, 1,secondCurrentValueStrongbox , secondStrongboxOcc)) secondCurrentValueStrongbox--;
    }

    @FXML
    public void plusThirdResourceFromStrongbox(){
        thirdCurrentValueStrongbox++;
        if(!editedCostList(requiredResources,3, 1, thirdCurrentValueStrongbox,thirdStrongboxOcc)) thirdCurrentValueStrongbox--;
    }

    @FXML
    public void plusFirstResourceFromExtra() {
        firstCurrentValueExtra++;
        if(!editedCostList(requiredResources,1, 1, firstCurrentValueExtra, firstExtraOcc)) firstCurrentValueExtra--;
    }

    @FXML
    public void plusSecondResourceFromExtra() {
        secondCurrentValueExtra++;
        if(!editedCostList(requiredResources,2, 1, secondCurrentValueExtra, secondExtraOcc)) secondCurrentValueExtra--;
    }

    @FXML
    public void plusThirdResourceFromExtra() {
        thirdCurrentValueExtra++;
        if(!editedCostList(requiredResources,3, 1, thirdCurrentValueExtra, thirdExtraOcc)) thirdCurrentValueExtra--;
    }

    @FXML
    public void minusFirstResourceFromExtra() {
        makeTextInvisible(activateProdText);
        firstCurrentValueExtra--;
        if(!addToCostList(requiredResources,1, 1, firstCurrentValueExtra, firstExtraOcc)) firstCurrentValueExtra++;
    }

    @FXML
    public void minusSecondResourceFromExtra() {
        makeTextInvisible(activateProdText);
        secondCurrentValueExtra--;
        if(!addToCostList(requiredResources,2,1,secondCurrentValueExtra, secondExtraOcc)) secondCurrentValueExtra++;
    }

    @FXML
    public void minusThirdResourceFromExtra() {
        makeTextInvisible(activateProdText);
        thirdCurrentValueExtra--;
        if (!addToCostList(requiredResources,3,1,thirdCurrentValueExtra, thirdExtraOcc)) thirdCurrentValueExtra++;
    }



    /*Aggiunti successivamente*/

    public void minusFourthResourceDepot() {
        makeTextInvisible(activateProdText);
        fourthCurrentValueDepot--;
        if (!addToCostList(requiredResources,4,1,fourthCurrentValueDepot, fourthDepotOcc)) fourthCurrentValueDepot++;
    }

    public void minusFourthResourceStrongbox() {
        makeTextInvisible(activateProdText);
        fourthCurrentValueStrongbox--;
        if (!addToCostList(requiredResources,4,1,fourthCurrentValueStrongbox, fourthStrongboxOcc)) fourthCurrentValueStrongbox++;
    }
    public void minusFourthResourceExtra() {
        makeTextInvisible(activateProdText);
        fourthCurrentValueExtra--;
        if (!addToCostList(requiredResources,4,1,fourthCurrentValueExtra, fourthExtraOcc)) fourthCurrentValueExtra++;
    }

    public void plusFourthResourceDepot() {
        fourthCurrentValueDepot++;
        if(!editedCostList(requiredResources,4, 1, fourthCurrentValueDepot, fourthDepotOcc)) fourthCurrentValueDepot--;
    }

    public void plusFourthResourceStrongbox() {
        fourthCurrentValueExtra++;
        if(!editedCostList(requiredResources,4, 1, fourthCurrentValueStrongbox, fourthStrongboxOcc)) fourthCurrentValueStrongbox--;
    }

    public void plusFourthResourceExtra() {
        fourthCurrentValueExtra++;
        if(!editedCostList(requiredResources,4, 1, fourthCurrentValueExtra, fourthExtraOcc)) fourthCurrentValueExtra--;
    }





    @FXML
    public void ifBasicShowInputOrElseSkip() {
        cleanPane();
        if(activateProductionAction.isBasicProduction()) {
            showActionRequired("Select two input and one output to complete basic production");
            input.setVisible(true);
            inputSecond.setVisible(true);
            output.setVisible(true);
            firstInputLabel.setVisible(true);
            secondInputLabel.setVisible(true);
            outputLabel.setVisible(true);
            clear.setVisible(true);
        } else {

        }
    }



    private void showActionRequired(String output) {
        activateProdText.setText(output);
        activateProdText.setVisible(false);
    }


    @FXML
    public void handleCloseChooseAction()
    {
        Stage stage = (Stage) closeActivateProd.getScene().getWindow();
        stage.close();
    }



    @Override
    public void showFinishButton() {

    }

    @Override
    public void hideError() {

    }

    @Override
    public void setErrorDevTextKO(String s) {

    }


    private void buttonSelection(Button button) {
        button.setStyle("-fx-background-size: 85% auto; -fx-border-color: rgb(255,255,0)");
    }

    private void deselect(VBox vBox) {
        List<Node> buttons = vBox.getChildren();
        buttons.forEach(x -> x.setStyle("-fx-background-size: 97% auto"));
    }
}
