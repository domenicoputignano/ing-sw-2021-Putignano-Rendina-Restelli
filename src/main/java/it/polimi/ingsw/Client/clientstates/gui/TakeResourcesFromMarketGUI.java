package it.polimi.ingsw.Client.clientstates.gui;

import it.polimi.ingsw.Client.clientstates.AbstractTakeResourcesFromMarket;
import it.polimi.ingsw.Client.reducedmodel.ReducedMarble;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.MarbleDestination;
import it.polimi.ingsw.Utils.MarketChoice;
import it.polimi.ingsw.Utils.Pair;

import java.util.List;

public class TakeResourcesFromMarketGUI extends AbstractTakeResourcesFromMarket {

    public TakeResourcesFromMarketGUI(Client client) {
        super(client);
    }

    @Override
    public void manageUserInteraction() {
        client.sendMessage(message);
    }

    public void setMarketChoice(MarketChoice choice, int index) {
        message.setPlayerChoice(choice);
        message.setIndex(index);
    }

    public List<ReducedMarble> getSelectedMarbles(){
        computeSelectedMarbles();
        return this.selectedMarbles;
    }

    public void addWhiteEffect(Integer choice){
        message.addWhiteEffect(choice);
    }

    public List<Integer> getAlreadySelectedWhiteEffects(){ return message.getWhiteEffects(); }

    public void addMarbleChoice(Pair<ReducedMarble, MarbleDestination> choice){
        message.addWhereToPutMarbles(choice);
    }

}
