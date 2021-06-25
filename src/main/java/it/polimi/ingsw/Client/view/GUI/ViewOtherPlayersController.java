package it.polimi.ingsw.Client.view.GUI;

import it.polimi.ingsw.Client.reducedmodel.ReducedPlayer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class that represents the controller of the ViewOtherPlayers page of the game
 * In this page, which is shown as a popup, the user can choose to view the PlayerBoard
 * of a player in the game in the case of MultiMode
 */
public class ViewOtherPlayersController extends Controller{
    /**
     * Attribute that represents the central pane of the page
     */
    @FXML
    public AnchorPane anchorViewOtherPlayers;

    /**
     * Attribute that represents the textField where the title of the page is shown
     */
    @FXML
    public Text ViewOtherPlayersText;
    /**
     * Attribute that represents the button to close the popup of the page
     */
    @FXML
    public Button closeView;

    /**
     * Attributes that represent the buttons associated to each player in the game
     */
    @FXML
    public Button player1,player2,player3;

    /**
     * attribute that represent the list of the players in the game
     */
    List<ReducedPlayer> playerList ;

    /**
     * Main method that initializes the scene within the stage
     * It takes care of setting the background of the scene
     * and the font of the texts and buttons
     */
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

    /**
     * method used to close the popup page over the main stage
     * It is performed when closeView button is pressed
     */
    @FXML
    public void handleCloseChooseAction()
    {
        Stage stage = (Stage) closeView.getScene().getWindow();
        stage.close();
    }

    /**
     * method used to view the playerBoard of the first player in list
     * It is performed when player1 button is pressed
     */
    @FXML
    public void handleViewPlayer1()
    {
        showPopup("/gui/fxml/PlayerBoardOtherPlayer.fxml", 1110, 810);
        ((PlayerBoardController)GUIApp.controller).initializePersonalBoard(playerList.get(0));
        handleCloseChooseAction();
    }
    /**
     * method used to view the playerBoard of the second player in list
     * It is performed when player2 button is pressed
     */
    @FXML
    public void handleViewPlayer2()
    {
        showPopup("/gui/fxml/PlayerBoardOtherPlayer.fxml", 1110, 810);
        ((PlayerBoardController)GUIApp.controller).initializePersonalBoard(playerList.get(1));
        handleCloseChooseAction();
    }
    /**
     * method used to view the playerBoard of the third player in list
     * It is performed when player3 button is pressed
     */
    @FXML
    public void handleViewPlayer3()
    {
        showPopup("/gui/fxml/PlayerBoardOtherPlayer.fxml", 1110, 810);
        ((PlayerBoardController)GUIApp.controller).initializePersonalBoard(playerList.get(2));
        handleCloseChooseAction();
    }
}
