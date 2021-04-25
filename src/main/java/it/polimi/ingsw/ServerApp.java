package it.polimi.ingsw;

import it.polimi.ingsw.Network.Server;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerApp {


    public static void main(String[] args) {

        Logger LOGGER = Logger.getLogger(ServerApp.class.getName());

        try {

            Server server = new Server(12345);
            server.start();

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Unable to start server");
        }

    }

}

