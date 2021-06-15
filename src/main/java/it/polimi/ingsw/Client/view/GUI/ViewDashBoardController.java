package it.polimi.ingsw.Client.view.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.stream.Collectors;

public class ViewDashBoardController extends Controller{

    @FXML
    public AnchorPane anchorViewDashBoard;

    @FXML
    public Text viewDashboardText;

    @FXML
    public Button closeViewDashboard;

    @FXML
    public Button marketTray,decks;

    private static final String CURSOR = "/gui/img/cursor.png";

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

    @FXML
    public void handleCloseViewDashboard()
    {
        Stage stage = (Stage) closeViewDashboard.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handleViewMarketTray()
    {
        Stage stage = (Stage) closeViewDashboard.getScene().getWindow();
        stage.close();
        showPopup("/gui/FXML/TakeResourcesFromMarketPage.fxml", 1180, 750);
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

    @FXML
    public void handleViewDecks(){
        Stage stage = (Stage) closeViewDashboard.getScene().getWindow();
        stage.close();
        showPopup("/gui/FXML/BuyDevCardPage.fxml", 1180, 750);
        ((BuyDevCardController)GUIApp.controller).buyDevCardText.setText("Decks");
        ((BuyDevCardController)GUIApp.controller).buyDevCardText.setX(60);
    }
}
