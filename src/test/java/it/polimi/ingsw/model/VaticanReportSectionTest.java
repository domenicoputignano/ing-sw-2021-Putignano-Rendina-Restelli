package it.polimi.ingsw.model;

import it.polimi.ingsw.commons.StateFavorTiles;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VaticanReportSectionTest {

    /**
     * Method to test the correct initialization of the related class.
     */
    @Test
    void initializationTest(){
        VaticanReportSection vaticanReportSection = new VaticanReportSection(3, 6, 2);
        assertEquals(3, vaticanReportSection.getStartSpace());
        assertEquals(6, vaticanReportSection.getPopeSpace());
        assertEquals(0, vaticanReportSection.getVictoryPoints());
        vaticanReportSection.setValidFavorTiles(StateFavorTiles.FACEUP);
        assertEquals(2, vaticanReportSection.getVictoryPoints());
    }

}