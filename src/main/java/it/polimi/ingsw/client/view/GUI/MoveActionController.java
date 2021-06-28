package it.polimi.ingsw.client.view.GUI;

import it.polimi.ingsw.client.clientstates.gui.MoveResourcesGUI;
import it.polimi.ingsw.client.reducedmodel.ReducedDepot;
import it.polimi.ingsw.commons.ResourceType;
import it.polimi.ingsw.utils.messages.clientMessages.MoveResourcesMessage;
import it.polimi.ingsw.utils.MoveFromExtraToNormalAction;
import it.polimi.ingsw.utils.MoveFromNormalToExtraAction;
import it.polimi.ingsw.utils.MoveFromNormalToNormalAction;
import it.polimi.ingsw.utils.ResourceLocator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Arrays;
/**
 * Class that represents the controller of the Move Action page of the game
 * on this page the user can perform the Move Resources action by choosing
 * the type of move to be carried out and moving the resources of his choice
 */
public class MoveActionController extends Controller{
    /**
     * Attribute that represents the central pane of the page
     */
    @FXML
    public AnchorPane anchorMoveAction;
    /**
     * Attribute that represents the text where the title of the page is shown
     */
    @FXML
    public Text moveActionText,typeOfMoveText,extraDepotsText;
    /**
     * Attribute that represents the button to close the page
     */
    @FXML
    public Button closeMoveAction;
    /**
     * Attributes that represent the buttons to select the type of Move that the player want to perform
     */
    @FXML
    public Button depot1,depot2,depot3,fromNormalToNormal,fromNormalToExtra,fromExtraToNormal,
        resExtraDepot1,resExtraDepot2;
    /**
     * Attributes that represents the imageView of the resources owned within the depots
     */
    @FXML
    public ImageView res1Depot1, res1Depot2, res2Depot2, res1Depot3, res2Depot3, res3Depot3;
    /**
     * Attributes that represents the imageView of the resources owned within the extra depots
     */
    @FXML
    public ImageView resourceExtraDepot1, resourceExtraDepot2, extraDepotPot1, extraDepotPot2,
            resource1ExtraDepot1,resource2ExtraDepot1,resource1ExtraDepot2,resource2ExtraDepot2;;

    /**
     * Attribute that represent the textField which takes into account the number of resources
     * that the user want to move from/to the extra depots
     */
    @FXML
    public TextField depotExtraOcc;

    /**
     * attributes that represent the buttons to increase and decrease the number of resources
     * that the user want to move from / to the extra depots
     */
    @FXML
    public Button minusResourceExtra,plusResourceExtra,okButtonExtraDepotOcc;
    /**
     * attributes used to account for which depots can be selected during the action based on what has been selected
     */
    boolean sourceAlreadySelected = false,isNormalToNormal = false,isExtraToNormal = false,isNormalToExtra = false;
    private int normalDepotIndexSource,normalDepotIndexDestination, extraDepotIndexSource, extraDepotIndexDestination;
    private int occ=1;

    private MoveResourcesGUI state;
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
        state = new MoveResourcesGUI(this.client);

