package it.polimi.ingsw.Client.view.GUI;

import it.polimi.ingsw.Client.clientstates.gui.LeaderActionGUI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.stream.Collectors;

public class LeaderActionController extends Controller{
    @FXML
    public AnchorPane anchorLeader;

    @FXML
    public Text leaderActionText;

    @FXML
    public Button closeLeaderAction;

    @FXML
    public Button active1;
    @FXML
    public Button active2;
    @FXML
    public Button discard1;
    @FXML
    public Button discard2;
    @FXML
    public Button okButton;
    @FXML
    public ImageView leaderCard1, leaderCard2;


    LeaderActionGUI leaderAction;

    @FXML
    @Override
    public void initialize() {
        anchorLeader.setBackground(new Background(new BackgroundImage(new Image("/gui/img/exit_tab.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false))));

        this.client = GUIApp.client;

        if(client.getGame().getPlayer(client.getUser()).getNumOfAvailableLeaderCards()==2){
            leaderCard1.setImage(new Image(client.getGame().getPlayer(client.getUser()).getAvailableLeaderCards()
                    .stream().filter(x->!x.isActive()).collect(Collectors.toList()).get(0).toImage()));
            leaderCard2.setImage(new Image(client.getGame().getPlayer(client.getUser()).getAvailableLeaderCards()
                    .stream().filter(x->!x.isActive()).collect(Collectors.toList()).get(1).toImage()));

        } else if(client.getGame().getPlayer(client.getUser()).getNumOfAvailableLeaderCards()==1){
            leaderCard1.setImage(new Image(client.getGame().getPlayer(client.getUser()).getAvailableLeaderCards()
                    .stream().filter(x->!x.isActive()).collect(Collectors.toList()).get(0).toImage()));
            // TODO centrare l'immagine e rendere i bottoni della seconda carta invisibili
            // leaderCard1.setX(467)
            // bottoni.setX(467)
        }

        leaderActionText.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(leaderActionText,39);
        active1.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(active1,24);
        active2.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(active2,24);
        discard1.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(discard1,24);
        discard2.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(discard2,24);
        okButton.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(okButton,24);
        leaderAction = new LeaderActionGUI(client);
    }

    @FXML
    public void handleCloseChooseAction()
    {
        Stage stage = (Stage) closeLeaderAction.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handleDiscardFirstLeader() {
        clearSelection();
        discard1.setStyle("-fx-background-size: 77% auto;");
        editLeaderAction(1, true);
    }

    @FXML
    public void handleDiscardSecondLeader() {
        clearSelection();
        discard2.setStyle("-fx-background-size: 77% auto;");
        editLeaderAction(2, true);
    }

    @FXML
    public void handleFirstLeaderActivation() {
        clearSelection();
        active1.setStyle("-fx-background-size: 77% auto;");
        editLeaderAction(1, false);
    }

    @FXML
    public void handleSecondLeaderActivation() {
        clearSelection();
        active2.setStyle("-fx-background-size: 77% auto;");
        editLeaderAction(2, false);
    }

    @FXML
    public void sendLeaderActionMessage() {
        leaderAction.manageUserInteraction();
    }

    @FXML
    void clearSelection() {
        active1.setStyle("-fx-background-size: 90% auto;");
        active2.setStyle("-fx-background-size: 90% auto;");
        discard1.setStyle("-fx-background-size: 90% auto;");
        discard2.setStyle("-fx-background-size: 90% auto;");
    }


    private void editLeaderAction(int leaderIndex, boolean toDiscard) {
        if(client.getGame().getCurrPlayer().getNumOfAvailableLeaderCards()==1) {
            leaderAction.setLeaderAction(toDiscard);
            //TODO setta ad 1 per evitare side effect o problemi lato server
            leaderAction.setLeaderIndex(1);
        } else if(client.getGame().getCurrPlayer().getNumOfAvailableLeaderCards() == 2) {
            leaderAction.setLeaderAction(toDiscard);
            leaderAction.setLeaderIndex(leaderIndex);
        }
    }
}
