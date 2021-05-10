package it.polimi.ingsw.Client.clientstates.cli;


import it.polimi.ingsw.Client.clientstates.AbstractActivateProduction;
import it.polimi.ingsw.Utils.Messages.ServerMessages.ServerMessage;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.ActivateProductionUpdate;


public class ActivateProductionCLI extends AbstractActivateProduction {


    public void render(ServerMessage message) {
        ActivateProductionUpdate update = (ActivateProductionUpdate) message;
        System.out.print("Player "+update.getUser()+" activated productions\nhe obtained following resources: "+update.getReceivedResources());
    }


    @Override
    public void manageUserInteraction() {
        System.out.println("Choose which productions you want to activate: ");
    }
}
