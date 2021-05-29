package it.polimi.ingsw;

import it.polimi.ingsw.Client.view.GUI.GUI;
import it.polimi.ingsw.Network.Client;
import javafx.application.Application;

import java.io.IOException;

public class ClientApp {

    public static void main(String[] args) {
        Client client = new Client("127.0.0.1", 12345);
        try{
            //Application.launch(GUI.class,args);
            client.start();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

}