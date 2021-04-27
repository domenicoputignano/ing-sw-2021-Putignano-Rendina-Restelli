package it.polimi.ingsw.Network;


import it.polimi.ingsw.Observable;
import it.polimi.ingsw.Utils.Messages.ClientMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientStatus extends Observable<ClientMessage> implements Runnable{

    private RemoteView remoteView;
    private boolean isActive;
    private final Socket socket;
    private final Logger LOGGER = Logger.getLogger(ClientStatus.class.getName());

    public ClientStatus(Socket socket) {
        this.socket = socket;
        this.isActive = true;
    }



    public void bindRemoteView(RemoteView remoteView) {
        this.remoteView = remoteView;

        // adding observers of ClientMessage
        addObserver(remoteView);
    }

    public boolean isActive() {
        return isActive;
    }

    public void run(){

        try {
            ObjectInputStream inputFromClient = new ObjectInputStream(socket.getInputStream());

            while(isActive){
                ClientMessage messageFromClient = (ClientMessage) inputFromClient.readObject();
                LOGGER.log(Level.INFO, String.format("Received message %s", messageFromClient.getClass().getName()));
                notify(messageFromClient);
            }

        } catch (IOException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Error in receiving message from thread");
        }

    }

}
