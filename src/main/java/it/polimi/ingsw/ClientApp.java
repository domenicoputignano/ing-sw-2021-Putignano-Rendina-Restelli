package it.polimi.ingsw;

import it.polimi.ingsw.Network.Client;

import java.io.IOException;
import java.util.Locale;

public class ClientApp {

    public static void main(String[] args) {
        Client client = new Client("10.144.208.119", 1234);
        boolean startAsGui = parseArgs(args);
        try{
            client.start(startAsGui);
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    private static boolean parseArgs(String[] args){
        String interfaceChoice = args.length > 0 ? args[0].toUpperCase(Locale.ROOT) : "GUI";
        return !"CLI".equals(interfaceChoice);
    }

}