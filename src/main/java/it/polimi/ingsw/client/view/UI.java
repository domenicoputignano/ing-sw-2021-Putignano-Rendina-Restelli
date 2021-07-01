package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.clientstates.AbstractClientState;
import it.polimi.ingsw.commons.ResourceType;
import it.polimi.ingsw.commons.User;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.messages.serverMessages.*;
import it.polimi.ingsw.utils.messages.serverMessages.Updates.*;

/**
 * UI stands for User Interface and it is an abstract class containing methods used by CLI and Gui
 * related to users game experience.
 */
public abstract class UI {
    /**
     * Instance of client main class over which UI is running.
     */
    protected Client client;
    /**
     * Represents a generic ui state where the client can be.
     */
    protected AbstractClientState clientState;
    /**
     * Boolean attribute establishes if a normal action has been done during the turn.
     */
    protected boolean isNormalActionDone;
    /**
     * Boolean attribute establishes if user is playing a solo mode game.
     */
    protected boolean isSoloMode;

    /**
     * Constructor of the class that binds UI and Client to which it is related.
     */
    protected UI(Client client) {
        this.client = client;
    }

    public void manageUserInteraction(){};

    public void changeCliState(AbstractClientState clientState){};

    public boolean isCLI() {
        return this instanceof CLI;
    }

    public Client getClient() {
        return client;
    }

    /**
     * Overloading methods that display different kind of messages received from remote host.
     */
    public abstract void render(ServerAsksForNickname message);
    public abstract void render(ServerAskForGameMode message);
    public abstract void render(ServerAskForNumOfPlayer message);
    public abstract void render(GameSetupMessage message);
    public abstract void render(GameResumedMessage message);
    public abstract void render(InitialLeaderChoiceUpdate message);
    public abstract void render(InitialResourceChoiceUpdate message);
    public abstract void render(ServerAsksForPositioning message);
    public abstract void render(NewTurnUpdate message);
    public abstract void render(TakeResourcesFromMarketUpdate message);
    public abstract void render(FaithMarkerUpdate message);
    public abstract void render(MoveUpdate message);
    public abstract void render(LeaderActionUpdate message);
    public abstract void render(PositioningUpdate message);
    public abstract void render(LorenzoPlayedUpdate message);
    public abstract void render(BuyDevCardPerformedUpdate message);
    public abstract void render(ActivateProductionUpdate message);
    public abstract void render(NotAvailableNicknameMessage message);
    public abstract void render(ActivateVaticanReportUpdate message);
    public abstract void render(JoinLobbyMessage message);
    public abstract void render(LastTurnMessage message);
    public abstract void render(RankMessage message);
    public abstract void render(UserDisconnectedMessage message);
    public abstract void render(SoloModeMatchWinnerMessage message);
    public abstract void render(LorenzoActivatedVaticanReportUpdate message);
    public abstract void renderError(String errorMessage);
    public boolean isReceiverAction(User sender) {
        return sender.equals(client.getUser());
    }

    /**
     * Method to notify player after a disconnection in order to let him know that
     * the application should be restarted.
     */
    public abstract void notifyCloseApplicationRequired();

    /**
     * Boolean method to detect if user that has just joined a lobby corresponds
     * to user of client that is displaying something through his UI.
     */
    public boolean isGuestWhoHasJustJoined(String senderNickname) {
        return senderNickname.equals(client.getUser().getNickname());
    }

    public AbstractClientState getClientState() {
        return clientState;
    }


    public boolean hasDoneNormalAction() {
        return isNormalActionDone;
    }

    public void setNormalActionDone(boolean normalActionDone) {
        this.isNormalActionDone = normalActionDone;
    }

    public boolean isSoloMode() {
        return isSoloMode;
    }

    /**
     * Returns one instance of resource type given a string typed by user.
     * @return correct resource type.
     */
    public static ResourceType fromStringToResourceType(String resource) {
        if(resource.equalsIgnoreCase("C")||resource.equalsIgnoreCase("coin")) return ResourceType.coin;
        if(resource.equalsIgnoreCase("SE")||resource.equalsIgnoreCase("servant")) return ResourceType.servant;
        if(resource.equalsIgnoreCase("SH")||resource.equalsIgnoreCase("shield")) return ResourceType.shield;
        if(resource.equalsIgnoreCase("ST")||resource.equalsIgnoreCase("stone")) return ResourceType.stone;
        else {
            System.out.println("Error detected while choosing resources, please select again ");
            return null;
        }
    }


    public void setSoloMode(boolean soloMode) {
        this.isSoloMode = soloMode;
    }
}
