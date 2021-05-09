package it.polimi.ingsw.Network;

import it.polimi.ingsw.Client.ReducedGame;
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
    private ObjectInputStream socketInObj ;
    private ObjectOutputStream socketOutObj ;
    private Logger LOGGER = Logger.getLogger(Client.class.getName());

    public Client(String ip, int port){
        this.ip = ip;
        this.port = port;
        this.isActive = true;
    }



    public void start() throws IOException {
        Socket socket = new Socket(ip, port);
        System.out.println("Connection established");

        DataInputStream socketIn = new DataInputStream(socket.getInputStream());
        DataOutputStream socketOut = new DataOutputStream(socket.getOutputStream());
        Scanner stdin = new Scanner(System.in);
        String socketLine;
        try{
            socketInObj = new ObjectInputStream(socket.getInputStream());
            socketOutObj = new ObjectOutputStream(socket.getOutputStream());
            socketLine = socketIn.readUTF();
            System.out.print(socketLine);
            while (isActive){
                String inputLine = stdin.nextLine();
                socketOut.writeUTF(inputLine);
                socketOut.flush();
                socketLine = socketIn.readUTF();
                //TODO chiamare opportuni metodi di CLI/GUI
                System.out.print(socketLine);


            }
        } catch(NoSuchElementException e){
            System.out.println("Connection closed from the client side");
            isActive = false;
        } finally {
            stdin.close();
            socketIn.close();
            socketOut.close();
            socket.close();
        }
    }


    public void setupGame(GameSetupMessage message) {

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

}

