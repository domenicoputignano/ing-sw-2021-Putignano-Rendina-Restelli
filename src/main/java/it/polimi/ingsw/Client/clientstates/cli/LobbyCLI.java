package it.polimi.ingsw.Client.clientstates.cli;

import it.polimi.ingsw.Client.clientstates.AbstractLobby;
import it.polimi.ingsw.Client.view.CLI;
import it.polimi.ingsw.Network.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LobbyCLI extends AbstractLobby {

    private final CLI cli;
    private final BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    private Thread waiterThread;

    protected LobbyCLI(Client client) {
        super(client);
        cli = (CLI)client.getUI();
    }

    @Override
    public void manageUserInteraction() {
        waiterThread = new Thread(() -> {
            while(!waiterThread.isInterrupted()) {
                try {
                    while(!input.ready()) {
                        Thread.sleep(50);
                    }
                    input.readLine();
                    System.out.println("You are in lobby, you can't type anything");
                } catch (IOException e) {
                    System.out.println("Buffered Reader accidentally cancelled, program will be shut down.");
                } catch (InterruptedException e) {
                    System.out.println("Match found. Exiting from lobby...");
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
