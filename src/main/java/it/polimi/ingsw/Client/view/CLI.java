package it.polimi.ingsw.Client.view;

import it.polimi.ingsw.Client.clientstates.ActivateProductionCLI;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.ActivateProductionUpdate;

public class CLI extends UI {


    @Override
    public void showUpdate(ActivateProductionUpdate message) {
        clientState = new ActivateProductionCLI();
        clientState.render(message);
    }



}
