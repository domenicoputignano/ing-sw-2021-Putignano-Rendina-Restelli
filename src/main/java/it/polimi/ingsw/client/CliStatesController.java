package it.polimi.ingsw.client;


import it.polimi.ingsw.client.clientstates.AbstractClientState;
import it.polimi.ingsw.client.clientstates.cli.*;
import it.polimi.ingsw.client.view.UI;
import it.polimi.ingsw.utils.messages.serverMessages.*;
import it.polimi.ingsw.utils.messages.serverMessages.Errors.ErrorMessage;
import it.polimi.ingsw.utils.messages.serverMessages.Updates.*;

/**
 * This class provides overloading methods to update status of the {@link it.polimi.ingsw.client.view.CLI} after
 * receiving a certain message, in the sense that this changes what a player graphically see in his command line interface
 * and the interaction allowed at runtime.
 */
public class CliStatesController {

    /**
     * Changes CLI state to {@link UsernameChoiceCLI} after a {@link ServerAsksForNickname} message is received.
     * @param message message received.
     * @param ui player's interface.
     */
    public static void updateCliState(ServerAsksForNickname message, UI ui) {
        if(ui.isCLI()){
            ui.changeCliState(new UsernameChoiceCLI(ui.getClient()));
        }
        ui.manageUserInteraction();
    }


    /**
     * Changes CLI state to {@link GameModeChoiceCLI} after a {@link ServerAskForGameMode} message is received.
     * @param message message received.
     * @param ui player's interface.
     */
    public static void updateCliState(ServerAskForGameMode message, UI ui) {
        if(ui.isCLI()){
            ui.changeCliState(new GameModeChoiceCLI(ui.getClient()));
            ui.manageUserInteraction();
        }
    }


    /**
     * Changes CLI state to {@link NumOfPlayersChoiceCLI} after a {@link ServerAskForNumOfPlayer} message is received.
     * @param message message received.
     * @param ui player's interface.
     */
    public static void updateCliState(ServerAskForNumOfPlayer message, UI ui) {
        if(ui.isCLI()) {
            ui.changeCliState(new NumOfPlayersChoiceCLI(ui.getClient()));
            ui.manageUserInteraction();
        }
    }

    /**
     * Changes CLI state to {@link NumOfPlayersChoiceCLI} after a {@link ServerAskForNumOfPlayer} message is received.
     * @param message message received.
     * @param ui player's interface.
     */
    public static void updateCliState(GameSetupMessage message, UI ui) {
        if(ui.isCLI()) {
            if (!ui.isSoloMode()) {
                ((LobbyCLI) ui.getClientState()).shutDownWaiterThread();
            }
            ui.changeCliState(new InitialLeaderChoiceCLI(ui.getClient()));
            ui.manageUserInteraction();
        }
    }

    /**
     * Changes CLI state to one between
     * {@link ActionChoiceCLI}, {@link WaitForTurnCLI} or {@link WaitForTurnCLI} depending on
     * selected game mode and his position after a {@link InitialLeaderChoiceUpdate} message is received.
     * @param message message received.
     * @param ui player's interface.
     */
    public static void updateCliState(InitialLeaderChoiceUpdate message, UI ui) {
        if(ui.isReceiverAction(message.getUser())) {
            if(ui.isCLI()) {
                if(ui.isSoloMode()) {
                    ui.changeCliState(new ActionChoiceCLI(ui.getClient()));
                    ui.manageUserInteraction();
                } else {
                    if(ui.getClient().getUserPosition() > 1) {
                        ui.changeCliState(new InitialResourceChoiceCLI(ui.getClient()));
                        ui.manageUserInteraction();
                    } else {
                        System.out.println("Waiting for players that are performing initial choices...");
                        ui.changeCliState(new WaitForTurnCLI(ui.getClient()));
                        ui.getClientState().manageUserInteraction();
                    }
                }
            }
        }

    }

    /**
     * Changes CLI state to {@link PositioningResourcesCLI} after a {@link ServerAsksForPositioning} message is received.
     * @param message message received.
     * @param ui player's interface.
     */
    public static void updateCliState(ServerAsksForPositioning message, UI ui) {
        if(ui.isReceiverAction(message.getUser())) {
            if(ui.isCLI()) {
                ui.changeCliState(new PositioningResourcesCLI(ui.getClient(), message.getResourcesToSettle()));
                ui.manageUserInteraction();
            }
        }
    }

    /**
     * Changes CLI state to {@link ActionChoiceCLI} for player in turn after a {@link NewTurnUpdate} is received.
     * @param message message received.
     * @param ui player's interface.
     */
    public static void updateCliState(NewTurnUpdate message, UI ui) {
        if(ui.isReceiverAction(message.getCurrentUser())) {
            if(ui.isCLI()) {
                WaitForTurnCLI state = (WaitForTurnCLI) ui.getClientState();
                state.shutDownWaiterThread();
                ui.changeCliState(new ActionChoiceCLI(ui.getClient()));
                ui.manageUserInteraction();
            }
        }
    }

    /**
     * Changes CLI state to {@link ActionChoiceCLI} after a {@link LorenzoPlayedUpdate} is received.
     * @param message message received.
     * @param ui player's interface.
     */
    public static void updateCliState(LorenzoPlayedUpdate message, UI ui) {
        if(ui.isCLI()) {
            if(ui.getClientState() instanceof WaitForTurnCLI) {
                WaitForTurnCLI state = (WaitForTurnCLI) ui.getClientState();
                state.shutDownWaiterThread();
            }
            ui.changeCliState(new ActionChoiceCLI(ui.getClient()));
            ui.manageUserInteraction();
        }
    }

    /**
     * Method called when an generic update is received.
     * @param message update received.
     * @param ui player's interface.
     */
    public static void updateCliState(UpdateMessage message, UI ui) {
        if(ui.isReceiverAction(message.getUser())) {
            if(ui.isCLI()){
                ui.changeCliState(new ActionChoiceCLI(ui.getClient()));
                ui.manageUserInteraction();
            }
        }
    }

    /**
     * Changes CLI state after a {@link GameResumedMessage} depending on game mode of the match that player joined again.
     * @param message message received.
     * @param ui player's interface.
     */
    public static void updateCliState(GameResumedMessage message, UI ui) {
        if(ui.isCLI()) {
            if (message.getGame().isSoloMode()) {
                ui.changeCliState(new ActionChoiceCLI(ui.getClient()));
                ui.manageUserInteraction();
            } else {
                if (!ui.getClient().getUser().equals(message.getCurrentUser())) {
                    ui.changeCliState(new WaitForTurnCLI(ui.getClient()));
                    ui.getClientState().manageUserInteraction();
                }
            }
        }
    }

    /**
     * Method that change CLI state of player that has triggered an error. It assumes that player in turn is the
     * only one that can make a mistake during his turn, for this reason, it updates his interface, while
     * other players get notified through error showing.
     * @param message message received.
     * @param ui player's interface.
     */
    public static void updateCliState(ErrorMessage message, UI ui) {
        if(ui.isReceiverAction(message.getTriggeringUser())){
            if(ui.isCLI()){
                AbstractClientState previousState = ui.getClientState();
                if(previousState instanceof WaitForTurnCLI){
                    ((WaitForTurnCLI) previousState).shutDownWaiterThread();
                    ui.changeCliState(new InitialResourceChoiceCLI(ui.getClient()));
                }
                else {
                    ui.changeCliState(new ActionChoiceCLI(ui.getClient()));
                }
                ui.manageUserInteraction();
            }
        }
    }

}
