package it.polimi.ingsw.Client.view.GUI;

import it.polimi.ingsw.Client.reducedmodel.ReducedPlayer;
import it.polimi.ingsw.Model.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

public class ViewOtherPlayersController extends Controller{
    @FXML
    public AnchorPane anchorViewOtherPlayers;

    @FXML
    public Text ViewOtherPlayersText;

    @FXML
    public Button closeView;

    @FXML
    public Button player1,player2,player3;

    private static final String CURSOR = "/gui/img/cursor.png";
    List<ReducedPlayer> playerList ;

    @FXML
    @Override
    public void initialize() {

        this.client = GUIApp.client;

        anchorViewOtherPlayers.setBackground(new Background(new BackgroundImage(new Image("/gui/img/exit_tab.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false))));
        setFont(ViewOtherPlayersText,39);
        ViewOtherPlayersText.setStyle("-fx-text-fill: rgb(35, 25, 22);");

        setFont(player1,35);
        player1.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(player2,35);
        player2.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(player3,35);
        player3.setStyle("-fx-text-fill: rgb(35, 25, 22);");

        playerList = client.getGame().getPlayers().stream().filter(x->!x.getNickname().equals(client.getUser())).collect(Collectors.toList());

        if(playerList.size() > 0) {
            player1.setVisible(true);
            player1.setText(""+playerList.get(0).getNickname());
        }
        if(playerList.size() > 1) {
            player2.setVisible(true);
            player2.setText(""+playerList.get(1).getNickname());
        }
        if(playerList.size() > 2){
            player3.setVisible(true);
            player3.setText(""+playerList.get(2).getNickname());
        }
    }

    @FXML
    public void handleCloseChooseAction()
    {
        Stage stage = (Stage) closeView.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handleViewPlayer1()
    {
        showPopup("/gui/FXML/PlayerBoardOtherPlayer.fxml", 1110, 810);
        ((PlayerBoardController)GUIApp.controller).initializePersonalBoard(playerList.get(0));
    }

}
