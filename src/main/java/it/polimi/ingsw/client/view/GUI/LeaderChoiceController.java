package it.polimi.ingsw.client.view.GUI;

import it.polimi.ingsw.client.clientstates.gui.InitialLeaderChoiceGUI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;
/**
 * Class that represents the controller of the LeaderChoice page of the game
 * On this page the user must choose two out of four leader cards in order to start the game
 */
public class LeaderChoiceController extends Controller{
    /**
     * Attribute that represents the textField where the title of the page is shown
     */
    @FXML
    public TextField chooseCardText;
    /**
     * Attribute that represents the textField where an error is shown in case the user
     * selects leader cards in an invalid way
     */
    @FXML
    public TextField errorSelectedResources,errorChoiceText;
    /**
     * Attribute that represents the button to press to choose the leader card
     */
    @FXML
    public Button leaderCard1,leaderCard2,leaderCard3,leaderCard4;
    /**
     * Attribute that represents the image of the indicator arrow for leader card
     */
    @FXML
    public ImageView indicator1,indicator2,indicator3,indicator4;

    /**
     * Attribute that represents the button to clean up the choices made
     */
    @FXML
    public Button clearButton;
    /**
     * Attribute that represents the button to confirm the choices and go on
     */
    @FXML
    public Button okButton;

    /**
     * Attributes used to set the choices made and to understand which ones he chose
     */
    private int selectedCard = 0;
    private boolean card1 = false,card2 = false,card3 = false,card4 = false;

    /**
     * Main method that initializes the scene within the stage
     * It takes care of setting the background of the scene
     * ,the font of the texts and buttons,
     * and the images on the scene
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

        this.client = GUIApp.client;

        chooseCardText.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        errorChoiceText.setStyle("-fx-text-fill: #f2b535");
        clearButton.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        okButton.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        errorSelectedResources.setStyle("-fx-text-fill: #f2b535");

        setLeaderCardImages();

        setFont(chooseCardText,30);
        setFont(errorSelectedResources,30);
        setFont(errorChoiceText,30);
        setFont(clearButton,22);
        setFont(okButton,20);
    }
    /**
     * method used to manage the user's choice to discard first leader card
     * It is performed when leaderCard1 button is pressed
     */
    @FXML
    void discardLeaderCard1()
    {
        errorSelectedResources.clear();
        if(!card1) {
            if(selectedCard < 2) {
                leaderCard1.setStyle("-fx-border-color: rgb(231,156,48);-fx-border-width: 5;" +
                        "-fx-background-image: url(" +
                        client.getGame().getPlayer(client.getUser()).getAvailableLeaderCards().get(0).toImage() + ")");
                selectedCard++;
                card1 = true;
                indicator1.setVisible(true);
            }
            else errorChoiceText.setText("ERROR: You can only select 2 leader cards");
        }
    }
    /**
     * method used to manage the user's choice to discard second leader card
     * It is performed when leaderCard2 button is pressed
     */
    @FXML
    void discardLeaderCard2()
    {
        errorSelectedResources.clear();
        if(!card2) {
            if(selectedCard < 2) {
                leaderCard2.setStyle("-fx-border-color: rgb(231,156,48);-fx-border-width: 5;" +
                        "-fx-background-image: url(" +
                        client.getGame().getPlayer(client.getUser()).getAvailableLeaderCards().get(1).toImage() + ")");
                selectedCard++;
                card2 = true;
                indicator2.setVisible(true);
            }
            else errorChoiceText.setText("ERROR: You can only select 2 leader cards");
        }
    }
    /**
     * method used to manage the user's choice to discard third leader card
     * It is performed when leaderCard3 button is pressed
     */
    @FXML
    void discardLeaderCard3()
    {
        errorSelectedResources.clear();
        if(!card3) {
            if(selectedCard < 2) {
                leaderCard3.setStyle("-fx-border-color: rgb(231,156,48);-fx-border-width: 5;" +
                        "-fx-background-image: url(" +
                        client.getGame().getPlayer(client.getUser()).getAvailableLeaderCards().get(2).toImage() + ")");
                selectedCard++;
                card3 = true;
                indicator3.setVisible(true);
            }
            else errorChoiceText.setText("ERROR: You can only select 2 leader cards");
        }
    }
    /**
     * method used to manage the user's choice to discard third leader card
     * It is performed when leaderCard3 button is pressed
     */
    @FXML
    void discardLeaderCard4()
    {
        errorSelectedResources.clear();
        if(!card4) {
            if(selectedCard < 2) {
                leaderCard4.setStyle("-fx-border-color: rgb(231,156,48);-fx-border-width: 5;" +
                        "-fx-background-image: url(" +
                        client.getGame().getPlayer(client.getUser()).getAvailableLeaderCards().get(3).toImage() + ")");
                selectedCard++;
                card4 = true;
                indicator4.setVisible(true);
            }
            else errorChoiceText.setText("ERROR: You can only select 2 Leader Cards");
        }
    }
    /**
     * method used to clear all choises made
     * It is performed when clearButton is pressed
     */
    @FXML
    void clearIndicator()
    {
        selectedCard = 0;
        card1=false;
        card2=false;
        card3=false;
        card4=false;
        leaderCard1.setStyle("-fx-border-color: none;-fx-border-width: none;");
        leaderCard1.setStyle(":hover -fx-border-color: rgb(231,156,48);-fx-border-width: 5;");
        leaderCard2.setStyle("-fx-border-color: none;-fx-border-width: none;");
        leaderCard2.setStyle(":hover -fx-border-color: rgb(231,156,48);-fx-border-width: 5;");
        leaderCard3.setStyle("-fx-border-color: none;-fx-border-width: none;");
        leaderCard3.setStyle(":hover -fx-border-color: rgb(231,156,48);-fx-border-width: 5;");
        leaderCard4.setStyle("-fx-border-color: none;-fx-border-width: none;");
        leaderCard4.setStyle(":hover -fx-border-color: rgb(231,156,48);-fx-border-width: 5;");
        setLeaderCardImages();
        indicator1.setVisible(false);
        indicator2.setVisible(false);
        indicator3.setVisible(false);
        indicator4.setVisible(false);
        errorChoiceText.clear();
        errorSelectedResources.clear();
    }

