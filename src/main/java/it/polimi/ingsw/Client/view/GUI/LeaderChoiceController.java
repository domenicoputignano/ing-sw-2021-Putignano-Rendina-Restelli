package it.polimi.ingsw.Client.view.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class LeaderChoiceController extends Controller{
    @FXML
    public TextField chooseCardText;

    @FXML
    public TextField errorSelectedResources;

    @FXML
    public Button leaderCard1;

    @FXML
    public Button leaderCard2;

    @FXML
    public Button leaderCard3;

    @FXML
    public Button leaderCard4;

    @FXML
    public ImageView indicator1;

    @FXML
    public ImageView indicator2;

    @FXML
    public ImageView indicator3;

    @FXML
    public ImageView indicator4;

    @FXML
    public TextField errorChoiceText;

    @FXML
    public Button clearButton;

    @FXML
    public Button okButton;

    private int selectedCard = 0;
    private boolean card1 = false,card2 = false,card3 = false,card4 = false;
    @FXML
    public void initialize() {
        super.initialize();

        BackgroundSize bSize = new BackgroundSize(80, 80, true, true, true, true);

        center.setBackground(new Background(new BackgroundImage(new Image("/gui/img/background.jpg"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bSize)));
        chooseCardText.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        errorChoiceText.setStyle("-fx-text-fill: #f2b535");
        clearButton.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        okButton.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        errorSelectedResources.setStyle("-fx-text-fill: #f2b535");
        setFont(chooseCardText,30);
        setFont(errorSelectedResources,30);
        setFont(errorChoiceText,30);
        setFont(clearButton,22);
        setFont(okButton,20);
    }

    @FXML
    void handleBackButton()
    {
        GUIApp.getStage().setScene(GUIApp.loadScene("/gui/fxml/SelectNumOfPlayersPage.fxml"));
        GUIApp.getStage().show();
    }

    @FXML
    void discardLeaderCard1()
    {
        errorSelectedResources.clear();
        if(!card1) {
            if(selectedCard < 2) {
                leaderCard1.setStyle("-fx-border-color: rgb(231,156,48);-fx-border-width: 5;");
                selectedCard++;
                card1 = true;
                indicator1.setVisible(true);
            }
            else errorChoiceText.setText("ERROR: You can only select 2 leader cards");
        }
    }
    @FXML
    void discardLeaderCard2()
    {
        errorSelectedResources.clear();
        if(!card2) {
            if(selectedCard < 2) {
                leaderCard2.setStyle("-fx-border-color: rgb(231,156,48);-fx-border-width: 5;");
                selectedCard++;
                card2 = true;
                indicator2.setVisible(true);
            }
            else errorChoiceText.setText("ERROR: You can only select 2 leader cards");
        }
    }
    @FXML
    void discardLeaderCard3()
    {
        errorSelectedResources.clear();
        if(!card3) {
            if(selectedCard < 2) {
                leaderCard3.setStyle("-fx-border-color: rgb(231,156,48);-fx-border-width: 5;");
                selectedCard++;
                card3 = true;
                indicator3.setVisible(true);
            }
            else errorChoiceText.setText("ERROR: You can only select 2 leader cards");
        }
    }
    @FXML
    void discardLeaderCard4()
    {
        errorSelectedResources.clear();
        if(!card4) {
            if(selectedCard < 2) {
                leaderCard4.setStyle("-fx-border-color: rgb(231,156,48);-fx-border-width: 5;");
                selectedCard++;
                card4 = true;
                indicator4.setVisible(true);
            }
            else errorChoiceText.setText("ERROR: You can only select 2 Leader Cards");
        }
    }
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
        indicator1.setVisible(false);
        indicator2.setVisible(false);
        indicator3.setVisible(false);
        indicator4.setVisible(false);
        errorChoiceText.clear();
        errorSelectedResources.clear();
    }

    @FXML
    void handleOkChoice()
    {
        if(selectedCard != 2)
        {
            errorSelectedResources.setText("Select 2 Leader Card");
        }
        else
        {
            GUIApp.getStage().setScene(GUIApp.loadScene("/gui/fxml/ResourceChoicePage.fxml"));
            GUIApp.getStage().show();
        }
    }
}
