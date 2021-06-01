package it.polimi.ingsw.Client.view.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class LobbyController extends Controller {
    @FXML
    public Text lobbyText;

    @FXML
    public TextField player1;

    @FXML
    public TextField player2;

    @FXML
    public TextField player3;

    @FXML
    public TextField player4;

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

    public void setPlayer1(String player)
    {
        player1.setText(player);
    }

    public void setPlayer2(String player)
    {
        player2.setText(player);
    }
    public void setPlayer3(String player)
    {
        player3.setText(player);
    }
    public void setPlayer4(String player)
    {
        player4.setText(player);
    }
}

