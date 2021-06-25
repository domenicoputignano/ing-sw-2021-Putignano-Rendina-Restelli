package it.polimi.ingsw.Client.view.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Class that represents the controller of the ViewDashBoard page of the game
 * In this page the user not in turn is able to view the decks and the MarketTray
 * of the game to check their status
 */
public class ViewDashBoardController extends Controller{
    /**
     * Attribute that represents the central pane of the page
     */
    @FXML
    public AnchorPane anchorViewDashBoard;
    /**
     * Attribute that represents the textField where the title of the page is shown
     */
    @FXML
    public Text viewDashboardText;
    /**
     * Attribute that represents the button to close the popup of the page
     */
    @FXML
    public Button closeViewDashboard;
    /**
     * attributes that represent the buttons associated with the view of the decks and the marketTray
     */
    @FXML
    public Button marketTray,decks;
    /**
     * Main method that initializes the scene within the stage
     * It takes care of setting the background of the scene
     * and the font of the texts and buttons
     */
    @FXML
    @Override
    public void initialize() {

        this.client = GUIApp.client;

        anchorViewDashBoard.setBackground(new Background(new BackgroundImage(new Image("/gui/img/exit_tab.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false))));
        setFont(viewDashboardText,39);
        viewDashboardText.setStyle("-fx-text-fill: rgb(35, 25, 22);");

        setFont(marketTray,35);
        marketTray.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(decks,35);
        decks.setStyle("-fx-text-fill: rgb(35, 25, 22);");
    }
    /**
     * method used to close the popup page over the main stage
     * It is performed when closeViewDashboard button is pressed
     */
    @FXML
    public void handleCloseViewDashboard()
    {
        Stage stage = (Stage) closeViewDashboard.getScene().getWindow();
        stage.close();
    }
    /**
     * method used to show the MarketTray of the game
     * It is performed when marketTray button is pressed
     */
    @FXML
    public void handleViewMarketTray()
    {
        Stage stage = (Stage) closeViewDashboard.getScene().getWindow();
        stage.close();
        showPopup("/gui/fxml/TakeResourcesFromMarketPage.fxml", 1180, 750);
        ((TakeResourcesController)GUIApp.controller).resourceSupply.setVisible(false);
        ((TakeResourcesController)GUIApp.controller).row1.setVisible(false);
        ((TakeResourcesController)GUIApp.controller).row2.setVisible(false);
        ((TakeResourcesController)GUIApp.controller).row3.setVisible(false);
        ((TakeResourcesController)GUIApp.controller).column1.setVisible(false);
        ((TakeResourcesController)GUIApp.controller).column2.setVisible(false);
        ((TakeResourcesController)GUIApp.controller).column3.setVisible(false);
        ((TakeResourcesController)GUIApp.controller).column4.setVisible(false);
        ((TakeResourcesController)GUIApp.controller).marketTrayImage.setX(230);
        ((TakeResourcesController)GUIApp.controller).marble11.setX(230);
        ((TakeResourcesController)GUIApp.controller).marble12.setX(230);
        ((TakeResourcesController)GUIApp.controller).marble13.setX(230);
        ((TakeResourcesController)GUIApp.controller).marble14.setX(230);
        ((TakeResourcesController)GUIApp.controller).marble21.setX(230);
        ((TakeResourcesController)GUIApp.controller).marble22.setX(230);
        ((TakeResourcesController)GUIApp.controller).marble23.setX(230);
        ((TakeResourcesController)GUIApp.controller).marble24.setX(230);
        ((TakeResourcesController)GUIApp.controller).marble31.setX(230);
        ((TakeResourcesController)GUIApp.controller).marble32.setX(230);
        ((TakeResourcesController)GUIApp.controller).marble33.setX(230);
        ((TakeResourcesController)GUIApp.controller).marble34.setX(230);
        ((TakeResourcesController)GUIApp.controller).slidingMarble.setX(230);
        ((TakeResourcesController)GUIApp.controller).takeResourcesText.setText("Market Tray");
        ((TakeResourcesController)GUIApp.controller).takeResourcesText.setX(60);
    }

    /**
     * method used to show the decks in the game
     * It is performed when decks button is pressed
     */
    @FXML
    public void handleViewDecks(){
        Stage stage = (Stage) closeViewDashboard.getScene().getWindow();
        stage.close();
        showPopup("/gui/fxml/BuyDevCardPage.fxml", 1180, 750);
        ((BuyDevCardController)GUIApp.controller).buyDevCardText.setText("Decks");
        ((BuyDevCardController)GUIApp.controller).buyDevCardText.setX(60);
    }
}