        anchorMoveAction.setBackground(new Background(new BackgroundImage(new Image("/gui/img/exit_tab.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false))));
        moveActionText.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(moveActionText,39);
        typeOfMoveText.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(typeOfMoveText,30);
        extraDepotsText.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(extraDepotsText,30);
        fromExtraToNormal.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(fromExtraToNormal,25);
        fromNormalToNormal.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(fromNormalToNormal,24);
        fromNormalToExtra.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(fromNormalToExtra,25);
        setFont(okButtonExtraDepotOcc,27);
        depot1.setVisible(false);
        depot2.setVisible(false);
        depot3.setVisible(false);
        depotExtraOcc.setVisible(false);
        minusResourceExtra.setVisible(false);
        plusResourceExtra.setVisible(false);
        setFont(depotExtraOcc,18);
        resExtraDepot1.setVisible(false);
        resExtraDepot2.setVisible(false);
        initializeDepots();
    }

    /**
     * method used to initialize the images within the depots within the depots based
     * on what you actually own
     */
    private void initializeDepots(){

        // SETUP DEPOT 1
        if(client.getGame().getPlayer(client.getUser()).getPersonalBoard().getWarehouse().getNormalDepots()[0].getOcc()==1){
            loadImageResource(res1Depot1, client.getGame().getPlayer(client.getUser()).
                    getPersonalBoard().getWarehouse().getNormalDepots()[0].getType());
        }

        // SETUP DEPOT 2
        if(client.getGame().getPlayer(client.getUser()).getPersonalBoard().getWarehouse().getNormalDepots()[1].getOcc()==2){
            loadImageResource(res1Depot2, client.getGame().getPlayer(client.getUser()).
                    getPersonalBoard().getWarehouse().getNormalDepots()[1].getType());
            loadImageResource(res2Depot2, client.getGame().getPlayer(client.getUser()).
                    getPersonalBoard().getWarehouse().getNormalDepots()[1].getType());
        } else if(client.getGame().getPlayer(client.getUser()).getPersonalBoard().getWarehouse().getNormalDepots()[1].getOcc()==1){
            loadImageResource(res1Depot2, client.getGame().getPlayer(client.getUser()).
                    getPersonalBoard().getWarehouse().getNormalDepots()[1].getType());
        }

        // SETUP DEPOT 3
        if(client.getGame().getPlayer(client.getUser()).getPersonalBoard().getWarehouse().getNormalDepots()[2].getOcc()==3){
            loadImageResource(res1Depot3, client.getGame().getPlayer(client.getUser()).
                    getPersonalBoard().getWarehouse().getNormalDepots()[2].getType());
            loadImageResource(res2Depot3, client.getGame().getPlayer(client.getUser()).
                    getPersonalBoard().getWarehouse().getNormalDepots()[2].getType());
            loadImageResource(res3Depot3, client.getGame().getPlayer(client.getUser()).
                    getPersonalBoard().getWarehouse().getNormalDepots()[2].getType());
        } else if(client.getGame().getPlayer(client.getUser()).getPersonalBoard().getWarehouse().getNormalDepots()[2].getOcc()==2){
            loadImageResource(res1Depot3, client.getGame().getPlayer(client.getUser()).
                    getPersonalBoard().getWarehouse().getNormalDepots()[2].getType());
            loadImageResource(res2Depot3, client.getGame().getPlayer(client.getUser()).
                    getPersonalBoard().getWarehouse().getNormalDepots()[2].getType());
        } else if(client.getGame().getPlayer(client.getUser()).getPersonalBoard().getWarehouse().getNormalDepots()[2].getOcc()==1){
            loadImageResource(res1Depot3, client.getGame().getPlayer(client.getUser()).
                    getPersonalBoard().getWarehouse().getNormalDepots()[2].getType());
        }

        if(state.getNumOfExtraDepots() > 0){
            ReducedDepot extraDepot1 = client.getGame()
                    .getPlayer(client.getUser()).getPersonalBoard().getWarehouse().
                            getExtraDepots()[0];
            extraDepotPot1.setVisible(true);
            resourceExtraDepot1.setImage(new Image(ResourceLocator.retrieveResourceTypeImage(extraDepot1.getType())));
            resourceExtraDepot1.setVisible(true);
            fromNormalToExtra.setVisible(true);
            fromExtraToNormal.setVisible(true);
            if(extraDepot1.getOcc() > 0)
                resource1ExtraDepot1.setImage(new Image(ResourceLocator.retrieveResourceTypeImage(extraDepot1.getType())));
            if(extraDepot1.getOcc() > 1)
                resource2ExtraDepot1.setImage(new Image(ResourceLocator.retrieveResourceTypeImage(extraDepot1.getType())));
        }

        if(state.getNumOfExtraDepots() > 1){
            ReducedDepot extraDepot2 = client.getGame()
                    .getPlayer(client.getUser()).getPersonalBoard().getWarehouse().
                            getExtraDepots()[1];
            extraDepotPot2.setVisible(true);
            resourceExtraDepot2.setImage(new Image(ResourceLocator.retrieveResourceTypeImage(extraDepot2.getType())));
            resourceExtraDepot2.setVisible(true);
            if(extraDepot2.getOcc() > 0)
                resource1ExtraDepot2.setImage(new Image(ResourceLocator.retrieveResourceTypeImage(extraDepot2.getType())));
            if(extraDepot2.getOcc() > 1)
                resource2ExtraDepot2.setImage(new Image(ResourceLocator.retrieveResourceTypeImage(extraDepot2.getType())));
        }

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
     * method used to close page of leader action over the main stage
     * It is performed when closeMoveAction button is pressed
     */
    @FXML
    public void handleCloseMoveAction()
    {
        Stage stage = (Stage) closeMoveAction.getScene().getWindow();
        stage.close();
    }

    /**
     * method used to manage a Normal To Normal move resources
     * It allows you to select depots and illuminate any destinations
     */
    @FXML
    public void handleNormaltoNormal()
    {
        typeOfMoveText.setText("Choose the depot source: ");
        fromNormalToExtra.setVisible(false);
        fromNormalToNormal.setVisible(false);
        fromExtraToNormal.setVisible(false);
        depot1.setVisible(true);
        depot2.setVisible(true);
        depot3.setVisible(true);
        depot1.setStyle("-fx-background-color: #e7f3ef;");
        depot2.setStyle("-fx-background-color: #e7f3ef;");
        depot3.setStyle("-fx-background-color: #e7f3ef;");
        isNormalToNormal = true;
    }

    /**
     * method used to manage a Normal To Extra move resources
     * It allows you to select depots and illuminate any destinations
     */
    @FXML
    public void handleNormaltoExtra()
    {
        typeOfMoveText.setText("Choose the depot source: ");
        fromNormalToExtra.setVisible(false);
        fromNormalToNormal.setVisible(false);
        fromExtraToNormal.setVisible(false);
        if(hasExtraDepotOfType(1)) {
            depot1.setVisible(true);
            depot1.setStyle("-fx-background-color: #e7f3ef;");
        }
        if(hasExtraDepotOfType(2)){
            depot2.setVisible(true);
            depot2.setStyle("-fx-background-color: #e7f3ef;");
        }
        if(hasExtraDepotOfType(3)) {
            depot3.setVisible(true);
            depot3.setStyle("-fx-background-color: #e7f3ef;");
        }
        isNormalToExtra = true;
    }

    /**
     * method that allows to understand if a depot referred to a resource
     * has associated an extra depot related to the same resource
     * @param depot depot under consideration
     * @return true if it exists, otherwise false
     */
    private boolean hasExtraDepotOfType(int depot)
    {
        ResourceType typeToSearch = client.getGame().getPlayer(client.getUser()).
                getPersonalBoard().getWarehouse().getNormalDepots()[depot-1].getType();
        return Arrays.stream(client.getGame().getPlayer(client.getUser()).
                getPersonalBoard().getWarehouse().getExtraDepots()).anyMatch(x->x!=null && x.getType() == typeToSearch);
    }
    /**
     * method used to manage a Extra To Normal move resources
     * It allows you to select depots and illuminate any destinations
     */
    @FXML
    public void handleExtratoNormal()
    {
        typeOfMoveText.setText("Choose the depot source: ");
        fromNormalToExtra.setVisible(false);
        fromNormalToNormal.setVisible(false);
        fromExtraToNormal.setVisible(false);

        if(state.getNumOfExtraDepots() > 0 && hasNormalDepotOfType(1)){
            resExtraDepot1.setVisible(true);
            resExtraDepot1.setStyle("-fx-background-color: #e7f3ef;");
        }
        if(state.getNumOfExtraDepots() > 1 && hasNormalDepotOfType(2)){
            resExtraDepot2.setVisible(true);
            resExtraDepot2.setStyle("-fx-background-color: #e7f3ef;");
        }

        isExtraToNormal = true;
    }
    /**
     * method that allows to understand if a extra depot referred to a resource
     * has associated a depot related to the same resource
     * @param extraDepot extradepot under consideration
     * @return true if it exists, otherwise false
     */
    private boolean hasNormalDepotOfType(int extraDepot){
        ResourceType typeToSearch = client.getGame().getPlayer(client.getUser()).
                getPersonalBoard().getWarehouse().getExtraDepots()[extraDepot-1].getType();
        return Arrays.stream(client.getGame().getPlayer(client.getUser()).
                getPersonalBoard().getWarehouse().getNormalDepots()).anyMatch(x -> x.getType() == null || x.getType() == typeToSearch);
    }

    /**
     * method used to send the update message relating to the move  From Normal To Normal action
     */
    private void sendFromNormalToNormalMessage()
    {
        MoveResourcesMessage messageToSend = new MoveResourcesMessage();
        messageToSend.setMoveAction(new MoveFromNormalToNormalAction(normalDepotIndexSource,normalDepotIndexDestination));
        state.setMessage(messageToSend);
        state.manageUserInteraction();
        handleCloseMoveAction();
    }
    /**
     * method used to send the update message relating to the move  From Normal To Extra action
     */
    private void sendFromNormalToExtraMessage()
    {
        MoveResourcesMessage messageToSend = new MoveResourcesMessage();
        messageToSend.setMoveAction(new MoveFromNormalToExtraAction(normalDepotIndexSource,occ,extraDepotIndexDestination));
        state.setMessage(messageToSend);
        state.manageUserInteraction();
        handleCloseMoveAction();
    }
    /**
     * method used to send the update message relating to the move  From Extra To Normal action
     */
    private void sendFromExtraToNormalMessage()
    {
        MoveResourcesMessage messageToSend = new MoveResourcesMessage();
        messageToSend.setMoveAction(new MoveFromExtraToNormalAction(extraDepotIndexSource, occ, normalDepotIndexDestination));
        state.setMessage(messageToSend);
        state.manageUserInteraction();
        handleCloseMoveAction();
    }

    /**
     * method that allows you to check which destination depots are
     * available after selecting depot 1 as the sender
     */
    @FXML
    public void handleDepot1()
    {
        if(!sourceAlreadySelected)
        {
            normalDepotIndexSource = 1;
            if(isNormalToNormal)
                nextStepSelectedSourceNormalToNormal();
            else if(isNormalToExtra)
                nextStepSelectedSourceNormalToExtra();
        }
        else{
            normalDepotIndexDestination = 1;
            if(isNormalToNormal)
                sendFromNormalToNormalMessage();
            else if(isExtraToNormal){
                setOccExtraToNormal();
            }
        }
    }
    /**
     * method that allows you to check which destination depots are
     * available after selecting depot 2 as the sender
     */
    @FXML
    public void handleDepot2()
    {
        if(!sourceAlreadySelected)
        {
            normalDepotIndexSource = 2;
            if(isNormalToNormal)
                nextStepSelectedSourceNormalToNormal();
            else if(isNormalToExtra)
                nextStepSelectedSourceNormalToExtra();
        }
        else{
            normalDepotIndexDestination = 2;
            if(isNormalToNormal)
                sendFromNormalToNormalMessage();
            else if(isExtraToNormal){
                setOccExtraToNormal();
            }
        }
    }
    /**
     * method that allows you to check which destination depots are
     * available after selecting depot 3 as the sender
     */
    @FXML
    public void handleDepot3()
    {
        if(!sourceAlreadySelected)
        {
            normalDepotIndexSource = 3;
            if(isNormalToNormal)
                nextStepSelectedSourceNormalToNormal();
            else if(isNormalToExtra)
                nextStepSelectedSourceNormalToExtra();
        }
        else{
            normalDepotIndexDestination = 3;
            if(isNormalToNormal)
                sendFromNormalToNormalMessage();
            else if(isExtraToNormal){
                setOccExtraToNormal();
            }
        }
    }

    /**
     * method that allows you to manage what to light up after choosing a sender
     * depot in a move normal to normal action
     */
    private void nextStepSelectedSourceNormalToNormal()
    {
        sourceAlreadySelected = true;
        typeOfMoveText.setText("Select the depot destination :");
        if(normalDepotIndexSource == 1)
        {
            depot1.setVisible(false);
        }
        if(normalDepotIndexSource == 2)
        {
            depot2.setVisible(false);
            if(client.getGame().getPlayer(client.getUser()).getPersonalBoard().getWarehouse().getNormalDepots()[1].getOcc()>1)
                depot1.setVisible(false);
        }
        if(normalDepotIndexSource == 3)
        {
            depot3.setVisible(false);
            if(client.getGame().getPlayer(client.getUser()).getPersonalBoard().getWarehouse().getNormalDepots()[2].getOcc()>1)
                depot1.setVisible(false);
            if(client.getGame().getPlayer(client.getUser()).getPersonalBoard().getWarehouse().getNormalDepots()[2].getOcc()>2)
            {
                depot2.setVisible(false);
                handleNormaltoNormal();
            }
        }
    }
    /**
     * method that allows you to manage what to light up after choosing a sender
     * depot in a move normal to extra action
     */
    private void nextStepSelectedSourceNormalToExtra() {
        sourceAlreadySelected = true;
        typeOfMoveText.setText("Select the extra depot destination :");
        depot1.setVisible(false);
        depot2.setVisible(false);
        depot3.setVisible(false);
        ResourceType resourceSelectedSource = client.getGame().getPlayer(client.getUser()).
                getPersonalBoard().getWarehouse().getNormalDepots()[normalDepotIndexSource-1].getType();
        if (state.getNumOfExtraDepots() > 0) {
            ReducedDepot extraDepot = client.getGame().getPlayer(client.getUser()).getPersonalBoard().getWarehouse().getExtraDepots()[0];
            if (extraDepot.getType() == resourceSelectedSource && extraDepot.getOcc() < 2) {
                resExtraDepot1.setVisible(true);
                resExtraDepot1.setStyle("-fx-background-color: #e7f3ef;");
            }
        }
        if (state.getNumOfExtraDepots() > 1) {
            ReducedDepot extraDepot = client.getGame().getPlayer(client.getUser()).getPersonalBoard().getWarehouse().getExtraDepots()[1];
            if (extraDepot.getType() == resourceSelectedSource && extraDepot.getOcc() < 2) {
                resExtraDepot2.setVisible(true);
                resExtraDepot2.setStyle("-fx-background-color: #e7f3ef;");
            }
        }
    }
    /**
     * method that allows you to manage what to light up after choosing a sender
     * depot in a move extra to normal action
     */
    private void nextStepSelectedSourceExtraToNormal() {
        sourceAlreadySelected = true;
        typeOfMoveText.setText("Select the normal depot destination!");
        ResourceType resourceToSearch = client.getGame().getPlayer(client.getUser()).
                getPersonalBoard().getWarehouse().getExtraDepots()[extraDepotIndexSource-1].getType();

        ReducedDepot normalDepot = client.getGame().getPlayer(client.getUser()).getPersonalBoard().getWarehouse().getNormalDepots()[0];
        if(normalDepot.getType() == null || normalDepot.getType() == resourceToSearch){
            if(normalDepot.getOcc() < normalDepot.getSize()){
                depot1.setVisible(true);
                depot1.setStyle("-fx-background-color: #e7f3ef;");
            }
        }

        normalDepot = client.getGame().getPlayer(client.getUser()).getPersonalBoard().getWarehouse().getNormalDepots()[1];
        if(normalDepot.getType() == null || normalDepot.getType() == resourceToSearch){
            if(normalDepot.getOcc() < normalDepot.getSize()) {
                depot2.setVisible(true);
                depot2.setStyle("-fx-background-color: #e7f3ef;");
            }
        }

        normalDepot = client.getGame().getPlayer(client.getUser()).getPersonalBoard().getWarehouse().getNormalDepots()[2];
        if(normalDepot.getType() == null || normalDepot.getType() == resourceToSearch){
            if(normalDepot.getOcc() < normalDepot.getSize()){
                depot3.setVisible(true);
                depot3.setStyle("-fx-background-color: #e7f3ef;");
            }
        }
    }

    /**
     * method to use to set the textField to the number of occurrences of resources
     * to be moved from extra depots to normal depots
     */
    private void setOccExtraToNormal(){
        ReducedDepot extraDepotSource = client.getGame().getPlayer(client.getUser()).
                getPersonalBoard().getWarehouse().getExtraDepots()[extraDepotIndexSource-1];

        if(extraDepotSource.getOcc() > 1){
            typeOfMoveText.setText("How many resources do you want to move?");
            minusResourceExtra.setVisible(true);
            plusResourceExtra.setVisible(true);
            depotExtraOcc.setVisible(true);
            okButtonExtraDepotOcc.setVisible(true);
        } else {
            this.occ = 1;
            sendFromExtraToNormalMessage();
        }
    }
    /**
     * method that allows you to check which destination depots are
     * available after selecting extra depot 1 as the sender
     */
    @FXML
    public void handleExtraDepot1()
    {
        if(!sourceAlreadySelected)
        {
            extraDepotIndexSource = 1;
            nextStepSelectedSourceExtraToNormal();
        }
        else{
            extraDepotIndexDestination = 1;
            setOccFromNormalToExtra();
        }
    }
    /**
     * method that allows you to check which destination depots are
     * available after selecting extra depot 2 as the sender
     */
    @FXML
    public void handleExtraDepot2()
    {
        if(!sourceAlreadySelected)
        {
            extraDepotIndexSource = 2;
            nextStepSelectedSourceExtraToNormal();
        }
        else{
            extraDepotIndexDestination = 2;
            setOccFromNormalToExtra();
        }
    }
    /**
     * method to use to set the textField to the number of occurrences of resources
     * to be moved from normal depots to extra depots
     */
    private void setOccFromNormalToExtra(){
        ReducedDepot normalDepotSource = client.getGame().getPlayer(client.getUser()).getPersonalBoard().getWarehouse().getNormalDepots()[normalDepotIndexSource-1];

        if(normalDepotSource.getOcc() > 1){
            typeOfMoveText.setText("How many resources do you want to move?");
            minusResourceExtra.setVisible(true);
            plusResourceExtra.setVisible(true);
            depotExtraOcc.setVisible(true);
            okButtonExtraDepotOcc.setVisible(true);
        } else {
            this.occ = 1;
            sendFromNormalToExtraMessage();
        }
    }
    /**
     * method used to decrease the number of occurrences of resources to be moved to / from the extra depots
     * It is performed when minusResourceExtra button is pressed
     */
    @FXML
    public void minusDepotOcc()
    {
        if(occ > 1)
        {
            occ--;
            depotExtraOcc.setText("" + occ);
        }
    }

    /**
     * method used to increase the number of occurrences of resources
     * to be moved to / from the extra depots
     * It is performed when plusResourceExtra button is pressed
     */
    @FXML
    public void plusDepotOcc()
    {
        if(isNormalToExtra){
            ReducedDepot normalDepotSource = client.getGame().getPlayer(client.getUser()).getPersonalBoard().getWarehouse().getNormalDepots()[normalDepotIndexSource-1];
            ReducedDepot extraDepotDestination = client.getGame().getPlayer(client.getUser()).getPersonalBoard().getWarehouse().getExtraDepots()[extraDepotIndexDestination-1];

            if(occ == 1 && normalDepotSource.getOcc()>1 && extraDepotDestination.getOcc() == 0)
            {
                occ++;
                depotExtraOcc.setText("" + occ);
            }
        } else if(isExtraToNormal){
            ReducedDepot extraDepotSource = client.getGame().getPlayer(client.getUser()).getPersonalBoard().getWarehouse().getExtraDepots()[extraDepotIndexSource-1];
            ReducedDepot normalDepotDestination = client.getGame().getPlayer(client.getUser()).getPersonalBoard().getWarehouse().getNormalDepots()[normalDepotIndexDestination-1];

            if(occ == 1 && extraDepotSource.getOcc()>1 && (normalDepotDestination.getSize()-normalDepotDestination.getOcc())>1){
                occ++;
                depotExtraOcc.setText("" + occ);
            }
        }

    }

    /**
     * method used to confirm the number of the resource that the user want to move from/to
     * extra depots
     * It is performed when okButtonExtraDepotOcc button is pressed
     */
    @FXML
    public void handleOkButtonDepotOcc()
    {
        if(isNormalToExtra)
            sendFromNormalToExtraMessage();
        else if(isExtraToNormal)
                sendFromExtraToNormalMessage();
    }
}
