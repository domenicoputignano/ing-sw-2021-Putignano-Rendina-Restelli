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
    private String stringToIgnore;
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
                    System.out.println("You have to wait until your turn will start," +
                            " you can see player's board by typing (PB), market tray (M) or decks (D)");
                    while(!input.ready()) {
                        Thread.sleep(50);
                    }
                    String command = input.readLine();
                    parseAndExecuteCommand(command);
                } catch (IOException e) {
                    System.out.println("Buffered Reader accidentally cancelled, program will be shut down.");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        });
        waiterThread.start();
    }

    public void parseAndExecuteCommand(String input) {
        if(input.equalsIgnoreCase("PB")) cli.askAndShowPlayerBoard();
        if(input.equalsIgnoreCase("M")) cli.showMarketTray();
        if(input.equalsIgnoreCase("D")) cli.printDecks();
        else System.out.println("Invalid command");
    }

    public void shutDownWaiterThread() {
        waiterThread.interrupt();
    }


}
