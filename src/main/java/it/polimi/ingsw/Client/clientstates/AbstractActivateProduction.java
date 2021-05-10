package it.polimi.ingsw.Client.clientstates;


import it.polimi.ingsw.Commons.Effect;
import it.polimi.ingsw.Model.ResourceType;
import it.polimi.ingsw.Utils.Messages.ClientMessages.ActivateProductionMessage;

public abstract class AbstractActivateProduction extends AbstractClientState {


    protected ActivateProductionMessage messageToSend = new ActivateProductionMessage();


    public int countExtraProductionEffect() {
        return client.getGame().getCurrPlayer().getCompatibleLeaderEffect(Effect.EXTRAPRODUCTION).size();
    }


    public ResourceType getExtraProductionType(int index) {
        return client.getGame().getCurrPlayer().getCompatibleLeaderEffect(Effect.EXTRAPRODUCTION).
                get(index).getLeaderEffect().getType();
    }

}
