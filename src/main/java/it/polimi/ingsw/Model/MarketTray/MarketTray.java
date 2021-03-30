package it.polimi.ingsw.Model.MarketTray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MarketTray {
    private Marble[][] availableMarbles = new Marble[3][4];
    private Marble slidingMarble ;



    private void slideMarbles(String choice, int index) {
        if(choice.equals("row")) {
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


    public List<Marble> takeMarbles(String choice, int index) {
        if (choice.equals("row")) {
            List<Marble> marbles = new ArrayList<Marble>(4);
            marbles.addAll(Arrays.asList(availableMarbles[index]));
            slideMarbles(choice, index);
            return marbles;
        } else {
            List<Marble> marbles = new ArrayList<Marble>(3);
            for (int i = 0; i < availableMarbles.length; i++) {
                marbles.add(availableMarbles[i][index]);
            }
            slideMarbles(choice, index);
            return marbles;
        }
    }

}
