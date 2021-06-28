package it.polimi.ingsw.client.view.GUI;

import it.polimi.ingsw.client.Checker;
import it.polimi.ingsw.client.clientstates.gui.ActivateProductionGUI;
import it.polimi.ingsw.commons.*;
import it.polimi.ingsw.utils.Pair;
import it.polimi.ingsw.utils.ResourceLocator;
import it.polimi.ingsw.utils.ResourceSource;
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


import java.util.EnumMap;
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

    public HBox costs;
    public Text firstCost, secondCost, thirdCost, fourthCost;
    public ImageView firstCostImg, secondCostImg, thirdCostImg, fourthCostImg;

    public Button coinInput, servantInput, shieldInput, stoneInput, coinOutput, servantOutput, shieldOutput, stoneOutput;
    public Button coinInputSecond, servantInputSecond, shieldInputSecond, stoneInputSecond;
    public Button clear;
    public Button toPayment, extraOutputChoice;
    public Label firstInputLabel, secondInputLabel, outputLabel;

    public Text errorText;

    public ImageView firstResourceImage, secondResourceImage, thirdResourceImage, fourthResourceImage;



    private boolean basicHasToBeDone, firstExtraHasToBeDone, secondExtraHasToBeDone;


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
    public TextField firstResourceDepot;
    public TextField secondResourceDepot;
    public TextField thirdResourceDepot;
    public TextField fourthResourceDepot;
    public TextField firstResourceStrongbox;
    public TextField secondResourceStrongbox;
    public TextField thirdResourceStrongbox;
    public TextField fourthResourceStrongbox;
    public TextField firstResourceExtra;
    public TextField secondResourceExtra;
    public TextField thirdResourceExtra;
    public TextField fourthResourceExtra;


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
        setFont(firstDepotOcc, 18);
        setFont(secondDepotOcc, 18);
        setFont(thirdDepotOcc, 18);
        setFont(fourthDepotOcc, 18);
        setFont(firstStrongboxOcc, 18);
        setFont(secondStrongboxOcc, 18);
        setFont(thirdStrongboxOcc, 18);
        setFont(fourthStrongboxOcc, 18);
        setFont(firstExtraOcc, 18);
        setFont(secondExtraOcc, 18);
        setFont(thirdExtraOcc, 18);
        setFont(fourthExtraOcc, 18);
        setFont(activateProdText, 35);
        setFont(clear, 25);
        setFont(firstInputLabel, 35);
        setFont(secondInputLabel, 35);
        setFont(outputLabel, 35);
        setFont(firstResourceDepot,30);
        setFont(firstResourceStrongbox,30);
        setFont(firstResourceExtra,30);
        setFont(secondResourceDepot,30);
        setFont(secondResourceStrongbox,30);
        setFont(secondResourceExtra,30);
        setFont(thirdResourceDepot,30);
        setFont(thirdResourceStrongbox,30);
        setFont(thirdResourceExtra,30);
        setFont(fourthResourceDepot,30);
        setFont(fourthResourceStrongbox,30);
        setFont(fourthResourceExtra,30);
        setFont(firstCost,25);
        firstCost.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        secondCost.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        thirdCost.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        fourthCost.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(secondCost,25);
        setFont(thirdCost,25);
        setFont(fourthCost, 25);
        setFont(concludeAction, 27);
        setFont(errorText, 30);
    }

    private void initializeSlotsImages()
    {
        initializeSlotImage(0);
        initializeSlotImage(1);
        initializeSlotImage(2);
        List<LeaderEffect> extraProduction = activateProductionAction.listExtraProductionEffect();
        for(int i = 0; i < extraProduction.size(); i++) {
            extraProductions.setVisible(true);
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

    private void initializeSlotImage(int slotIndex) {
        switch (slotIndex) {
            case(0) : {
                if(!client.getGame().getPlayer(client.getUser()).getPersonalBoard().isEmptySlot(0)) {
                    slot1.setStyle("-fx-background-image: url(" +
                            client.getGame().getPlayer(client.getUser()).getPersonalBoard().peekTopCardInSlot(0).toImage() + ")");
                    slot1.setVisible(true);
                } else {
                    slot1.setVisible(false);
                }
                return;
            }
            case(1) : {
                if(!client.getGame().getPlayer(client.getUser()).getPersonalBoard().isEmptySlot(1)) {
                    slot2.setStyle("-fx-background-image: url(" +
                            client.getGame().getPlayer(client.getUser()).getPersonalBoard().peekTopCardInSlot(1).toImage() + ")");
                    slot2.setVisible(true);
                } else {
                    slot2.setVisible(false);
                }
                return;
            }
            case (2) : {
                if(!client.getGame().getPlayer(client.getUser()).getPersonalBoard().isEmptySlot(2)) {
                    slot3.setStyle("-fx-background-image: url(" +
                            client.getGame().getPlayer(client.getUser()).getPersonalBoard().peekTopCardInSlot(2).toImage() + ")");
                    slot3.setVisible(true);
                } else {
                    slot3.setVisible(false);
                }
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

    private void nextScene() {
        activateProdText.setTranslateX(-180);
        if(basicHasToBeDone) {
                showActionRequired("Select two input and one output to complete basic production");
                input.setVisible(true);
                inputSecond.setVisible(true);
                output.setVisible(true);
                firstInputLabel.setVisible(true);
                secondInputLabel.setVisible(true);
                outputLabel.setVisible(true);
                clear.setVisible(true);
            }
        else if(firstExtraHasToBeDone) {
            cleanPane();
            showActionRequired("You selected an extra production, choose its output resource");
            outputLabel.setVisible(true);
            deselect(output);
            output.setVisible(true);
        } else if(secondExtraHasToBeDone) {
            cleanPane();
            showActionRequired("You selected an extra production, choose its output resource");
            outputLabel.setVisible(true);
            deselect(output);
            output.setVisible(true);
        } else concludeProductionsSelection();
    }


    @FXML
    public void handleBasicSelection() {
        basicHasToBeDone = true;
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
        if(activateProductionAction.isBasicSettingDone()) basicHasToBeDone = false;
        nextScene();
    }

    @FXML
    public void coinInputSecondChosen() {
        deselect(inputSecond);
        buttonSelection(coinInputSecond);
        activateProductionAction.setInput(2, ResourceType.coin);
        if(activateProductionAction.isBasicSettingDone()) basicHasToBeDone = false;
        nextScene();
    }

    @FXML
    public void servantInputChosen() {
        deselect(input);
        buttonSelection(servantInput);
        activateProductionAction.setInput(1, ResourceType.servant);
        if(activateProductionAction.isBasicSettingDone()) basicHasToBeDone = false;
        nextScene();
    }

    @FXML
    public void servantInputSecondChosen() {
        deselect(inputSecond);
        buttonSelection(servantInputSecond);
        activateProductionAction.setInput(2, ResourceType.servant);
        if(activateProductionAction.isBasicSettingDone()) basicHasToBeDone = false;
        nextScene();
    }

    @FXML
    public void shieldInputChosen() {
        deselect(input);
        buttonSelection(shieldInput);
        activateProductionAction.setInput(1, ResourceType.shield);
        if(activateProductionAction.isBasicSettingDone()) basicHasToBeDone = false;
        nextScene();
    }

    @FXML
    public void shieldInputSecondChosen() {
        deselect(inputSecond);
        buttonSelection(shieldInputSecond);
        activateProductionAction.setInput(2, ResourceType.shield);
        if(activateProductionAction.isBasicSettingDone()) basicHasToBeDone = false;
        nextScene();
    }

    @FXML
    public void stoneInputChosen() {
        deselect(input);
        buttonSelection(stoneInput);
        activateProductionAction.setInput(1, ResourceType.stone);
        if(activateProductionAction.isBasicSettingDone()) basicHasToBeDone = false;
        nextScene();
    }

    @FXML
    public void stoneInputSecondChosen(){
        deselect(inputSecond);
        buttonSelection(stoneInputSecond);
        activateProductionAction.setInput(2, ResourceType.stone);
        if(activateProductionAction.isBasicSettingDone()) basicHasToBeDone = false;
        nextScene();
    }

    @FXML
    public void coinOutputChosen() {
        deselect(output);
        buttonSelection(coinOutput);
        if(activateProductionAction.isBasicProduction()&&!activateProductionAction.isBasicSettingDone()) {
            activateProductionAction.setOutput("normal",1,ResourceType.coin);
            if(activateProductionAction.isBasicSettingDone()) basicHasToBeDone = false;
        } else {
            if(firstExtraHasToBeDone) {
                activateProductionAction.setOutput("extra",1, ResourceType.coin);
                firstExtraHasToBeDone = false;
            } else if(secondExtraHasToBeDone) {
                activateProductionAction.setOutput("extra",2, ResourceType.coin);
                secondExtraHasToBeDone = false;
            }
        }
        nextScene();
    }

    @FXML
    public void servantOutputChosen(){
        deselect(output);
        buttonSelection(servantOutput);
        if(activateProductionAction.isBasicProduction()&&!activateProductionAction.isBasicSettingDone()) {
            activateProductionAction.setOutput("normal",1,ResourceType.servant);
            if(activateProductionAction.isBasicSettingDone()) basicHasToBeDone = false;
        } else {
            if(firstExtraHasToBeDone) {
                activateProductionAction.setOutput("extra",1, ResourceType.servant);
                firstExtraHasToBeDone = false;
            } else if(secondExtraHasToBeDone) {
                activateProductionAction.setOutput("extra",2, ResourceType.servant);
                secondExtraHasToBeDone = false;
            }
        }
        nextScene();
    }
    @FXML
    public void shieldOutputChosen() {
        deselect(output);
        buttonSelection(shieldOutput);
        if(activateProductionAction.isBasicProduction()&&!activateProductionAction.isBasicSettingDone()) {
            activateProductionAction.setOutput("normal",1,ResourceType.shield);
            if(activateProductionAction.isBasicSettingDone()) basicHasToBeDone = false;
        } else {
            if(firstExtraHasToBeDone) {
                activateProductionAction.setOutput("extra",1, ResourceType.shield);
                firstExtraHasToBeDone = false;
            } else if(secondExtraHasToBeDone) {
                activateProductionAction.setOutput("extra",2, ResourceType.shield);
                secondExtraHasToBeDone = false;
            }
        }
        nextScene();
    }
    @FXML
    public void stoneOutputChosen() {
        deselect(output);
        buttonSelection(stoneOutput);
        if(activateProductionAction.isBasicProduction()&&!activateProductionAction.isBasicSettingDone()) {
            activateProductionAction.setOutput("normal",1,ResourceType.stone);
            if(activateProductionAction.isBasicSettingDone()) basicHasToBeDone = false;
        } else {
            if(firstExtraHasToBeDone) {
                activateProductionAction.setOutput("extra",1, ResourceType.stone);
                firstExtraHasToBeDone = false;
            } else if(secondExtraHasToBeDone) {
                activateProductionAction.setOutput("extra",2, ResourceType.stone);
                secondExtraHasToBeDone = false;
            }
        }
        nextScene();
    }



    @FXML
    public void handleFirstSlotSelection() {
        hideError();
        if(activateProductionAction.canActivateSlot(0)) {
            slot1.setStyle("-fx-border-color: rgb(231,156,48); -fx-border-width: 5;" +
                    "-fx-background-image: url("+client.getGame().getPlayer(client.getUser()).
                    getPersonalBoard().getSlot(0).peekTopCard().toImage()+");");
            activateProductionAction.setSlot(0,true);
            nextStep.setVisible(true);
        } else {
            showErrorInSlotsSelection();
        }
    }

    @FXML
    public void handleSecondSlotSelection() {
        hideError();
        if(activateProductionAction.canActivateSlot(1)) {
            slot2.setStyle("-fx-border-color: rgb(231,156,48) ;-fx-border-width: 5;" +
                    "-fx-background-image: url("+client.getGame().getPlayer(client.getUser()).
                    getPersonalBoard().getSlot(1).peekTopCard().toImage()+");");
            activateProductionAction.setSlot(1,true);
            nextStep.setVisible(true);
        } else {
            showErrorInSlotsSelection();
        }
    }

    @FXML
    public void handleThirdSlotSelection() {
        hideError();
        if(activateProductionAction.canActivateSlot(2)) {
            slot3.setStyle("-fx-border-color: rgb(231,156,48); -fx-border-width: 5;" +
                    "-fx-background-image: url("+client.getGame().getPlayer(client.getUser()).
                    getPersonalBoard().getSlot(2).peekTopCard().toImage()+");");
            activateProductionAction.setSlot(2, true);
            nextStep.setVisible(true);
        } else {
            showErrorInSlotsSelection();
        }
    }

    @FXML
    public void firstExtraProductionSelected() {
        activateProductionAction.setExtra(1);
        firstExtraHasToBeDone = true;
        firstExtraSlot.setStyle("-fx-border-color: rgb(231,156,48); -fx-border-width: 5;" +
                "-fx-background-image: url("+client.getGame().getPlayer(client.getUser()).
                getCompatibleLeaderEffect(Effect.EXTRAPRODUCTION).get(0).getLeaderEffect().toImage()+");");
        nextStep.setVisible(true);
    }

    @FXML
    public void secondExtraProductionSelected() {
        activateProductionAction.setExtra(2);
        secondExtraHasToBeDone = true;
        secondExtraSlot.setStyle("-fx-border-color: rgb(231,156,48); -fx-border-width: 5;" +
                "-fx-background-image: url("+client.getGame().getPlayer(client.getUser()).
                getCompatibleLeaderEffect(Effect.EXTRAPRODUCTION).get(1).getLeaderEffect().toImage()+");");
        nextStep.setVisible(true);
    }


    private void showErrorInSlotsSelection() {
        setActivateProdText("You can't activate this production because the slot is empty");
    }

    private void setActivateProdText(String text) {
        activateProdText.setText(text);
        activateProdText.setVisible(true);
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

        activateProdText.setVisible(false);
        firstInputLabel.setVisible(false);
        secondInputLabel.setVisible(false);
        outputLabel.setVisible(false);
        input.setVisible(false);
        inputSecond.setVisible(false);
        output.setVisible(false);
        toPayment.setVisible(false);


        firstResourceImage.setVisible(false);
        secondResourceImage.setVisible(false);
        thirdResourceImage.setVisible(false);
        fourthResourceImage.setVisible(false);

        nextStep.setVisible(false);
        clear.setVisible(false);
        concludeAction.setVisible(false);

        extraProductions.setVisible(false);
        costs.setVisible(false);
        firstCost.setVisible(false);
        secondCost.setVisible(false);
        thirdCost.setVisible(false);
        fourthCost.setVisible(false);
    }



    @Override
    public void setVisibleFirstResource() {
        firstResourceImage.setImage(new Image(ResourceLocator.retrieveResourceTypeImage(requiredResources.get(0).getKey())));

        firstCostImg.setImage(new Image(ResourceLocator.retrieveResourceTypeImage(requiredResources.get(0).getKey())));
        firstCost.setText(""+requiredResources.get(0).getValue());
        firstCost.setVisible(true);
        firstCostImg.setVisible(true);

        firstResourceImage.setVisible(true);
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
        secondResourceImage.setImage(new Image(ResourceLocator.retrieveResourceTypeImage(requiredResources.get(1).getKey())));
        secondResourceImage.setVisible(true);

        secondCostImg.setImage(new Image(ResourceLocator.retrieveResourceTypeImage(requiredResources.get(1).getKey())));
        secondCost.setText(""+requiredResources.get(1).getValue());
        secondCost.setVisible(true);
        secondCostImg.setVisible(true);

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
        thirdResourceImage.setImage(new Image(ResourceLocator.retrieveResourceTypeImage(requiredResources.get(2).getKey())));
        thirdResourceImage.setVisible(true);

        thirdCostImg.setImage(new Image(ResourceLocator.retrieveResourceTypeImage(requiredResources.get(2).getKey())));
        thirdCost.setVisible(true);
        thirdCost.setText(""+requiredResources.get(2).getValue());
        thirdCostImg.setVisible(true);

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

    private void setVisibleFourthResource() {
        fourthResourceImage.setImage(new Image(ResourceLocator.retrieveResourceTypeImage(requiredResources.get(3).getKey())));
        fourthResourceImage.setVisible(true);

        fourthCostImg.setImage(new Image(ResourceLocator.retrieveResourceTypeImage(requiredResources.get(3).getKey())));
        fourthCost.setVisible(true);
        fourthCost.setText(""+requiredResources.get(3).getValue());
        fourthCostImg.setVisible(true);


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
        makeTextInvisible(errorText);
        firstCurrentValueDepot--;
        if(!addToCostList(requiredResources,1, 1, firstCurrentValueDepot, firstDepotOcc)) firstCurrentValueDepot++;
    }

    @FXML
    public void minusSecondResourceFromDepot() {
        makeTextInvisible(errorText);
        secondCurrentValueDepot--;
        if(!addToCostList(requiredResources,2, 1, secondCurrentValueDepot, secondDepotOcc)) secondCurrentValueDepot++;
    }

    @FXML
    public void minusThirdResourceFromDepot() {
        makeTextInvisible(errorText);
        thirdCurrentValueDepot--;
        if(!addToCostList(requiredResources,3,1, thirdCurrentValueDepot, thirdDepotOcc)) thirdCurrentValueDepot++;
    }

    @FXML
    public void minusFirstResourceFromStrongbox() {
        makeTextInvisible(errorText);
        firstCurrentValueStrongbox--;
        if(!addToCostList(requiredResources,1,1, firstCurrentValueStrongbox, firstStrongboxOcc)) firstCurrentValueStrongbox++;
    }
    @FXML
    public void minusSecondResourceFromStrongbox() {
        makeTextInvisible(errorText);
        secondCurrentValueStrongbox--;
        if(!addToCostList(requiredResources,2,1, secondCurrentValueStrongbox, secondStrongboxOcc)) secondCurrentValueStrongbox++;
    }
    @FXML
    public void minusThirdResourceFromStrongbox() {
        makeTextInvisible(errorText);
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
        makeTextInvisible(errorText);
        firstCurrentValueExtra--;
        if(!addToCostList(requiredResources,1, 1, firstCurrentValueExtra, firstExtraOcc)) firstCurrentValueExtra++;
    }

    @FXML
    public void minusSecondResourceFromExtra() {
        makeTextInvisible(errorText);
        secondCurrentValueExtra--;
        if(!addToCostList(requiredResources,2,1,secondCurrentValueExtra, secondExtraOcc)) secondCurrentValueExtra++;
    }

    @FXML
    public void minusThirdResourceFromExtra() {
        makeTextInvisible(errorText);
        thirdCurrentValueExtra--;
        if (!addToCostList(requiredResources,3,1,thirdCurrentValueExtra, thirdExtraOcc)) thirdCurrentValueExtra++;
    }



    /*Aggiunti successivamente*/

    public void minusFourthResourceDepot() {
        makeTextInvisible(errorText);
        fourthCurrentValueDepot--;
        if (!addToCostList(requiredResources,4,1,fourthCurrentValueDepot, fourthDepotOcc)) fourthCurrentValueDepot++;
    }

    public void minusFourthResourceStrongbox() {
        makeTextInvisible(errorText);
        fourthCurrentValueStrongbox--;
        if (!addToCostList(requiredResources,4,1,fourthCurrentValueStrongbox, fourthStrongboxOcc)) fourthCurrentValueStrongbox++;
    }
    public void minusFourthResourceExtra() {
        makeTextInvisible(errorText);
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
    public void concludeProductionsSelection() {
        cleanPane();
        Map<ResourceType, Integer> inputResources = activateProductionAction.calculateInputResources();
        requiredResources = inputResources.entrySet().stream().filter(x -> x.getValue()>0).
                map(x -> new Pair<>(x.getKey(),x.getValue())).collect(Collectors.toList());
        if(Checker.checkResources(inputResources, client.getGame().getPlayer(client.getUser()).getPersonalBoard())){
            showActionRequired("It's time to choose from where pick resources needed for production");
            costs.setVisible(true);
            showRequiredResources(requiredResources);
        } else {
            showActionRequired("Oh no, seems that you can't perform selected productions!");
        }
    }




    @FXML
    public void ifBasicShowInputOrElseCheckExtra() {
        cleanPane();
        nextScene();
    }



    private void showActionRequired(String output) {
        activateProdText.setText(output);
        activateProdText.setVisible(true);
    }


    @FXML
    public void handleCloseChooseAction()
    {
        Stage stage = (Stage) closeActivateProd.getScene().getWindow();
        stage.close();
    }



    @Override
    public void showFinishButton() {
        concludeAction.setVisible(isPaymentDone(requiredResources));
    }

    @Override
    public void hideError() {
        errorText.setVisible(false);
    }

    @Override
    public void maxOccurrencesSet(String error) {
        errorText.setText(error);
        errorText.setVisible(true);
    }


    private void buttonSelection(Button button) {
        button.setStyle("-fx-border-color: rgb(231,156,48);-fx-border-width: 5;");
    }

    private void slotSelection(Button slot, int slotIndex) {
        initializeSlotImage(slotIndex);
        buttonSelection(slot);

    }


    private void deselect(VBox vBox) {
        List<Node> buttons = vBox.getChildren();
        buttons.forEach(x -> x.setStyle("-fx-background-size: 97% auto"));
    }


    public void handleActionConclusion() {
        Map<ResourceSource, EnumMap<ResourceType, Integer>> paymentInstructions = initializeMap();
        for(int i = 0; i < requiredResources.size(); i++) {
            if (i == 0)
                createInstructionEntry(paymentInstructions, requiredResources, i, firstCurrentValueDepot, firstCurrentValueStrongbox, firstCurrentValueExtra);
            if (i == 1)
                createInstructionEntry(paymentInstructions, requiredResources, i, secondCurrentValueDepot, secondCurrentValueStrongbox, secondCurrentValueExtra);
            if (i == 2)
                createInstructionEntry(paymentInstructions, requiredResources, i, thirdCurrentValueDepot, thirdCurrentValueStrongbox, thirdCurrentValueExtra);
            if (i == 3)
                createInstructionEntry(paymentInstructions, requiredResources, i, fourthCurrentValueDepot, fourthCurrentValueStrongbox, fourthCurrentValueExtra);
        }
        activateProductionAction.setPaymentInstructions(paymentInstructions);
        activateProductionAction.manageUserInteraction();
        closeAction(concludeAction);
    }
}
