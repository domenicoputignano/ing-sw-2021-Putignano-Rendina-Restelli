package it.polimi.ingsw.utils.messages.serverMessages;


import it.polimi.ingsw.client.CliStatesController;
import it.polimi.ingsw.network.Client;

public class ServerAsksForNickname implements ServerMessage {

    @Override
    public void handleMessage(Client handler) {
        //CliStatesController.updateCliState(this, handler.getUI());
        handler.getUI().render(this);
        //Aggiorna lo stato del client solo quando richiede l'interazione dell'utente, questo
        //posso deciderlo io stesso nel messaggio
        CliStatesController.updateCliState(this, handler.getUI());
    }

}
