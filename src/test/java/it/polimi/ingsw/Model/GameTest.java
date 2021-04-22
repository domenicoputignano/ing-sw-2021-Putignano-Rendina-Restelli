package it.polimi.ingsw.Model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {


    List<Player> playerList = new ArrayList<>();


    public List<Player> getPlayerList() {
        return playerList;
    }


    @Test
    void setup() {
        playerList.add(new Player("Piero"));
        playerList.add(new Player("Domenico"));
        playerList.add(new Player("Andrea"));
        Game game = new Game(playerList.get(0),playerList,playerList.get(0),playerList.size());
        game.setup();
        assertNotNull(game.getDecks());
        assertNotNull(game.getCurrPlayer());
        assertNotNull(game.getMarketTray());
        for(Player p:playerList) {
            assertNotNull(game.getPlayerList().get(getPlayerList().indexOf(p)).getPersonalBoard());
            assertNotNull(game.getPlayerList().get(getPlayerList().indexOf(p)).getLeaderCards());
            assertEquals(getPlayerList().indexOf(p)+1, game.getPlayerList().get(getPlayerList().indexOf(p)).getPosition());
        }
    }

    @Test
    void activateVaticanReport() {
        //TODO: MODIFICARE SETTER
        playerList.add(new Player("Piero"));
        playerList.add(new Player("Domenico"));
        playerList.add(new Player("Andrea"));
        Game game = new Game(playerList.get(0),playerList,playerList.get(0),playerList.size());
        game.setup();
        assertEquals(StateFavorTiles.FACEDOWN,game.getCurrPlayer().getPersonalBoard().getFaithTrack().getSections()[0].getState());
        assertEquals(StateFavorTiles.FACEDOWN,game.getPlayerList().get(1).getPersonalBoard().getFaithTrack().getSections()[0].getState());
        assertEquals(StateFavorTiles.FACEDOWN, game.getPlayerList().get(2).getPersonalBoard().getFaithTrack().getSections()[0].getState());
        game.getCurrPlayer().getPersonalBoard().getFaithTrack().moveMarker(8);
        assertEquals(StateFavorTiles.FACEUP,game.getCurrPlayer().getPersonalBoard().getFaithTrack().getSections()[0].getState());
        assertEquals(StateFavorTiles.DISCARDED,game.getPlayerList().get(1).getPersonalBoard().getFaithTrack().getSections()[0].getState());
        assertEquals(StateFavorTiles.DISCARDED, game.getPlayerList().get(2).getPersonalBoard().getFaithTrack().getSections()[0].getState());
    }

}