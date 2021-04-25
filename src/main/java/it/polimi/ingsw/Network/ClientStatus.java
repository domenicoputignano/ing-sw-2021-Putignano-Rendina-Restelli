package it.polimi.ingsw.Network;


import it.polimi.ingsw.Controller.TurnController;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Observable;
import it.polimi.ingsw.Utils.Messages.ClientMessage;
import java.net.Socket;

public class ClientStatus extends Observable<ClientMessage> {

    private final RemoteView remoteView;
    private final Socket socket;

    public ClientStatus(RemoteView remoteView, Socket socket) {
        this.remoteView = remoteView;
        this.socket = socket;
    }



}
