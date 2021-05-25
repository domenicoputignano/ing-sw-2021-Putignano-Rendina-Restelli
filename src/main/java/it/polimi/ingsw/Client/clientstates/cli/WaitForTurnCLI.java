package it.polimi.ingsw.Client.clientstates.cli;

import it.polimi.ingsw.Client.clientstates.AbstractWaitForTurn;
import it.polimi.ingsw.Client.view.CLI;
import it.polimi.ingsw.Network.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class WaitForTurnCLI extends AbstractWaitForTurn {

    private final CLI cli;
    private final BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    private Thread waiterThread;

    public WaitForTurnCLI(Client client) {
        super(client);
        this.cli = (CLI) client.getUI();
    }

    @Override
    public void manageUserInteraction() {
        waiterThread = new Thread(()-> {
            while(!waiterThread.isInterrupted()){
                try{
                    while(!input.ready()){
                        Thread.sleep(50);
                    }
                    input.readLine();
                    System.out.println("It's not your turn, please wait..");
                } catch (IOException e) {
                    System.out.println("Buffered Reader accidentally cancelled, program will be shut down.");
                } catch (InterruptedException e) {
                    return;
                }
            }
        });
        waiterThread.start();
    }

    public void shutDownWaiterThread() {
        waiterThread.interrupt();
    }




}
