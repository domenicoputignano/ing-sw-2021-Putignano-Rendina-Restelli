package it.polimi.ingsw.Client.view.GUI;

import it.polimi.ingsw.Client.clientstates.AbstractClientState;
import it.polimi.ingsw.Network.Client;
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

/**
 * Main controller of all GUI scenes
 * It is used to set the background and scene borders of each page
 * Contains all generic methods that are used in all Controller classes of GUI pages
 */
public class Controller {

    /**
     *Attribute used to define the cursor in the scene
     */
    private static final String CURSOR = "/gui/img/cursor.png";

    /**
     * Attribute that represents the top Pane of the scene into the stage
     */
    @FXML
    public AnchorPane top;

    /**
     * Attribute that represents the bottom Pane of the scene into the stage
     */
    @FXML
    public AnchorPane bottom;

    /**
     * Attribute that represents the center Pane of the scene into the stage
     */
    @FXML
    public AnchorPane center;

    /**
     * Attribute that represents the button used to close the scene and log out of the game
     */
    @FXML
    public Button exitButton;
    /**
     * Attribute that represents the button to view the rules of the game
     */
    @FXML
    public Button infoButton;
    /**
     *Attribute used to define the client associated with the scene
     */
    protected Client client;

    /**
     * Attribute that represents the client state
     * @see AbstractClientState
     */
    protected AbstractClientState clientState;

    /**
     * attribute used to set the offset of the x coordinate of the scene
     */
    protected double xOffset = 0;
    /**
     * attribute used to set the offset of the y coordinate of the scene
     */
    protected double yOffset = 0;
    /**
     * attribute that represents the main Stage of the page
     */
    Stage mainstage;
    /**
     * attribute that represents the Stage of generic popup which must be shown on stage
     */
    Stage popup;

    /**
     * Main method that initializes the scene within the stage
     * It is executed every time the scene is loaded into the stage
     */
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

    /**
     * Generic method used to close the stage and log out of the game
     * It is inherited by each controller class of each page
     * It is performed when the closing button is pressed
     */
    @FXML
    void exit()
    {
        showPopup("/gui/fxml/ExitPage.fxml", 570, 380);
    }

    /**
     * Method used to show the Take Resources From Market action update popup
     */
    public void showTakeResourcesFromMarketUpdate()
    {
        showPopup("/gui/fxml/TakeResourcesPopup.fxml", 420, 280);
    }

    /**
     * Method used to show the Leader action update popup
     */
    public void showLeaderActionUpdate(){
        showPopup("/gui/fxml/LeaderActionPopup.fxml", 300, 369);
    }

    /**
     * Method used to show the Buy Development card action update popup
     */
    public void showBuyDevCardUpdate(){
        showPopup("/gui/fxml/BuyDevCardPopup.fxml", 500, 400);
    }

    /**
     * Method used to show a generic error message update popup
     */
    public void showErrorMessage(){
        showPopup("/gui/fxml/ErrorPopup.fxml", 500, 400);
    }

    /**
     * Method used to show the update popup of the action performed by Lorenzo the Magnificent
     */
    public void showLorenzoPlayedPopup(){
        showPopup("/gui/fxml/LorenzoPlayedPopup.fxml", 500, 400);
    }

    /**
     * Method used to show the Move Resources action update popup
     */
    public void showMoveResourcesUpdate(){
        showPopup("/gui/fxml/GenericPopup.fxml", 500, 400);
    }

    /**
     * Method used to show a generic popup on the stage
     * @param FXMLFile fxml file of the page associated with the popup
     * @param width represents the width of the popup to show on the stage
     * @param height represents the height of the popup to show on the stage
     */
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
            popup.setAlwaysOnTop(false);
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

    /**
     * Generic method used to show the rules of the game
     * It is inherited by each controller class of each page
     * It is performed when the info button is pressed
     */
    @FXML
    void info()
    {
        showPopup("/gui/fxml/InfoPage.fxml", 1180, 750);
    }
    /**
     * Static method used to set the client associated with the scene
     * @param client client associated with the scene
     */
    public void setClient(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

    /**
     * Method used to set the font of the textField into the scene
     * The font that is set is Enchanted Land
     * @param textField textField where the font is to be set
     * @param dim font size of the textField
     */
    public void setFont(TextField textField, int dim){
        Font fontText = Font.loadFont(getClass().getResourceAsStream("/gui/font/Enchanted-Land.otf"),dim);
        textField.setFont(fontText);
    }
    /**
     * Method used to set the font of the Button into the scene
     * The font that is set is Enchanted Land
     * @param button button where the font is to be set
     * @param dim font size of the button
     */
    public void setFont(ButtonBase button, int dim){
        Font fontButton = Font.loadFont(getClass().getResourceAsStream("/gui/font/Enchanted-Land.otf"),dim);
        button.setFont(fontButton);
        button.setStyle("-fx-text-fill: rgb(35, 25, 22);");
    }
    /**
     * Method used to set the font of the Text into the scene
     * The font that is set is Enchanted Land
     * @param text button where the font is to be set
     * @param dim font size of the text
     */
    public void setFont(Text text, int dim){
        Font fontText = Font.loadFont(getClass().getResourceAsStream("/gui/font/Enchanted-Land.otf"),dim);
        text.setFont(fontText);
    }
    /**
     * Method used to set the font of the Label into the scene
     * The font that is set is Enchanted Land
     * @param label label where the font is to be set
     * @param dim font size of the label
     */
    public void setFont(Label label, int dim){
        Font fontText = Font.loadFont(getClass().getResourceAsStream("/gui/font/Enchanted-Land.otf"),dim);
        label.setFont(fontText);
        label.setStyle("-fx-text-fill: rgb(211,211,211)");
    }


}