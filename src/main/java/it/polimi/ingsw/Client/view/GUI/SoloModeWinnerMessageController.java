package it.polimi.ingsw.Client.view.GUI;

import it.polimi.ingsw.Utils.Messages.ServerMessages.SoloModeMatchWinnerMessage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

/**
 * Class that represents the controller of the SoloModeWinnerMessage page of the game
 * In this page, which is shown as a popup, the user can view the user can see if he has won
 * or lost against Lorenzo il Magnifico and in case of victory he is shown the score
 * Finally he closes the game
 */
public class SoloModeWinnerMessageController extends Controller{

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
     * Attribute that represent the conclusion event that caused the end of the game
     */
    @FXML
    public Text conclusionEvent;

    /**
     * Attribute that represents the text where the score of the player is shown
     */
    @FXML
    public Text scoreText;

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
        setFont(winnerText,40);
        winnerText.setStyle("-fx-text-fill: rgb(62,11,11);");
        setFont(scoreText, 25);
        scoreText.setStyle("-fx-text-fill: rgb(62,11,11);");
        setFont(conclusionEvent, 25);
        conclusionEvent.setStyle("-fx-text-fill: rgb(62,11,11);");
        setFont(closeButton,24);
        closeButton.setStyle("-fx-text-fill: rgb(35, 25, 22);");
    }

    /**
     * method used to show the result of the game
     * @param message message containing the result of the match in SoloMode
     * @see SoloModeMatchWinnerMessage
     */
    public void setText(SoloModeMatchWinnerMessage message){
        if(message.hasPlayerWon()){
            winnerText.setText("YOU WON!");
            scoreText.setText("Score: " + message.getVictoryPoints());
        } else {
            winnerText.setText("YOU LOST!");
        }

        conclusionEvent.setText(message.getConclusionEvent().eventTrigger());

    }

    /**
     * method used to close the popup and end the game
     */
    public void handleCloseButton(){
        System.exit(0);
    }


}
