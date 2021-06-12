package it.polimi.ingsw.Client.view.GUI;

import it.polimi.ingsw.Client.clientstates.gui.MoveResourcesGUI;
import it.polimi.ingsw.Client.reducedmodel.ReducedDepot;
import it.polimi.ingsw.Commons.ResourceType;
import it.polimi.ingsw.Utils.Messages.ClientMessages.MoveResourcesMessage;
import it.polimi.ingsw.Utils.MoveFromExtraToNormalAction;
import it.polimi.ingsw.Utils.MoveFromNormalToExtraAction;
import it.polimi.ingsw.Utils.MoveFromNormalToNormalAction;
import it.polimi.ingsw.Utils.ResourceLocator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
    public ImageView resourceExtraDepot1, resourceExtraDepot2, extraDepotPot1, extraDepotPot2;

    @FXML
    public Text extraDepotsText;

    @FXML
    public TextField depotExtraOcc;

    @FXML
    public Button minusResourceExtra,plusResourceExtra,okButtonExtraDepotOcc;

    boolean sourceAlreadySelected = false,isNormalToNormal = false,isExtraToNormal = false,isNormalToExtra = false;
    private int normalDepotIndexSource,normalDepotIndexDestination, extraDepotIndexSource, extraDepotIndexDestination;
    private int occ=1;

    private MoveResourcesGUI state;

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
            extraDepotPot1.setVisible(true);
            resourceExtraDepot1.setImage(new Image(ResourceLocator.retrieveResourceTypeImage(client.getGame()
                    .getPlayer(client.getUser()).getPersonalBoard().getWarehouse().
                            getExtraDepots()[0].getType())));
            resourceExtraDepot1.setVisible(true);
            fromNormalToExtra.setVisible(true);
            fromExtraToNormal.setVisible(true);
        }

        if(state.getNumOfExtraDepots() > 1){
            extraDepotPot2.setVisible(true);
            resourceExtraDepot2.setImage(new Image(ResourceLocator.retrieveResourceTypeImage(client.getGame()
                    .getPlayer(client.getUser()).getPersonalBoard().getWarehouse().
                            getExtraDepots()[1].getType())));
            resourceExtraDepot2.setVisible(true);
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
        ResourceType typeToSearch = client.getGame().getPlayer(client.getUser()).
                getPersonalBoard().getWarehouse().getNormalDepots()[depot-1].getType();
        return Arrays.stream(client.getGame().getPlayer(client.getUser()).
                getPersonalBoard().getWarehouse().getExtraDepots()).anyMatch(x->x!=null && x.getType() == typeToSearch);
    }

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

    private boolean hasNormalDepotOfType(int extraDepot){
        ResourceType typeToSearch = client.getGame().getPlayer(client.getUser()).
                getPersonalBoard().getWarehouse().getExtraDepots()[extraDepot-1].getType();
        return Arrays.stream(client.getGame().getPlayer(client.getUser()).
                getPersonalBoard().getWarehouse().getNormalDepots()).anyMatch(x -> x.getType() == null || x.getType() == typeToSearch);
    }

    private void sendFromNormalToNormalMessage()
    {
        MoveResourcesMessage messageToSend = new MoveResourcesMessage();
        messageToSend.setMoveAction(new MoveFromNormalToNormalAction(normalDepotIndexSource,normalDepotIndexDestination));
        state.setMessage(messageToSend);
        state.manageUserInteraction();
        handleCloseMoveAction();
    }
    private void sendFromNormalToExtraMessage()
    {
        MoveResourcesMessage messageToSend = new MoveResourcesMessage();
        messageToSend.setMoveAction(new MoveFromNormalToExtraAction(normalDepotIndexSource,occ,extraDepotIndexDestination));
        state.setMessage(messageToSend);
        state.manageUserInteraction();
        handleCloseMoveAction();
    }
    private void sendFromExtraToNormalMessage()
    {
        MoveResourcesMessage messageToSend = new MoveResourcesMessage();
        messageToSend.setMoveAction(new MoveFromExtraToNormalAction(extraDepotIndexSource, occ, normalDepotIndexDestination));
        state.setMessage(messageToSend);
        state.manageUserInteraction();
        handleCloseMoveAction();
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
            if(isNormalToNormal)
                sendFromNormalToNormalMessage();
            else if(isExtraToNormal){
                setOccExtraToNormal();
            }
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
            if(isNormalToNormal)
                sendFromNormalToNormalMessage();
            else if(isExtraToNormal){
                setOccExtraToNormal();
            }
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
            if(isNormalToNormal)
                sendFromNormalToNormalMessage();
            else if(isExtraToNormal){
                setOccExtraToNormal();
            }
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

    @FXML
    public void minusDepotOcc()
    {
        if(occ > 1)
        {
            occ--;
            depotExtraOcc.setText("" + occ);
        }
    }

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

    @FXML
    public void handleOkButtonDepotOcc()
    {
        if(isNormalToExtra)
            sendFromNormalToExtraMessage();
        else if(isExtraToNormal)
                sendFromExtraToNormalMessage();
    }
}
