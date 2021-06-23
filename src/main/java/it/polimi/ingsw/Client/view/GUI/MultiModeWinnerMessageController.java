package it.polimi.ingsw.Client.view.GUI;

import it.polimi.ingsw.Utils.Messages.ServerMessages.RankMessage;
import it.polimi.ingsw.Utils.Messages.ServerMessages.SoloModeMatchWinnerMessage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class MultiModeWinnerMessageController extends Controller{
    @FXML
    public AnchorPane popupPane;

    @FXML
    public Button closeButton;

    @FXML
    public Text winnerText;

    @FXML
    public Text scorePlayer1,scorePlayer2,scorePlayer3,scorePlayer4;

    @Override
    public void initialize(){
        popupPane.setBackground(new Background(new BackgroundImage(new Image("/gui/img/exit_tab.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false))));
        setFont(winnerText,40);
        winnerText.setStyle("-fx-text-fill: rgb(62,11,11);");
        setFont(scorePlayer1, 25);
        scorePlayer1.setStyle("-fx-text-fill: rgb(62,11,11);");
        setFont(scorePlayer2, 25);
        scorePlayer2.setStyle("-fx-text-fill: rgb(62,11,11);");
        setFont(scorePlayer3, 25);
        scorePlayer3.setStyle("-fx-text-fill: rgb(62,11,11);");
        setFont(scorePlayer4, 25);
        scorePlayer4.setStyle("-fx-text-fill: rgb(62,11,11);");
        setFont(closeButton,24);
        closeButton.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        this.client = GUIApp.client;
    }

    public void setRank(RankMessage message){
        if(message.getRank().get(0).getKey().equals(client.getUser())){
            winnerText.setText("You WON!");
            setText(message);
        } else {
            winnerText.setText("You LOST!\n"+message.getRank().get(0).getKey().getNickname()+" WON!");
            setText(message);
        }

    }

    private void setText(RankMessage message)
    {
        scorePlayer1.setVisible(true);
        scorePlayer1.setText("1. "+message.getRank().get(0).getKey().getNickname()+"   Score: " + message.getRank().get(0).getValue());
        if(message.getRank().size() > 1){
            scorePlayer2.setVisible(true);
            scorePlayer2.setText("2. "+message.getRank().get(1).getKey().getNickname()+"   Score: " + message.getRank().get(1).getValue());
        }
        if(message.getRank().size() > 2){
            scorePlayer3.setVisible(true);
            scorePlayer3.setText("3. "+message.getRank().get(2).getKey().getNickname()+"   Score: " + message.getRank().get(2).getValue());
        }
        if(message.getRank().size() > 3){
            scorePlayer4.setVisible(true);
            scorePlayer4.setText("4. "+message.getRank().get(3).getKey().getNickname()+"   Score: " + message.getRank().get(3).getValue());
        }
    }


    public void handleCloseButton(){
        System.exit(0);
    }

}
