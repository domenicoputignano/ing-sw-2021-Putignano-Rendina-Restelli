package it.polimi.ingsw.Client.view.GUI;

import it.polimi.ingsw.Client.Checker;
import it.polimi.ingsw.Commons.ColorCard;
import it.polimi.ingsw.Commons.DevelopmentCard;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    public Text errorDevText;

    @FXML
    public ImageView selectedCard;

    @FXML
    public Button okButton;

    @FXML
    @Override
    public void initialize() {

        this.client = GUIApp.client;

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
        errorDevText.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(errorDevText,30);
        okButton.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(okButton,24);
        initializeDecksImages();
    }

    private void initializeDecksImages(){

        green3.setStyle("-fx-background-image: url(" +
                client.getGame().getDeckTopCard(3, ColorCard.green).toImage() + ")");
        green2.setStyle("-fx-background-image: url(" +
                client.getGame().getDeckTopCard(2, ColorCard.green).toImage() + ")");
        green1.setStyle("-fx-background-image: url(" +
                client.getGame().getDeckTopCard(1, ColorCard.green).toImage() + ")");

        blue3.setStyle("-fx-background-image: url(" +
                client.getGame().getDeckTopCard(3, ColorCard.blue).toImage() + ")");
        blue2.setStyle("-fx-background-image: url(" +
                client.getGame().getDeckTopCard(2, ColorCard.blue).toImage() + ")");
        blue1.setStyle("-fx-background-image: url(" +
                client.getGame().getDeckTopCard(1, ColorCard.blue).toImage() + ")");

        yellow3.setStyle("-fx-background-image: url(" +
                client.getGame().getDeckTopCard(3, ColorCard.yellow).toImage() + ")");
        yellow2.setStyle("-fx-background-image: url(" +
                client.getGame().getDeckTopCard(2, ColorCard.yellow).toImage() + ")");
        yellow1.setStyle("-fx-background-image: url(" +
                client.getGame().getDeckTopCard(1, ColorCard.yellow).toImage() + ")");

        purple3.setStyle("-fx-background-image: url(" +
                client.getGame().getDeckTopCard(3, ColorCard.purple).toImage() + ")");
        purple2.setStyle("-fx-background-image: url(" +
                client.getGame().getDeckTopCard(2, ColorCard.purple).toImage() + ")");
        purple1.setStyle("-fx-background-image: url(" +
                client.getGame().getDeckTopCard(1, ColorCard.purple).toImage() + ")");

    }

    private void setSelectedCardImage(DevelopmentCard developmentCard){
        selectedCard.setImage(new Image(developmentCard.toImage()));
    }

    private void setErrorDevTextOK(){
        errorDevText.setText("You have enough \nresources to purchase \nthe selected card");
        okButton.setVisible(true);
    }

    private void setErrorDevTextKO(){
        errorDevText.setText("You don't have \nenough resources \nto purchase \nthe selected card");
    }

    @FXML
    public void checkIfCanBuyCard3Green() {
        DevelopmentCard developmentCard = client.getGame().getDeckTopCard(3,ColorCard.green);
        setSelectedCardImage(developmentCard);
        if(Checker.checkResources(developmentCard.getCost(),client.getGame().getPlayer(client.getUser()).getPersonalBoard())) {
            //TODO mandarlo nella scelta di dove vuole prendere le risorse per acquistarla
            setErrorDevTextOK();
        } else {
            setErrorDevTextKO();
        }
    }

    @FXML
    public void checkIfCanBuyCard2Green() {
        DevelopmentCard developmentCard = client.getGame().getDeckTopCard(2,ColorCard.green);
        setSelectedCardImage(developmentCard);
        if(Checker.checkResources(developmentCard.getCost(),client.getGame().getPlayer(client.getUser()).getPersonalBoard())) {
            //TODO mandarlo nella scelta di dove vuole prendere le risorse per acquistarla
            setErrorDevTextOK();
        } else {
            setErrorDevTextKO();
        }
    }

    @FXML
    public void checkIfCanBuyCard1Green() {
        DevelopmentCard developmentCard = client.getGame().getDeckTopCard(1,ColorCard.green);
        setSelectedCardImage(developmentCard);
        if(Checker.checkResources(developmentCard.getCost(),client.getGame().getPlayer(client.getUser()).getPersonalBoard())) {
            //TODO mandarlo nella scelta di dove vuole prendere le risorse per acquistarla
            setErrorDevTextOK();
        } else {
            setErrorDevTextKO();
        }

    }

    @FXML
    public void checkIfCanBuyCard3Blue() {
        DevelopmentCard developmentCard = client.getGame().getDeckTopCard(3,ColorCard.blue);
        setSelectedCardImage(developmentCard);
        if(Checker.checkResources(developmentCard.getCost(),client.getGame().getPlayer(client.getUser()).getPersonalBoard())) {
            //TODO mandarlo nella scelta di dove vuole prendere le risorse per acquistarla
            setErrorDevTextOK();
        } else {
            setErrorDevTextKO();
        }
    }

    @FXML
    public void checkIfCanBuyCard2Blue() {
        DevelopmentCard developmentCard = client.getGame().getDeckTopCard(2,ColorCard.blue);
        setSelectedCardImage(developmentCard);
        if(Checker.checkResources(developmentCard.getCost(),client.getGame().getPlayer(client.getUser()).getPersonalBoard())) {
            //TODO mandarlo nella scelta di dove vuole prendere le risorse per acquistarla
            setErrorDevTextOK();
        } else {
            setErrorDevTextKO();
        }
    }

    @FXML
    public void checkIfCanBuyCard1Blue() {
        DevelopmentCard developmentCard = client.getGame().getDeckTopCard(1,ColorCard.blue);
        setSelectedCardImage(developmentCard);
        if(Checker.checkResources(developmentCard.getCost(),client.getGame().getPlayer(client.getUser()).getPersonalBoard())) {
            //TODO mandarlo nella scelta di dove vuole prendere le risorse per acquistarla
            setErrorDevTextOK();
        } else {
            setErrorDevTextKO();
        }
    }

    @FXML
    public void checkIfCanBuyCard3Yellow() {
        DevelopmentCard developmentCard = client.getGame().getDeckTopCard(3,ColorCard.yellow);
        setSelectedCardImage(developmentCard);
        if(Checker.checkResources(developmentCard.getCost(),client.getGame().getPlayer(client.getUser()).getPersonalBoard())) {
            //TODO mandarlo nella scelta di dove vuole prendere le risorse per acquistarla
            setErrorDevTextOK();
        } else {
            setErrorDevTextKO();
        }
    }

    @FXML
    public void checkIfCanBuyCard2Yellow() {
        DevelopmentCard developmentCard = client.getGame().getDeckTopCard(2,ColorCard.yellow);
        setSelectedCardImage(developmentCard);
        if(Checker.checkResources(developmentCard.getCost(),client.getGame().getPlayer(client.getUser()).getPersonalBoard())) {
            //TODO mandarlo nella scelta di dove vuole prendere le risorse per acquistarla
            setErrorDevTextOK();
        } else {
            setErrorDevTextKO();
        }
    }

    @FXML
    public void checkIfCanBuyCard1Yellow() {
        DevelopmentCard developmentCard = client.getGame().getDeckTopCard(1,ColorCard.yellow);
        setSelectedCardImage(developmentCard);
        if(Checker.checkResources(developmentCard.getCost(),client.getGame().getPlayer(client.getUser()).getPersonalBoard())) {
            //TODO mandarlo nella scelta di dove vuole prendere le risorse per acquistarla
            setErrorDevTextOK();
        } else {
            setErrorDevTextKO();
        }
    }

    @FXML
    public void checkIfCanBuyCard3Purple() {
        DevelopmentCard developmentCard = client.getGame().getDeckTopCard(3,ColorCard.purple);
        setSelectedCardImage(developmentCard);
        if(Checker.checkResources(developmentCard.getCost(),client.getGame().getPlayer(client.getUser()).getPersonalBoard())) {
            //TODO mandarlo nella scelta di dove vuole prendere le risorse per acquistarla
            setErrorDevTextOK();
        } else {
            setErrorDevTextKO();
        }
    }

    @FXML
    public void checkIfCanBuyCard2Purple() {
        DevelopmentCard developmentCard = client.getGame().getDeckTopCard(2,ColorCard.purple);
        setSelectedCardImage(developmentCard);
        if(Checker.checkResources(developmentCard.getCost(),client.getGame().getPlayer(client.getUser()).getPersonalBoard())) {
            //TODO mandarlo nella scelta di dove vuole prendere le risorse per acquistarla
            setErrorDevTextOK();
        } else {
            setErrorDevTextKO();
        }
    }

    @FXML
    public void checkIfCanBuyCard1Purple() {
        DevelopmentCard developmentCard = client.getGame().getDeckTopCard(1,ColorCard.purple);
        setSelectedCardImage(developmentCard);
        if(Checker.checkResources(developmentCard.getCost(),client.getGame().getPlayer(client.getUser()).getPersonalBoard())) {
            //TODO mandarlo nella scelta di dove vuole prendere le risorse per acquistarla
            setErrorDevTextOK();
        } else {
            setErrorDevTextKO();
        }
    }





    @FXML
    public void handleCloseChooseAction()
    {
        Stage stage = (Stage) closeBuyDevCard.getScene().getWindow();
        stage.close();
    }


}
