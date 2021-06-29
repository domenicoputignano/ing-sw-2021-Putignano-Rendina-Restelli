package it.polimi.ingsw.client.clientstates.cli;

import it.polimi.ingsw.client.clientstates.AbstractLobby;
import it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.network.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This class represents the CLI-specific lobby state which is reached by the client
 * after having chosen the number of players in Multiplayer Mode.
 */
public class LobbyCLI extends AbstractLobby {
    /**
     * The CLI instance this state refers to.
     */
    private final CLI cli;
    /**
     * The input reader used to receive inputs from user.
     */
    private final BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    /**
     * String to ignore written by the user.
     */
    private String stringToIgnore;
    /**
     * The thread which manages the state execution and that is interrupted when a
     * {@link it.polimi.ingsw.utils.messages.serverMessages.GameSetupMessage} arrives.
     */
    private Thread waiterThread;

    /**
     * Initializes reference to CLI and client.
     */
    protected LobbyCLI(Client client) {
        super(client);
        cli = (CLI)client.getUI();
    }

    /**
     * Main method of the class. It starts a thread that continuosly reads from stdin and writes an error message on
     * stdout because no inputs can be given during this state. We decided to submit this task to a different thread
     * than the CLI main thread executor because when a {@link it.polimi.ingsw.utils.messages.serverMessages.GameSetupMessage}
     * arrives, this task must be immediately interrupted without having to write anything on stdin.
     */
    @Override
    public void manageUserInteraction() {
        waiterThread = new Thread(() -> {
            while(!waiterThread.isInterrupted()) {
                try {
                    while(!input.ready()) {
                        Thread.sleep(50);
                    }
                    stringToIgnore = input.readLine();
                    System.out.println("You are in lobby, you can't type anything");
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
     * Method which interrupts this state by interrupting the waiter thread.
     */
    public void shutDownWaiterThread() {
        waiterThread.interrupt();
    }
}
