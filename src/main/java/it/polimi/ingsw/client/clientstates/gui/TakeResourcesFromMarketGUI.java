package it.polimi.ingsw.client.clientstates.gui;

import it.polimi.ingsw.client.clientstates.AbstractTakeResourcesFromMarket;
import it.polimi.ingsw.client.reducedmodel.ReducedMarble;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.MarbleDestination;
import it.polimi.ingsw.utils.MarketChoice;
import it.polimi.ingsw.utils.Pair;

import java.util.List;

/**
 * This class represents the GUI-specific state that is reached when the client chooses to
 * take resources from the {@link it.polimi.ingsw.model.marketTray.MarketTray} during his turn.
 */
public class TakeResourcesFromMarketGUI extends AbstractTakeResourcesFromMarket {

    /**
     * Initializes reference to client.
     */
    public TakeResourcesFromMarketGUI(Client client) {
        super(client);
    }

    /**
     * Main method of the class used to send the message to the server after the user has chosen how to perform this
     * action using the JavaFX GUI.
     */
    @Override
    public void manageUserInteraction() {
        client.sendMessage(message);
    }

    /**
     * Sets the row/column and the index of the Market Tray chosen by the user.
     * @param choice the choice between row and column.
     * @param index the index of the row/column chosen.
     */
    public void setMarketChoice(MarketChoice choice, int index) {
        message.setPlayerChoice(choice);
        message.setIndex(index);
    }

    /**
     * Computes the selected marble from the chosen row/column of the Market Tray.
     * @return the computed list of selected marbles.
     */
    public List<ReducedMarble> getSelectedMarbles(){
        computeSelectedMarbles();
        return this.selectedMarbles;
    }

    /**
     * Adds a choice between the available convert marble effects to the message to send.
     * @param choice the index of the convert marble effect to activate.
     */
    public void addWhiteEffect(Integer choice){
        message.addWhiteEffect(choice);
    }

    /**
     * @return the list of the convert marble effects already chosen.
     */
    public List<Integer> getAlreadySelectedWhiteEffects(){ return message.getWhiteEffects(); }

    /**
     * Adds an instruction on how to place one of the selected marbles to the message to send.
     * @param choice the instruction on how to place the marble.
     */
    public void addMarbleChoice(Pair<ReducedMarble, MarbleDestination> choice){
        message.addWhereToPutMarbles(choice);
    }

}
