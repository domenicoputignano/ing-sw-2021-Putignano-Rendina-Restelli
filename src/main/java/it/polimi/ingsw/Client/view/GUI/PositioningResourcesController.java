package it.polimi.ingsw.Client.view.GUI;

import it.polimi.ingsw.Client.clientstates.gui.PositioningResourcesGUI;
import it.polimi.ingsw.Commons.ResourceType;
import it.polimi.ingsw.Utils.MarbleDestination;
import it.polimi.ingsw.Utils.Messages.ClientMessages.PositioningMessage;
import it.polimi.ingsw.Utils.Pair;
import it.polimi.ingsw.Utils.ResourceLocator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class PositioningResourcesController extends Controller{

    @FXML
    public Button okButton;

    @FXML
    public AnchorPane positioningResourcesPane;

    @FXML
    public ImageView depot1, depot2left, depot2right, depot3left, depot3mid, depot3right;

    @FXML
    public Text titleMessage, subTitleDepots, subTitleIndication;

    @FXML
    public ImageView resource1ToSettle, resource2ToSettle, resource3ToSettle, resource4ToSettle;

    @FXML
    public VBox resource1Choice, resource2Choice, resource3Choice, resource4Choice;

    private MarbleDestination[] choices = new MarbleDestination[4];
    private List<ResourceType> resourcesToSettle;
    private PositioningResourcesGUI state;

    @FXML
    @Override
    public void initialize() {

        this.client = GUIApp.client;

        positioningResourcesPane.setBackground(new Background(new BackgroundImage(new Image("/gui/img/exit_tab.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false))));

        setFont(titleMessage, 35);
        setFont(subTitleDepots, 25);
        setFont(subTitleIndication, 25);
        resource1Choice.getChildren().forEach(x->setFont((Button) x,17));
        resource2Choice.getChildren().forEach(x->setFont((Button) x,17));
        resource3Choice.getChildren().forEach(x->setFont((Button) x,17));
        resource4Choice.getChildren().forEach(x->setFont((Button) x,17));
        setFont(okButton, 20);

        initializeDepots();
    }

    private void initializeDepots(){
        // SETUP DEPOT 1
        if(client.getGame().getPlayer(client.getUser()).getPersonalBoard().getWarehouse().getNormalDepots()[0].getOcc()==1){
            loadImageResource(depot1, client.getGame().getPlayer(client.getUser()).
                    getPersonalBoard().getWarehouse().getNormalDepots()[0].getType());
        }

        // SETUP DEPOT 2
        if(client.getGame().getPlayer(client.getUser()).getPersonalBoard().getWarehouse().getNormalDepots()[1].getOcc()==2){
            loadImageResource(depot2left, client.getGame().getPlayer(client.getUser()).
                    getPersonalBoard().getWarehouse().getNormalDepots()[1].getType());
            loadImageResource(depot2right, client.getGame().getPlayer(client.getUser()).
                    getPersonalBoard().getWarehouse().getNormalDepots()[1].getType());
        } else if(client.getGame().getPlayer(client.getUser()).getPersonalBoard().getWarehouse().getNormalDepots()[1].getOcc()==1){
            loadImageResource(depot2left, client.getGame().getPlayer(client.getUser()).
                    getPersonalBoard().getWarehouse().getNormalDepots()[1].getType());
        }

        // SETUP DEPOT 3
        if(client.getGame().getPlayer(client.getUser()).getPersonalBoard().getWarehouse().getNormalDepots()[2].getOcc()==3){
            loadImageResource(depot3left, client.getGame().getPlayer(client.getUser()).
                    getPersonalBoard().getWarehouse().getNormalDepots()[2].getType());
            loadImageResource(depot3mid, client.getGame().getPlayer(client.getUser()).
                    getPersonalBoard().getWarehouse().getNormalDepots()[2].getType());
            loadImageResource(depot3right, client.getGame().getPlayer(client.getUser()).
                    getPersonalBoard().getWarehouse().getNormalDepots()[2].getType());
        } else if(client.getGame().getPlayer(client.getUser()).getPersonalBoard().getWarehouse().getNormalDepots()[2].getOcc()==2){
            loadImageResource(depot3left, client.getGame().getPlayer(client.getUser()).
                    getPersonalBoard().getWarehouse().getNormalDepots()[2].getType());
            loadImageResource(depot3mid, client.getGame().getPlayer(client.getUser()).
                    getPersonalBoard().getWarehouse().getNormalDepots()[2].getType());
        } else if(client.getGame().getPlayer(client.getUser()).getPersonalBoard().getWarehouse().getNormalDepots()[2].getOcc()==1){
            loadImageResource(depot3left, client.getGame().getPlayer(client.getUser()).
                    getPersonalBoard().getWarehouse().getNormalDepots()[2].getType());
        }
    }

    public void setResourcesToSettle(List<ResourceType> resourcesToSettle){
        this.resourcesToSettle = resourcesToSettle;
        if(resourcesToSettle.size() > 0){
            resource1ToSettle.setImage(new Image(ResourceLocator.retrieveResourceTypeImage(resourcesToSettle.get(0))));
            resource1Choice.setVisible(true);
        }
        if(resourcesToSettle.size() > 1){
            resource2ToSettle.setImage(new Image(ResourceLocator.retrieveResourceTypeImage(resourcesToSettle.get(1))));
            resource2Choice.setVisible(true);
        }
        if(resourcesToSettle.size() > 2){
            resource3ToSettle.setImage(new Image(ResourceLocator.retrieveResourceTypeImage(resourcesToSettle.get(2))));
            resource3Choice.setVisible(true);
        }
        if(resourcesToSettle.size() > 3){
            resource4ToSettle.setImage(new Image(ResourceLocator.retrieveResourceTypeImage(resourcesToSettle.get(3))));
            resource4Choice.setVisible(true);
        }
        state = new PositioningResourcesGUI(this.client, resourcesToSettle);
    }

    public void handleOkButton(){
        PositioningMessage messageToSend = new PositioningMessage();
        List<Pair<ResourceType, MarbleDestination>> whereToPutResources = new ArrayList<>();
        for(ResourceType resourceType : resourcesToSettle){
            whereToPutResources.add(new Pair<>(resourceType, choices[resourcesToSettle.indexOf(resourceType)]));
        }
        messageToSend.setWhereToPutResources(whereToPutResources);
        state.setMessageToSend(messageToSend);
        state.manageUserInteraction();
        ((Stage)positioningResourcesPane.getScene().getWindow()).close();
    }

    private void loadImageResource(ImageView target, ResourceType resourceType) {
        if(ResourceLocator.retrieveResourceTypeImage(resourceType) != null)
            target.setImage(new Image(ResourceLocator.retrieveResourceTypeImage(resourceType)));
    }

    private void pressedButton(Button target){
        target.setStyle("-fx-background-size: 75% auto;");
        showOkButton();
    }

    public void chooseDepot1Resource1(ActionEvent actionEvent) {
        this.choices[0] = MarbleDestination.DEPOT1;
        resource1Choice.getChildren().forEach(x -> x.setStyle("-fx-background-size: 100% auto"));
        pressedButton((Button)actionEvent.getSource());
    }

    public void chooseDepot2Resource1(ActionEvent actionEvent) {
        this.choices[0] = MarbleDestination.DEPOT2;
        resource1Choice.getChildren().forEach(x -> x.setStyle("-fx-background-size: 100% auto"));
        pressedButton((Button)actionEvent.getSource());
    }

    public void chooseDepot3Resource1(ActionEvent actionEvent) {
        this.choices[0] = MarbleDestination.DEPOT3;
        resource1Choice.getChildren().forEach(x -> x.setStyle("-fx-background-size: 100% auto"));
        pressedButton((Button)actionEvent.getSource());
    }

    public void chooseExtraDepotResource1(ActionEvent actionEvent) {
        this.choices[0] = MarbleDestination.EXTRA;
        resource1Choice.getChildren().forEach(x -> x.setStyle("-fx-background-size: 100% auto"));
        pressedButton((Button)actionEvent.getSource());
    }

    public void chooseDiscardResource1(ActionEvent actionEvent) {
        this.choices[0] = MarbleDestination.DISCARD;
        resource1Choice.getChildren().forEach(x -> x.setStyle("-fx-background-size: 100% auto"));
        pressedButton((Button)actionEvent.getSource());
    }

    public void chooseDepot1Resource2(ActionEvent actionEvent) {
        this.choices[1] = MarbleDestination.DEPOT1;
        resource2Choice.getChildren().forEach(x -> x.setStyle("-fx-background-size: 100% auto"));
        pressedButton((Button)actionEvent.getSource());
    }

    public void chooseDepot2Resource2(ActionEvent actionEvent) {
        this.choices[1] = MarbleDestination.DEPOT2;
        resource2Choice.getChildren().forEach(x -> x.setStyle("-fx-background-size: 100% auto"));
        pressedButton((Button)actionEvent.getSource());
    }

    public void chooseDepot3Resource2(ActionEvent actionEvent) {
        this.choices[1] = MarbleDestination.DEPOT3;
        resource2Choice.getChildren().forEach(x -> x.setStyle("-fx-background-size: 100% auto"));
        pressedButton((Button)actionEvent.getSource());
    }

    public void chooseExtraDepotResource2(ActionEvent actionEvent) {
        this.choices[1] = MarbleDestination.EXTRA;
        resource2Choice.getChildren().forEach(x -> x.setStyle("-fx-background-size: 100% auto"));
        pressedButton((Button)actionEvent.getSource());
    }

    public void chooseDiscardResource2(ActionEvent actionEvent) {
        this.choices[1] = MarbleDestination.DISCARD;
        resource2Choice.getChildren().forEach(x -> x.setStyle("-fx-background-size: 100% auto"));
        pressedButton((Button)actionEvent.getSource());
    }

    public void chooseDepot1Resource3(ActionEvent actionEvent) {
        this.choices[2] = MarbleDestination.DEPOT1;
        resource3Choice.getChildren().forEach(x -> x.setStyle("-fx-background-size: 100% auto"));
        pressedButton((Button)actionEvent.getSource());
    }

    public void chooseDepot2Resource3(ActionEvent actionEvent) {
        this.choices[2] = MarbleDestination.DEPOT2;
        resource3Choice.getChildren().forEach(x -> x.setStyle("-fx-background-size: 100% auto"));
        pressedButton((Button)actionEvent.getSource());
    }

    public void chooseDepot3Resource3(ActionEvent actionEvent) {
        this.choices[2] = MarbleDestination.DEPOT3;
        resource3Choice.getChildren().forEach(x -> x.setStyle("-fx-background-size: 100% auto"));
        pressedButton((Button)actionEvent.getSource());
    }

    public void chooseExtraDepotResource3(ActionEvent actionEvent) {
        this.choices[2] = MarbleDestination.EXTRA;
        resource3Choice.getChildren().forEach(x -> x.setStyle("-fx-background-size: 100% auto"));
        pressedButton((Button)actionEvent.getSource());
    }

    public void chooseDiscardResource3(ActionEvent actionEvent) {
        this.choices[2] = MarbleDestination.DISCARD;
        resource3Choice.getChildren().forEach(x -> x.setStyle("-fx-background-size: 100% auto"));
        pressedButton((Button)actionEvent.getSource());
    }

    public void chooseDepot1Resource4(ActionEvent actionEvent) {
        this.choices[3] = MarbleDestination.DEPOT1;
        resource4Choice.getChildren().forEach(x -> x.setStyle("-fx-background-size: 100% auto"));
        pressedButton((Button)actionEvent.getSource());
    }

    public void chooseDepot2Resource4(ActionEvent actionEvent) {
        this.choices[3] = MarbleDestination.DEPOT2;
        resource4Choice.getChildren().forEach(x -> x.setStyle("-fx-background-size: 100% auto"));
        pressedButton((Button)actionEvent.getSource());
    }

    public void chooseDepot3Resource4(ActionEvent actionEvent) {
        this.choices[3] = MarbleDestination.DEPOT3;
        resource4Choice.getChildren().forEach(x -> x.setStyle("-fx-background-size: 100% auto"));
        pressedButton((Button)actionEvent.getSource());
    }

    public void chooseExtraDepotResource4(ActionEvent actionEvent) {
        this.choices[3] = MarbleDestination.EXTRA;
        resource4Choice.getChildren().forEach(x -> x.setStyle("-fx-background-size: 100% auto"));
        pressedButton((Button)actionEvent.getSource());
    }

    public void chooseDiscardResource4(ActionEvent actionEvent) {
        this.choices[3] = MarbleDestination.DISCARD;
        resource4Choice.getChildren().forEach(x -> x.setStyle("-fx-background-size: 100% auto"));
        pressedButton((Button)actionEvent.getSource());
    }

    private void showOkButton(){
        boolean toShowOK = true;
        if(resourcesToSettle.size()>0){
            if(choices[0]==null) toShowOK = false;
        }
        if(resourcesToSettle.size()>1){
            if(choices[1]==null) toShowOK = false;
        }
        if(resourcesToSettle.size()>2){
            if(choices[2]==null) toShowOK = false;
        }
        if(resourcesToSettle.size()>3){
            if(choices[3]==null) toShowOK = false;
        }
        if(toShowOK) okButton.setVisible(true);
    }
}
