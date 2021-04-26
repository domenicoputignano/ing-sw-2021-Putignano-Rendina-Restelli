package it.polimi.ingsw.Network;


import it.polimi.ingsw.Controller.TurnController;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Observable;
import it.polimi.ingsw.Utils.Messages.ClientMessage;
import java.net.Socket;

public class ClientStatus extends Observable<ClientMessage> {

    private RemoteView remoteView;
    private boolean isActive;
    private final Socket socket;

    public ClientStatus(Socket socket) {
        this.socket = socket;
        this.isActive = true;
    }

    public void bindRemoteView(RemoteView remoteView) {
        this.remoteView = remoteView;
    }

    public boolean isActive() {
        return isActive;
    }
}
