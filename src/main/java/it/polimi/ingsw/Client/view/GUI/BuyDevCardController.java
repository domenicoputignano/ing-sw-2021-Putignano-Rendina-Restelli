package it.polimi.ingsw.Client.view.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class BuyDevCardController extends Controller{
    @FXML
    public AnchorPane anchorBuyDevCard;

    @FXML
    public TextField buyDevCardText;

    @FXML
    public Button closeBuyDevCard;

    @FXML
    public Button green3;


    @FXML
    @Override
    public void initialize() {


        anchorBuyDevCard.setBackground(new Background(new BackgroundImage(new Image("/gui/img/exit_tab.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false))));
        //buyDevCardText.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        //setFont(buyDevCardText,39);
    }

    @FXML
    public void handleCloseChooseAction()
    {
        Stage stage = (Stage) closeBuyDevCard.getScene().getWindow();
        stage.close();
    }


}
