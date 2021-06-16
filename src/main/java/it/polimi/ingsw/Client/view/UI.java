package it.polimi.ingsw.Client.view;

import it.polimi.ingsw.Client.clientstates.AbstractClientState;
import it.polimi.ingsw.Commons.ResourceType;
import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Messages.ServerMessages.*;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.*;


public abstract class UI {
    protected Client client;
    protected AbstractClientState clientState;
    protected boolean normalActionDone;
    protected boolean soloMode;

    protected UI(Client client) {
        this.client = client;
    }

    public abstract void manageUserInteraction();

    public abstract void changeClientState(AbstractClientState clientState);


    public boolean isCLI() {
        return this instanceof CLI;
    }

    public Client getClient() {
        return client;
    }

    //Metodi tutti in overloading per gestire il render di qualsiasi messaggio in qualsiasi finestra
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
    public abstract void renderError(String errorMessage);

    public boolean isReceiverAction(User sender) {
        return sender.equals(client.getUser());
    }

    public boolean isGuestWhoHasJustJoined(String senderNickname) {
        return senderNickname.equals(client.getUser().getNickname());
    }

    public AbstractClientState getClientState() {
        return clientState;
    }

    public boolean hasDoneNormalAction() {
        return normalActionDone;
    }

    public void setNormalActionDone(boolean normalActionDone) {
        this.normalActionDone = normalActionDone;
    }

    public boolean isSoloMode() {
        return soloMode;
    }

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
        this.soloMode = soloMode;
    }
}
