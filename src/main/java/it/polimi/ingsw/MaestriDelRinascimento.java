package it.polimi.ingsw;

import it.polimi.ingsw.client.OfflineClient;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.NetworkClient;
import it.polimi.ingsw.network.Server;
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
                    "cli to start client in cli mode \n" +
                    "gui to start client in gui mode \n" +
                    "server to start the server \n"+
                    "local to start an offline solo mode with gui \n"+
                    "local cli to start an offline solo mode with cli\n" +
                    "options : -ip when starting client to specify the server ip\n" +
                    "          -port when starting client to specify the server port\n" +
                    "          -port when starting server to specify the port to listen on");
            return;
        }

        switch (parsedChoice) {
            case "GUI" : {
                startClient(args,true);
                break;
            }
            case "CLI" : {
                startClient(args,false);
                break;
            }
            case "SERVER" : {
                startServer(args);
                break;
            }
            case "LOCAL" : {
                if(args.length == 2 && args[1].equalsIgnoreCase("cli")) {
                    startOfflineClient(false);
                } else startOfflineClient(true);
                break;
            }
        }

    }

    private static void startServer(String[] args){
        try {
            int port = parsePort(args);
            Server server = new Server(port);
            server.start();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Unable to start server");
        }
    }
    private static int parsePort(String[] args){
        int port = 1234; // Default port
        for (String arg : args){
            if(arg.startsWith("-port:")){
                try{
                    port = Integer.parseInt(arg.substring(6));
                } catch (NumberFormatException e){
                    System.out.println("Error while parsing arguments! Port must be an integer!");
                    System.exit(-1);
                }
            }
        }
        return port;
    }

    private static void startClient(String[] args, boolean startAsGui){
        int port = parsePort(args);
        String serverIp = parseIp(args);
        Client client = new NetworkClient(serverIp, port);
        client.start(startAsGui);
    }

    private static String parseIp(String[] args){
        String ip = "127.0.0.1"; // By default connects to localhost
        for (String arg : args){
            if(arg.startsWith("-ip:")){
                ip = arg.substring(4);
            }
        }
        return ip;
    }

    private static void startOfflineClient(boolean startAsGui) {
        OfflineClient client = new OfflineClient();
        client.start(startAsGui);
    }
}

