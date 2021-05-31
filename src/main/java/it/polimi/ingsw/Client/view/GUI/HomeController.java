package it.polimi.ingsw.Client.view.GUI;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;

public class HomeController extends Controller{

    @FXML
    public ImageView loading;

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
