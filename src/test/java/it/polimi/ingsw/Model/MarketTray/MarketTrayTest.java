package it.polimi.ingsw.Model.MarketTray;

import it.polimi.ingsw.Commons.ColorMarble;
import it.polimi.ingsw.Utils.MarketChoice;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MarketTrayTest {

    MarketTray marketTray = new MarketTray();
    @Test
    void attributesNotNull()
    {
        assertNotNull(marketTray.getAvailableMarbles());
        assertNotNull(marketTray.getSlidingMarble());
    }
    @Test
    void whiteMarblesNumber()
    {
        int count=0;
        for(int i=0;i<3;i++)
            for(int j=0;j<4;j++)
            {
                if(marketTray.getAvailableMarbles()[i][j].getColor()== ColorMarble.WHITE)
                    count++;
            }
        if(marketTray.getSlidingMarble().getColor()== ColorMarble.WHITE)
            count++;
        assertEquals(4,count);
    }
    @Test
    void redMarblesNumber()
    {
        int count=0;
        for(int i=0;i<3;i++)
            for(int j=0;j<4;j++)
            {
                if(marketTray.getAvailableMarbles()[i][j].getColor()== ColorMarble.RED)
                    count++;
            }
        if(marketTray.getSlidingMarble().getColor()== ColorMarble.RED)
            count++;
        assertEquals(1,count);
    }
    @Test
    void greyMarblesNumber()
    {
        int count=0;
        for(int i=0;i<3;i++)
            for(int j=0;j<4;j++)
            {
                if(marketTray.getAvailableMarbles()[i][j].getColor()== ColorMarble.GREY)
                    count++;
            }
        if(marketTray.getSlidingMarble().getColor()== ColorMarble.GREY)
            count++;
        assertEquals(2,count);
    }
    @Test
    void purpleMarblesNumber()
    {
        int count=0;
        for(int i=0;i<3;i++)
            for(int j=0;j<4;j++)
            {
                if(marketTray.getAvailableMarbles()[i][j].getColor()== ColorMarble.PURPLE)
                    count++;
            }
        if(marketTray.getSlidingMarble().getColor()== ColorMarble.PURPLE)
            count++;
        assertEquals(2,count);
    }
    @Test
    void yellowMarblesNumber()
    {
        int count=0;
        for(int i=0;i<3;i++)
            for(int j=0;j<4;j++)
            {
                if(marketTray.getAvailableMarbles()[i][j].getColor()== ColorMarble.YELLOW)
                    count++;
            }
        if(marketTray.getSlidingMarble().getColor()== ColorMarble.YELLOW)
            count++;
        assertEquals(2,count);
    }
    @Test
    void blueMarblesNumber()
    {
        int count=0;
        for(int i=0;i<3;i++)
            for(int j=0;j<4;j++)
            {
                if(marketTray.getAvailableMarbles()[i][j].getColor()== ColorMarble.BLUE)
                    count++;
            }
        if(marketTray.getSlidingMarble().getColor()== ColorMarble.BLUE)
            count++;
        assertEquals(2,count);
    }
    @Test
    void takeMarblesRow() {
        marketTray.takeMarbles(MarketChoice.ROW,1);
        blueMarblesNumber();
        whiteMarblesNumber();
        purpleMarblesNumber();
        yellowMarblesNumber();
        redMarblesNumber();
        greyMarblesNumber();
        attributesNotNull();
    }
    @Test
    void takeMarblesColumn() {
        marketTray.takeMarbles(MarketChoice.COLUMN,2);
        blueMarblesNumber();
        whiteMarblesNumber();
        purpleMarblesNumber();
        yellowMarblesNumber();
        redMarblesNumber();
        greyMarblesNumber();
        attributesNotNull();
    }
    @Test
    void checkReturnValueRow()
    {
        Marble a[] = marketTray.getAvailableMarbles()[1].clone();
        assertArrayEquals(a,marketTray.takeMarbles(MarketChoice.ROW,1).toArray());
    }
    @Test
    void checkReturnValueColumn()
    {
        Marble a[] = new Marble[3];
        a[0] = marketTray.getAvailableMarbles()[0][2];
        a[1] = marketTray.getAvailableMarbles()[1][2];
        a[2] = marketTray.getAvailableMarbles()[2][2];
        assertArrayEquals(a,marketTray.takeMarbles(MarketChoice.COLUMN,2).toArray());
    }
    @Test
    void checkSlidingRow()
    {
        Marble sliding = marketTray.getSlidingMarble();
        Marble a[] = marketTray.getAvailableMarbles()[1].clone();
        marketTray.takeMarbles(MarketChoice.ROW,1);
        assertEquals(marketTray.getAvailableMarbles()[1][3],sliding);
        assertEquals(a[0],marketTray.getSlidingMarble());
        assertEquals(marketTray.getAvailableMarbles()[1][0],a[1]);
        assertEquals(marketTray.getAvailableMarbles()[1][1],a[2]);
        assertEquals(marketTray.getAvailableMarbles()[1][2],a[3]);
    }
    @Test
    void checkSlidingColumn()
    {
        Marble sliding = marketTray.getSlidingMarble();
        Marble a[] = new Marble[3];
        a[0] = marketTray.getAvailableMarbles()[0][2];
        a[1] = marketTray.getAvailableMarbles()[1][2];
        a[2] = marketTray.getAvailableMarbles()[2][2];
        marketTray.takeMarbles(MarketChoice.COLUMN,2);
        assertEquals(marketTray.getAvailableMarbles()[2][2],sliding);
        assertEquals(a[0],marketTray.getSlidingMarble());
        assertEquals(marketTray.getAvailableMarbles()[0][2],a[1]);
        assertEquals(marketTray.getAvailableMarbles()[1][2],a[2]);
    }
}