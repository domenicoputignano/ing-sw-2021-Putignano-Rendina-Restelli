package it.polimi.ingsw.Model;

import it.polimi.ingsw.Commons.StateFavorTiles;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MultiPlayerModeTest {


    List<Player> playerList = new ArrayList<>();


    public List<Player> getPlayerList() {
        return playerList;
    }


    @Test
    void setup() {
        playerList.add(new Player("Piero"));
        playerList.add(new Player("Domenico"));
        playerList.add(new Player("Andrea"));
        MultiPlayerMode multiPlayerMode = new MultiPlayerMode(playerList.get(0),playerList,playerList.get(0),playerList.size());
        multiPlayerMode.setup();
        assertNotNull(multiPlayerMode.getDecks());
        assertNotNull(multiPlayerMode.getCurrPlayer());
        assertNotNull(multiPlayerMode.getMarketTray());
        for(Player p:playerList) {
            assertNotNull(multiPlayerMode.getPlayerList().get(getPlayerList().indexOf(p)).getPersonalBoard());
            assertNotNull(multiPlayerMode.getPlayerList().get(getPlayerList().indexOf(p)).getLeaderCards());
            assertEquals(getPlayerList().indexOf(p)+1, multiPlayerMode.getPlayerList().get(getPlayerList().indexOf(p)).getPosition());
        }
    }

    @Test
    void activateVaticanReport() {
        //TODO: MODIFICARE SETTER
        playerList.add(new Player("Piero"));
        playerList.add(new Player("Domenico"));
        playerList.add(new Player("Andrea"));
        MultiPlayerMode multiPlayerMode = new MultiPlayerMode(playerList.get(0),playerList,playerList.get(0),playerList.size());
        multiPlayerMode.setup();
        assertEquals(StateFavorTiles.FACEDOWN, multiPlayerMode.getCurrPlayer().getPersonalBoard().getFaithTrack().getSections()[0].getState());
        assertEquals(StateFavorTiles.FACEDOWN, multiPlayerMode.getPlayerList().get(1).getPersonalBoard().getFaithTrack().getSections()[0].getState());
        assertEquals(StateFavorTiles.FACEDOWN, multiPlayerMode.getPlayerList().get(2).getPersonalBoard().getFaithTrack().getSections()[0].getState());
        multiPlayerMode.getCurrPlayer().getPersonalBoard().getFaithTrack().moveMarker(8);
        assertEquals(StateFavorTiles.FACEUP, multiPlayerMode.getCurrPlayer().getPersonalBoard().getFaithTrack().getSections()[0].getState());
        assertEquals(StateFavorTiles.DISCARDED, multiPlayerMode.getPlayerList().get(1).getPersonalBoard().getFaithTrack().getSections()[0].getState());
        assertEquals(StateFavorTiles.DISCARDED, multiPlayerMode.getPlayerList().get(2).getPersonalBoard().getFaithTrack().getSections()[0].getState());
    }

    @Test
    void nextPlayer()
    {
        playerList.add(new Player("Piero"));
        playerList.add(new Player("Domenico"));
        playerList.add(new Player("Andrea"));
        playerList.add(new Player("Pippo"));
        MultiPlayerMode multiPlayerMode = new MultiPlayerMode(playerList.get(0),playerList,playerList.get(0),playerList.size());
        multiPlayerMode.setup();
        playerList.get(1).getUser().setActive(false);
        playerList.get(2).getUser().setActive(false);
        System.out.println(multiPlayerMode.nextPlayer(multiPlayerMode.getCurrPlayer()));
    }
}