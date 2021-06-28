package it.polimi.ingsw.model.marketTray;

import it.polimi.ingsw.client.reducedmodel.ReducedMarble;
import it.polimi.ingsw.client.reducedmodel.ReducedMarketTray;
import it.polimi.ingsw.commons.ColorMarble;
import it.polimi.ingsw.commons.ResourceType;
import it.polimi.ingsw.utils.MarketChoice;

import java.util.*;
import java.util.stream.Collectors;


/**
 * This class represents the market board containing thirteen marbles distributed over three rows and four columns and one
 * remaining placed outside as stated by game rules.
 */
public class MarketTray {
    private Marble[][] availableMarbles = new Marble[3][4];
    private Marble slidingMarble ;


    /**
     * Constructor that casually initializes market board.
     */
    public MarketTray(){
        List<Marble> marbles = new ArrayList<>();
        marbles.add(new WhiteMarble());
        marbles.add(new WhiteMarble());
        marbles.add(new WhiteMarble());
        marbles.add(new WhiteMarble());
        marbles.add(new RedMarble());
        marbles.add(new StandardMarble(ColorMarble.BLUE, ResourceType.shield));
        marbles.add(new StandardMarble(ColorMarble.BLUE, ResourceType.shield));
        marbles.add(new StandardMarble(ColorMarble.GREY, ResourceType.stone));
        marbles.add(new StandardMarble(ColorMarble.GREY, ResourceType.stone));
        marbles.add(new StandardMarble(ColorMarble.YELLOW, ResourceType.coin));
        marbles.add(new StandardMarble(ColorMarble.YELLOW, ResourceType.coin));
        marbles.add(new StandardMarble(ColorMarble.PURPLE, ResourceType.servant));
        marbles.add(new StandardMarble(ColorMarble.PURPLE, ResourceType.servant));

        Random rand = new Random();
        int randNum = rand.nextInt(13);
        slidingMarble = marbles.remove(randNum);
        Collections.shuffle(marbles);

        for(int i=0; i<3; i++){
            for(int j=0; j<4; j++){
                availableMarbles[i][j] = marbles.remove(0);
            }
        }
    }

    /**
     * Translates row from right to left, places the sliding marble in the fourth position coming from left and replaces it with the first one.
     * Translates column from bottom to top and places the sliding marble in the third position coming from top and replace it with the first one.
     * @param marketChoice establish if a row or column has to be slided.
     * @param index index of the row/column selected.
     */
    private void slideMarbles(MarketChoice marketChoice, int index) {
        if(marketChoice == MarketChoice.ROW) {
            Marble temp = availableMarbles[index][0];
            for(int i = 0; i < availableMarbles[index].length - 1; i++ ) {
                availableMarbles[index][i] = availableMarbles[index][i+1];
            }
            availableMarbles[index][availableMarbles[index].length-1] = slidingMarble;
            slidingMarble = temp;
        } else {
            Marble temp = availableMarbles[0][index];
            for(int i = 0; i < availableMarbles.length - 1; i++) {
                availableMarbles[i][index] = availableMarbles[i+1][index];
            }
            availableMarbles[availableMarbles.length - 1][index] = slidingMarble;
            slidingMarble = temp;
        }
    }

    /**
     * Returns the marbles coming from a row or a column.
     * @param marketChoice selects between row and column.
     * @param index index of the row/column chosen.
     */
    public List<Marble> peekMarbles(MarketChoice marketChoice, int index) {
        List<Marble> marbles = new ArrayList<>();
        if(marketChoice==MarketChoice.ROW) {
            //TODO : MODIFICARE CON DELLE COPIE
            marbles.addAll(Arrays.asList(availableMarbles[index]));
        } else {
            for(int i = 0; i < availableMarbles.length; i++) {
                marbles.add(availableMarbles[i][index]);
            }
        }
        return marbles;
    }

    /**
     * This method is used by turn controller to check matching between marbles selected by player on the client side and
     * marbles effectively existing in server's model before performing actual action.
     * @param marblesList marbles chosen by player in turn
     * @param marketChoice selects between row and column
     * @param index index of the row/column chosen.
     * @return
     */
    public boolean checkRequestedMarbles(List<ReducedMarble> marblesList,MarketChoice marketChoice, int index)
    {
        List<ReducedMarble> expectedMarbles = peekMarbles(marketChoice, index-1).stream().map(Marble::getReducedVersion).collect(Collectors.toList());
        return expectedMarbles.containsAll(marblesList) && marblesList.containsAll(expectedMarbles);
    }


    /**
     *
     */
    public List<Marble> takeMarbles(MarketChoice marketChoice, int index) {
            List<Marble> marbles = peekMarbles(marketChoice,index);
            slideMarbles(marketChoice, index);
            return marbles;
    }


    /**
     * It resets white marble effects because they are player dependent and for each one of them a different effect
     * can be set.
     */
    public void clearWhiteMarbleEffect() {
        for (Marble[] availableMarble : availableMarbles) {
            for (Marble marble : availableMarble) {
                if (marble.getColor() == ColorMarble.WHITE) {
                    WhiteMarble whiteMarble = (WhiteMarble) marble;
                    whiteMarble.setEffect(null);
                }
            }
        }
    }

    public Marble[][] getAvailableMarbles() {
        return availableMarbles;
    }

    public Marble getSlidingMarble() {
        return slidingMarble;
    }

    /**
     * Method used to create simplified instance of this class that will be sent and used client side.
     */
    public ReducedMarketTray getReducedVersion() {
        return new ReducedMarketTray(getReducedVersionOfMarbles(), slidingMarble.getReducedVersion());
    }

    private ReducedMarble[][] getReducedVersionOfMarbles() {
        ReducedMarble[][] reducedMarbles = new ReducedMarble[3][4];
        for(int i = 0; i < reducedMarbles.length; i++) {
            for (int j = 0; j < reducedMarbles[i].length; j++) {
                reducedMarbles[i][j] = new ReducedMarble(availableMarbles[i][j].getColor());
            }
        }
        return reducedMarbles;
    }
}
