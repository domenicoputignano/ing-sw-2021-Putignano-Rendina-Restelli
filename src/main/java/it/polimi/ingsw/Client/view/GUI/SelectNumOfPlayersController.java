package it.polimi.ingsw.Client.view.GUI;

import it.polimi.ingsw.Client.clientstates.gui.NumOfPlayerChoiceGUI;
import it.polimi.ingsw.Client.clientstates.gui.UsernameChoiceGUI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
/**
 * Class that represents the controller of the SelectNumbersOfPlayers page of the game
 * On this page the user must choose the number of players he wants to play with
 * Once the choice has been made, the user will be placed in a lobby
 * waiting for the number of chosen players to connect
 */
public class SelectNumOfPlayersController extends Controller{
    /**
     * Attribute that represents the textField where the title of the page is shown
     */
    @FXML
    public TextField numberPlayers;
    /**
     * Attribute that represents the button to press to choose two-player mode
     */
    @FXML
    public Button twoPlayers;

    /**
     * Attribute that represents the button to press to choose three-player mode
     */
    @FXML
    public Button threePlayers;

    /**
     * Attribute that represents the button to press to choose four-player mode
     */
    @FXML
    public Button fourPlayers;

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
        numberPlayers.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        twoPlayers.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        threePlayers.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        fourPlayers.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(numberPlayers,50);
        setFont(twoPlayers,26);
        setFont(threePlayers,26);
        setFont(fourPlayers,26);
    }
    /**
     * method used to manage the user's choice to two-player mode
     * It is performed when the twoPlayers button is pressed
     */
    @FXML
    void handleTwoPlayersChoice() {
        clientState = new NumOfPlayerChoiceGUI(client, 2);
        clientState.manageUserInteraction();
        GUIApp.showScene("/gui/fxml/LobbyPage.fxml");
    }
    /**
     * method used to manage the user's choice to three-player mode
     * It is performed when the threePlayers button is pressed
     */
    @FXML
    void handleThreePlayersChoice() {
        clientState = new NumOfPlayerChoiceGUI(client, 3);
        clientState.manageUserInteraction();
        GUIApp.showScene("/gui/fxml/LobbyPage.fxml");
    }
    /**
     * method used to manage the user's choice to four-player mode
     * It is performed when the fourPlayers button is pressed
     */
    @FXML
    void handleFourPlayersChoice() {
        clientState = new NumOfPlayerChoiceGUI(client, 4);
        clientState.manageUserInteraction();
        GUIApp.showScene("/gui/fxml/LobbyPage.fxml");

    }


}