    /**
     * method used to set the card images based on those actually present in the model
     */
    private void setLeaderCardImages() {
        leaderCard1.setStyle("-fx-background-image: url(" +
                client.getGame().getPlayer(client.getUser()).getAvailableLeaderCards().get(0).toImage() + ")");
        leaderCard2.setStyle("-fx-background-image: url(" +
                client.getGame().getPlayer(client.getUser()).getAvailableLeaderCards().get(1).toImage() + ")");
        leaderCard3.setStyle("-fx-background-image: url(" +
                client.getGame().getPlayer(client.getUser()).getAvailableLeaderCards().get(2).toImage() + ")");
        leaderCard4.setStyle("-fx-background-image: url(" +
                client.getGame().getPlayer(client.getUser()).getAvailableLeaderCards().get(3).toImage() + ")");
    }

    /**
     * method used to confirm all choises made
     * It is performed when okButton is pressed
     */
    @FXML
    void handleOkChoice()
    {
        if(selectedCard != 2)
        {
            errorSelectedResources.setText("Select 2 Leader Card");
        }
        else
        {
            List<Integer> toDiscard = new ArrayList<>();
            if(card1) toDiscard.add(1);
            if(card2) toDiscard.add(2);
            if(card3) toDiscard.add(3);
            if(card4) toDiscard.add(4);
            clientState = new InitialLeaderChoiceGUI(client, toDiscard.get(0), toDiscard.get(1));
            clientState.manageUserInteraction();
        }
        showWaitingSceneForFirstPlayer();
    }
    /**
     * method used to show a page with a message to player one that he will not have to
     * choose the resources and therefore will have to wait for the choice of the resources
     * of the other players
     */
    private void showWaitingSceneForFirstPlayer() {
        if(client.getGame().getPlayer(client.getUser()).equals(client.getGame().getCurrPlayer())) {
            if(!client.getUI().isSoloMode()) {
                cleanPane();
                chooseCardText.setText("You are the first player, match will start soon...");
                chooseCardText.setVisible(true);
            }
        } else cleanPane();
    }

    /**
     * method used to clean Pane only for the first player to show him a message
     */
    private void cleanPane() {
        leaderCard1.setVisible(false);
        leaderCard2.setVisible(false);
        leaderCard3.setVisible(false);
        leaderCard4.setVisible(false);
        indicator1.setVisible(false);
        indicator2.setVisible(false);
        indicator3.setVisible(false);
        indicator4.setVisible(false);
        okButton.setVisible(false);
        clearButton.setVisible(false);
        errorChoiceText.setVisible(false);
        if(client.getUserPosition() == 1 ){
            chooseCardText.setText("You are the first player, wait until the other players choose the resources");
        }
    }
}
