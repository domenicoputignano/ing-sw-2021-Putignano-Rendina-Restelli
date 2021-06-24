package it.polimi.ingsw;

import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Network.Server;
import java.io.IOException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MaestriDelRinascimento {

    private static final Logger LOGGER = Logger.getLogger(MaestriDelRinascimento.class.getName());

    public static void main(String[] args) {

        String parsedChoice = args.length > 0 ? args[0].toUpperCase(Locale.ROOT) : null;

        if(parsedChoice == null){
            System.out.println("Wrong arguments specification, type: \n" +
                    "-cli to start client in cli mode \n" +
                    "-gui to start client in gui mode \n" +
                    "-server to start the server");
            return;
        }

        switch (parsedChoice) {
            case "GUI" : {
                startClient(true);
                break;
            }
            case "CLI" : {
                startClient(false);
                break;
            }
            case "SERVER" : {
                startServer();
                break;
            }
        }

    }
    public static void startServer(){
        try {
            Server server = new Server(1234);
            server.start();

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Unable to start server");
        }
    }

    public static void startClient(boolean startAsGui){
        Client client = new Client("127.0.0.1", 1234);
        try{
            client.start(startAsGui);
        } catch (IOException e){
            LOGGER.log(Level.SEVERE, "Unable to start client");
        }
    }

}

