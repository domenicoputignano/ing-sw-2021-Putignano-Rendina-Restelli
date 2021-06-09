package it.polimi.ingsw.Client.view.GUI;

import it.polimi.ingsw.Commons.ColorCard;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ActivateProductionController extends Controller implements PaymentController{
    @FXML
    public AnchorPane anchorActivateProd;

    @FXML
    public Text activateProdText;

    @FXML
    public Button closeActivateProd;

    @FXML
    public Button slot1,slot2,slot3,basicProduction;

    @FXML
    @Override
    public void initialize() {

        this.client = GUIApp.client;
        anchorActivateProd.setBackground(new Background(new BackgroundImage(new Image("/gui/img/exit_tab.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false))));
        activateProdText.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(activateProdText,39);
        initializeSlotsImages();
    }

    private void initializeSlotsImages()
    {
        if(!client.getGame().getPlayer(client.getUser()).getPersonalBoard().isEmptySlot(0)) {
            slot1.setStyle("-fx-background-image: url(" +
                    client.getGame().getPlayer(client.getUser()).getPersonalBoard().peekTopCardInSlot(0).toImage() + ")");
        }
        if(!client.getGame().getPlayer(client.getUser()).getPersonalBoard().isEmptySlot(1)) {
            slot2.setStyle("-fx-background-image: url(" +
                    client.getGame().getPlayer(client.getUser()).getPersonalBoard().peekTopCardInSlot(1).toImage() + ")");
        }
        if(!client.getGame().getPlayer(client.getUser()).getPersonalBoard().isEmptySlot(2)) {
            slot3.setStyle("-fx-background-image: url(" +
                    client.getGame().getPlayer(client.getUser()).getPersonalBoard().peekTopCardInSlot(2).toImage() + ")");
        }
    }

    @FXML
    public void handleCloseChooseAction()
    {
        Stage stage = (Stage) closeActivateProd.getScene().getWindow();
        stage.close();
    }

    @Override
    public void setVisibleFirstResource() {

    }

    @Override
    public void setVisibleSecondResource() {

    }

    @Override
    public void setVisibleThirdResource() {

    }

}
