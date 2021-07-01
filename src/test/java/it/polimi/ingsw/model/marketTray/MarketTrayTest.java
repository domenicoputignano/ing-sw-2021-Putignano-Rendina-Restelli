package it.polimi.ingsw.model.marketTray;

import it.polimi.ingsw.commons.ColorMarble;
import it.polimi.ingsw.utils.MarketChoice;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class that tests the methods of the MarketTray class
 */
public class MarketTrayTest {

    MarketTray marketTray = new MarketTray();

    /**
     * Test method that checks that the attributes of the MarketTray class are not null
     */
    @Test
    void attributesNotNull()
    {
        assertNotNull(marketTray.getAvailableMarbles());
        assertNotNull(marketTray.getSlidingMarble());
    }

    /**
     * Test method that checks that the number of white marbles is correct
     */
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

    /**
     * Test method that checks that the number of red marbles is correct
     */
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
    /**
     * Test method that checks that the number of grey marbles is correct
     */
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
    /**
     * Test method that checks that the number of purple marbles is correct
     */
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
    /**
     * Test method that checks that the number of yellow marbles is correct
     */
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
    /**
     * Test method that checks that the number of blue marbles is correct
     */
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

    /**
     * Test method that checks the selection of the row from the MarketTray
     */
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

    /**
     * Test method that checks the selection of the column from the MarketTray
     */
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
    /**
     * Test method that checks the correct return of the selected resources
     * on the specific row of the MarketTray
     */
    @Test
    void checkReturnValueRow()
    {
        Marble a[] = marketTray.getAvailableMarbles()[1].clone();
        assertArrayEquals(a,marketTray.takeMarbles(MarketChoice.ROW,1).toArray());
    }
    /**
     * Test method that checks the correct return of the selected resources
     * on the specific column of the MarketTray
     */
    @Test
    void checkReturnValueColumn()
    {
        Marble a[] = new Marble[3];
        a[0] = marketTray.getAvailableMarbles()[0][2];
        a[1] = marketTray.getAvailableMarbles()[1][2];
        a[2] = marketTray.getAvailableMarbles()[2][2];
        assertArrayEquals(a,marketTray.takeMarbles(MarketChoice.COLUMN,2).toArray());
    }

    /**
     * Test method that checks the correct sliding of the MarketTray rows
     */
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

    /**
     * Test method that checks the correct sliding of the MarketTray columns
     */
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