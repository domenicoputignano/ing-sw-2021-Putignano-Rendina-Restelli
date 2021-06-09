package it.polimi.ingsw.Client.view.GUI;

import it.polimi.ingsw.Client.clientstates.gui.ActivateProductionGUI;
import it.polimi.ingsw.Commons.*;
import it.polimi.ingsw.Utils.Pair;
import it.polimi.ingsw.Utils.ResourceSource;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.util.EnumMap;
import java.util.HashMap;
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

    public Button toPayment;

    public ImageView slotsBackground;

    @FXML
    public Text firstDepotOcc, secondDepotOcc, thirdDepotOcc, fourthDepotOcc;
    public Text firstStrongboxOcc, secondStrongboxOcc, thirdStrongboxOcc, fourthStrongboxOcc;
    public Text firstExtraOcc, secondExtraOcc,thirdExtraOcc,fourthExtraOcc;

    private ActivateProductionGUI activateProductionAction;
    private List<Pair<ResourceType,Integer>> requiredResources;


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
        setText();
        initializeSlotsImages();
    }


    private void setText() {
        setFont(firstDepotOcc, 20);
        setFont(secondDepotOcc,20);
        setFont(thirdDepotOcc,20);
        setFont(fourthDepotOcc, 20);
        setFont(firstStrongboxOcc, 20);
        setFont(secondStrongboxOcc,20);
        setFont(thirdStrongboxOcc,20);
        setFont(fourthStrongboxOcc,20);
        setFont(firstExtraOcc,20);
        setFont(secondExtraOcc, 20);
        setFont(thirdExtraOcc,20);
        setFont(fourthExtraOcc,20);
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

    @FXML
    public void handleFirstSlotSelection() {
        if(activateProductionAction.canActivateSlot(0)) {
            slot1.setStyle("-fx-border-color: rgb(230,230,180);-fx-border-width: 5;");
            activateProductionAction.setSlot(0,true);
            toPayment.setVisible(true);
        } else {
            showErrorInSlotsSelection();
        }
    }

    @FXML
    public void handleSecondSlotSelection() {
        if(activateProductionAction.canActivateSlot(1)) {
            slot2.setStyle("-fx-border-color: rgb(230,230,180);-fx-border-width: 5;");
            activateProductionAction.setSlot(1,true);
            toPayment.setVisible(true);
        } else {
            showErrorInSlotsSelection();
        }
    }

    @FXML
    public void handleThirdSlotSelection() {
        if(activateProductionAction.canActivateSlot(2)) {
            slot3.setStyle("-fx-border-color: rgb(230,230,180);-fx-border-width: 5;");
            activateProductionAction.setSlot(2, true);
            toPayment.setVisible(true);
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
    public void concludeSlotSelection() {
        Map<ResourceType, Integer> inputResources = activateProductionAction.calculateInputResources();
        requiredResources = inputResources.entrySet().stream().
                map(x -> new Pair<>(x.getKey(),x.getValue())).collect(Collectors.toList());
        if(activateProductionAction.checkRequiredResources()) {
            toPayment.setVisible(false);
            switchToHowToTakeResources();
            showRequiredResources(requiredResources);
        } else {

        }
    }


    public void switchToHowToTakeResources() {
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


    }



    @Override
    public void setVisibleFirstResource() {
        plusFirstResourceDepot.setVisible(true);
        plusFirstResourceStrongbox.setVisible(true);
        minusFirstResourceDepot.setVisible(true);
        minusFirstResourceStrongbox.setVisible(true);
        firstDepotOcc.setVisible(true);
        firstStrongboxOcc.setVisible(true);
        if(isAvailableExtraDepotOfType(requiredResources.get(0).getKey(), client)) {
            plusFirstResourceExtra.setVisible(true);
            minusFirstResourceExtra.setVisible(true);
            firstExtraOcc.setVisible(true);
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
        if(isAvailableExtraDepotOfType(requiredResources.get(1).getKey(), client)) {
            plusSecondResourceExtra.setVisible(true);
            minusSecondResourceExtra.setVisible(true);
            secondExtraOcc.setVisible(true);
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
        if(isAvailableExtraDepotOfType(requiredResources.get(2).getKey(), client)) {
            plusThirdResourceExtra.setVisible(true);
            minusThirdResourceExtra.setVisible(true);
            thirdExtraOcc.setVisible(true);
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
        if(isAvailableExtraDepotOfType(requiredResources.get(3).getKey(),client)) {
            plusFourthResourceExtra.setVisible(true);
            minusFourthResourceExtra.setVisible(true);
            fourthExtraOcc.setVisible(true);
        }
    }



    @FXML
    public void handleCloseChooseAction()
    {
        Stage stage = (Stage) closeActivateProd.getScene().getWindow();
        stage.close();
    }

    public void goToPayment() {

    }


}
