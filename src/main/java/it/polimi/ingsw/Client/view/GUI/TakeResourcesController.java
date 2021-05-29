package it.polimi.ingsw.Client.view.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TakeResourcesController extends Controller{
    @FXML
    public AnchorPane anchorBuyDevCard;

    @FXML
    public TextField takeResourcesText;

    @FXML
    public Text resourcesText;

    @FXML
    public Button closeBuyDevCard;

    @FXML
    public ImageView light1rows,light2rows,light3rows;
    @FXML
    public ImageView light1columns,light2columns,light3columns,light4columns;

    @FXML
    public ImageView marble11,marble12,marble13,marble14,
                        marble21,marble22,marble23,marble24
                        ,marble31,marble32,marble33,marble34;

    @FXML
    public ImageView selResources1,selResources2,selResources3,selResources4;

    @FXML
    public Button row1,row2,row3;
    @FXML
    public Button column1,column2,column3,column4;

    @FXML
    public Button yesResources,noResources;

    @FXML
    public MenuButton menu1,menu2,menu3,menu4;

    @FXML
    public Button sel1Depot1,sel1Depot2,sel1Depot3,
                sel2Depot1,sel2Depot2,sel2Depot3,
                sel3Depot1,sel3Depot2,sel3Depot3,
                sel4Depot1,sel4Depot2,sel4Depot3,
                sel1ExtraDepot,sel2ExtraDepot,sel3ExtraDepot,sel4ExtraDepot,
                discard1,discard2,discard3,discard4;

    @FXML
    @Override
    public void initialize() {


        anchorBuyDevCard.setBackground(new Background(new BackgroundImage(new Image("/gui/img/exit_tab.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false))));
        resourcesText.setStyle("-fx-text-fill: rgb(35, 25, 22);");
        setFont(resourcesText,27);
        setFont(yesResources,23);
        setFont(noResources,23);
        setFont(sel1Depot1,21);
        setFont(sel1Depot2,21);
        setFont(sel1Depot3,21);
        setFont(sel1ExtraDepot,21);
        setFont(sel2Depot1,21);
        setFont(sel2Depot2,21);
        setFont(sel2Depot3,21);
        setFont(sel2ExtraDepot,21);
        setFont(sel3Depot1,21);
        setFont(sel3Depot2,21);
        setFont(sel3Depot3,21);
        setFont(sel3ExtraDepot,21);
        setFont(sel4Depot1,21);
        setFont(sel4Depot2,21);
        setFont(sel4Depot3,21);
        setFont(sel4ExtraDepot,21);
        setFont(discard1,21);
        setFont(discard2,21);
        setFont(discard3,21);
        setFont(discard4,21);
    }

    @FXML
    public void handleCloseChooseAction()
    {
        Stage stage = (Stage) closeBuyDevCard.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void selectedRow1()
    {
        setVisibleFalse();
        resourcesText.setText("Do you want to get the following resources?");
        yesResources.setVisible(true);
        noResources.setVisible(true);
        light1rows.setVisible(true);
        selResources1.setImage(marble11.getImage());
        selResources2.setImage(marble12.getImage());
        selResources3.setImage(marble13.getImage());
        selResources4.setImage(marble14.getImage());
    }
    @FXML
    public void selectedRow2()
    {
        setVisibleFalse();
        resourcesText.setText("Do you want to get the following resources?");
        yesResources.setVisible(true);
        noResources.setVisible(true);
        light2rows.setVisible(true);
        selResources1.setImage(marble21.getImage());
        selResources2.setImage(marble22.getImage());
        selResources3.setImage(marble23.getImage());
        selResources4.setImage(marble24.getImage());
    }
    @FXML
    public void selectedRow3()
    {
        setVisibleFalse();
        resourcesText.setText("Do you want to get the following resources?");
        yesResources.setVisible(true);
        noResources.setVisible(true);
        light3rows.setVisible(true);
        selResources1.setImage(marble31.getImage());
        selResources2.setImage(marble32.getImage());
        selResources3.setImage(marble33.getImage());
        selResources4.setImage(marble34.getImage());
    }
    @FXML
    public void selectedColumn1()
    {
        setVisibleFalse();
        resourcesText.setText("Do you want to get the following resources?");
        yesResources.setVisible(true);
        noResources.setVisible(true);
        light1columns.setVisible(true);
        selResources1.setImage(marble11.getImage());
        selResources2.setImage(marble21.getImage());
        selResources3.setImage(marble31.getImage());
    }
    @FXML
    public void selectedColumn2()
    {
        setVisibleFalse();
        resourcesText.setText("Do you want to get the following resources?");
        yesResources.setVisible(true);
        noResources.setVisible(true);
        light2columns.setVisible(true);
        selResources1.setImage(marble12.getImage());
        selResources2.setImage(marble22.getImage());
        selResources3.setImage(marble32.getImage());
    }
    @FXML
    public void selectedColumn3()
    {
        setVisibleFalse();
        resourcesText.setText("Do you want to get the following resources?");
        yesResources.setVisible(true);
        noResources.setVisible(true);
        light3columns.setVisible(true);

        selResources1.setImage(marble13.getImage());
        selResources2.setImage(marble23.getImage());
        selResources3.setImage(marble33.getImage());
    }
    @FXML
    public void selectedColumn4()
    {
        setVisibleFalse();
        resourcesText.setText("Do you want to get the following marbles?");
        yesResources.setVisible(true);
        noResources.setVisible(true);
        light4columns.setVisible(true);

        selResources1.setImage(marble14.getImage());
        selResources2.setImage(marble24.getImage());
        selResources3.setImage(marble34.getImage());
    }
    public void setVisibleFalse()
    {
        light1columns.setVisible(false);
        light2columns.setVisible(false);
        light3columns.setVisible(false);
        light4columns.setVisible(false);
        light1rows.setVisible(false);
        light2rows.setVisible(false);
        light3rows.setVisible(false);
        resourcesText.setText("");
        yesResources.setVisible(false);
        noResources.setVisible(false);
        selResources1.setImage(null);
        selResources2.setImage(null);
        selResources3.setImage(null);
        selResources4.setImage(null);
    }
    @FXML
    public void handleNoResources()
    {
        setVisibleFalse();
    }

    @FXML public void handleYesResources()
    {
        yesResources.setVisible(false);
        noResources.setVisible(false);
        resourcesText.setText("Select where to place the resources: ");
        row1.setVisible(false);
        row2.setVisible(false);
        row3.setVisible(false);
        column1.setVisible(false);
        column2.setVisible(false);
        column3.setVisible(false);
        column4.setVisible(false);
        setPositionButton();
    }
    public void setPositionButton()
    {
        sel1Depot1.setVisible(true);
        sel1Depot2.setVisible(true);
        sel1Depot3.setVisible(true);
        sel2Depot1.setVisible(true);
        sel2Depot2.setVisible(true);
        sel2Depot3.setVisible(true);
        sel3Depot1.setVisible(true);
        sel3Depot2.setVisible(true);
        sel3Depot3.setVisible(true);
        sel4Depot1.setVisible(true);
        sel4Depot2.setVisible(true);
        sel4Depot3.setVisible(true);
        sel1ExtraDepot.setVisible(true);
        sel2ExtraDepot.setVisible(true);
        sel3ExtraDepot.setVisible(true);
        sel4ExtraDepot.setVisible(true);
        discard1.setVisible(true);
        discard2.setVisible(true);
        discard3.setVisible(true);
        discard4.setVisible(true);;
    }

}
