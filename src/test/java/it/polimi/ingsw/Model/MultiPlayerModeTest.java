package it.polimi.ingsw.Model;

import it.polimi.ingsw.Commons.*;
import it.polimi.ingsw.Exceptions.DepotOutOfBoundsException;
import it.polimi.ingsw.Exceptions.IncompatibleResourceTypeException;
import org.junit.jupiter.api.Test;

import java.util.*;

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
    @Test
    void concludeGame() throws DepotOutOfBoundsException, IncompatibleResourceTypeException {
        playerList.add(new Player("Piero"));
        playerList.add(new Player("Domenico"));
        playerList.add(new Player("Andrea"));
        playerList.add(new Player("Mario"));
        MultiPlayerMode multiPlayerMode = new MultiPlayerMode(playerList.get(0),playerList,playerList.get(0),playerList.size());
        multiPlayerMode.setup();
        Map<ResourceType,Integer> startingresource = new EnumMap<ResourceType, Integer>(ResourceType.class);

        for(int i=0;i<4;i++) {
            playerList.get(i).getPersonalBoard().getWarehouse().addResourcesToDepot(3, ResourceType.shield, 3);
            playerList.get(i).getPersonalBoard().getWarehouse().addResourcesToDepot(2, ResourceType.coin, 2);
            playerList.get(i).getPersonalBoard().getWarehouse().addResourcesToDepot(1, ResourceType.stone, 1);
            startingresource.put(ResourceType.servant, 2);
            startingresource.put(ResourceType.coin, 3);
            startingresource.put(ResourceType.stone, 4);
            startingresource.put(ResourceType.shield, 1);
            playerList.get(i).getPersonalBoard().getWarehouse().addResourcesToStrongbox(startingresource);
        }
        playerList.get(0).getLeaderCards().get(0).setIsActive();
        playerList.get(1).getLeaderCards().get(0).setIsActive();
        playerList.get(1).getLeaderCards().get(1).setIsActive();
        playerList.get(2).getLeaderCards().get(0).setIsActive();
        playerList.get(2).getLeaderCards().get(1).setIsActive();
        playerList.get(3).getLeaderCards().get(0).setIsActive();

        playerList.get(0).getPersonalBoard().getFaithTrack().moveMarker(8);
        playerList.get(3).getPersonalBoard().getFaithTrack().moveMarker(11);
        playerList.get(1).getPersonalBoard().getFaithTrack().moveMarker(5);
        playerList.get(2).getPersonalBoard().getFaithTrack().moveMarker(15);



        Map<ResourceType, Integer> cost = new EnumMap<ResourceType, Integer>(ResourceType.class);
        cost.put(ResourceType.servant,0);
        cost.put(ResourceType.coin,0);
        cost.put(ResourceType.stone,0);
        cost.put(ResourceType.shield,0);
        playerList.get(0).getPersonalBoard().putCardOnTop(new DevelopmentCard(cost,1, ColorCard.blue,3,null),1);
        playerList.get(0).getPersonalBoard().putCardOnTop(new DevelopmentCard(cost,2,ColorCard.yellow,5,null),1);
        playerList.get(0).getPersonalBoard().putCardOnTop(new DevelopmentCard(cost,1,ColorCard.purple,2,null),2);
        playerList.get(0).getPersonalBoard().putCardOnTop(new DevelopmentCard(cost,1,ColorCard.green,3,null),3);

        playerList.get(1).getPersonalBoard().putCardOnTop(new DevelopmentCard(cost,1, ColorCard.blue,2,null),1);
        playerList.get(1).getPersonalBoard().putCardOnTop(new DevelopmentCard(cost,2,ColorCard.yellow,4,null),1);

        playerList.get(2).getPersonalBoard().putCardOnTop(new DevelopmentCard(cost,1, ColorCard.blue,1,null),1);
        playerList.get(2).getPersonalBoard().putCardOnTop(new DevelopmentCard(cost,2,ColorCard.yellow,2,null),1);
        playerList.get(2).getPersonalBoard().putCardOnTop(new DevelopmentCard(cost,1, ColorCard.blue,4,null),2);

        playerList.get(3).getPersonalBoard().putCardOnTop(new DevelopmentCard(cost,1, ColorCard.blue,3,null),1);

        int victorypoints1 = playerList.get(0).calcVictoryPointsPlayer();
        int victorypoints2 = playerList.get(1).calcVictoryPointsPlayer();
        int victorypoints3 = playerList.get(2).calcVictoryPointsPlayer();
        int victorypoints4 = playerList.get(3).calcVictoryPointsPlayer();

        System.out.println("1) "+victorypoints1);
        System.out.println("2) "+victorypoints2);
        System.out.println("3) "+victorypoints3);
        System.out.println("4) "+victorypoints4);

        multiPlayerMode.concludeGame();
       }
}