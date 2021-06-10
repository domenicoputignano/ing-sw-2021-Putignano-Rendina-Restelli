package it.polimi.ingsw.Client.view.GUI;

import it.polimi.ingsw.Commons.ResourceType;
import it.polimi.ingsw.Utils.ResourceLocator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MoveActionController extends Controller{
    @FXML
    public AnchorPane anchorMoveAction;

    @FXML
    public Text moveActionText,typeOfMoveText;

    @FXML
    public Button closeMoveAction;

    @FXML
    public Button depot1,depot2,depot3,fromNormalToNormal,fromNormalToExtra,fromExtraToNormal;

    @FXML
    public ImageView res1Depot1, res1Depot2, res2Depot2, res1Depot3, res2Depot3, res3Depot3;

    @FXML
    public Text extraDepotsText;

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
}
