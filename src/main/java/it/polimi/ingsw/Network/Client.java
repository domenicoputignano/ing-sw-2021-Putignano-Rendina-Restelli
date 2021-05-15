package it.polimi.ingsw.Network;

import it.polimi.ingsw.Client.ReducedGame;
import it.polimi.ingsw.Client.view.UI;
import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Utils.Messages.ClientMessages.ClientMessage;
import it.polimi.ingsw.Utils.Messages.ServerMessages.ServerMessage;
import it.polimi.ingsw.Utils.Messages.ServerMessages.GameSetupMessage;

import java.io.*;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {

    private String ip;
    private int port;
    private boolean isActive;
    private ReducedGame game;
    private User user;
    private ObjectInputStream socketInObj ;
    private ObjectOutputStream socketOutObj ;
    private Logger LOGGER = Logger.getLogger(Client.class.getName());
    private UI ui;
    private Socket socket;


    public Client(String ip, int port){
        this.ip = ip;
        this.port = port;
        this.isActive = true;
    }


    public void start() throws IOException {
        socket = new Socket(ip, port);
        System.out.println("Connection established");
        socketOutObj = new ObjectOutputStream(socket.getOutputStream());
        socketOutObj.flush();
        socketInObj = new ObjectInputStream(socket.getInputStream());
        Scanner stdin = new Scanner(System.in);
        String socketLine;
        try{
            socketLine = socketInObj.readUTF();
            System.out.print(socketLine);
            while (isActive){
                String inputLine = stdin.nextLine();
                socketOutObj.writeUTF(inputLine);
                socketOutObj.flush();
                socketLine = socketInObj.readUTF();
                //TODO chiamare opportuni metodi di CLI/GUI
                System.out.print(socketLine);



            }
        } catch(NoSuchElementException e){
            System.out.println("Connection closed from the client side");
            isActive = false;
        } finally {
            stdin.close();
            socketInObj.close();
            socketOutObj.close();
            socket.close();
        }
    }

    public void sendMessage(ClientMessage message) {
        try {
            socketOutObj.writeObject(message);
            socketOutObj.flush();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error: unable to process messageToSend sending");
        }
    }


    public void setupGame(GameSetupMessage message) {
        game = message.getGame();
    }


    private Thread createListeningThread() {
        Thread t = new Thread(() -> {
            while(isActive) {
                try {
                    ServerMessage message = (ServerMessage) socketInObj.readObject();
                    LOGGER.log(Level.INFO, "Received message from Server");
                    //TODO handle of Received message
                } catch (IOException e) {
                    LOGGER.log(Level.SEVERE, "Client disconnected!");
                } catch (ClassNotFoundException e) {
                    LOGGER.log(Level.SEVERE, "Error occurred in receiving thread");
                }
            }
        });
        t.start();
        return t;
    }


    public UI getUI() {
        return ui;
    }

    //TODO rivedere
    public void setUi(UI ui) {
        this.ui = ui;
    }

    public ReducedGame getGame() {
        return game;
    }

    public void closeConnection() {
        try {
            socket.close();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Impossible to close connection!");
        }
    }
}

