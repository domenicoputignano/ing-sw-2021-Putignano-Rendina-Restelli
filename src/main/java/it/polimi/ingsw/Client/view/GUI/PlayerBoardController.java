package it.polimi.ingsw.Client.view.GUI;

import it.polimi.ingsw.Client.reducedmodel.ReducedFaithTrack;
import it.polimi.ingsw.Client.reducedmodel.ReducedPlayer;
import it.polimi.ingsw.Client.reducedmodel.ReducedSoloMode;
import it.polimi.ingsw.Client.reducedmodel.ReducedStrongbox;
import it.polimi.ingsw.Commons.Effect;
import it.polimi.ingsw.Commons.LeaderCard;
import it.polimi.ingsw.Commons.ResourceType;
import it.polimi.ingsw.Commons.StateFavorTiles;
import it.polimi.ingsw.Utils.Messages.ClientMessages.EndTurnMessage;
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
import java.util.List;
import java.util.stream.Collectors;

public class PlayerBoardController extends Controller {
    public HBox strongboxResources;
    Stage actions;
    Stage move;

    @FXML
    public ImageView leaderCard1;

    @FXML
    public Button chooseAction,moveAction,endTurn,otherPlayers,viewDashboard,closePopup;

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
    public ImageView resource1LeaderCard1,resource2LeaderCard1,resource1LeaderCard2,resource2LeaderCard2;

    @FXML
    public ImageView slot1Card1,slot2Card1,slot3Card1,slot1Card2,slot2Card2,slot3Card2,slot1Card3,slot2Card3,slot3Card3;

    @FXML
    public ImageView depot1, depot21, depot22, depot31, depot32, depot33;

    @FXML
    public TextField username;

    @FXML
    public ImageView lightActive1,lightActive2;

    @FXML
    Text numCoinStrongbox, numServantStrongbox, numShieldStrongbox, numStoneStrongbox;

    ReducedPlayer playerToShow;

    public void initialize() {
        lightActive1.setImage(new Image("/gui/img/activeLeaderCardLight.png"));
        lightActive2.setImage(new Image("/gui/img/activeLeaderCardLight.png"));
        setFont(username,23);
        setFont(chooseAction,24);
        setFont(moveAction,24);
        setFont(endTurn,24);
        setFont(viewDashboard,24);
        setFont(otherPlayers,21);
        setFont(numCoinStrongbox, 22);
        setFont(numServantStrongbox, 22);
        setFont(numShieldStrongbox, 22);
        setFont(numStoneStrongbox, 22);
        otherPlayers.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        endTurn.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        chooseAction.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        moveAction.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        viewDashboard.setStyle("-fx-text-fill: rgb(35, 25, 22);");

        this.client = GUIApp.client;
    }

    private void initializeLeaderCards()
    {
        if(playerToShow.getNumOfLeaderCards() > 0) {
            LeaderCard l1 = playerToShow.getAvailableLeaderCards().get(0);
            leaderCard1.setImage(new Image(l1.toImage()));
            if(l1.isActive())
                lightActive1.setVisible(true);
        }
        if(playerToShow.getNumOfLeaderCards() > 1) {
            LeaderCard l2 = playerToShow.getAvailableLeaderCards().get(1);
            leaderCard2.setImage(new Image(l2.toImage()));
            if(l2.isActive())
                lightActive2.setVisible(true);
        }
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
        cells[0].setImage(new Image("/gui/img/faith.png"));
        favorTiles[0] = favorTile1;
        favorTiles[1] = favorTile2;
        favorTiles[2] = favorTile3;
        ReducedFaithTrack faithTrack = playerToShow.getPersonalBoard().getFaithTrack();

        if(faithTrack.getStateFavorTile(1) == StateFavorTiles.FACEDOWN)
            favorTiles[0].setImage(new Image("/gui/img/favorTile1D.png"));
        else
            if(faithTrack.getStateFavorTile(1) == StateFavorTiles.FACEUP)
                favorTiles[0].setImage(new Image("/gui/img/favorTile1.png"));
            else favorTiles[0].setImage(null);

        if(faithTrack.getStateFavorTile(2) == StateFavorTiles.FACEDOWN)
            favorTiles[1].setImage(new Image("/gui/img/favorTile2D.png"));
        else
            if(faithTrack.getStateFavorTile(2) == StateFavorTiles.FACEUP)
                favorTiles[1].setImage(new Image("/gui/img/favorTile2.png"));
            else favorTiles[1].setImage(null);

        if(faithTrack.getStateFavorTile(3) == StateFavorTiles.FACEDOWN)
            favorTiles[2].setImage(new Image("/gui/img/favorTile3D.png"));
        else
        if(faithTrack.getStateFavorTile(3) == StateFavorTiles.FACEUP)
            favorTiles[2].setImage(new Image("/gui/img/favorTile3.png"));
        else favorTiles[2].setImage(null);
    }

