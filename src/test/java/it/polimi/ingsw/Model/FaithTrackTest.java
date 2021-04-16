package it.polimi.ingsw.Model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class FaithTrackTest {

    Player owner = new Player("Proprietario");
    PersonalBoard personalBoard = new PersonalBoard(owner);
    FaithTrack faithTrack = personalBoard.getFaithTrack();


    @Test
    void initialization(){
        assertEquals(0, faithTrack.getFaithMarker());
        assertEquals(0 , faithTrack.getPassedSection());
        assertEquals(25, faithTrack.getVictoryPoints().length);

    }

    @Test
    void moveMarker() {
        int start = faithTrack.getFaithMarker();
        faithTrack.moveMarker(23);
        assertEquals(start + 23,faithTrack.getFaithMarker());
        assertEquals(2, faithTrack.getPassedSection());
    }

    @Test
    void calcVictoryPoints() {
        faithTrack.moveMarker(22);
        assertEquals(16, faithTrack.calcVictoryPoints());
    }

    @Test
    void activeVaticanReport() {

    }
}