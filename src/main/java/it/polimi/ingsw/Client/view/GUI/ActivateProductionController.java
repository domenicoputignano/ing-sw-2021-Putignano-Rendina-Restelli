package it.polimi.ingsw.Client.view.GUI;

import it.polimi.ingsw.Client.Checker;
import it.polimi.ingsw.Client.clientstates.gui.ActivateProductionGUI;
import it.polimi.ingsw.Commons.*;
import it.polimi.ingsw.Utils.Pair;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
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
    public Button firstResourceDepot, secondResourceDepot, thirdResourceDepot, fourthResourceDepot;
    public Button firstResourceStrongbox, secondResourceStrongbox, thirdResourceStrongbox, fourthResourceStrongbox;
    public Button firstResourceExtra, secondResourceExtra, thirdResourceExtra, fourthResourceExtra;

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
        initializeSlotsImages();
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
        } else {
            showErrorInSlotsSelection();
        }
    }

    @FXML
    public void handleSecondSlotSelection() {
        if(activateProductionAction.canActivateSlot(1)) {
            slot2.setStyle("-fx-border-color: rgb(230,230,180);-fx-border-width: 5;");
            activateProductionAction.setSlot(1,true);
        } else {
            showErrorInSlotsSelection();
        }
    }

    @FXML
    public void handleThirdSlotSelection() {
        if(activateProductionAction.canActivateSlot(2)) {
            slot3.setStyle("-fx-border-color: rgb(230,230,180);-fx-border-width: 5;");
            activateProductionAction.setSlot(2, true);
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
            showRequiredResources(requiredResources);
        } else {

        }
    }


    @Override
    public void setVisibleFirstResource() {
        firstResourceDepot.setVisible(true);
        firstResourceStrongbox.setVisible(true);
        if(isAvailableExtraDepotOfType(requiredResources.get(0).getKey(), client)) {
            firstResourceExtra.setVisible(true);
        }
    }

    @Override
    public void setVisibleSecondResource() {
        secondResourceDepot.setVisible(true);
        secondResourceStrongbox.setVisible(true);
        if(isAvailableExtraDepotOfType(requiredResources.get(1).getKey(), client)) {
            secondResourceExtra.setVisible(true);
        }
    }

    @Override
    public void setVisibleThirdResource() {
        thirdResourceDepot.setVisible(true);
        thirdResourceStrongbox.setVisible(true);
        if(isAvailableExtraDepotOfType(requiredResources.get(2).getKey(), client)) {
            thirdResourceExtra.setVisible(true);
        }
    }

    public void setVisibleFourthResource() {
        fourthResourceDepot.setVisible(true);
        fourthResourceStrongbox.setVisible(true);
        if(isAvailableExtraDepotOfType(requiredResources.get(3).getKey(),client)) {
            fourthResourceExtra.setVisible(true);
        }
    }



    @FXML
    public void handleCloseChooseAction()
    {
        Stage stage = (Stage) closeActivateProd.getScene().getWindow();
        stage.close();
    }




}
