package it.polimi.ingsw.client.reducedmodel;

import it.polimi.ingsw.utils.MarketChoice;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class represents a simplified version of a Market Tray with respect to the server's class.
 * It contains only the information required client side.
 */
public class ReducedMarketTray implements Serializable {
    /**
     * Attributes that represent the contents of the Market Tray
     */
    private final ReducedMarble[][] availableMarbles;
    private final ReducedMarble slidingMarble;
    /**
     * Initialize an instance by setting availableMarbles and slidingMarble of the depot.
     */
    public ReducedMarketTray(ReducedMarble[][] availableMarbles, ReducedMarble slidingMarble) {
        this.availableMarbles = availableMarbles;
        this.slidingMarble = slidingMarble;
    }

    /**
     * method used to obtain the balls obtained after choosing a specific row / column
     * @param marketChoice choice of a specific row / column
     * @param index number of row/column
     * @return list of marbles obtained
     */
    public List<ReducedMarble> peekMarbles(MarketChoice marketChoice, int index){
        List<ReducedMarble> marbles = new ArrayList<>();
        if(marketChoice==MarketChoice.ROW) {
            marbles.addAll(Arrays.asList(availableMarbles[index]));
        } else {
            for (ReducedMarble[] availableMarble : availableMarbles) {
                marbles.add(availableMarble[index]);
            }
        }
        return marbles;
    }

    @Override
    public String toString() {
        return  toStringAvailableMarbles() +
                " slidingMarble =" + slidingMarble;
    }

    private String toStringAvailableMarbles() {
        String result = "";
        for (ReducedMarble[] availableMarble : availableMarbles) {
            for (ReducedMarble reducedMarble : availableMarble) {
                result = result.concat(reducedMarble.toString());
            }
            result = result + "\n";
        }
        return result;
    }

    public ReducedMarble getMarble(int row, int col) {
        return availableMarbles[row-1][col-1];
    }

    public ReducedMarble getSlidingMarble() {
        return slidingMarble;
    }
}
