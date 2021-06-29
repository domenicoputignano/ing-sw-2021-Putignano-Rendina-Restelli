package it.polimi.ingsw.client.clientstates.cli;

import it.polimi.ingsw.client.clientstates.AbstractWaitForTurn;
import it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.network.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class represents the Cli-specific wait for turn state that is reached by all the clients
 * who aren't in turn.
 */
public class WaitForTurnCLI extends AbstractWaitForTurn {

    /**
     * The CLI instance this state refers to.
     */
    private final CLI cli;
    /**
     * The input reader used to receive inputs from user.
     */
    private final BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    /**
     * The thread which manages the state execution and that is interrupted when the user becomes in turn.
     */
    private Thread waiterThread;
    /**
     * The static variable used to synchronize the concurrent access to stdin.
     */
    public final static AtomicBoolean occupied = new AtomicBoolean(false);

    /**
     * Initializes references to CLI and client.
     */
    public WaitForTurnCLI(Client client) {
        super(client);
        this.cli = (CLI) client.getUI();
    }

    /**
     * Main method of the class. It starts a thread that continuosly reads from stdin and parses the user's choice
     * showing him the chosen part of the model. We decided to submit this task to a different thread
     * than the CLI main thread executor because when the user becomes in turn this task must be immediately
     * interrupted without having to write anything on stdin.
     */
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

    /**
     * Method used to parse and execute the proper method in order to show the chosen part of the model.
     * @param input the user's choice.
     */
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

    /**
     * Method which interrupts this state by interrupting the waiter thread.
     */
    public void shutDownWaiterThread() {
        waiterThread.interrupt();
    }


}
