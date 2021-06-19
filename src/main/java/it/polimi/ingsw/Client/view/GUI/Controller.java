package it.polimi.ingsw.Client.view.GUI;

import it.polimi.ingsw.Client.clientstates.AbstractClientState;
import it.polimi.ingsw.Network.Client;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class Controller {


    private static final String CURSOR = "/gui/img/cursor.png";

    @FXML
    public AnchorPane top;

    @FXML
    public AnchorPane bottom;

    @FXML
    public AnchorPane center;

    @FXML
    public Button exitButton;

    @FXML
    public Button infoButton;

    protected Client client;

    protected AbstractClientState clientState;

    protected double xOffset = 0;
    protected double yOffset = 0;

    Stage mainstage;
    Stage popup;

    @FXML
    public void initialize() {

        mainstage = new Stage();
        mainstage.setTitle("Master of Renaissance");
        mainstage.initStyle(StageStyle.TRANSPARENT);


        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO,BackgroundSize.AUTO, false,false,true,true);

        top.setBackground(new Background(new BackgroundImage(new Image("/gui/img/top.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bSize)));
        bottom.setBackground(new Background(new BackgroundImage(new Image("/gui/img/bottom.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bSize)));
    }
    @FXML
    void exit()
    {
        showPopup("/gui/FXML/ExitPage.fxml", 570, 380);
    }

    public void showTakeResourcesFromMarketUpdate()
    {
        showPopup("/gui/FXML/TakeResourcesPopup.fxml", 420, 280);
    }

    public void showLeaderActionUpdate(){
        showPopup("/gui/FXML/LeaderActionPopup.fxml", 300, 369);
    }

    public void showBuyDevCardUpdate(){
        showPopup("/gui/FXML/BuyDevCardPopup.fxml", 500, 400);
    }

    public void showErrorMessage(){
        showPopup("/gui/FXML/ErrorPopup.fxml", 500, 400);
    }

    public void showLorenzoPlayedPopup(){
        showPopup("/gui/FXML/LorenzoPlayedPopup.fxml", 500, 400);
    }

    public void showMoveResourcesUpdate(){
        showPopup("/gui/FXML/GenericPopup.fxml", 500, 400);
    }

    public void showPopup(String FXMLFile, int width, int height){
        popup = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLFile));
            Parent root = loader.load();
            GUIApp.controller = loader.getController();
            popup.initStyle(StageStyle.TRANSPARENT);
            root.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                }
            });
            root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    popup.setX(event.getScreenX() - xOffset);
                    popup.setY(event.getScreenY() - yOffset);
                }
            });
            Scene scene = new Scene(Objects.requireNonNull(root), width, height, Color.TRANSPARENT);
            scene.setCursor(new ImageCursor(new Image(CURSOR), 36, 45));
            popup.setAlwaysOnTop(true);
            scene.setUserData(loader);
            popup.initModality(Modality.WINDOW_MODAL);
            popup.initOwner(GUIApp.getStage());
            scene.setUserData(loader);
            popup.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
        popup.show();
    }

    @FXML
    void info()
    {
        showPopup("/gui/FXML/InfoPage.fxml", 1180, 750);
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

    public void setFont(TextField textField, int dim){
        Font fontText = Font.loadFont(getClass().getResourceAsStream("/gui/font/Enchanted-Land.otf"),dim);
        textField.setFont(fontText);
    }
    public void setFont(ButtonBase button, int dim){
        Font fontButton = Font.loadFont(getClass().getResourceAsStream("/gui/font/Enchanted-Land.otf"),dim);
        button.setFont(fontButton);
        button.setStyle("-fx-text-fill: rgb(35, 25, 22);");
    }
    public void setFont(Text text, int dim){
        Font fontText = Font.loadFont(getClass().getResourceAsStream("/gui/font/Enchanted-Land.otf"),dim);
        text.setFont(fontText);
    }

    public void setFont(Label label, int dim){
        Font fontText = Font.loadFont(getClass().getResourceAsStream("/gui/font/Enchanted-Land.otf"),dim);
        label.setFont(fontText);
        label.setStyle("-fx-text-fill: rgb(211,211,211)");
    }


}