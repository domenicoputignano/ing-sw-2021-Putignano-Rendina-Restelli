package it.polimi.ingsw.Client.clientstates.cli;

import it.polimi.ingsw.Client.clientstates.AbstractWaitForTurn;
import it.polimi.ingsw.Client.view.CLI;
import it.polimi.ingsw.Network.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WaitForTurnCLI extends AbstractWaitForTurn {

    private final CLI cli;
    private final BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    private String stringToIgnore;
    private Thread waiterThread;
    public final static AtomicBoolean occupied = new AtomicBoolean(false);

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
                    synchronized (occupied) {
                        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Sono il thread della wait for turn e sto leggendo l'input");
                        String command = input.readLine();
                        parseAndExecuteCommand(command);
                    }
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
        switch (input.toUpperCase())  {
            case "PB" : {
                cli.askAndShowPlayerBoard();
                return;
            }
            case "M" : {
                cli.showMarketTray();
                return;
            } case "D" : {
                cli.printDecks();
                return;
            }
            default:
                System.out.println("Invalid command");
        }
    }

    public void shutDownWaiterThread() {
        waiterThread.interrupt();
    }


}
