package it.polimi.ingsw.Client.view.GUI;

import it.polimi.ingsw.Client.clientstates.gui.MoveResourcesGUI;
import it.polimi.ingsw.Client.reducedmodel.ReducedDepot;
import it.polimi.ingsw.Commons.ResourceType;
import it.polimi.ingsw.Utils.Messages.ClientMessages.MoveResourcesMessage;
import it.polimi.ingsw.Utils.MoveFromNormalToExtraAction;
import it.polimi.ingsw.Utils.MoveFromNormalToNormalAction;
import it.polimi.ingsw.Utils.ResourceLocator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Arrays;

public class MoveActionController extends Controller{
    @FXML
    public AnchorPane anchorMoveAction;

    @FXML
    public Text moveActionText,typeOfMoveText;

    @FXML
    public Button closeMoveAction;

    @FXML
    public Button depot1,depot2,depot3,fromNormalToNormal,fromNormalToExtra,fromExtraToNormal,
        resExtraDepot1,resExtraDepot2;

    @FXML
    public ImageView res1Depot1, res1Depot2, res2Depot2, res1Depot3, res2Depot3, res3Depot3;

    @FXML
    public Text extraDepotsText;

    boolean sourceAlreadySelected = false,isNormalToNormal = false,isExtraToNormal = false,isNormalToExtra = false;
    private int normalDepotIndexSource,normalDepotIndexDestination,extraDepotIndexDestination;

    private MoveResourcesGUI state;

    @FXML
    @Override
    public void initialize() {
        this.client = GUIApp.client;

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
        depot1.setVisible(false);
        depot2.setVisible(false);
        depot3.setVisible(false);
        resExtraDepot1.setVisible(false);
        resExtraDepot2.setVisible(false);
        initializeDepots();
        state = new MoveResourcesGUI(this.client);
    }

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

    }

    private void loadImageResource(ImageView target, ResourceType resourceType) {
        if(ResourceLocator.retrieveResourceTypeImage(resourceType) != null)
            target.setImage(new Image(ResourceLocator.retrieveResourceTypeImage(resourceType)));
    }

    @FXML
    public void handleCloseMoveAction()
    {
        Stage stage = (Stage) closeMoveAction.getScene().getWindow();
        stage.close();
    }

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

    private boolean hasExtraDepotOfType(int depot)
    {
        ResourceType typeToSearch = client.getGame().getPlayer(client.getUser()).getPersonalBoard().getWarehouse().getNormalDepots()[depot-1].getType();
        return Arrays.stream(client.getGame().getPlayer(client.getUser()).getPersonalBoard().getWarehouse().getExtraDepots()).anyMatch(x->x!=null && x.getType() == typeToSearch);
    }

    @FXML
    public void handleExtratoNormal()
    {
        typeOfMoveText.setText("Choose the depot source: ");
        fromNormalToExtra.setVisible(false);
        fromNormalToNormal.setVisible(false);
        fromExtraToNormal.setVisible(false);
        depot1.setVisible(true);
        depot2.setVisible(true);
        depot3.setVisible(true);
        isExtraToNormal = false;
    }

    private void sendFromNormalToNormalMessage()
    {
        MoveResourcesMessage messageToSend = new MoveResourcesMessage();
        messageToSend.setMoveAction(new MoveFromNormalToNormalAction(normalDepotIndexSource,normalDepotIndexDestination));
        state.setMessage(messageToSend);
        state.manageUserInteraction();
    }
    private void sendFromNormalToExtraMessage()
    {
        MoveResourcesMessage messageToSend = new MoveResourcesMessage();
        messageToSend.setMoveAction(new MoveFromNormalToExtraAction(normalDepotIndexSource,0,extraDepotIndexDestination));
        state.setMessage(messageToSend);
        state.manageUserInteraction();
    }

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
            sendFromNormalToNormalMessage();
        }
    }
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
            sendFromNormalToNormalMessage();
        }
    }
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
            sendFromNormalToNormalMessage();
        }
    }
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
    private void nextStepSelectedSourceNormalToExtra() {
        sourceAlreadySelected = true;
        typeOfMoveText.setText("Select the extra depot destination :");
        depot1.setVisible(false);
        depot2.setVisible(false);
        depot3.setVisible(false);
        ResourceType resourceSelectedSource = client.getGame().getPlayer(client.getUser()).getPersonalBoard().getWarehouse().getNormalDepots()[normalDepotIndexSource].getType();
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
    @FXML
    public void handleExtraDepot1()
    {
        if(!sourceAlreadySelected)
        {

        }
        else{
            extraDepotIndexDestination = 1;
            //TODO inserire il numero di occorrenze da muovere nell'extra depot
            sendFromNormalToExtraMessage();
        }
    }
    @FXML
    public void handleExtraDepot2()
    {
        if(!sourceAlreadySelected)
        {

        }
        else{
            extraDepotIndexDestination = 2;
            //TODO inserire il numero di occorrenze da muovere nell'extra depot
            sendFromNormalToExtraMessage();
        }
    }
}