    private void initializeSlots()
    {
        if(!playerToShow.getPersonalBoard().isEmptySlot(0)){
            slot1Card1.setImage(new Image(playerToShow.getPersonalBoard().getSlot(0).getDevelopmentCardStack().get(0).toImage()));
            if(playerToShow.getPersonalBoard().getSlot(0).getNumOfStackedCards()>1)
                slot1Card2.setImage(new Image(playerToShow.getPersonalBoard().getSlot(0).getDevelopmentCardStack().get(1).toImage()));
            if(playerToShow.getPersonalBoard().getSlot(0).getNumOfStackedCards()>2)
                slot1Card3.setImage(new Image(playerToShow.getPersonalBoard().getSlot(0).getDevelopmentCardStack().get(2).toImage()));
        }
        if(!playerToShow.getPersonalBoard().isEmptySlot(1)){
            slot2Card1.setImage(new Image(playerToShow.getPersonalBoard().getSlot(1).getDevelopmentCardStack().get(0).toImage()));
            if(playerToShow.getPersonalBoard().getSlot(1).getNumOfStackedCards()>1)
                slot2Card2.setImage(new Image(playerToShow.getPersonalBoard().getSlot(1).getDevelopmentCardStack().get(1).toImage()));
            if(playerToShow.getPersonalBoard().getSlot(1).getNumOfStackedCards()>2)
                slot2Card3.setImage(new Image(playerToShow.getPersonalBoard().getSlot(1).getDevelopmentCardStack().get(2).toImage()));
        }
        if(!playerToShow.getPersonalBoard().isEmptySlot(2)){
            slot3Card1.setImage(new Image(playerToShow.getPersonalBoard().getSlot(2).getDevelopmentCardStack().get(0).toImage()));
            if(playerToShow.getPersonalBoard().getSlot(2).getNumOfStackedCards()>1)
                slot3Card2.setImage(new Image(playerToShow.getPersonalBoard().getSlot(2).getDevelopmentCardStack().get(1).toImage()));
            if(playerToShow.getPersonalBoard().getSlot(2).getNumOfStackedCards()>2)
                slot3Card3.setImage(new Image(playerToShow.getPersonalBoard().getSlot(2).getDevelopmentCardStack().get(2).toImage()));
        }
    }

    private void initializeExtraDepots()
    {
        List<LeaderCard> leaderCards = playerToShow.getAvailableLeaderCards();

        if(leaderCards.size() > 0)
        {
            LeaderCard l1 = leaderCards.get(0);
            if(l1.getLeaderEffect().getEffect() == Effect.EXTRADEPOT && l1.isActive())
            {
                int occLeaderCard1 = Arrays.stream(playerToShow.getPersonalBoard().getWarehouse().getExtraDepots())
                        .filter(x->x!=null && x.getType()==l1.getLeaderEffect().getType()).collect(Collectors.toList()).get(0)
                    .getOcc();
                if(occLeaderCard1 > 0)
                    resource1LeaderCard1.setImage(new Image(ResourceLocator.retrieveResourceTypeImage(l1.getLeaderEffect().getType())));
                if(occLeaderCard1 > 1)
                    resource2LeaderCard1.setImage(new Image(ResourceLocator.retrieveResourceTypeImage(l1.getLeaderEffect().getType())));
            }
        }
        if(leaderCards.size() > 1)
        {
            LeaderCard l2 = leaderCards.get(1);
            if(l2.getLeaderEffect().getEffect() == Effect.EXTRADEPOT && l2.isActive())
            {
                int occLeaderCard2 = Arrays.stream(playerToShow.getPersonalBoard().getWarehouse().getExtraDepots())
                        .filter(x->x!=null && x.getType()==l2.getLeaderEffect().getType()).collect(Collectors.toList()).get(0)
                        .getOcc();
                if(occLeaderCard2 > 0)
                    resource1LeaderCard2.setImage(new Image(ResourceLocator.retrieveResourceTypeImage(l2.getLeaderEffect().getType())));
                if(occLeaderCard2 > 1)
                    resource2LeaderCard2.setImage(new Image(ResourceLocator.retrieveResourceTypeImage(l2.getLeaderEffect().getType())));
            }
        }


    }

    private void initializeFaithTrackWithBlackCross()
    {
        clearFaithMarker();
        int faith = playerToShow.getPersonalBoard().getFaithTrack().getFaithMarker();
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
        cells[playerToShow.getPersonalBoard().getFaithTrack().getFaithMarker()].setImage(new Image("/gui/img/faith.png"));
    }

    private void clearFaithMarker()
    {
        for(int i=0;i<25;i++)
            cells[i].setImage(null);
    }

