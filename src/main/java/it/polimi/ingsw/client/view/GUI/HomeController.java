package it.polimi.ingsw.client.view.GUI;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;

/**
 * Class that represents the controller of the Home page of the game
 * this page is the game start page showing the game icon
 * and a loading waiting for the game to start
 */
public class HomeController extends Controller{
    /**
     * attribute that represents the imageView of the
     * loading waiting for the start of the game
     */
    @FXML
    public ImageView loading;

    /**
     * Main method that initializes the scene within the stage
     * It takes care of setting the background of the scene
     * and the animated image of the loading
     */
    @FXML
    public void initialize(){
        super.initialize();
        BackgroundSize bSize = new BackgroundSize(80, 80, true, true, true, true);

        center.setBackground(new Background(new BackgroundImage(new Image("/gui/img/background.jpg"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bSize)));
        loading.setImage(new Image("/gui/img/loading0.png"));
        Timeline t = new Timeline();
        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(500),
                (ActionEvent event) ->{
                    loading.setImage(new Image("/gui/img/loading1.png"));
                }
        ));
        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(1000),
                (ActionEvent event) ->{
                    loading.setImage(new Image("/gui/img/loading2.png"));
                }
        ));
        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(1500),
                (ActionEvent event) ->{
                    loading.setImage(new Image("/gui/img/loading3.png"));
                }
        ));
        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(2000),
                (ActionEvent event) ->{
                    loading.setImage(new Image("/gui/img/loading4.png"));
                }
        ));
        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(2500),
                (ActionEvent event) ->{
                    loading.setImage(new Image("/gui/img/loading5.png"));
                }
        ));
        t.play();
    }
}
