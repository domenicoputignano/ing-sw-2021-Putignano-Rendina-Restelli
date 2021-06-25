package it.polimi.ingsw.Client.view.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

/**
 * Class that represents the controller of the Lobby page of the game
 * On this page the user will have to wait for the number of players
 * chosen in the previous step to connect to the server to play the game
 */
public class LobbyController extends Controller {
    /**
     * Attribute that represents the textField where the title of the page is shown
     */
    @FXML
    public Text lobbyText;
    /**
     * Attribute that represents the textField where the first player in lobby is shown
     */
    @FXML
    public TextField player1;
    /**
     * Attribute that represents the textField where the second player in lobby is shown
     */
    @FXML
    public TextField player2;
    /**
     * Attribute that represents the textField where the third player in lobby is shown
     */
    @FXML
    public TextField player3;
    /**
     * Attribute that represents the textField where the fourth player in lobby is shown
     */
    @FXML
    public TextField player4;

    /**
     * Main method that initializes the scene within the stage
     * It takes care of setting the background of the scene
     * and the font of the texts and buttons
     */
    @FXML
    public void initialize() {
        super.initialize();

        BackgroundSize bSize = new BackgroundSize(80, 80, true, true, true, true);

        center.setBackground(new Background(new BackgroundImage(new Image("/gui/img/background.jpg"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bSize)));
        lobbyText.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(lobbyText,45);
        player1.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(player1,30);
        player2.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(player2,30);
        player3.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(player3,30);
        player4.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(player4,30);
    }
    /**
     * method used to set the player username in the first textField of Lobby page
     * @param player String that represents the player username to show in the TextField
     */
    public void setPlayer1(String player)
    {
        player1.setText(player);
    }
    /**
     * method used to set the player username in the second textField of Lobby page
     * @param player String that represents the player username to show in the TextField
     */
    public void setPlayer2(String player)
    {
        player2.setText(player);
    }
    /**
     * method used to set the player username in the third textField of Lobby page
     * @param player String that represents the player username to show in the TextField
     */
    public void setPlayer3(String player)
    {
        player3.setText(player);
    }
    /**
     * method used to set the player username in the fourth textField of Lobby page
     * @param player String that represents the player username to show in the TextField
     */
    public void setPlayer4(String player)
    {
        player4.setText(player);
    }
}