    private void initializeDepots(){

        // SETUP DEPOT 1
        if(playerToShow.getPersonalBoard().getWarehouse().getNormalDepots()[0].getOcc()==1){
            loadImageResource(depot1, playerToShow.getPersonalBoard().getWarehouse().getNormalDepots()[0].getType());
        }

        // SETUP DEPOT 2
        if(playerToShow.getPersonalBoard().getWarehouse().getNormalDepots()[1].getOcc()==2){
            loadImageResource(depot21, playerToShow.getPersonalBoard().getWarehouse().getNormalDepots()[1].getType());
            loadImageResource(depot22, playerToShow.getPersonalBoard().getWarehouse().getNormalDepots()[1].getType());
        } else if(playerToShow.getPersonalBoard().getWarehouse().getNormalDepots()[1].getOcc()==1){
            loadImageResource(depot21, playerToShow.getPersonalBoard().getWarehouse().getNormalDepots()[1].getType());
        }

        // SETUP DEPOT 3
        if(playerToShow.getPersonalBoard().getWarehouse().getNormalDepots()[2].getOcc()==3){
            loadImageResource(depot31, playerToShow.getPersonalBoard().getWarehouse().getNormalDepots()[2].getType());
            loadImageResource(depot32, playerToShow.getPersonalBoard().getWarehouse().getNormalDepots()[2].getType());
            loadImageResource(depot33, playerToShow.getPersonalBoard().getWarehouse().getNormalDepots()[2].getType());
        } else if(playerToShow.getPersonalBoard().getWarehouse().getNormalDepots()[2].getOcc()==2){
            loadImageResource(depot31, playerToShow.getPersonalBoard().getWarehouse().getNormalDepots()[2].getType());
            loadImageResource(depot32, playerToShow.getPersonalBoard().getWarehouse().getNormalDepots()[2].getType());
        } else if(playerToShow.getPersonalBoard().getWarehouse().getNormalDepots()[2].getOcc()==1){
            loadImageResource(depot31, playerToShow.getPersonalBoard().getWarehouse().getNormalDepots()[2].getType());
        }
    }

    private void initializeStrongbox() {
        ReducedStrongbox strongbox = playerToShow.getPersonalBoard().getWarehouse().getStrongbox();
        numCoinStrongbox.setText(""+strongbox.getOccurrencesOfResource(ResourceType.coin));
        numServantStrongbox.setText(""+strongbox.getOccurrencesOfResource(ResourceType.servant));
        numShieldStrongbox.setText(""+strongbox.getOccurrencesOfResource(ResourceType.shield));
        numStoneStrongbox.setText(""+strongbox.getOccurrencesOfResource(ResourceType.stone));
    }


    private void loadImageResource(ImageView target, ResourceType resourceType){
        target.setImage(new Image(ResourceLocator.retrieveResourceTypeImage(resourceType)));
    }

    @FXML
    public void handleChooseActionButton()
    {
        showPopup("/gui/fxml/ChooseActionPage.fxml", 820, 520);
    }
    @FXML
    public void handleMoveActionButton()
    {
        showPopup("/gui/fxml/MoveActionPage.fxml", 1180, 750);
    }

    @FXML
    public void handleEndTurn()
    {
        client.sendMessage(new EndTurnMessage());
    }



    public void initializePersonalBoard(ReducedPlayer player) {
        this.playerToShow = player;
        if(!playerToShow.getNickname().equals(client.getGame().getPlayer(client.getUser()).getNickname())) {
            chooseAction.setVisible(false);
            moveAction.setVisible(false);
            endTurn.setVisible(false);
            otherPlayers.setVisible(false);
            BackgroundSize bSize = new BackgroundSize(80, 80, true, true, true, true);
            center.setBackground(new Background(new BackgroundImage(new Image("/gui/img/exit_tab.png"),
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    bSize)));
        }
        else {
            super.initialize();
            setAvailableActions();
            BackgroundSize bSize = new BackgroundSize(80, 80, true, true, true, true);
            center.setBackground(new Background(new BackgroundImage(new Image("/gui/img/background.jpg"),
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    bSize)));
        }
        username.setText(""+playerToShow.getNickname());
        initializeLeaderCards();
        strongboxResources.setVisible(true);
        initializeCells();
        initializeDepots();
        initializeStrongbox();
        if(client.getUI().isSoloMode())
            initializeFaithTrackWithBlackCross();
        else
            initializeFaithMarker();
        initializeSlots();
        initializeExtraDepots();
    }

    private void setAvailableActions() {
        if(!client.getGame().getCurrPlayer().equals(playerToShow)) {
            moveAction.setVisible(false);
            chooseAction.setVisible(false);
            endTurn.setVisible(false);
            viewDashboard.setVisible(true);
        } else {
            endTurn.setVisible(client.getUI().hasDoneNormalAction());
            moveAction.setVisible(true);
            viewDashboard.setVisible(false);
            chooseAction.setVisible(true);
        }
    }

    @FXML
    public void handleCloseViewOthers()
    {
        Stage stage = (Stage) closePopup.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handleViewOtherPlayers()
    {
        showPopup("/gui/fxml/ViewOtherPlayersPage.fxml", 820, 520);
    }

    @FXML
    public void handleViewDashboard()
    {
        showPopup("/gui/fxml/ViewDashboardPage.fxml", 820, 520);
    }
}
