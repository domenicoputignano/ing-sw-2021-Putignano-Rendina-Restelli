package it.polimi.ingsw.Client.view;

import it.polimi.ingsw.Client.clientstates.AbstractClientState;
import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Network.Server;
import it.polimi.ingsw.Utils.Messages.ServerMessages.*;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.*;


public abstract class UI {
    protected Client client;
    protected AbstractClientState clientState;

    protected UI(Client client) {
        this.client = client;
    }

    public abstract void showUpdate(ServerMessage message);

    public abstract void manageUserInteraction();

    public abstract void changeClientState(AbstractClientState clientState);

    public abstract void showLeaderCards();

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
    public abstract void render(InitialLeaderChoiceUpdate message);
    public abstract void render(InitialResourceChoiceUpdate message);
    public abstract void render(ServerAsksForPositioning message);
    public abstract void render(NewTurnUpdate message);
    public abstract void render(TakeResourcesFromMarketUpdate message);
    public abstract void render(FaithMarkerUpdate message);
    public abstract void render(MoveUpdate message);
    public abstract void render(LeaderActionUpdate message);

    public boolean isReceiverAction(User sender) {
        return sender.equals(client.getUser());
    }

    public AbstractClientState getClientState() {
        return clientState;
    }
}
