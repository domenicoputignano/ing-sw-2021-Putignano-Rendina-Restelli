package it.polimi.ingsw.Client.view.GUI;

import it.polimi.ingsw.Client.reducedmodel.ReducedSoloMode;
import it.polimi.ingsw.Commons.ResourceType;
import it.polimi.ingsw.Utils.Messages.ClientMessages.EndTurnMessage;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.TakeResourcesFromMarketUpdate;
import it.polimi.ingsw.Utils.ResourceLocator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class PlayerBoardController extends Controller {
    public HBox strongboxResources;
    Stage actions;
    Stage move;

    @FXML
    public ImageView leaderCard1;

    @FXML
    public Button chooseAction,moveAction,endTurn,otherPlayers;

    @FXML
    public ImageView leaderCard2;
    @FXML
    private static final String CURSOR = "/gui/img/cursor.png";

    public ImageView[] cells = new ImageView[25];
    public ImageView[] favorTiles = new ImageView[3];

    @FXML
    public ImageView cell0,cell1,cell2,cell3,cell4,cell5,cell6,cell7,cell8,cell9,cell10
            ,cell11,cell12,cell13,cell14,cell15,cell16,cell17,cell18,cell19,cell20,cell21,
            cell22,cell23,cell24;
    @FXML
    public ImageView favorTile1,favorTile2,favorTile3;

    @FXML
    public ImageView topCard1,topCard2,topCard3,bgSlots1,bgSlots2,bgSlots3;

    @FXML
    public ImageView depot1, depot21, depot22, depot31, depot32, depot33;

    @FXML
    Text numCoinStrongbox, numServantStrongbox, numShieldStrongbox, numStoneStrongbox;

    public void initialize() {
        super.initialize();

        BackgroundSize bSize = new BackgroundSize(80, 80, true, true, true, true);

        center.setBackground(new Background(new BackgroundImage(new Image("/gui/img/background.jpg"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bSize)));
        setFont(chooseAction,24);
        setFont(moveAction,24);
        setFont(endTurn,24);
        setFont(otherPlayers,21);
        setFont(numCoinStrongbox, 22);
        setFont(numServantStrongbox, 22);
        setFont(numShieldStrongbox, 22);
        setFont(numStoneStrongbox, 22);
        otherPlayers.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        endTurn.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        chooseAction.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        moveAction.setStyle("-fx-text-fill: rgb(35, 25, 22);");

        this.client = GUIApp.client;

        if(client.getGame().getPlayer(client.getUser()).getNumOfLeaderCards() > 0)
            leaderCard1.setImage(new Image(client.getGame().getPlayer(client.getUser()).getAvailableLeaderCards().get(0).toImage()));

        if(client.getGame().getPlayer(client.getUser()).getNumOfLeaderCards() > 1)
            leaderCard2.setImage(new Image(client.getGame().getPlayer(client.getUser()).getAvailableLeaderCards().get(1).toImage()));
        strongboxResources.setVisible(true);
        initializeCells();
        initializeDepots();
        if(client.getUI().isSoloMode())
            initializeFaithTrackWithBlackCross();
        else
            initializeFaithMarker();
        initializeSlots();
        if(!client.getUI().hasDoneNormalAction())
            endTurn.setVisible(false);
        else endTurn.setVisible(true);
    }

    private void initializeCells()
    {
        cells[0] = cell0;
        cells[1] = cell1;
        cells[2] = cell2;
        cells[3] = cell3;
        cells[4] = cell4;
        cells[5] = cell5;
        cells[6] = cell6;
        cells[7] = cell7;
        cells[8] = cell8;
        cells[9] = cell9;
        cells[10] = cell10;
        cells[11] = cell11;
        cells[12] = cell12;
        cells[13] = cell13;
        cells[14] = cell14;
        cells[15] = cell15;
        cells[16] = cell16;
        cells[17] = cell17;
        cells[18] = cell18;
        cells[19] = cell19;
        cells[20] = cell20;
        cells[21] = cell21;
        cells[22] = cell22;
        cells[23] = cell23;
        cells[24] = cell24;
        favorTiles[0] = favorTile1;
        favorTiles[1] = favorTile2;
        favorTiles[2] = favorTile3;
        favorTiles[0].setImage(new Image("/gui/img/favorTile1D.png"));
        favorTiles[1].setImage(new Image("/gui/img/favorTile2D.png"));
        favorTiles[2].setImage(new Image("/gui/img/favorTile3D.png"));
        cells[0].setImage(new Image("/gui/img/faith.png"));
    }

    private void initializeSlots()
    {
        if(!client.getGame().getPlayer(client.getUser()).getPersonalBoard().isEmptySlot(0)){
            topCard1.setImage(new Image(client.getGame().getPlayer(client.getUser()).getPersonalBoard().peekTopCardInSlot(0).toImage()));
            if(client.getGame().getPlayer(client.getUser()).getPersonalBoard().getSlot(0).getNumOfStackedCards()>1)
                bgSlots1.setVisible(true);
        }
        if(!client.getGame().getPlayer(client.getUser()).getPersonalBoard().isEmptySlot(1)){
            topCard2.setImage(new Image(client.getGame().getPlayer(client.getUser()).getPersonalBoard().peekTopCardInSlot(1).toImage()));
            if(client.getGame().getPlayer(client.getUser()).getPersonalBoard().getSlot(1).getNumOfStackedCards()>1)
                bgSlots2.setVisible(true);
        }
        if(!client.getGame().getPlayer(client.getUser()).getPersonalBoard().isEmptySlot(2)){
            topCard3.setImage(new Image(client.getGame().getPlayer(client.getUser()).getPersonalBoard().peekTopCardInSlot(2).toImage()));
            if(client.getGame().getPlayer(client.getUser()).getPersonalBoard().getSlot(2).getNumOfStackedCards()>1)
                bgSlots3.setVisible(true);
        }
    }

    private void initializeFaithTrackWithBlackCross()
    {
        clearFaithMarker();
        int faith = client.getGame().getPlayer(client.getUser()).getPersonalBoard().getFaithTrack().getFaithMarker();
        int blackCross = ((ReducedSoloMode) client.getGame()).getBlackCross();
        if(faith == blackCross)
            cells[faith].setImage(new Image("/gui/img/blackCross&Faith.png"));
        else{
            cells[faith].setImage(new Image("/gui/img/faith.png"));
            cells[blackCross].setImage(new Image("/gui/img/blackCross.png"));
        }
    }
    private void initializeFaithMarker()
    {
        clearFaithMarker();
        cells[client.getGame().getPlayer(client.getUser()).getPersonalBoard().getFaithTrack().getFaithMarker()].setImage(new Image("/gui/img/faith.png"));
    }

    private void clearFaithMarker()
    {
        for(int i=0;i<25;i++)
            cells[i].setImage(null);
    }

    private void initializeDepots(){

        // SETUP DEPOT 1
        if(client.getGame().getPlayer(client.getUser()).getPersonalBoard().getWarehouse().getNormalDepots()[0].getOcc()==1){
            loadImageResource(depot1, client.getGame().getPlayer(client.getUser()).
                    getPersonalBoard().getWarehouse().getNormalDepots()[0].getType());
        }

        // SETUP DEPOT 2
        if(client.getGame().getPlayer(client.getUser()).getPersonalBoard().getWarehouse().getNormalDepots()[1].getOcc()==2){
            loadImageResource(depot21, client.getGame().getPlayer(client.getUser()).
                    getPersonalBoard().getWarehouse().getNormalDepots()[1].getType());
            loadImageResource(depot22, client.getGame().getPlayer(client.getUser()).
                    getPersonalBoard().getWarehouse().getNormalDepots()[1].getType());
        } else if(client.getGame().getPlayer(client.getUser()).getPersonalBoard().getWarehouse().getNormalDepots()[1].getOcc()==1){
            loadImageResource(depot21, client.getGame().getPlayer(client.getUser()).
                    getPersonalBoard().getWarehouse().getNormalDepots()[1].getType());
        }

        // SETUP DEPOT 3
        if(client.getGame().getPlayer(client.getUser()).getPersonalBoard().getWarehouse().getNormalDepots()[2].getOcc()==3){
            loadImageResource(depot31, client.getGame().getPlayer(client.getUser()).
                    getPersonalBoard().getWarehouse().getNormalDepots()[2].getType());
            loadImageResource(depot32, client.getGame().getPlayer(client.getUser()).
                    getPersonalBoard().getWarehouse().getNormalDepots()[2].getType());
            loadImageResource(depot33, client.getGame().getPlayer(client.getUser()).
                    getPersonalBoard().getWarehouse().getNormalDepots()[2].getType());
        } else if(client.getGame().getPlayer(client.getUser()).getPersonalBoard().getWarehouse().getNormalDepots()[2].getOcc()==2){
            loadImageResource(depot31, client.getGame().getPlayer(client.getUser()).
                    getPersonalBoard().getWarehouse().getNormalDepots()[2].getType());
            loadImageResource(depot32, client.getGame().getPlayer(client.getUser()).
                    getPersonalBoard().getWarehouse().getNormalDepots()[2].getType());
        } else if(client.getGame().getPlayer(client.getUser()).getPersonalBoard().getWarehouse().getNormalDepots()[2].getOcc()==1){
            loadImageResource(depot31, client.getGame().getPlayer(client.getUser()).
                    getPersonalBoard().getWarehouse().getNormalDepots()[2].getType());
        }
    }

    private void loadImageResource(ImageView target, ResourceType resourceType){
        target.setImage(new Image(ResourceLocator.retrieveResourceTypeImage(resourceType)));
    }

    @FXML
    public void handleChooseActionButton()
    {
        actions = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/ChooseActionPage.fxml"));
            Parent root = loader.load();
            GUIApp.controller = loader.getController();
            Scene scene = new Scene(Objects.requireNonNull(root), 820, 520, Color.TRANSPARENT);
            scene.setCursor(new ImageCursor(new Image(CURSOR), 36, 45));
            actions.initStyle(StageStyle.TRANSPARENT);
            actions.setAlwaysOnTop(true);
            scene.setUserData(loader);
            actions.initModality(Modality.WINDOW_MODAL);
            actions.initOwner(GUIApp.getStage());
            scene.setUserData(loader);
            actions.setScene(scene);

        } catch (IOException e) {
                e.printStackTrace();
            }
        actions.show();
    }
    @FXML
    public void handleMoveActionButton()
    {
        move = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/MoveActionPage.fxml"));
            Parent root = loader.load();
            GUIApp.controller = loader.getController();
            Scene scene = new Scene(Objects.requireNonNull(root), 1180, 750, Color.TRANSPARENT);
            scene.setCursor(new ImageCursor(new Image(CURSOR), 36, 45));
            move.initStyle(StageStyle.TRANSPARENT);
            move.setAlwaysOnTop(true);
            scene.setUserData(loader);
            move.initModality(Modality.WINDOW_MODAL);
            move.initOwner(GUIApp.getStage());
            scene.setUserData(loader);
            move.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
        move.show();
    }

    @FXML
    public void handleEndTurn()
    {
        client.sendMessage(new EndTurnMessage());
    }

    public void showTakeResourcesFromMarketUpdate(TakeResourcesFromMarketUpdate update){
        Alert alertUpdate = new Alert(Alert.AlertType.INFORMATION);
        alertUpdate.setTitle("Take resources from market correctly performed!");
        alertUpdate.setX(400);
        alertUpdate.setY(200);
        alertUpdate.setContentText("You correctly performed you action: \n" + "You got " + update.getEarnedResources());
        alertUpdate.show();
    }
}
