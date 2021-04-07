package it.polimi.ingsw.Model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class FaithTrackTest {

    PersonalBoard personalBoard = new PersonalBoard();
    FaithTrack faithTrack = personalBoard.getFaithTrack();

    @Test
    void initialization(){
        assertEquals(0, faithTrack.getFaithMarker());
        assertEquals(0 , faithTrack.getPassedSection());
        assertEquals(25, faithTrack.getVictoryPoints().length);

    }

    @Test
    void moveMarker() {
    }

    @Test
    void calcVictoryPoints() {
    }

    @Test
    void activeVaticanReport() {
    }
}