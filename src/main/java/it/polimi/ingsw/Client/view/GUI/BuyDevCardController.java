package it.polimi.ingsw.Client.view.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BuyDevCardController extends Controller{
    @FXML
    public AnchorPane anchorBuyDevCard;

    @FXML
    public Text buyDevCardText;

    @FXML
    public Text green,yellow,purple,blue,level1,level2,level3;

    @FXML
    public Button closeBuyDevCard;

    @FXML
    public Button green3,green2,green1,blue3,blue2,blue1,yellow1,yellow2,yellow3,purple1,purple2,purple3;


    @FXML
    @Override
    public void initialize() {


        anchorBuyDevCard.setBackground(new Background(new BackgroundImage(new Image("/gui/img/exit_tab.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false))));
        buyDevCardText.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(buyDevCardText,39);
        green.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(green,24);
        blue.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(blue,24);
        yellow.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(yellow,24);
        purple.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(purple,24);
        level1.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(level1,24);
        level2.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(level2,24);
        level3.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(level3,24);
    }

    @FXML
    public void handleCloseChooseAction()
    {
        Stage stage = (Stage) closeBuyDevCard.getScene().getWindow();
        stage.close();
    }


}
