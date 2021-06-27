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

/**
 * Class that represents the controller of the PositioningResources page of the game
 * In this page, which is shown as a popup, the user can select where to place pending resources
 * after incorrectly placing resources in the Take Resources From Market action
 */
public class PositioningResourcesController extends Controller{
    /**
     * Attribute that represents the button to confirm the view of the popup and close it
     */
    @FXML
    public Button okButton;
    /**
     * Attribute that represents the central pane of the page
     */
    @FXML
    public AnchorPane positioningResourcesPane;
    /**
     * Attributes that represent the imageView of the resources within the depots
     */
    @FXML
    public ImageView depot1, depot2left, depot2right, depot3left, depot3mid, depot3right;
    /**
     * Attributes that represent the text where the message of popup is shown
     */
    @FXML
    public Text titleMessage, subTitleDepots, subTitleIndication;
    /**
     * Attributes that represent the imageView of the pending resources to settle
     */
    @FXML
    public ImageView resource1ToSettle, resource2ToSettle, resource3ToSettle, resource4ToSettle;

    @FXML
    public VBox resource1Choice, resource2Choice, resource3Choice, resource4Choice;

    private MarbleDestination[] choices = new MarbleDestination[4];
    /**
     * Attribute that represents the list of the pending resources to settle
     */
    private List<ResourceType> resourcesToSettle;
    private PositioningResourcesGUI state;
    /**
     * Main method that initializes the scene within the stage
     * It takes care of setting the background of the scene
     * ,the font of the texts and buttons,
     * and the images of the resources
     */
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
    /**
     * method used to initialize the images within the depots within the depots based
     * on what you actually own
     */
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

    /**
     * method used to initialize the images of the pending resources to settle
     * @param resourcesToSettle list of the pending resources to settle
     */
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
    /**
     * method used to confirm the choices made in the positioning of pending resources
     * It is performed when okButton button is pressed
     */
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
    /**
     * method used to set a generic image of the resources
     * @param target imageView to be set with image related to the owned resource
     * @param resourceType resource to be inserted into the imageView
     */
    private void loadImageResource(ImageView target, ResourceType resourceType) {
        if(ResourceLocator.retrieveResourceTypeImage(resourceType) != null)
            target.setImage(new Image(ResourceLocator.retrieveResourceTypeImage(resourceType)));
    }

    /**
     * method used to show Ok button only after choosing the placement of all pending resources
     * @param target pressed Button
     */
    private void pressedButton(Button target){
        target.setStyle("-fx-background-size: 75% auto;");
        showOkButton();
    }

    /**
     * method used to handle the choice of the depot 1 for the first pending resource
     * @param actionEvent pressed button
     */
    public void chooseDepot1Resource1(ActionEvent actionEvent) {
        this.choices[0] = MarbleDestination.DEPOT1;
        resource1Choice.getChildren().forEach(x -> x.setStyle("-fx-background-size: 100% auto"));
        pressedButton((Button)actionEvent.getSource());
    }

    /**
     * method used to handle the choice of the depot 2 for the first pending resource
     * @param actionEvent pressed button
     */
    public void chooseDepot2Resource1(ActionEvent actionEvent) {
        this.choices[0] = MarbleDestination.DEPOT2;
        resource1Choice.getChildren().forEach(x -> x.setStyle("-fx-background-size: 100% auto"));
        pressedButton((Button)actionEvent.getSource());
    }
    /**
     * method used to handle the choice of the depot 3 for the first pending resource
     * @param actionEvent pressed button
     */
    public void chooseDepot3Resource1(ActionEvent actionEvent) {
        this.choices[0] = MarbleDestination.DEPOT3;
        resource1Choice.getChildren().forEach(x -> x.setStyle("-fx-background-size: 100% auto"));
        pressedButton((Button)actionEvent.getSource());
    }
    /**
     * method used to handle the choice of the extra depot for the first pending resource
     * @param actionEvent pressed button
     */
    public void chooseExtraDepotResource1(ActionEvent actionEvent) {
        this.choices[0] = MarbleDestination.EXTRA;
        resource1Choice.getChildren().forEach(x -> x.setStyle("-fx-background-size: 100% auto"));
        pressedButton((Button)actionEvent.getSource());
    }

