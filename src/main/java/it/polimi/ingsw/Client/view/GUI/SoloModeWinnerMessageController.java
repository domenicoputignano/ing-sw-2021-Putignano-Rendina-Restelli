package it.polimi.ingsw.Client.view.GUI;

import it.polimi.ingsw.Utils.Messages.ServerMessages.SoloModeMatchWinnerMessage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class SoloModeWinnerMessageController extends Controller{

    @FXML
    public AnchorPane popupPane;

    @FXML
    public Button closeButton;

    @FXML
    public Text winnerText;

    @FXML
    public Text conclusionEvent;

    @FXML
    public Text scoreText;

    @Override
    public void initialize(){
        popupPane.setBackground(new Background(new BackgroundImage(new Image("/gui/img/exit_tab.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false))));
        setFont(winnerText,40);
        winnerText.setStyle("-fx-text-fill: rgb(62,11,11);");
        setFont(scoreText, 25);
        scoreText.setStyle("-fx-text-fill: rgb(62,11,11);");
        setFont(conclusionEvent, 25);
        conclusionEvent.setStyle("-fx-text-fill: rgb(62,11,11);");
        setFont(closeButton,24);
        closeButton.setStyle("-fx-text-fill: rgb(35, 25, 22);");
    }

    public void setText(SoloModeMatchWinnerMessage message){
        if(message.hasPlayerWon()){
            winnerText.setText("You WON!");
            scoreText.setText("Score: " + message.getVictoryPoints());
        } else {
            winnerText.setText("You LOST!");
        }

        conclusionEvent.setText(message.getConclusionEvent().eventTrigger());

    }

    public void handleCloseButton(){
        System.exit(0);
    }


}
