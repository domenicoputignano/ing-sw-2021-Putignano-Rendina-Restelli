package it.polimi.ingsw.Client.view.GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class PlayerBoardController extends Controller {
    Stage actions;
    Stage move;

    @FXML
    public ImageView leaderCard1;

    @FXML
    public Button chooseAction,moveAction;

    @FXML
    public ImageView leaderCard2;
    @FXML
    private static final String CURSOR = "/gui/img/cursor.png";

    public ImageView[] cells = new ImageView[25];
    public ImageView[] favorTiles = new ImageView[3];

    @FXML
    public ImageView cell0,cell1,cell2,cell3,cell4,cell5,cell6,cell7,cell8,cell9,cell10
            ,cell11,cell12,cell13,cell14,cell15,cell16,cell17,cell18,cell19,cell20,cell21,
            cell22,cell23,cell24;
    @FXML
    public ImageView favorTile1,favorTile2,favorTile3;

    public void initialize() {
        super.initialize();

        BackgroundSize bSize = new BackgroundSize(80, 80, true, true, true, true);

        center.setBackground(new Background(new BackgroundImage(new Image("/gui/img/background.jpg"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bSize)));
        setFont(chooseAction,24);
        setFont(moveAction,24);
        chooseAction.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        moveAction.setStyle("-fx-text-fill: rgb(35, 25, 22);");

        this.client = GUIApp.client;

        leaderCard1.setImage(new Image(client.getGame().getPlayer(client.getUser()).getAvailableLeaderCards().get(0).toImage()));

        leaderCard2.setImage(new Image(client.getGame().getPlayer(client.getUser()).getAvailableLeaderCards().get(1).toImage()));
        initializeCells();
    }

    private void initializeCells()
    {
        cells[0] = cell0;
        cells[1] = cell1;
        cells[2] = cell2;
        cells[3] = cell3;
        cells[4] = cell4;
        cells[5] = cell5;
        cells[6] = cell6;
        cells[7] = cell7;
        cells[8] = cell8;
        cells[9] = cell9;
        cells[10] = cell10;
        cells[11] = cell11;
        cells[12] = cell12;
        cells[13] = cell13;
        cells[14] = cell14;
        cells[15] = cell15;
        cells[16] = cell16;
        cells[17] = cell17;
        cells[18] = cell18;
        cells[19] = cell19;
        cells[20] = cell20;
        cells[21] = cell21;
        cells[22] = cell22;
        cells[23] = cell23;
        cells[24] = cell24;
        favorTiles[0] = favorTile1;
        favorTiles[1] = favorTile2;
        favorTiles[2] = favorTile3;
        favorTiles[0].setImage(new Image("/gui/img/favorTile1D.png"));
        favorTiles[1].setImage(new Image("/gui/img/favorTile2D.png"));
        favorTiles[2].setImage(new Image("/gui/img/favorTile3D.png"));
        cells[12].setImage(new Image("/gui/img/faith.png"));
    }
    @FXML
    public void handleChooseActionButton()
    {
        actions = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/ChooseActionPage.fxml"));
            Parent root = loader.load();
            GUIApp.controller = loader.getController();
            Scene scene = new Scene(Objects.requireNonNull(root), 820, 520, Color.TRANSPARENT);
            scene.setCursor(new ImageCursor(new Image(CURSOR), 36, 45));
            actions.initStyle(StageStyle.TRANSPARENT);
            actions.setAlwaysOnTop(true);
            scene.setUserData(loader);
            actions.initModality(Modality.WINDOW_MODAL);
            actions.initOwner(GUIApp.getStage());
            scene.setUserData(loader);
            actions.setScene(scene);

        } catch (IOException e) {
                e.printStackTrace();
            }
        actions.show();
    }
    @FXML
    public void handleMoveActionButton()
    {
        move = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/MoveActionPage.fxml"));
            Parent root = loader.load();
            GUIApp.controller = loader.getController();
            Scene scene = new Scene(Objects.requireNonNull(root), 1180, 750, Color.TRANSPARENT);
            scene.setCursor(new ImageCursor(new Image(CURSOR), 36, 45));
            move.initStyle(StageStyle.TRANSPARENT);
            move.setAlwaysOnTop(true);
            scene.setUserData(loader);
            move.initModality(Modality.WINDOW_MODAL);
            move.initOwner(GUIApp.getStage());
            scene.setUserData(loader);
            move.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
        move.show();
    }
}