    /**
     * method used to handle the choice to discard the first pending resource
     * @param actionEvent pressed button
     */
    public void chooseDiscardResource1(ActionEvent actionEvent) {
        this.choices[0] = MarbleDestination.DISCARD;
        resource1Choice.getChildren().forEach(x -> x.setStyle("-fx-background-size: 100% auto"));
        pressedButton((Button)actionEvent.getSource());
    }
    /**
     * method used to handle the choice of the depot 1 for the second pending resource
     * @param actionEvent pressed button
     */
    public void chooseDepot1Resource2(ActionEvent actionEvent) {
        this.choices[1] = MarbleDestination.DEPOT1;
        resource2Choice.getChildren().forEach(x -> x.setStyle("-fx-background-size: 100% auto"));
        pressedButton((Button)actionEvent.getSource());
    }
    /**
     * method used to handle the choice of the depot 2 for the second pending resource
     * @param actionEvent pressed button
     */
    public void chooseDepot2Resource2(ActionEvent actionEvent) {
        this.choices[1] = MarbleDestination.DEPOT2;
        resource2Choice.getChildren().forEach(x -> x.setStyle("-fx-background-size: 100% auto"));
        pressedButton((Button)actionEvent.getSource());
    }
    /**
     * method used to handle the choice of the depot 3 for the second pending resource
     * @param actionEvent pressed button
     */
    public void chooseDepot3Resource2(ActionEvent actionEvent) {
        this.choices[1] = MarbleDestination.DEPOT3;
        resource2Choice.getChildren().forEach(x -> x.setStyle("-fx-background-size: 100% auto"));
        pressedButton((Button)actionEvent.getSource());
    }
    /**
     * method used to handle the choice of the extra depot for the second pending resource
     * @param actionEvent pressed button
     */
    public void chooseExtraDepotResource2(ActionEvent actionEvent) {
        this.choices[1] = MarbleDestination.EXTRA;
        resource2Choice.getChildren().forEach(x -> x.setStyle("-fx-background-size: 100% auto"));
        pressedButton((Button)actionEvent.getSource());
    }
    /**
     * method used to handle the choice to discard the second pending resource
     * @param actionEvent pressed button
     */
    public void chooseDiscardResource2(ActionEvent actionEvent) {
        this.choices[1] = MarbleDestination.DISCARD;
        resource2Choice.getChildren().forEach(x -> x.setStyle("-fx-background-size: 100% auto"));
        pressedButton((Button)actionEvent.getSource());
    }
    /**
     * method used to handle the choice of the depot 1 for the third pending resource
     * @param actionEvent pressed button
     */
    public void chooseDepot1Resource3(ActionEvent actionEvent) {
        this.choices[2] = MarbleDestination.DEPOT1;
        resource3Choice.getChildren().forEach(x -> x.setStyle("-fx-background-size: 100% auto"));
        pressedButton((Button)actionEvent.getSource());
    }
    /**
     * method used to handle the choice of the depot 2 for the third pending resource
     * @param actionEvent pressed button
     */
    public void chooseDepot2Resource3(ActionEvent actionEvent) {
        this.choices[2] = MarbleDestination.DEPOT2;
        resource3Choice.getChildren().forEach(x -> x.setStyle("-fx-background-size: 100% auto"));
        pressedButton((Button)actionEvent.getSource());
    }
    /**
     * method used to handle the choice of the depot 3 for the third pending resource
     * @param actionEvent pressed button
     */
    public void chooseDepot3Resource3(ActionEvent actionEvent) {
        this.choices[2] = MarbleDestination.DEPOT3;
        resource3Choice.getChildren().forEach(x -> x.setStyle("-fx-background-size: 100% auto"));
        pressedButton((Button)actionEvent.getSource());
    }
    /**
     * method used to handle the choice of the extra depot for the third pending resource
     * @param actionEvent pressed button
     */
    public void chooseExtraDepotResource3(ActionEvent actionEvent) {
        this.choices[2] = MarbleDestination.EXTRA;
        resource3Choice.getChildren().forEach(x -> x.setStyle("-fx-background-size: 100% auto"));
        pressedButton((Button)actionEvent.getSource());
    }
    /**
     * method used to handle the choice to discard the third pending resource
     * @param actionEvent pressed button
     */
    public void chooseDiscardResource3(ActionEvent actionEvent) {
        this.choices[2] = MarbleDestination.DISCARD;
        resource3Choice.getChildren().forEach(x -> x.setStyle("-fx-background-size: 100% auto"));
        pressedButton((Button)actionEvent.getSource());
    }
    /**
     * method used to handle the choice of the depot 1 for the fourth pending resource
     * @param actionEvent pressed button
     */
    public void chooseDepot1Resource4(ActionEvent actionEvent) {
        this.choices[3] = MarbleDestination.DEPOT1;
        resource4Choice.getChildren().forEach(x -> x.setStyle("-fx-background-size: 100% auto"));
        pressedButton((Button)actionEvent.getSource());
    }
    /**
     * method used to handle the choice of the depot 2 for the fourth pending resource
     * @param actionEvent pressed button
     */
    public void chooseDepot2Resource4(ActionEvent actionEvent) {
        this.choices[3] = MarbleDestination.DEPOT2;
        resource4Choice.getChildren().forEach(x -> x.setStyle("-fx-background-size: 100% auto"));
        pressedButton((Button)actionEvent.getSource());
    }
    /**
     * method used to handle the choice of the depot 3 for the fourth pending resource
     * @param actionEvent pressed button
     */
    public void chooseDepot3Resource4(ActionEvent actionEvent) {
        this.choices[3] = MarbleDestination.DEPOT3;
        resource4Choice.getChildren().forEach(x -> x.setStyle("-fx-background-size: 100% auto"));
        pressedButton((Button)actionEvent.getSource());
    }
    /**
     * method used to handle the choice of the extra depot for the fourth pending resource
     * @param actionEvent pressed button
     */
    public void chooseExtraDepotResource4(ActionEvent actionEvent) {
        this.choices[3] = MarbleDestination.EXTRA;
        resource4Choice.getChildren().forEach(x -> x.setStyle("-fx-background-size: 100% auto"));
        pressedButton((Button)actionEvent.getSource());
    }
    /**
     * method used to handle the choice to discard the fourth pending resource
     * @param actionEvent pressed button
     */
    public void chooseDiscardResource4(ActionEvent actionEvent) {
        this.choices[3] = MarbleDestination.DISCARD;
        resource4Choice.getChildren().forEach(x -> x.setStyle("-fx-background-size: 100% auto"));
        pressedButton((Button)actionEvent.getSource());
    }

    /**
     * method used to show the okButton only when all the needed choices have been done
     */
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
