package it.polimi.ingsw.Client.clientstates.cli;

import it.polimi.ingsw.Client.clientstates.AbstractClientState;
import it.polimi.ingsw.Client.clientstates.AbstractLobby;
import it.polimi.ingsw.Client.view.CLI;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Messages.ServerMessages.ServerMessage;

import java.util.Scanner;

public class LobbyCLI extends AbstractLobby {

    private final CLI cli;
    private Thread waiterThread;
    private Scanner input = new Scanner(System.in);

    protected LobbyCLI(Client client) {
        super(client);
        cli = (CLI)client.getUI();
    }

    @Override
    public AbstractClientState getGUIVersion() {
        return null;
    }

    @Override
    public AbstractClientState getCLIVersion() {
        return null;
    }

    @Override
    public void render(ServerMessage message) {

    }

    @Override
    public void manageUserInteraction() {
        waiterThread = new Thread(() -> {
           while(!waiterThread.isInterrupted()) {
               if(input.hasNext()) {
                   input.next();
                   System.out.println("You are in lobby, you can't type anything ");
               }
           }
        });
        waiterThread.start();
    }

    public void shutDownWaiterThread() {
        waiterThread.interrupt();
    }
}
