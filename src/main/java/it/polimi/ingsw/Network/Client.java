package it.polimi.ingsw.Network;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {

    private String ip;
    private int port;

    public Client(String ip, int port){
        this.ip = ip;
        this.port = port;
    }

    public void start() throws IOException {
        Socket socket = new Socket(ip, port);
        System.out.println("Connection established");
        //Scanner socketIn = new Scanner(socket.getInputStream());
        //PrintWriter socketOut = new PrintWriter(socket.getOutputStream());
        DataInputStream socketIn = new DataInputStream(socket.getInputStream());
        DataOutputStream socketOut = new DataOutputStream(socket.getOutputStream());
        Scanner stdin = new Scanner(System.in);
        String socketLine;
        try{
            socketLine = socketIn.readUTF();
            System.out.print(socketLine);
            while (true){
                String inputLine = stdin.nextLine();
                socketOut.writeUTF(inputLine);
                socketOut.flush();
                socketLine = socketIn.readUTF();
                System.out.print(socketLine);
                int numOfPlayers = stdin.nextInt();
                socketOut.write(numOfPlayers);
                socketOut.flush();
            }
        } catch(NoSuchElementException e){
            System.out.println("Connection closed from the client side");
        } finally {
            stdin.close();
            socketIn.close();
            socketOut.close();
            socket.close();
        }
    }
}

