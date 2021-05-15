package it.polimi.ingsw.Network;


import it.polimi.ingsw.Utils.Messages.ClientMessages.ClientMessage;
import it.polimi.ingsw.Utils.Messages.ServerMessages.ServerMessage;

import java.io.IOException;
import java.io.ObjectInput;
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
        /*try {
            //this.outputStreamToClient = new ObjectOutputStream(socket.getOutputStream());
            //outputStreamToClient.flush();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error occurred while creating client object stream ");
        }*/
    }

    public void send(ServerMessage serverMessage) {
        try {
             outputStreamToClient.writeObject(serverMessage);
             outputStreamToClient.flush();
            } catch (IOException e) {
                //TODO modificare come se trovassimo una disconnessione
                LOGGER.log(Level.SEVERE, "Disconnection detected!");
            }
    }




    public void bindRemoteView(RemoteView remoteView) {
        this.remoteView = remoteView;
    }

    public boolean isActive() {
        return isActive;
    }

    public void run(){

        try {
            //socket.getInputStream().reset();
            //ObjectInputStream inputFromClient = new ObjectInputStream(socket.getInputStream());

            while(isActive){
                ClientMessage messageFromClient = (ClientMessage) inputFromClient.readObject();
                LOGGER.log(Level.INFO, String.format("Received messageToSend %s", messageFromClient.getClass().getName()));
                remoteView.handleClientMessage(messageFromClient);
            }

        } catch (IOException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Error in receiving messageToSend from thread");
        }

    }

}
