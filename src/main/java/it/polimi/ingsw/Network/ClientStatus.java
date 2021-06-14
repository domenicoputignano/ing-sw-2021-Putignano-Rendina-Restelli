package it.polimi.ingsw.Network;


import it.polimi.ingsw.Utils.Messages.ClientMessages.ClientMessage;
import it.polimi.ingsw.Utils.Messages.ServerMessages.ServerMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientStatus implements Runnable {

    private RemoteView remoteView;
    private boolean isActive;
    private final ObjectOutputStream outputStreamToClient;
    private final ObjectInputStream inputFromClient;
    private final Socket socket;
    private final Logger LOGGER = Logger.getLogger(ClientStatus.class.getName());

    public ClientStatus(Socket socket, ObjectInputStream inputFromClient, ObjectOutputStream outputStreamToClient) {
        this.socket = socket;
        this.outputStreamToClient = outputStreamToClient;
        this.inputFromClient = inputFromClient;
        this.isActive = true;
    }

    public void send(ServerMessage serverMessage) {
        try {
            outputStreamToClient.reset();
            outputStreamToClient.writeObject(serverMessage);
            outputStreamToClient.flush();
        } catch (IOException e) {
            e.printStackTrace(System.out);
            //TODO modificare come se trovassimo una disconnessione
            LOGGER.log(Level.SEVERE, "Disconnection detected while sending a message");
            isActive = false;
        }
    }


    public void run(){
        try {
            while(isActive){
                ClientMessage messageFromClient = (ClientMessage) inputFromClient.readObject();
                LOGGER.log(Level.INFO, "Message from client of type "+messageFromClient.getClass().getName()+"");
                remoteView.handleClientMessage(messageFromClient);
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Disconnection detected while receiving a message");
            isActive = false;
            remoteView.handlePlayerDisconnection();

        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Error in receiving message from client");
        }
    }


    public void bindRemoteView(RemoteView remoteView) {
        this.remoteView = remoteView;
    }

    public boolean isActive() {
        return isActive;
    }

    public RemoteView getRemoteView() { return remoteView; }
}
