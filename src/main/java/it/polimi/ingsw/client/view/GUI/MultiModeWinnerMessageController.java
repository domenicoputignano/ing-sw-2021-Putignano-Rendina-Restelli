package it.polimi.ingsw.client.view.GUI;

import it.polimi.ingsw.utils.messages.serverMessages.RankMessage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

/**
 * Class that represents the controller of the MultiModeWinnerMessage page of the game
 * In this page, which is shown as a popup, the user can view the ranking of the match after
 * it has finished and can view the winner of the match
 * Finally he closes the game
 */
public class MultiModeWinnerMessageController extends Controller{
    /**
     * Attribute that represents the central pane of the page
     */
    @FXML
    public AnchorPane popupPane;
    /**
     * Attribute that represents the button to close the popup of the page
     */
    @FXML
    public Button closeButton;

    /**
     * Attribute that represents the text where the winner of the game is shown
     */
    @FXML
    public Text winnerText;

    /**
     * Attribute that represents the text where the players of the match are shown on the rank
     *
     */
    @FXML
    public Text scorePlayer1,scorePlayer2,scorePlayer3,scorePlayer4;

    /**
     * Main method that initializes the scene within the stage
     * It takes care of setting the background of the scene
     * and the font of the texts and buttons
     */
    @Override
    public void initialize(){
        popupPane.setBackground(new Background(new BackgroundImage(new Image("/gui/img/exit_tab.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false))));
        setFont(winnerText,60);
        winnerText.setStyle("-fx-text-fill: rgb(62,11,11);");
        setFont(scorePlayer1, 40);
        scorePlayer1.setStyle("-fx-text-fill: rgb(62,11,11);");
        setFont(scorePlayer2, 40);
        scorePlayer2.setStyle("-fx-text-fill: rgb(62,11,11);");
        setFont(scorePlayer3, 40);
        scorePlayer3.setStyle("-fx-text-fill: rgb(62,11,11);");
        setFont(scorePlayer4, 40);
        scorePlayer4.setStyle("-fx-text-fill: rgb(62,11,11);");
        setFont(closeButton,24);
        closeButton.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        this.client = GUIApp.client;
    }

    /**
     * method used to set the rank of the game showing the score for each player
     * @param message message that contains the game ranking
     * consisting of a list of pairs that contains the player and his score
     * @see RankMessage
     */
    public void setRank(RankMessage message){
        if(message.getRank().get(0).getKey().equals(client.getUser())){
            winnerText.setText("You WON!");
            setText(message);
        } else {
            winnerText.setText("You LOST!\n"+message.getRank().get(0).getKey().getNickname()+" WON!");
            setText(message);
        }

    }

    /**
     * method used to set text of the players in rank
     * @param message message that contains the game ranking
     * consisting of a list of pairs that contains the player and his score
     * @see RankMessage
     */
    private void setText(RankMessage message)
    {
        scorePlayer1.setVisible(true);
        scorePlayer1.setText("1. "+message.getRank().get(0).getKey().getNickname()+"\t\t   Score: " + message.getRank().get(0).getValue());
        if(message.getRank().size() > 1){
            scorePlayer2.setVisible(true);
            scorePlayer2.setText("2. "+message.getRank().get(1).getKey().getNickname()+"\t\t   Score: " + message.getRank().get(1).getValue());
        }
        if(message.getRank().size() > 2){
            scorePlayer3.setVisible(true);
            scorePlayer3.setText("3. "+message.getRank().get(2).getKey().getNickname()+"\t\t   Score: " + message.getRank().get(2).getValue());
        }
        if(message.getRank().size() > 3){
            scorePlayer4.setVisible(true);
            scorePlayer4.setText("4. "+message.getRank().get(3).getKey().getNickname()+"\t\t   Score: " + message.getRank().get(3).getValue());
        }
    }

    /**
     * method used to close the popup and end the game
     */
    public void handleCloseButton(){
        System.exit(0);
    }

}
