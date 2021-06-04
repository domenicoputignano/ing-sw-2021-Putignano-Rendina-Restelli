package it.polimi.ingsw.Client.reducedmodel;

import it.polimi.ingsw.Utils.MarketChoice;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReducedMarketTray implements Serializable {
    private final ReducedMarble[][] availableMarbles;
    private final ReducedMarble slidingMarble;

    public ReducedMarketTray(ReducedMarble[][] availableMarbles, ReducedMarble slidingMarble) {
        this.availableMarbles = availableMarbles;
        this.slidingMarble = slidingMarble;
    }

    public List<ReducedMarble> peekMarbles(MarketChoice marketChoice, int index){
        List<ReducedMarble> marbles = new ArrayList<>();
        if(marketChoice==MarketChoice.ROW) {
            //TODO : MODIFICARE CON DELLE COPIE
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
