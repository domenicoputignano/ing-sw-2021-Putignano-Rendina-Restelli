package it.polimi.ingsw.Client.clientstates;


import it.polimi.ingsw.Client.ReducedPersonalBoard;
import it.polimi.ingsw.Commons.Effect;
import it.polimi.ingsw.Commons.ResourceType;
import it.polimi.ingsw.Utils.Messages.ClientMessages.ActivateProductionMessage;

public abstract class AbstractActivateProduction extends AbstractClientState {


    protected ActivateProductionMessage messageToSend = new ActivateProductionMessage();


    public int countExtraProductionEffect() {
        return client.getGame().getCurrPlayer().getCompatibleLeaderEffect(Effect.EXTRAPRODUCTION).size();
    }


    public ResourceType getExtraProductionType(int leaderCardIndex) {
        return client.getGame().getCurrPlayer().getCompatibleLeaderEffect(Effect.EXTRAPRODUCTION).
                get(leaderCardIndex).getLeaderEffect().getType();
    }



}
