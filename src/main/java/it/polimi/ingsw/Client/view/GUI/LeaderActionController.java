package it.polimi.ingsw.Client.view.GUI;

import it.polimi.ingsw.Client.clientstates.gui.LeaderActionGUI;
import it.polimi.ingsw.Commons.LeaderCard;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class that represents the controller of the LeaderAction page of the game
 * on this page the user can perform a leader action by activating (if required)
 * or discarding a leader card
 */
public class LeaderActionController extends Controller{
    /**
     * Attribute that represents the central pane of the page
     */
    @FXML
    public AnchorPane anchorLeader;
    @FXML
    /**
     * Attribute that represents the textField where the title of the page is shown
     */
    public Text leaderActionText;
    @FXML
    /**
     * Attribute that represents the button to close the page
     */
    public Button closeLeaderAction;
    @FXML
    /**
     * Attributes that represents the buttons to activate leader card 1 and leader card 2
     */
    public Button active1,active2;
    @FXML
    /**
     * Attributes that represents the buttons to discard leader card 1 and leader card 2
     */
    public Button discard1,discard2;
    @FXML
    /**
     * Attribute that represents the button that confirms the chosen leader action
     */
    public Button okButton;
    /**
     * Attributes that represent the imageView of the two leader Cards owned
     */
    @FXML
    public ImageView leaderCard1, leaderCard2;


    LeaderActionGUI leaderAction;

    /**
     * Main method that initializes the scene within the stage
     * It takes care of setting the background of the scene
     * ,the font of the texts and buttons,
     * and the images on the scene
     */
    @FXML
    @Override
    public void initialize() {
        anchorLeader.setBackground(new Background(new BackgroundImage(new Image("/gui/img/exit_tab.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false))));

        this.client = GUIApp.client;

        if(client.getGame().getCurrPlayer().getNumOfNotActiveLeaderCards()==1) {
            leaderCard1.setX(210);
            active1.setTranslateX(205);
            discard1.setTranslateX(210);
            active2.setVisible(false);
            discard2.setVisible(false);
        }
        if(client.getGame().getCurrPlayer().getNumOfNotActiveLeaderCards()==0) {
            active1.setVisible(false);
            discard1.setVisible(false);
            leaderCard1.setVisible(false);
            leaderCard2.setVisible(false);
            okButton.setText("Close");
            active2.setVisible(false);
            discard2.setVisible(false);
        }

        if(client.getGame().getPlayer(client.getUser()).getNumOfNotActiveLeaderCards()==2){
            leaderCard1.setImage(new Image(client.getGame().getPlayer(client.getUser()).getAvailableLeaderCards()
                    .stream().filter(x->!x.isActive()).collect(Collectors.toList()).get(0).toImage()));
            leaderCard2.setImage(new Image(client.getGame().getPlayer(client.getUser()).getAvailableLeaderCards()
                    .stream().filter(x->!x.isActive()).collect(Collectors.toList()).get(1).toImage()));

        } else if(client.getGame().getPlayer(client.getUser()).getNumOfNotActiveLeaderCards()==1){
            leaderCard1.setImage(new Image(client.getGame().getPlayer(client.getUser()).getAvailableLeaderCards()
                    .stream().filter(x->!x.isActive()).collect(Collectors.toList()).get(0).toImage()));
        }

        leaderActionText.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(leaderActionText,39);
        active1.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(active1,24);
        active2.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(active2,24);
        discard1.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(discard1,24);
        discard2.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(discard2,24);
        okButton.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(okButton,24);
        leaderAction = new LeaderActionGUI(client);
    }


    /**
     * method used to close page of leader action over the main stage
     * It is performed when closeLeaderAction button is pressed
     */
    @FXML
    public void handleCloseChooseAction()
    {
        Stage stage = (Stage) closeLeaderAction.getScene().getWindow();
        stage.close();
    }

    /**
     * method used to set discarded the first leader card
     * It is performed when discard1 button is pressed
     */
    @FXML
    public void handleDiscardFirstLeader() {
        clearSelection();
        discard1.setStyle("-fx-background-size: 77% auto;");
        int leaderIndex = correctLeaderIndex();
        editLeaderAction(leaderIndex, true);
    }

    /**
     * method used to set discarded the second leader card
     * It is performed when discard2 button is pressed
     */
    @FXML
    public void handleDiscardSecondLeader() {
        clearSelection();
        discard2.setStyle("-fx-background-size: 77% auto;");
        editLeaderAction(2, true);
    }
    /**
     * method used to set active the first leader card
     * It is performed when active1 button is pressed
     */
    @FXML
    public void handleFirstLeaderActivation() {
        clearSelection();
        int leaderIndex;
        active1.setStyle("-fx-background-size: 77% auto;");
        leaderIndex = correctLeaderIndex();
        editLeaderAction(leaderIndex, false);
    }

    /**
     * method used to manage correctly the index of leader card
     * @return
     */
    private int correctLeaderIndex(){
        if(client.getGame().getPlayer(client.getUser()).getAvailableLeaderCards().size() > 1 &&
                client.getGame().getPlayer(client.getUser()).getAvailableLeaderCards().get(0).isActive() &&
                !client.getGame().getPlayer(client.getUser()).getAvailableLeaderCards().get(1).isActive())
            return 2;
        else return  1;
    }

    /**
     * method used to set active the second leader card
     * It is performed when active2 button is pressed
     */
    @FXML
    public void handleSecondLeaderActivation() {
        clearSelection();
        active2.setStyle("-fx-background-size: 77% auto;");
        editLeaderAction(2, false);
    }

    /**
     * method used to send the leader's action message after taking the action
     */
    @FXML
    public void sendLeaderActionMessage() {
        leaderAction.manageUserInteraction();
        Stage stage = (Stage) closeLeaderAction.getScene().getWindow();
        stage.close();
    }

    /**
     * method used to manage the CSS style of the buttons
     */
    @FXML
    void clearSelection() {
        active1.setStyle("-fx-background-size: 90% auto;");
        active2.setStyle("-fx-background-size: 90% auto;");
        discard1.setStyle("-fx-background-size: 90% auto;");
        discard2.setStyle("-fx-background-size: 90% auto;");
    }

    /**
     * //TODO
     * @param leaderIndex
     * @param toDiscard
     */
    private void editLeaderAction(int leaderIndex, boolean toDiscard) {
        if(client.getGame().getCurrPlayer().getNumOfNotActiveLeaderCards()==1) {
            leaderAction.setLeaderAction(toDiscard);
            leaderAction.setLeaderIndex(leaderIndex);
        } else if(client.getGame().getCurrPlayer().getNumOfNotActiveLeaderCards() == 2) {
            leaderAction.setLeaderAction(toDiscard);
            leaderAction.setLeaderIndex(leaderIndex);
        }
    }
}
