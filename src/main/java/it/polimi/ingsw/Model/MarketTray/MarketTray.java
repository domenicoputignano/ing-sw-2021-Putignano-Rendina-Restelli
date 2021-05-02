package it.polimi.ingsw.Model.MarketTray;

import it.polimi.ingsw.Commons.ColorMarble;
import it.polimi.ingsw.Model.ResourceType;
import it.polimi.ingsw.Utils.MarketChoice;

import java.util.*;

public class MarketTray {
    private Marble[][] availableMarbles = new Marble[3][4];
    private Marble slidingMarble ;

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

    public boolean checkRequestedMarbles(List<Marble> marblesList,MarketChoice marketChoice, int index)
    {
        List<Marble> expectedMarbles = peekMarbles(marketChoice, index);
        return expectedMarbles.containsAll(marblesList) && marblesList.containsAll(expectedMarbles);
    }

    public List<Marble> takeMarbles(MarketChoice marketChoice, int index) {
            List<Marble> marbles = peekMarbles(marketChoice,index);
            slideMarbles(marketChoice, index);
            return marbles;
    }

    public void clearWhiteMarbleEffect() {
        for (int i = 0; i < availableMarbles.length; i++) {
            for(int j = 0; j < availableMarbles[i].length; j++) {
                if(availableMarbles[i][j].getColor() == ColorMarble.WHITE) {
                    WhiteMarble whiteMarble = (WhiteMarble)availableMarbles[i][j];
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
}
